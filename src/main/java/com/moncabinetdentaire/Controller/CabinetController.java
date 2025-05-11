package com.moncabinetdentaire.Controller;

import com.moncabinetdentaire.dto.CabinetDto;
import com.moncabinetdentaire.entities.Cabinet;
import com.moncabinetdentaire.service.CabinetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cabinets")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CabinetController {

    private final CabinetService cabinetService;

    @PostMapping
    public Cabinet createCabinet(@RequestBody CabinetDto dto) {
        return cabinetService.createCabinet(dto);
    }

    @GetMapping
    public List<Cabinet> getAllCabinets() {
        return cabinetService.getAll();
    }

    /**
     * Nouveau endpoint pour récupérer les cabinets d'un utilisateur spécifique
     * @param userId ID de l'utilisateur
     * @return Liste des cabinets associés à cet utilisateur
     */
    @GetMapping("/user/{userId}")
    public List<Cabinet> getCabinetsByUserId(@PathVariable Long userId) {
        return cabinetService.getCabinetsByUserId(userId);
    }

    @PutMapping("/{id}")
    public Cabinet updateCabinet(@PathVariable Long id, @RequestBody CabinetDto dto) {
        return cabinetService.updateCabinet(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteCabinet(@PathVariable Long id) {
        cabinetService.deleteCabinet(id);
    }
}