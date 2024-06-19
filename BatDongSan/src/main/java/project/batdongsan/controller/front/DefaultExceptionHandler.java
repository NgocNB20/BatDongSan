package project.batdongsan.controller.front;

import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ AuthenticationException.class })
    public String handleAuthenticationException(Exception ex) {
        return "error";
    }

    @ExceptionHandler({ UsernameNotFoundException.class })
    public String notFound(Exception ex) {
        return "not";
    }
}
