package shoppingmall.shoppingmallspring.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shoppingmall.shoppingmallspring.domain.Cloth;
import shoppingmall.shoppingmallspring.service.ShoppingmallService;

import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class ShoppingmallController {

    ShoppingmallService service;

    @ExceptionHandler(value = IllegalStateException.class)
    public String controllerExceptionHandler(Exception e) {
        System.out.println(e.getMessage());
        return "/error/404";
    }

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
    public String mainRequest(Model model){
        String json = service.getClothesJson();

        return json;
    }

    @GetMapping("main/add_form")
    public String addForm(Model model){
        return "shoppingmall/add_cloth";
    }

    @PostMapping("main/add_form")
    public String addFormDo(@ModelAttribute Cloth cloth){
        service.addCloth(cloth);
        return "shoppingmall/shoppingmall_home";
    }

    @GetMapping("main/delete_form")
    public String deleteForm(Model model) {
        return "shoppingmall/delete_form";
    }

    @PostMapping("main/delete_form")
    public String deleteForm(@ModelAttribute Cloth cloth) throws IllegalStateException{
        service.deleteCloth(cloth.getType(), cloth.getColor());
        return "shoppingmall/shoppingmall_home";
    }
}
