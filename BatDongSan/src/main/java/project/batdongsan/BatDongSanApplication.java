package project.batdongsan;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.batdongsan.model.entity.Role;
import project.batdongsan.model.entity.User;
import project.batdongsan.repositoty.UserRepository;

import java.util.Properties;
import java.util.Set;

@SpringBootApplication
public class BatDongSanApplication {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(BatDongSanApplication.class, args);
    }

    @Bean
    public CommandLineRunner init() {
        System.out.println("acđgagaga");
        return args -> {
            User user = User.builder().email("ngocbanguyen2k@gmail.com").username("ngocbanguyen2k@gmail.com").password(passwordEncoder.encode("user")).accountNonLocked(true).enabled(true).roles(Set.of(new Role(null,"ROLE_USER"))).build();
            User admin = User.builder().email("admin@gmail.com").username("admin@gmail.com").password(passwordEncoder.encode("admin")).accountNonLocked(true).enabled(true).roles(Set.of(new Role(null,"ROLE_ADMIN"))).build();
            userRepository.save(user);
            userRepository.save(admin);
        };
    }

}
