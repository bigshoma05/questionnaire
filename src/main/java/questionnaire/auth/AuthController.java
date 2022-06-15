package questionnaire.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import questionnaire.config.jwt.JwtProvider;
import questionnaire.user.CreateUserRequest;
import questionnaire.user.User;
import questionnaire.user.UserService;

@RestController
public class AuthController {
  @Autowired private UserService userService;
  @Autowired private JwtProvider jwtProvider;

  @PostMapping("/register")
  public String registerUser(@RequestBody CreateUserRequest request) {
    User u = new User();
    u.setPassword(request.getPassword());
    u.setUsername(request.getUsername());
    u.setEmail(request.getEmail());
    u.setRole(request.getRole());
    userService.createUser(u);
    return "User '" + u.getUsername() + "' is created";
  }

  @PostMapping("/auth")
  public AuthResponse auth(@RequestBody AuthRequest request) {
    User user = userService.findByLoginAndPassword(request.getUsername(), request.getPassword());
    String token = jwtProvider.generateToken(user.getUsername());
    return new AuthResponse(token);
  }
}
