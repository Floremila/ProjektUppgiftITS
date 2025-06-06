Att göra:

[DONE] Fler metoder i userController: delete och update
[DONE] Lägg till motsvarande metoder med logik i UserService

[DOING] Skapa en SecurityConfig
[x] Skapat `SecurityConfig`-klass
[x] Lagt till `@Bean` för `PasswordEncoder` (`BCryptPasswordEncoder`)
[x] Lagt till `AuthenticationManager`-bean
[x] Påbörjat `SecurityFilterChain` med CSRF avstängt och stateless session
[ ] Lägg till `.authorizeHttpRequests()` med rätt regler: - **ATT GÖRA**
  - `/auth/login` → permitAll
  - `/user` → tillåts för ADMIN och USER
  - `/admin` → endast för ADMIN
[ ] Lägg till jwtAuthenticationConverter och aktivera JWT-stöd med .oauth2ResourceServer(...) - **ATT GÖRA**

    
[DONE] UserNotFoundException och lägg till ExceptionHandler

[DOING] JWT – skapa och validera token
[x] JwtUserDetailsService – laddar användare från databasen 
[x] TokenService – generera JWT-token från Authentication 
[x] LoginRequest – Java record med username och password 
[x] Lägg till JwtEncoder och JwtDecoder som @Bean i SecurityConfig 
[] AuthController – endpoint /request-token för inloggning  -  **ATT GÖRA**
[] Lägg till jwtAuthenticationConverter och koppla scope till roller -  **ATT GÖRA**

[] Logging component - logga registrering och borttagning i service-klassen
[] Logback.xml


[] Tester med MockMmc för:
    - Inloggning
    - Registrering
    - Borttagning

