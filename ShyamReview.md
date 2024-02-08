## Code Review
### BookingResponse

- optimize Imports

### UserResponse
- password given in response
- 
### Jwt
- Providing the secret key directly in JwtService class, is not a good practice

### Booking
- So many getter and setter methods in  model class , Already there is annotations to reduce boilerplate code.

## General Comments
- avoid @Setter
- Some Exception Tests are pending.
- remove commented codes.