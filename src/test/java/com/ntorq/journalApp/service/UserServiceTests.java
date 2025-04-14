package com.ntorq.journalApp.service;
import com.ntorq.journalApp.entity.User;
import com.ntorq.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

import static org.junit.jupiter.api.Assertions.*;

//App context -> Ioc -> Component scan -> beans store
@SpringBootTest
public class UserServiceTests {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;


    @ParameterizedTest
    @CsvSource({
            "ram","dinesh","sanjay"
    })
    public void testGetUser(String userName){
        User user = userRepository.getUserByUserName(userName);
        assertNotNull(user);
//        assertTrue(user.getRoles().contains("ADMIN"));
    }

    @Disabled
    @ParameterizedTest
    @ArgumentsSource(UserArgumentsProvider.class)
    public void testSaveUser(User user){
        assertNotNull(user);
//        assertTrue(userService.saveUser(user));
    }


    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,4,6",
            "4,3,7"
    })
    public void test(int a, int b, int expected){
        assertEquals(expected,a+b);
    }
}
