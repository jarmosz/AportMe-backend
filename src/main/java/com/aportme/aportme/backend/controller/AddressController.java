package com.aportme.aportme.backend.controller;

import com.aportme.aportme.backend.entity.Address;
import com.aportme.aportme.backend.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/{id}")
    public Address getById(@PathVariable Long id) {
        return addressService.getById(id);
    }

    @PutMapping("/{id}")
    public Address update(@PathVariable Long id, @RequestBody Address address) {
        return addressService.update(id, address);
    }

    @PostMapping
    public Address create(@RequestBody Address address) {
        return addressService.create(address);
    }
}
