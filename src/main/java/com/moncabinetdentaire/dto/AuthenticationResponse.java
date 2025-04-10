package com.moncabinetdentaire.dto;

import com.moncabinetdentaire.enums.UserRole;
import lombok.Data;

@Data

public class AuthenticationResponse {

    private String jwt;
    private String name;

    private Long userId;

    private UserRole userRole;
    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

}