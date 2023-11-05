package vn.edu.iuh.fit.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.backend.models.Product;
import vn.edu.iuh.fit.backend.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/products")
public class ProductControllers {
    @Autowired
    private ProductRepository productRepository;
    @GetMapping
    public String showAll(Model model){
        List<Product> products = productRepository.findAll();
        model.addAttribute("products",products);
        return  "admin/product/products";
    }
    @GetMapping("/{id}")
    public String viewDetail(Model model, @PathVariable("id") long id){
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            model.addAttribute("product",product.get());
        }else{
            model.addAttribute("product",new Product());
        }
        return "admin/product/proc_view";
    }
    @GetMapping("/update/{id}")
    public  String update(Model model, @PathVariable("id") long id){
        Product product = productRepository.findById(id).get();
        model.addAttribute("product", product);
        return  "admin/product/proc_update";
    }
    @PostMapping("/update")
    public  String update(@ModelAttribute("product")  Product product){
        productRepository.save(product);
        return  "redirect:/";
    }
}
