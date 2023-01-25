package com.allen.user_center.service;

import com.allen.user_center.model.domain.User;
//import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
* UserService test
* @author allen
 **/

@SpringBootTest
//@RunWith(SpringRunner.class)
public class UserServiceTest {


    @Resource
    private UserService userService;

    @Test
    public void testAddUser(){
        User user = new User();
        user.setUsername("allen");
        user.setLogin_account("123");
        user.setAvatarUrl("https://images.zsxq.com/FiPBFfWlwK0F_LGC_khEhIsSBLsO?e=1677599999&token=kIxbL07-8jAj8w1n4s9zv64FuZZNEATmlU_Vm6zD:vX2u8Ja-sxQXg9OVDNihsC8Cavk=");
        user.setGender(0);
        user.setLoginPassword("123456");
        user.setPhoneNumber("123");
        user.setEmail("123");
        user.setUserStatus(0);
        user.setDeleted(0);
        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);
    }

    @Test
    void registration() {
        String login_account = "123allen", login_password = "123456", check_password = "123456";
        long result = userService.user_register(login_account,login_password,check_password);
        Assertions.assertEquals(-1,result);
        login_password = "123456789";
        check_password = "12345678";
        result = userService.user_register(login_account,login_password,check_password);
        Assertions.assertEquals(-1,result);
        check_password = login_password;
        login_account = "allen han";
        Assertions.assertEquals(-1,result);
        login_account = "allenhan";
        login_password = "123456789";
        check_password = "123456789";
        result = userService.user_register(login_account,login_password,check_password);
        Assertions.assertTrue(result > 0);
//        String login_account = "allenHan145", login_password = "12345678", check_password = "12345678";
//        long result = userService.user_register(login_account,login_password,check_password);
//        Assertions.assertTrue(result >= 0);
    }
}