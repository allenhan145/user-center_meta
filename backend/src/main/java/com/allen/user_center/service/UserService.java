package com.allen.user_center.service;

import com.allen.user_center.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Allen
 * @description 针对表【user】的数据库操作Service
 * @createDate 2023-01-07 17:59:14
 */
public interface UserService extends IService<User> {

    /**
     * user user_register
     *
     * @param login_account
     * @param login_password
     * @param check_password
     * @return
     */
    long user_register(String login_account, String login_password, String check_password);

    User user_login(String login_account, String login_password, HttpServletRequest request);

    User get_masked_user(User user);
}
