package project.batdongsan.controller.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.batdongsan.model.dto.HouseDTO;
import project.batdongsan.repositoty.HouseRepository;
import project.batdongsan.service.HouseService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;


@Controller
@RequestMapping("/front")
public class FrontController {

    private final HouseService houseService;

    @Value("${app.page.size}")
    private Integer sizePage;

    @Autowired
    public FrontController(HouseService houseService, HouseRepository houseRepository) {
        this.houseService = houseService;
    }

    @GetMapping({"/index","/"})
    public String showIndexPage(Model model,@ModelAttribute HouseDTO houseDTO,@RequestParam(name = "page", defaultValue = "0") int page) {
        Page<HouseDTO> houseDTOPage = houseService.findByCondition(houseDTO, page, sizePage);
        List<HouseDTO> houseDTOList = houseDTOPage.getContent();
        model.addAttribute("houseDTOList",houseDTOList);

        return "index";
    }

    @PostMapping("/upload")
    public String uploadImage(@ModelAttribute HouseDTO houseDTO) throws IOException {
        houseService.save(houseDTO);
        return "redirect:/house/index";
    }


    @GetMapping("/house/search")
    public String doSearch(@ModelAttribute HouseDTO houseDTO,@RequestParam(name = "page", defaultValue = "0") int page) {
        Page<HouseDTO> houseDTOPage = houseService.findByCondition(houseDTO, page, sizePage);
        return "front/house/index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        // Kiểm tra xem có thông báo lỗi không
        if (model.containsAttribute("error")) {
            // Nếu có, truyền thông báo lỗi vào model để hiển thị trên trang Thymeleaf
            model.addAttribute("errorMessage", "Đăng nhập không thành công. Vui lòng kiểm tra lại email và mật khẩu.");
        }
        return "login"; // Trả về tên của trang Thymeleaf (ví dụ: login.html)
    }
}