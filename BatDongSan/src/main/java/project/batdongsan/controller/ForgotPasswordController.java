package project.batdongsan.controller;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.batdongsan.component.UserDetailsServiceApp;
import project.batdongsan.model.entity.User;
import project.batdongsan.model.entity.UserDetailsApp;
import project.batdongsan.service.UserService;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


@Controller
public class ForgotPasswordController {
    private final UserService userService;
    private final JavaMailSender javaMailSender;

    @Qualifier("passwordEncoder")
    private final PasswordEncoder passwordEncoder;


    private final DaoAuthenticationProvider daoAuthenticationProvider;

    public ForgotPasswordController(UserService userService, JavaMailSender javaMailSender, PasswordEncoder passwordEncoder, DaoAuthenticationProvider daoAuthenticationProvider) {
        this.userService = userService;
        this.javaMailSender = javaMailSender;
        this.passwordEncoder = passwordEncoder;
        this.daoAuthenticationProvider = daoAuthenticationProvider;
    }


    @GetMapping("/forgot_password")
    public String doShowForgotPassword() {

        return "forgot";
    }


    @Transactional
    @PostMapping("/process_send_mail")
    public String doProcessResetPassword(@RequestParam("email") String email, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Model model) throws MessagingException, UnsupportedEncodingException {
        String siteURL = httpServletRequest.getRequestURL().toString();
        String link = siteURL.replace(httpServletRequest.getServletPath(), "");
        String message = "";
        String token = RandomStringUtils.random(34);
        User user = userService.getByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            user.setExpireTimeResetPassWord(new Date());
            userService.updateUser(user);
            String resetPasswordLink = link + "/reset_password?token=" + token;
            message = "We have sent a reset password link to your email. Please check.";
            sendEmail(email, resetPasswordLink);
        }
        model.addAttribute("message",message);
        return "forgot";
    }

    @GetMapping("/reset_password")
    public String doShowResetPassWord(@RequestParam("token") String token)  {

        return "reset_password";
    }

    @Transactional
    @PostMapping("/reset_password")
    public String doProcessResetPassWord(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,HttpSession httpSession) {
            String token = httpServletRequest.getParameter("token");
            String newPassword = httpServletRequest.getParameter("newPassword");
            User user = userService.findByResetToken(token);

            if (user == null) {
                System.out.println("Token invalid");
                return "redirect:/login";
            } else if (user.getExpireTimeResetPassWord() != null && user.getExpireTimeResetPassWord().getTime() + 2 * 60 * 1000 < System.currentTimeMillis()) {
                System.out.println("Time out");
                return "redirect:/login";
            } else {
                user.setPassword(passwordEncoder.encode(newPassword));
                user.setExpireTimeResetPassWord(null);
                user.setResetPasswordToken(null);
                userService.updateUser(user);
                UserDetailsApp userDetails = new UserDetailsApp();
                userDetails.setUser(user);

                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userDetails, newPassword, userDetails.getAuthorities());
                Authentication authentication = daoAuthenticationProvider.authenticate(authRequest);
                SecurityContextHolder.clearContext();
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(authentication);
                SecurityContextHolder.setContext(context);

                // Create session and set security context
                httpServletRequest.getSession(true); // true to create a new session if one does not exist
                httpSession.setAttribute("SPRING_SECURITY_CONTEXT", context);


                if (authentication.isAuthenticated()) {
                    System.out.println("auto login");
                }
                if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                    System.out.println("update Admin");
                    return "redirect:/admin/index";
                } else {
                    System.out.println("Update User");
                    return "redirect:/front/index";
                }
            }
    }

    public void sendEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("contact@shopme.com", "Shopme Support");
        helper.setTo(recipientEmail);

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        javaMailSender.send(message);
    }


    public static void main(String[] args) {
        System.out.println(RandomStringUtils.random(32));
    }
}
