package com.example.taxiBooking.serviceTest;

import com.example.taxiBooking.contract.request.BookingRequest;
import com.example.taxiBooking.contract.response.BookingResponse;
import com.example.taxiBooking.contract.response.TaxiResponse;
import com.example.taxiBooking.exception.BookingNotFoundException;
import com.example.taxiBooking.exception.TaxiNotFoundException;
import com.example.taxiBooking.model.Booking;
import com.example.taxiBooking.model.Taxi;
import com.example.taxiBooking.model.User;
import com.example.taxiBooking.repository.BookingRepository;
import com.example.taxiBooking.repository.TaxiRepository;
import com.example.taxiBooking.repository.UserRepository;
import com.example.taxiBooking.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BookingServiceTest {

    private  ModelMapper modelMapper;
    private  BookingRepository bookingRepository;
    private  UserRepository userRepository;
    private  TaxiRepository taxiRepository;
    private BookingService bookingService;


    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
        userRepository = Mockito.mock(UserRepository.class);
        taxiRepository = Mockito.mock(TaxiRepository.class);
        bookingRepository = Mockito.mock(BookingRepository.class);
        modelMapper=new ModelMapper();
        bookingService=new BookingService(modelMapper,bookingRepository,userRepository,taxiRepository);
    }
    @Test
    void book_ValidRequest_SuccessfullyBooked() {

        BookingService bookingService = new BookingService(modelMapper,bookingRepository, userRepository, taxiRepository);

        BookingRequest request = new BookingRequest(null,null);
        request.setPickUpLocation("PickupLocation");
        request.setDropOffLocation("DropOffLocation");

        Long userId = 1L;
        Long taxiId = 1L;
        double distance = 10.0;

        User user = new User();
        Booking booking= new Booking();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Taxi taxi = new Taxi();
        taxi.setTaxiId(taxiId);
        when(taxiRepository.findById(taxiId)).thenReturn(Optional.of(taxi));

        BookingResponse response = bookingService.book(request, userId, taxiId, distance);

        assertNotNull(response);
        assertEquals("PickupLocation", response.getPickUpLocation());
        assertEquals("DropOffLocation", response.getDropOffLocation());
        assertFalse(booking.getBookingStatus());
        assertFalse(booking.getRideStatus());

        ArgumentCaptor<Booking> captor = ArgumentCaptor.forClass(Booking.class);
        verify(bookingRepository).save(captor.capture());
        Booking savedBooking = captor.getValue();
        assertNotNull(savedBooking);
        assertEquals(user, savedBooking.getUser());
        assertEquals(taxi, savedBooking.getTaxi());
        assertEquals(100.00 + ((distance - 5) * 20.00), savedBooking.getFare());
        assertNotNull(savedBooking.getBookingTime());
        assertEquals("PickupLocation", savedBooking.getPickUpLocation());
        assertEquals("DropOffLocation", savedBooking.getDropOffLocation());

    }
    @Test
    void testBookWhenTaxiNotFound() {

        Long userId = 1L;
        Long taxiId=1L;
        BookingRequest bookingRequest = new BookingRequest(null,null);
        when(taxiRepository.findById(anyLong())).thenReturn(Optional.empty());

        TaxiNotFoundException exception = assertThrows(TaxiNotFoundException.class,
                () -> bookingService.book(bookingRequest,userId,taxiId,10d));
        verify(taxiRepository, times(1)).findById(taxiId);

        verify(taxiRepository, never()).save(any());

        assertEquals(" not available near pickup location", exception.getMessage());
    }


    @Test
    void testGetById(){
        Booking sampleBooking= new Booking(1L,null,null,null,null,10d,null,true,true);
        Long id=1L;
        when (bookingRepository.findById(id)).thenReturn(Optional.of(sampleBooking));
        Booking retrievedEmployee= bookingService.getById(id);
        assertEquals(sampleBooking,retrievedEmployee);
    }
    @Test
    void testGetByIdWhenBookingNotFound() {

        Long bookingId = 1L;
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.empty());

        BookingNotFoundException exception = assertThrows(BookingNotFoundException.class,
                () -> bookingService.getById(bookingId));

        verify(bookingRepository, times(1)).findById(bookingId);

        verify(bookingRepository, never()).save(any());

        assertEquals("booking not found", exception.getMessage());
    }

    @Test
    public void testAvailableTaxi() {

        TaxiRepository taxiRepository = Mockito.mock(TaxiRepository.class);
        List<Taxi> allTaxis = new ArrayList<>();
        allTaxis.add(new Taxi(1L,null,null,"Location1",null));
        allTaxis.add(new Taxi(1L,null,null,"Location1",null));
        Mockito.when(taxiRepository.findAll()).thenReturn(allTaxis);

        ModelMapper modelMapper = Mockito.mock(ModelMapper.class);
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.eq(TaxiResponse.class))).thenReturn(new TaxiResponse());


        BookingService bookingService = new BookingService( modelMapper,bookingRepository,userRepository,taxiRepository);

        List<TaxiResponse> availableTaxis = bookingService.availableTaxi("Location1");
        assertEquals(2, availableTaxis.size());

        assertThrows(TaxiNotFoundException.class, () -> bookingService.availableTaxi("Location3"));
    }

    @Test
    public void testCancelBooking() {
        Long bookingId = 1L;

        Booking booking = new Booking();
        booking.setBookingStatus(true);

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

        bookingService.cancelBooking(bookingId);

        verify(bookingRepository).save(booking);

    }
    @Test
    void testCancelBookingWhenBookingNotFound() {

        Long bookingId = 1L;
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.empty());

        BookingNotFoundException exception = assertThrows(BookingNotFoundException.class,
                () -> bookingService.cancelBooking(bookingId));

        verify(bookingRepository, times(1)).findById(bookingId);

        verify(bookingRepository, never()).save(any());

        assertEquals("booking not found", exception.getMessage());
    }

}
