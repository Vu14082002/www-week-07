package vn.edu.iuh.fit.backend.controller;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.backend.models.Customer;
import vn.edu.iuh.fit.backend.models.Employee;
import vn.edu.iuh.fit.backend.models.Order;
import vn.edu.iuh.fit.backend.models.OrderDetail;
import vn.edu.iuh.fit.backend.repository.CustomerRepository;
import vn.edu.iuh.fit.backend.repository.EmployeeRepository;
import vn.edu.iuh.fit.backend.repository.OrderDetailRepository;
import vn.edu.iuh.fit.backend.repository.OrderRepository;

import java.util.List;

@Controller
@RequestMapping("/admin/orders")
@AllArgsConstructor
public class OrderController {
    private OrderRepository orderRepository;
    private OrderDetailRepository orderDetailRepository;
    private CustomerRepository customerRepository;
    private EmployeeRepository employeeRepository;

    @GetMapping
    public  String getAll(Model model){
        List<Order> orders = orderRepository.findAll();
        model.addAttribute("orders",orders);
        return "admin/Order/orders";
    }
    @GetMapping("/{id}")
    public  String findOne(Model model, @PathVariable("id") long id){
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrder_Order_id(id);
        System.out.println(orderDetails.size());
        model.addAttribute("orderDetail",orderDetails);
        return "admin/Order/order-detail";
    }
    @GetMapping("/new")
    public  String newOrder(Model model){
        model.addAttribute("order",new Order());
        model.addAttribute("employees",employeeRepository.findAll());
        model.addAttribute("customers",customerRepository.findAll());
        return "admin/Order/order-add-form";
    }
    @GetMapping("/update/{id}")
    public  String showUpdaetFoem(Model model, @PathVariable("id") Long id){
        Order order = orderRepository.findById(id).get();
        List<Customer> customers = customerRepository.findAll();
        List<Employee> employees = employeeRepository.findAll();
        model.addAttribute("order",order);
        model.addAttribute("customers",customers);
        model.addAttribute("employees",employees);
        return "admin/Order/order-update-form";
    }
    @PostMapping("/save")
    public  String save(@ModelAttribute("order") Order order){
        orderRepository.save(order);
        return "redirect:/admin/orders";
    }

}
