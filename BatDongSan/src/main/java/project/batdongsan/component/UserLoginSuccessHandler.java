package project.batdongsan.component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import project.batdongsan.model.entity.User;
import project.batdongsan.model.entity.UserDetailsApp;
import project.batdongsan.service.UserService;

import java.io.IOException;
import java.util.Set;

@Component
public class UserLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
    private final UserService userService;

    public UserLoginSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetailsApp userDetailsApp = (UserDetailsApp) authentication.getPrincipal();
        User user = userDetailsApp.getUser();
        if (user.getFailedAttempt() > 0) {
            userService.resetFailedAttempt(user);
        }
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roles.contains("ROLE_USER")) {
            response.sendRedirect("/front/index");
        } else if (roles.contains("ROLE_ADMIN")) {
            response.sendRedirect("/admin/index");
        } else {
            response.sendRedirect("/login?error=true");
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }

}
