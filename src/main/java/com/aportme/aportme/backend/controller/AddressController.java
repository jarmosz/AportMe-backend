package com.aportme.aportme.backend.controller;

import com.aportme.aportme.backend.dto.address.AddOrUpdateAddressDTO;
import com.aportme.aportme.backend.dto.DTOEntity;
import com.aportme.aportme.backend.dto.address.AddressDTO;
import com.aportme.aportme.backend.service.AddressService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;

    @ApiOperation(value = "Find address by id", response = AddressDTO.class)
    @GetMapping("/{id}")
    public DTOEntity getById(@PathVariable Long id) {
        return addressService.getById(id);
    }

    @ApiOperation(value = "Update address", response = AddressDTO.class)
    @PutMapping("/{id}")
    public DTOEntity update(@PathVariable Long id, @RequestBody AddOrUpdateAddressDTO addressDTO) throws Exception {
        return addressService.update(id, addressDTO);
    }
}
