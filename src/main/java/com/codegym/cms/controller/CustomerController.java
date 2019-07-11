package com.codegym.cms.controller;

import com.codegym.cms.model.Customer;
import com.codegym.cms.model.CustomerForm;
import com.codegym.cms.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class CustomerController {
    @Autowired
    Environment env;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/create-customer")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/customer/create");
        modelAndView.addObject("customer", new Customer());
        return modelAndView;
    }

    @RequestMapping(value = "/create-customer", method = RequestMethod.POST)
    public ModelAndView saveProduct(@ModelAttribute("customerForm") CustomerForm customerForm, BindingResult result, HttpServletRequest servletRequest) {

        // thong bao neu xay ra loi
        if (result.hasErrors()) {
            System.out.println("Result Error Occured" + result.getAllErrors());
        }

        // lay ten file
        MultipartFile multipartFile = customerForm.getImage();
        String fileName = multipartFile.getOriginalFilename();
        String fileUpload = env.getProperty("file_upload").toString();

        // luu file len server
        try {
            //multipartFile.transferTo(imageFile);
            FileCopyUtils.copy(customerForm.getImage().getBytes(), new File(fileUpload + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        // tham khảo: https://github.com/codegym-vn/spring-static-resources

        // tao doi tuong de luu vao db
        Customer productObject = new Customer(customerForm.getFirstName(), fileName, customerForm.getLastName());

        // luu vao db
        customerService.save(productObject);


        ModelAndView modelAndView = new ModelAndView("/customer/create");
        modelAndView.addObject("product", new CustomerForm());
        modelAndView.addObject("message", "New product created successfully");
        return modelAndView;
    }

    @GetMapping("/customers")
    public ModelAndView listCustomers() {
        List<Customer> customers = customerService.findAll();
        ModelAndView modelAndView = new ModelAndView("/customer/list");
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }

    @GetMapping("/edit-customer/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Customer customer = customerService.findById(id);
        if (customer != null) {
            CustomerForm customerForm = new CustomerForm(customer.getId(), null, customer.getFirstName(), customer.getLastName());
            ModelAndView mv = new ModelAndView("/customer/edit");
            mv.addObject("customerform", customerForm);
            mv.addObject("customer", customer);
            return mv;
        } else {
            ModelAndView mv = new ModelAndView("/customer/error");
            return mv;
        }
    }

    @PostMapping("/edit-customer")

    public ModelAndView editProduct(@ModelAttribute("customerform") CustomerForm customerForm, BindingResult result) {

        // thong bao neu xay ra loi
        if (result.hasErrors()) {
            System.out.println("Result Error Occured" + result.getAllErrors());
        }

        // lay ten file
        MultipartFile multipartFile = customerForm.getImage();
        String fileName = multipartFile.getOriginalFilename();


        // luu file len server
        try {
            FileCopyUtils.copy(customerForm.getImage().getBytes(), new File(env.getProperty("file_upload") + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // tao doi tuong de luu vao db
        Customer productObject = new Customer(customerForm.getId(), customerForm.getFirstName(), customerForm.getLastName(), null);

        // luu vao db
        customerService.save(productObject);


        ModelAndView modelAndView = new ModelAndView("/customer/edit");
        modelAndView.addObject("customer", new CustomerForm());
        modelAndView.addObject("message", "Product edited successfully");
        return modelAndView;
    }
}


