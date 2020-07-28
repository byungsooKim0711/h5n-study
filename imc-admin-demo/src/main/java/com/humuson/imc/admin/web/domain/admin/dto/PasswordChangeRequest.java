package com.humuson.imc.admin.web.domain.admin.dto;

import lombok.Data;

@Data
public class PasswordChangeRequest {

    private String oldPassword;
    private String newPassword;
    private String checkNewPassword;
}
