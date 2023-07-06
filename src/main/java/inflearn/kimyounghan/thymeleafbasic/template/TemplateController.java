package inflearn.kimyounghan.thymeleafbasic.template;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/template")
@Controller
public class TemplateController {

    @GetMapping("fragment")
    public String fragment() {
       return "/template/fragment/fragmentMain";
    }

    @GetMapping("layout")
    public String layout() {
        return "/template/layout/layoutMain";
    }
}
