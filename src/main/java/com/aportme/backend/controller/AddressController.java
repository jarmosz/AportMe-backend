package com.aportme.backend.controller;

import com.aportme.backend.entity.dto.address.AddOrUpdateAddressDTO;
import com.aportme.backend.entity.dto.address.AddressDTO;
import com.aportme.backend.service.AddressService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/{id}")
    @ApiOperation(value = "Find address by id", response = AddressDTO.class)
    public AddressDTO getAddressById(@PathVariable Long id) {
        return addressService.getById(id);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update address")
    public ResponseEntity<Object> updateAddress(@PathVariable Long id, @RequestBody AddOrUpdateAddressDTO addressDTO) {
        return addressService.update(id, addressDTO);
    }
}
