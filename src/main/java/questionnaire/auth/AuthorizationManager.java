package questionnaire.auth;

import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import questionnaire.config.CustomUserDetails;
import questionnaire.user.Role;

import java.util.Objects;
import java.util.Optional;

@Component
public class AuthorizationManager {

  @Nullable
  public Authentication getAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }

  public boolean isAuthenticated() {
    return getInstanceOptional().isPresent();
  }

  public boolean hasAuthority(Role authority) {
    return getInstanceOptional()
        .map(CustomUserDetails::getAuthorities)
        .map(
            authorities ->
                authorities.stream()
                    .map(GrantedAuthority::getAuthority)
                    .filter(Objects::nonNull)
                    .anyMatch(a -> a.equals(authority.name())))
        .orElse(false);
  }

  public Optional<CustomUserDetails> getInstanceOptional() {
    return Optional.ofNullable(getAuthentication())
        .map(
            authentication -> {
              Object principal = authentication.getPrincipal();
              if (principal == null) {
                throw new RuntimeException("User unauthenticated");
              }
              if (principal instanceof CustomUserDetails) {
                return (CustomUserDetails) principal;
              } else {
                CustomUserDetails customUserDetails = new CustomUserDetails();
                customUserDetails.setGrantedAuthorities(authentication.getAuthorities());
                return customUserDetails;
              }
            });
  }

  public CustomUserDetails getInstance() {
    return getInstanceOptional().orElseThrow(() -> new RuntimeException("User unauthenticated"));
  }

  public boolean isAdmin() {
    return hasAuthority(Role.ADMIN);
  }

  public boolean isUser() {
    return hasAuthority(Role.USER);
  }

  public boolean isAuthor() {
    return hasAuthority(Role.AUTHOR);
  }
}
