package project.batdongsan.component;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import project.batdongsan.model.entity.User;
import project.batdongsan.model.entity.UserDetailsApp;
import project.batdongsan.service.UserService;

import java.util.Optional;

@Component
public class UserDetailsServiceApp implements UserDetailsService {
    private final UserService userService;

    public UserDetailsServiceApp(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  userService.getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found in DB");
        }
        return UserDetailsApp.builder().user(user).build();
    }
}
