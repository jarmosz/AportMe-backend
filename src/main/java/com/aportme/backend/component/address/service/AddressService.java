package com.aportme.backend.component.address.service;

import com.aportme.backend.component.address.dto.AddOrUpdateAddressDTO;
import com.aportme.backend.component.address.dto.AddressDTO;
import com.aportme.backend.component.address.entity.Address;
import com.aportme.backend.component.address.repository.AddressRepository;
import com.aportme.backend.utils.dto.DTOEntity;
import com.aportme.backend.utils.dto.EntityDTOConverter;
import com.aportme.backend.utils.UtilsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final EntityDTOConverter entityDTOConverter;

    public DTOEntity getById(Long id) {
        Optional<Address> addressFromDB = addressRepository.findById(id);
        if (addressFromDB.isEmpty()) {
            return null;
        }
        return entityDTOConverter.convertToDto(addressFromDB.get(), new AddressDTO());
    }

    public DTOEntity update(Long id, AddOrUpdateAddressDTO addressDTO) throws Exception {
        Optional<Address> addressFromDB = addressRepository.findById(id);
        if (addressFromDB.isEmpty()) {
            throw new Exception("Address not found");
        }
        Address dbAddress = addressFromDB.get();
        UtilsService.copyNonNullProperties(addressDTO, dbAddress);
        return entityDTOConverter.convertToDto(addressRepository.save(dbAddress), new AddressDTO());
    }

    public Address create(AddOrUpdateAddressDTO addressDTO) {
        Address dbAddress = (Address) entityDTOConverter.convertToEntity(new Address(), addressDTO);
        return addressRepository.save(dbAddress);
    }
}
