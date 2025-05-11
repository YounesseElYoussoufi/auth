package com.moncabinetdentaire.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cabinet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String adresse;
    private String telephone;
    private String email;
    private String horaireTravail;

    @OneToMany(mappedBy = "cabinet")
    private List<User> users;

    @OneToMany(mappedBy = "cabinet")
    private List<RendezVous> rendezVousList;

}