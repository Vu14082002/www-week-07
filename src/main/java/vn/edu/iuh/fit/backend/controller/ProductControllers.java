package vn.edu.iuh.fit.backend.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.backend.enums.ProductStatus;
import vn.edu.iuh.fit.backend.models.Product;
import vn.edu.iuh.fit.backend.models.ProductImage;
import vn.edu.iuh.fit.backend.models.ProductPrice;
import vn.edu.iuh.fit.backend.repository.ProductRepository;

import java.time.LocalDateTime;
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
        return "admin/product/proc-view";
    }
    @GetMapping("/new")
    public String create(Model model, HttpSession session){
        session.setAttribute("productCreate","create");
        model.addAttribute("product", new Product());
        model.addAttribute("price", new ProductPrice());
        model.addAttribute("image", new ProductImage());
        return "admin/product/product-create-form";
    }
    @GetMapping("/update/{id}")
    public String showFormUpdate(Model model, @PathVariable("id") long id){
        Product product = productRepository.findById(id).get();
        model.addAttribute("product",product);
        model.addAttribute("productstatus",ProductStatus.values());
        return "admin/product/product-form-update";
    }
    @PostMapping ("/save")
    public String save(@ModelAttribute("product") Product product,HttpSession session){
        if(session.getAttribute("productCreate")!=null){
            product.setStatus(ProductStatus.ACTIVE);
            product.getProductPrices().get(0).setProduct(product);
            product.getProductImageList().get(0).setProduct(product);
            session.removeAttribute("productCreate");
        }
        productRepository.save(product);
        return "redirect:/admin/products";
    }
    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable("id") long id){
        Product product = productRepository.findById(id).get();
        product.setStatus(ProductStatus.TERMINATED);
        productRepository.save(product);
        return "redirect:/admin/products";
    }
}
