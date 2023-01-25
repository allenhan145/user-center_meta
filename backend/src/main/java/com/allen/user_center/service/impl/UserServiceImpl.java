package com.allen.user_center.service.impl;

import com.allen.user_center.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.allen.user_center.model.domain.User;
import com.allen.user_center.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.allen.user_center.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @author Allen
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2023-01-07 17:59:14
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * Cryptographic salt
     */
    public static final String SALT = "database";





    /**
     *
     * @param login_account user input account
     * @param login_password user input password
     * @param check_password user input check password
     * @return id of the new user
     */
    @Override
    public long user_register(String login_account, String login_password, String check_password) {
        if (StringUtils.isAnyBlank(login_account, login_password, check_password)) {
            return -1;
        }
        if (login_account.length() < 4) {
            return -1;
        }
        if (login_password.length() < 8) {
            return -1;
        }
        if (!login_password.equals(check_password)) {
            return -1;
        }
        // no special char
        String valid_pattern = "\\pP|\\pS|\\s+";
        Matcher matcher = Pattern.compile(valid_pattern).matcher(login_account);
        if (matcher.find()) {
            return -1;
        }
        // no same existing account
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("login_account", login_account);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            return -1;
        }
        // Encryption of password
        String encrypted_password = DigestUtils.md5DigestAsHex((SALT + login_password).getBytes());
        // add data to database
        User user = new User();
        user.setLogin_account(login_account);
        user.setLoginPassword(encrypted_password);
        boolean save_result = this.save(user);
        if (!save_result) return -1;
        return user.getId();
    }

    /**
     *
     * @param login_account user input account
     * @param login_password user input password
     * @return the user information after data masking
     */
    @Override
    public User user_login(String login_account, String login_password, HttpServletRequest request) {
        if (StringUtils.isAnyBlank(login_account, login_password)) {
            return null;
        }
        if (login_account.length() < 4) {
            return null;
        }
        if (login_password.length() < 8) {
            return null;
        }
        // no special char
        String valid_pattern = "\\pP|\\pS|\\s+";
        Matcher matcher = Pattern.compile(valid_pattern).matcher(login_account);
        if (matcher.find()) {
            return null;
        }
        // Encryption of password
        String encrypted_password = DigestUtils.md5DigestAsHex((SALT + login_password).getBytes());
        // Search for user
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("login_account", login_account);
        queryWrapper.eq("loginPassword", encrypted_password);
        User user = userMapper.selectOne(queryWrapper);
        // user does not exist
        if(user==null){
            log.info("user login failed, login_account does not match with login_password");
            return null;
        }
        // Data masking
        User masked_user = get_masked_user(user);
        // Record user login status
        request.getSession().setAttribute(USER_LOGIN_STATE, masked_user);
        return masked_user;
    }

    /**
     * user masking
     * @param user
     * @return the masked user
     */
    @Override
    public User get_masked_user(User user){
        // Data masking
        User masked_user = new User();
        masked_user.setId(user.getId());
        masked_user.setUsername(user.getUsername());
        masked_user.setLogin_account(user.getLogin_account());
        masked_user.setAvatarUrl(user.getAvatarUrl());
        masked_user.setGender(user.getGender());
        masked_user.setPhoneNumber(user.getPhoneNumber());
        masked_user.setEmail(user.getEmail());
        masked_user.setUserStatus(user.getUserStatus());
        masked_user.setCreateTime(user.getCreateTime());
        masked_user.setPermission(user.getPermission());
        return masked_user;
    }
}




