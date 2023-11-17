package vn.edu.iuh.fit.backend.controller;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import vn.edu.iuh.fit.backend.cart.Cart;
import vn.edu.iuh.fit.backend.models.Product;
import vn.edu.iuh.fit.backend.repository.ProductRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class UserController {
    private ProductRepository productRepository;

    @GetMapping("/home")
    public String homePage(Model model, HttpSession httpSession) {
        Object quantityItem = httpSession.getAttribute("quantityItem");
        if(quantityItem==null){
            httpSession.setAttribute("quantityItem",0);
        }else{
            int quanTIty = (Integer) httpSession.getAttribute("quantityItem");
            httpSession.setAttribute("quantityItem",quanTIty+1);
        }
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "user/home";
    }

    @GetMapping("/home/{id}")
    public String homePage(Model model, @PathVariable("id") long id, HttpSession httpSession) {
        Object cartss = httpSession.getAttribute("carts");
        Map<Long, Integer> carts = null;
        if (cartss == null) {
            carts = new HashMap<>();
        } else {
            carts = (Map<Long, Integer>) cartss;
        }
        if (carts.get(id) == null) {
            carts.put(id, 1);
        } else {
            carts.replace(id, carts.get(id).intValue() + 1);
        }
        httpSession.setAttribute("carts", carts);
        return "redirect:/home";
    }

    @GetMapping("/cart")
    public String cartPage(Model model, HttpSession httpSession) {
        Object cartss = httpSession.getAttribute("carts");
        if (cartss == null) {
            return "redirect:/home";
        }
        Map<Long, Integer> carts = (Map<Long, Integer>) cartss;
        Map<Product, Integer> cartProc = new HashMap<>();
        carts.forEach((k, v) -> {
            cartProc.put(productRepository.findById(k).get(), v);
        });
        model.addAttribute("cartProc", cartProc);
        return "user/cart";
    }
    @GetMapping("/checkout")
    public String checkout(Model model, HttpSession httpSession){
        return "redirect:/home";
    }
}
