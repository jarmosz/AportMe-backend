package com.aportme.backend.component.address.controller;

import com.aportme.backend.component.address.dto.AddOrUpdateAddressDTO;
import com.aportme.backend.component.address.dto.AddressDTO;
import com.aportme.backend.component.address.service.AddressService;
import com.aportme.backend.utils.dto.DTOEntity;
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
