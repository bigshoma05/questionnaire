package questionnaire.user;

import lombok.Data;

@Data
public class CreateUserRequest {
  private String username;
  private String password;
  private String email;
  private Role role;
}
