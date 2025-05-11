package com.moncabinetdentaire.repositories;

import com.moncabinetdentaire.enums.UserRole;
import com.moncabinetdentaire.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UserRepositories extends JpaRepository<User, Long> {
    Optional<User> findFirstByEmail(String username);
    Optional<User> findByUserRole(UserRole userRole);

    Optional<User> findByEmail(String email);

    Page<User> findByNameContaining(String name, Pageable pageable);

}
