package com.ntorq.journalApp.service;

import com.ntorq.journalApp.entity.User;
import com.ntorq.journalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

//CRUD OPERATIONS

@Slf4j
@RestController
public class UserService {
    @Autowired
    private UserRepository userRepository;
//    Logger logger= LoggerFactory.getLogger(UserService.class);

    private static final PasswordEncoder encoder =new BCryptPasswordEncoder();

    public boolean saveUser(User user){

        try {
            user.setPassword(encoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return true;

        }catch (Exception e){

            log.info("hahahahahahah");
            log.warn("hahahahahahah");
            log.error("hahahahahahah");

            return false;
        }
    }
    public void saveAdmin(User user){
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userRepository.save(user);
    }

    public void saveNewUser(User user){
        userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    //Ram:Ram -> authenticate -> details stored SecurityContextHolder
    public User updateUser(User newUser){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDB = userRepository.getUserByUserName(userName);

        userInDB.setUserName(newUser.getUserName());
        userInDB.setPassword(newUser.getPassword());

        return userInDB;

    }
    // Test
    public User getUser(String userName){
        return userRepository.getUserByUserName(userName);
    }

    public void deleteUser(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.getUserByUserName(authentication.getName());
        userRepository.delete(user);
    }


}
