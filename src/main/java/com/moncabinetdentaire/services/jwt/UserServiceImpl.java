package com.moncabinetdentaire.services.jwt;

import com.moncabinetdentaire.repositories.UserRepositories;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepositories userRepository;
    @Override
    public UserDetailsService userDetailService() {
        return  new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findFirstByEmail(username).orElseThrow(()-> new UsernameNotFoundException("Utilisateur non trouvé"));
            }
        };

    }
}