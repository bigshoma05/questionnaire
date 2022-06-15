package questionnaire.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
  @Autowired UserRepository userRepository;
  @Autowired private PasswordEncoder passwordEncoder;

  public UserDto getUserByUsername(String username) {
    User user = userRepository.findByUsername(username);
    return userToDto(user);
  }

  public List<UserDto> getUsers() {
    List<User> users = userRepository.findAll();
    return users.stream().map(this::userToDto).collect(Collectors.toList());
  }

  public void createUser(User request) {
    User user = new User();

    user.setUsername(request.getUsername());
    user.setRole(request.getRole());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setEmail(request.getEmail());

    userRepository.save(user);
  }

  public void deleteUser(String username) {
    userRepository.deleteByUsername(username);
  }

  public UserDto updateUser(UpdateUserRequest request) {
    User user = userRepository.findByUsername(request.getUsername());

    if (request.getUsername() != null) {
      user.setUsername(request.getUsername());
    }

    if (request.getPassword() != null) {
      user.setPassword(request.getPassword());
    }

    if (request.getEmail() != null) {
      user.setEmail(request.getEmail());
    }

    userRepository.save(user);
    return userToDto(user);
  }

  public User findByLoginAndPassword(String username, String password) {
    User user = findByUsername(username);
    if (user != null) {
      if (passwordEncoder.matches(password, user.getPassword())) {
        return user;
      }
    }
    return null;
  }

  public UserDto userToDto(User user) {
    UserDto userDto = new UserDto();
    userDto.setId(user.getId());
    userDto.setUsername(user.getUsername());
    userDto.setEmail(user.getEmail());
    userDto.setRole(user.getRole());
    return userDto;
  }

  public User findByUsername(String name) {
    return userRepository.findByUsername(name);
  }
}
