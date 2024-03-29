package com.example.batch1.services;


import com.example.batch1.entities.Customer;
import com.example.batch1.exceptions.NoUserFoundException;
import com.example.batch1.repositories.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private CustomerRepository repository;

    public List<Customer> findAllCustomers(){
        return repository.findAll();
    }

    public Customer findCustomerByUsername(String username) throws NoUserFoundException {
        Optional<Customer>optionalUsers = repository.findCustomerByUsername(username);
        if(optionalUsers.isPresent()){
            logger.info("user found,  email: {}", optionalUsers.get().getEmail());
            return optionalUsers.get();
        }
        else {
            throw new NoUserFoundException("user with username "+ username+ "doesn't exist in the database");
        }
    }

    public Customer findCustomerById(Long id) throws NoUserFoundException {
        Optional<Customer>optionalUsers = repository.findById(id);
        if(optionalUsers.isPresent()){
            logger.info("user found,  email: {}", optionalUsers.get().getEmail());
            return optionalUsers.get();
        }
        else {
            throw new NoUserFoundException("user with id "+ id+ "doesn't exist in the database");
        }
    }

    public Customer saveCustomer(Customer customer){
        return repository.save(customer);
    }
}
