package shoppingmall.shoppingmallspring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShoppingmallController {

    @GetMapping("main")
    public String main() {
        return "shoppingmall/shoppingmall_main";
    }

}
