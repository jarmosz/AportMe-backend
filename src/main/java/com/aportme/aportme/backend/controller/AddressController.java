package com.aportme.aportme.backend.controller;

import com.aportme.aportme.backend.dto.AddressDTO;
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
    public List<DTOEntity> getAllAddresses() {
        return addressService.getAll();
    }

    @GetMapping("/{id}")
    public DTOEntity getById(@PathVariable Long id) {
        return addressService.getById(id);
    }

    @PutMapping("/{id}")
    public DTOEntity update(@PathVariable Long id, @RequestBody AddressDTO addressDTO) throws Exception {
        return addressService.update(id, addressDTO);
    }

    @PostMapping
    public DTOEntity create(@RequestBody AddressDTO address) {
        return addressService.create(address);
    }
}
