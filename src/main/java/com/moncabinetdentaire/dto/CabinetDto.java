package com.moncabinetdentaire.dto;

import lombok.Data;

@Data
public class CabinetDto {
    private Long id;
    private String nom;
    private String adresse;
    private String telephone;
    private String email;
    private String horaireTravail;
    private Long userId; // Pour savoir qui a créé le cabinet
}
