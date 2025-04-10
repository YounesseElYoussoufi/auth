package com.moncabinetdentaire.service.auth;

import com.moncabinetdentaire.dto.SignupRequest;
import com.moncabinetdentaire.dto.UserDto;

public interface AuthService {

    UserDto signupUser(SignupRequest signupRequest);

    boolean hasUserWithEmail(String email);
}