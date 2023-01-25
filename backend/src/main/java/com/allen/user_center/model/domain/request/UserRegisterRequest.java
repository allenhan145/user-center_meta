package com.allen.user_center.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * user register request
 *
 * @author Allen
 */
@Data
public class UserRegisterRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -1085563854794785830L;

    private String login_account;
    private String login_password;
    private String check_password;
}
