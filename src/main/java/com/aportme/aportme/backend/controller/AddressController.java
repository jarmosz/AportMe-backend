package com.aportme.aportme.backend.controller;

import com.aportme.aportme.backend.dto.address.AddOrUpdateAddressDTO;
import com.aportme.aportme.backend.dto.DTOEntity;
import com.aportme.aportme.backend.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;

    @GetMapping
    public List<DTOEntity> getAll() {
        return addressService.getAll();
    }

    @GetMapping("/{id}")
    public DTOEntity getById(@PathVariable Long id) {
        return addressService.getById(id);
    }

    @PutMapping("/{id}")
    public DTOEntity update(@PathVariable Long id, @RequestBody AddOrUpdateAddressDTO addressDTO) throws Exception {
        return addressService.update(id, addressDTO);
    }
}
