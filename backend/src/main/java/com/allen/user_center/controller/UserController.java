package com.allen.user_center.controller;

import com.allen.user_center.model.domain.User;
import com.allen.user_center.model.domain.request.UserLoginRequest;
import com.allen.user_center.model.domain.request.UserRegisterRequest;
import com.allen.user_center.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.allen.user_center.constant.UserConstant.ADMIN_ROLE;
import static com.allen.user_center.constant.UserConstant.USER_LOGIN_STATE;

/**
 * User interface
 *
 * @author Allen
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public Long user_register(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            return null;
        }
        String login_account = userRegisterRequest.getLogin_account();
        String login_password = userRegisterRequest.getLogin_password();
        String check_password = userRegisterRequest.getCheck_password();
        if (StringUtils.isAnyBlank(login_account, login_password, check_password)) return null;
        long id = userService.user_register(login_account, login_password, check_password);
        return id;
    }

    @PostMapping("/login")
    public User user_login(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            return null;
        }
        String login_account = userLoginRequest.getLogin_account();
        String login_password = userLoginRequest.getLogin_password();
        if (StringUtils.isAnyBlank(login_account, login_password)) return null;
        User user = userService.user_login(login_account, login_password, request);
        return user;
    }

    @GetMapping("/search")
    public List<User> search_users(String username, HttpServletRequest request) {
        // Only administrator can search for users
        if(!has_permission(request)){
            return new ArrayList<>();
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(username)){
            queryWrapper.like("username",username);
        }
        List<User> userList = userService.list(queryWrapper);
        return userList.stream().map(user -> userService.get_masked_user(user)).collect(Collectors.toList());
    }

    @PostMapping("/delete")
    public boolean delete_user(@RequestBody long id, HttpServletRequest request){
        // Only administrator can delete for users
        if(!has_permission(request)){
            return false;
        }
        if(id <=0) return false;
        return userService.removeById(id);
    }

    private boolean has_permission(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        return user!= null && user.getPermission() == ADMIN_ROLE;
    }
}
