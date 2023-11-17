package vn.edu.iuh.fit.backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.backend.models.Customer;
import vn.edu.iuh.fit.backend.repository.CustomerRepository;

import java.util.List;

@Controller
@RequestMapping("/admin/customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public String getAll(Model model) {
        List<Customer> customers = customerRepository.findAll();
        model.addAttribute("customers", customers);
        return "admin/customer/customers";
    }
    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable("id") long id) {
        Customer customer = customerRepository.findById(id).get();
        model.addAttribute("customers", customer);
        return "admin/customer/customer-view";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("customer", new Customer());
        return "admin/customer/customer-create-form";
    }

    @GetMapping("/update/{id}")
    public String updaet(Model model, @PathVariable("id") long id) {
        Customer customer = customerRepository.findById(id).get();
        model.addAttribute("customer", customer);
        return "admin/customer/customer-update-form";
    }

    @PostMapping("save")
    public String save(@ModelAttribute("customer") Customer customer) {
        customerRepository.save(customer);
        return "redirect:/admin/customers";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable("id")long id) {
        Customer customer = customerRepository.findById(id).get();
        customerRepository.delete(customer);
        return "redirect:/admin/customers";
    }
}
