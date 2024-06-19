package project.batdongsan.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {
    @GetMapping("/login")
    public String login(Model model) {
        // Kiểm tra xem có thông báo lỗi không
        if (model.containsAttribute("error")) {
            // Nếu có, truyền thông báo lỗi vào model để hiển thị trên trang Thymeleaf
            model.addAttribute("errorMessage", "Đăng nhập không thành công. Vui lòng kiểm tra lại email và mật khẩu.");
        }
        return "login"; // Trả về tên của trang Thymeleaf (ví dụ: login.html)
    }

    @GetMapping("/error")
    public String doError(Model model) {
        System.out.println("");
        return "error";
    }
}
