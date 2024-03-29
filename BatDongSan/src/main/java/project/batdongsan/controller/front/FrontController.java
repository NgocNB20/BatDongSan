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

    @GetMapping({"/index","/","/index.html"})
    public String showIndexPage(Model model) {
        List<HouseDTO> houseDTOList = houseService.findAll();
        model.addAttribute("houseDTOList",houseDTOList);

        return "/index";
    }

    @PostMapping("/upload")
    public String uploadImage(@ModelAttribute HouseDTO houseDTO) throws IOException {
        houseService.save(houseDTO);
        return "redirect:/house/index";
    }

//    @GetMapping("/house/index/{housePath}")
//    public String ShowPageHouseById(@PathVariable String housePath, Model molModel) {
//
//        String[] parts = housePath.split("-");
//        Integer houseId  = Integer.valueOf(parts[parts.length -1]);
//        HouseDTO houseDTO = houseService.findById(houseId);
//        molModel.addAttribute("houseDTO",houseDTO);
//
//        return "front/house/index";
//
//    }

    @GetMapping("/house/search")
    public String doSearch(@ModelAttribute HouseDTO houseDTO,Model model) {
        Page<HouseDTO> houseDTOPage = houseService.findByCondition(houseDTO, PageRequest.of(houseDTO.getPage(), sizePage));
        return "front/house/index";
    }
}
