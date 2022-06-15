package questionnaire.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import questionnaire.user.User;
import questionnaire.user.UserService;

@Component
public class CustomUserDetailsService implements UserDetailsService {
  @Autowired private UserService userService;

  @Override
  public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userService.findByUsername(username);
    return CustomUserDetails.fromUserEntityToCustomUserDetails(user);
  }
}
