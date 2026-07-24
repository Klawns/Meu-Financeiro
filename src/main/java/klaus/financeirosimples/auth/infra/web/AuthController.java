package klaus.financeirosimples.auth.infra.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import klaus.financeirosimples.auth.application.commands.SigninCommand;
import klaus.financeirosimples.auth.application.commands.SignupCommand;
import klaus.financeirosimples.auth.application.usecases.AuthenticatedUser;
import klaus.financeirosimples.auth.application.usecases.SigninUseCase;
import klaus.financeirosimples.auth.application.usecases.SignupUseCase;
import klaus.financeirosimples.auth.infra.web.dto.request.SigninRequest;
import klaus.financeirosimples.auth.infra.web.dto.request.SignupRequest;
import klaus.financeirosimples.auth.infra.web.dto.response.UserResponse;
import klaus.financeirosimples.user.application.output.UserOutput;
import klaus.financeirosimples.user.application.usecases.FindUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "User authentication operations.")
public class AuthController {
    private final SigninUseCase signin;
    private final SignupUseCase signup;
    private final FindUserUseCase findUser;
    private final AuthenticatedUser authenticatedUser;


    @Value("${jwt.cookie.secure}")
    private boolean secureCookie;

    @Value("${security.expiration-seconds}")
    private long expiration;


    @PostMapping("/signup")
    @Operation(summary = "Signup an user.", security = {})
    public ResponseEntity<?> signup(@RequestBody SignupRequest req) {
        SignupCommand command = new SignupCommand(
                req.username(),
                req.email(),
                req.password()
        );
        signup.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/signin")
    @Operation(summary = "Authenticate a user.", security = {})
    public ResponseEntity<Void> signin(@RequestBody SigninRequest req) {
        SigninCommand command = new SigninCommand(
                req.email(),
                req.password()
        );

        String accessToken = signin.execute(command);
        ResponseCookie jwtCookie = ResponseCookie.from("access-token", accessToken)
                .httpOnly(true)
                .secure(secureCookie)
                .path("/")
                .maxAge(expiration)
                .sameSite("Strict")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .build();
    }

    @GetMapping("/me")
    @Operation(summary = "Get the authenticated user")
    public UserResponse me() {
        UUID userId = authenticatedUser.id();

        UserOutput output = findUser.findById(userId);

        return new UserResponse(userId, output.username(), output.email());
    }
}
