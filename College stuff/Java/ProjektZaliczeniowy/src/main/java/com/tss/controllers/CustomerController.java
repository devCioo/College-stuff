package com.tss.controllers;

import com.tss.components.SessionComponent;
import com.tss.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.tss.repositories.CustomerRepository;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CustomerController {
    
    private final CustomerRepository customerRepository;
    
    @Autowired
    SessionComponent sessionComponent;
    
    @Autowired
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    
    @RequestMapping("/customersList")
    public String page(Model model) {
        model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("counterValue", sessionComponent.getCounter());
        return "customersList.html";
    }
    
    @GetMapping("/showAddCustomerForm")
    public String showAddForm(Customer customer) {
        return "addCustomerForm";
    }
    
    @PostMapping("/addcustomer")
    public String addCustomer(Customer customer, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "addCustomerForm";
        }
        customerRepository.save(customer);
        sessionComponent.increaseCounter();
        return "redirect:/customersList";
    }
    
    @GetMapping("/showEditCustomerForm/{id}")
    public String showEditForm(@PathVariable("id") long id, Model model) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id: " + id));
        model.addAttribute("customer", customer);
        return "editCustomerForm";
    }
    
    @PostMapping("/editcustomer/{id}")
    public String editCustomer(@PathVariable("id") long id, Customer customer,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            customer.setId(id);
            return "editUserForm";
        }
        customerRepository.save(customer);
        sessionComponent.increaseCounter();
        return "redirect:/customersList";
    }
    
    @GetMapping("/deleteCustomer/{id}")
    public String deleteCustomer(@PathVariable("id") long id, Model model) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id: " + id));
        customerRepository.delete(customer);
        sessionComponent.increaseCounter();
        return "redirect:/customersList";
    }
}
