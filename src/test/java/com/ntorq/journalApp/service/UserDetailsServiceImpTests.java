package com.ntorq.journalApp.service;

import com.ntorq.journalApp.entity.User;
import com.ntorq.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Arrays;

import static org.mockito.Mockito.*;


public class UserDetailsServiceImpTests {

    @InjectMocks
    UserDetailsServiceImp userDetailsServiceImp;

        @Mock
        UserRepository userRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLoadUserByUsername(){
        when(userRepository.getUserByUserName(ArgumentMatchers.anyString())).thenReturn(User.builder().userName("dinesh").password("1234").roles(Arrays.asList("XXXX")).build());
        UserDetails userDetails = userDetailsServiceImp.loadUserByUsername("ram");
        Assertions.assertNotNull(userDetails);

    }
}
