package com.allen.user_center.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * nickname
     */
    private String username;

    /**
     * user set account name
     */
    private String login_account;

    /**
     * 
     */
    private String avatarUrl;

    /**
     * 1 for male, 0 for female, and 2 for any other
     */
    private Integer gender;

    /**
     * 
     */
    private String loginPassword;

    /**
     * 
     */
    private String phoneNumber;

    /**
     * 
     */
    private String email;

    /**
     * 0 for valid account, 1 for suspended, 2 for other
     */
    private Integer userStatus;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 0 for not deleted, 1 for deleted
     */
    @TableLogic
    private Integer deleted;

    /**
     * 0 for normal user
     * 1 for administrator
     */
    private Integer permission;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}