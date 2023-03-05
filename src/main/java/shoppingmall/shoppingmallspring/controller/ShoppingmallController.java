package shoppingmall.shoppingmallspring.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import shoppingmall.shoppingmallspring.service.ShoppingmallService;

import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class ShoppingmallController {

    ShoppingmallService service;

    @Autowired
    public ShoppingmallController(ShoppingmallService service) {
        this.service = service;
    }

    @GetMapping("main")
    public String main(Model model){
        return "shoppingmall/shoppingmall_main";
    }

    @ResponseBody
    @PostMapping("main")
    public String main_request(Model model){
        String json = service.getClothesJson();

        return json;
    }
}
