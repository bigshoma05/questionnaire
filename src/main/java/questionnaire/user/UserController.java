package questionnaire.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @GetMapping("/hello")
  public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
    return String.format("Hello %s!", name);
  }

  @GetMapping
  public UserDto getUserByUsername(@RequestParam String username) {
    return userService.getUserByUsername(username);
  }

  @GetMapping("/all")
  public List<UserDto> getUsers() {
    return userService.getUsers();
  }

  @PutMapping
  public UserDto updateUser(@RequestBody UpdateUserRequest request) {
    return userService.updateUser(request);
  }

  @DeleteMapping
  public String deleteUser(@RequestParam String username) {
    userService.deleteUser(username);
    return "The user '" + username + "' was deleted";
  }

  @PostMapping
  public void createUser(@RequestBody User request) {
    userService.createUser(request);
  }
}
