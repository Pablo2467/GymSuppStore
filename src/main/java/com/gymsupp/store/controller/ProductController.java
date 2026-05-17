package com.gymsupp.store.controller;

import org.springframework.ui.Model;
import com.gymsupp.store.model.Product;
import com.gymsupp.store.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductController {

    private final ProductService service;
    public ProductController(ProductService productService) {
        this.service = productService;
    }
    @GetMapping("/")
    public String listProducts(Model model) {
        model.addAttribute("products", service.findAll());
        return "index";
    }
    @PostMapping("/save")
    public String saveProduct(Product product) {
        service.save(product);
        return "redirect:/";
    }
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable long id) {
        service.delete(id);
        return "redirect:/";
    }
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", service.findById(id));
        return "edit";
    }

    @PostMapping("/update")
    public String updateProduct(Product product) {
        service.save(product);
        return "redirect:/";
    }
    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
}
