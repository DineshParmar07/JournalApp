package com.ntorq.journalApp.controller;

import com.ntorq.journalApp.entity.User;
import com.ntorq.journalApp.service.JournalEntryService;
import com.ntorq.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User newUser){

        User updateUser = userService.updateUser(newUser);
        userService.saveUser(updateUser);
        return new ResponseEntity<>(updateUser,HttpStatus.NO_CONTENT);

    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(){
        userService.deleteUser();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
//controller -> service -> repository
