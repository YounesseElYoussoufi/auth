package com.moncabinetdentaire.repositories;

import com.moncabinetdentaire.entities.Cabinet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CabinetRepository extends JpaRepository<Cabinet, Long> {
}