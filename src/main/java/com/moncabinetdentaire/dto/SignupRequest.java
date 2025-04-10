package com.moncabinetdentaire.dto;

import com.moncabinetdentaire.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class SignupRequest {

    private String name;

    private String email;

    private String cin;



    private String password;

    private UserRole userRole;
    private String region;

}