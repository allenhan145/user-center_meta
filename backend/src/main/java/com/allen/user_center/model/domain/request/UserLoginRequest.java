package com.allen.user_center.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UserLoginRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 6355848417420027696L;

    private String login_account;
    private String login_password;
}
