Att göra:

[DONE] Fler metoder i userController: delete och update
[DONE] Lägg till motsvarande metoder med logik i UserService
[DONE] Login metod i authController

[DOING] Skapa en SecurityConfig
[x] Skapat `SecurityConfig`-klass
[x] Lagt till `@Bean` för `PasswordEncoder` (`BCryptPasswordEncoder`)
[x] Lagt till `AuthenticationManager`-bean
[x] Påbörjat `SecurityFilterChain` med CSRF avstängt och stateless session
[x] Lägg till `.authorizeHttpRequests()` med rätt regler:
  - `/auth/login` → permitAll
  - `/appUser` → tillåts för ADMIN och USER
  - `/admin` → endast för ADMIN

    (jag använde /appUsers/register och appUsers/login -> permitAll i secutityFilterChain, och sedan @PreAuthorize på
    metoderna i UserController för att specificera vilken behörighet som får göra vilken metod)

[x] Lägg till jwtAuthenticationConverter och aktivera JWT-stöd med .oauth2ResourceServer(...) 
[x] Lägg till `.requestMatchers` för swagger

    
[DONE] UserNotFoundException och lägg till ExceptionHandler

[DOING] JWT – skapa och validera token
[x] JwtUserDetailsService – laddar användare från databasen 
[x] TokenService – generera JWT-token från Authentication 
[x] LoginRequest – Java record med username och password 
[x] Lägg till JwtEncoder och JwtDecoder som @Bean i SecurityConfig 
[x] AuthController – endpoint /request-token för inloggning 
[x] Lägg till jwtAuthenticationConverter och koppla scope till roller 

[x] Logging component - logga registrering och borttagning i service-klassen (update method ska ha Log?)
[x] Logback.xml


[DONE] Tester med MockMmc för:
    - Inloggning
    - Registrering
    - Borttagning

[x] Felmeddelande när man som user försöker göra åtgärder utanför ens behörighet

