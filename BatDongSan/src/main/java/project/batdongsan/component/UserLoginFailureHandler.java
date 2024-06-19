package project.batdongsan.component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import project.batdongsan.model.entity.User;
import project.batdongsan.service.UserService;
import java.io.IOException;
import static project.batdongsan.service.UserService.MAX_FAILED_ATTEMPTS;

@Component
public class UserLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private final UserService userService;

    public UserLoginFailureHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String email = request.getParameter("username");
        User user = userService.getByEmail(email);
        if (user != null) {
            if (user.isEnabled() && user.isAccountNonLocked()) {
                if (user.getFailedAttempt() < MAX_FAILED_ATTEMPTS - 1) {
                    userService.increaseFailedAttempt(user);
                } else {
                    userService.lockUser(user);
                    System.out.println("Account Lock 24h");
                }
            } else if (!user.isAccountNonLocked()) {
                if (userService.unlockWhenTimeExpired(user)){
                    System.out.println("Account Unlock, You can login");
                }

            }
        }

//        super.setDefaultFailureUrl("/login?error=true");
        super.onAuthenticationFailure(request, response, exception);
    }
}
