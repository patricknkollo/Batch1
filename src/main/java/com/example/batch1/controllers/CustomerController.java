package com.example.batch1.controllers;

import com.example.batch1.entities.Customer;
import com.example.batch1.exceptions.NoUserFoundException;
import com.example.batch1.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    @ResponseBody
    public List<Customer> getAllCustomers (){
        return service.findAllCustomers();
    }

    @RequestMapping(path = "/app", method = RequestMethod.GET)
    @ResponseBody
    public Customer getCustomerByUsername (@RequestParam("username") String username) throws NoUserFoundException {
        return service.findCustomerByUsername(username);
    }

    @RequestMapping(path = "/app/id", method = RequestMethod.GET)
    @ResponseBody
    public Customer getCustomerById (@RequestParam("customerid") Long id) throws NoUserFoundException {
        return service.findCustomerById(id);
    }

    @RequestMapping(path = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Customer saveCustomer(@RequestBody Customer customer){
        return service.saveCustomer(customer);
    }
}
