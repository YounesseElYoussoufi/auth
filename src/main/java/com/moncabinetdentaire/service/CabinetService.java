package com.moncabinetdentaire.service;

import com.moncabinetdentaire.dto.CabinetDto;
import com.moncabinetdentaire.entities.Cabinet;
import com.moncabinetdentaire.entities.User;
import com.moncabinetdentaire.repositories.CabinetRepository;
import com.moncabinetdentaire.repositories.UserRepositories;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CabinetService {

    private final CabinetRepository cabinetRepository;
    private final UserRepositories userRepository;

    public Cabinet createCabinet(CabinetDto dto) {
        Optional<User> userOpt = userRepository.findById(dto.getUserId());
        if (userOpt.isEmpty()) throw new RuntimeException("Utilisateur non trouvé");

        Cabinet cabinet = new Cabinet();
        cabinet.setNom(dto.getNom());
        cabinet.setAdresse(dto.getAdresse());
        cabinet.setTelephone(dto.getTelephone());
        cabinet.setEmail(dto.getEmail());
        cabinet.setHoraireTravail(dto.getHoraireTravail());
        cabinetRepository.save(cabinet);

        // Ajout du cabinet à l'utilisateur
        User user = userOpt.get();
        user.setCabinet(cabinet);
        userRepository.save(user);

        return cabinet;
    }

    public List<Cabinet> getAll() {
        return cabinetRepository.findAll();
    }

    /**
     * Récupère tous les cabinets associés à un utilisateur spécifique
     * @param userId ID de l'utilisateur
     * @return Liste des cabinets de l'utilisateur
     */
    public List<Cabinet> getCabinetsByUserId(Long userId) {
        // Méthode 1: Si la relation est bidirectionnelle et vous avez une méthode dans le repository
        // return cabinetRepository.findByUserId(userId);

        // Méthode 2: En utilisant la relation User -> Cabinet existante
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Utilisateur non trouvé avec l'ID: " + userId);
        }

        User user = userOpt.get();
        Cabinet cabinet = user.getCabinet();

        // Si l'utilisateur a un seul cabinet associé (comme indiqué par la relation dans votre code)
        if (cabinet != null) {
            return List.of(cabinet);
        } else {
            return List.of(); // Liste vide si aucun cabinet n'est associé
        }

        // Méthode 3: Si un utilisateur peut avoir plusieurs cabinets (collection)
        // return userOpt.get().getCabinets(); // Si User a une collection de cabinets
    }

    public Cabinet updateCabinet(Long id, CabinetDto dto) {
        Cabinet cabinet = cabinetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cabinet introuvable"));

        cabinet.setNom(dto.getNom());
        cabinet.setAdresse(dto.getAdresse());
        cabinet.setTelephone(dto.getTelephone());
        cabinet.setEmail(dto.getEmail());
        cabinet.setHoraireTravail(dto.getHoraireTravail());

        return cabinetRepository.save(cabinet);
    }

    public void deleteCabinet(Long id) {
        cabinetRepository.deleteById(id);
    }
}