package com.moncabinetdentaire.service.jwt;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    UserDetailsService userDetailService();
}
