package com.example.batch1.controllers;

import com.example.batch1.entities.Users;
import com.example.batch1.exceptions.NoUserFoundException;
import com.example.batch1.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService service;

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public @ResponseBody List<Users> getAllUserFromDB(){
        return service.findAllUser();
    }

    @RequestMapping(path = "/users/userid/id", method = RequestMethod.GET)
    public @ResponseBody Users getUserById( @RequestParam("id") Long userid) throws NoUserFoundException {
        return service.findUserWithID(userid);
    }

    @RequestMapping(path = "/users/name/nom", method = RequestMethod.GET)
    public @ResponseBody Users getUserByUsername(@RequestParam("username") String username) throws NoUserFoundException {
        //logger.info("the user is: {}", principal.getName());
        return service.findUserWithUsername(username);
    }

    @RequestMapping(path = "/users/email/mail", method = RequestMethod.GET)
    public @ResponseBody Users getUserByUserMail(@RequestParam("email") String mail) throws NoUserFoundException {
        return service.findUserWithEmail(mail);
    }
}
