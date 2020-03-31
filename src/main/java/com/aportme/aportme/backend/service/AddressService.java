package com.aportme.aportme.backend.service;

import com.aportme.aportme.backend.dto.AddressDTO;
import com.aportme.aportme.backend.dto.DTOEntity;
import com.aportme.aportme.backend.entity.Address;
import com.aportme.aportme.backend.repository.AddressRepository;
import com.aportme.aportme.backend.utils.EntityDTOConverter;
import com.aportme.aportme.backend.utils.UtilsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final EntityDTOConverter entityDTOConverter;

    public List<DTOEntity> getAll() {
        return addressRepository.findAll()
                .stream()
                .map((address -> entityDTOConverter.convertToDto(address, new AddressDTO())))
                .collect(Collectors.toList());
    }

    public DTOEntity getById(Long id) {
        Optional<Address> addressFromDB = addressRepository.findById(id);
        if (addressFromDB.isEmpty()) {
            return null;
        }
        return entityDTOConverter.convertToDto(addressFromDB.get(), new AddressDTO());
    }

    public DTOEntity update(Long id, AddressDTO addressDTO) throws Exception {
        Optional<Address> addressFromDB = addressRepository.findById(id);
        if (addressFromDB.isEmpty()) {
            throw new Exception("Address not found");
        }
        Address dbAddress = addressFromDB.get();
        Address convertedEntity = (Address) entityDTOConverter.convertToEntity(new Address(), addressDTO);
        UtilsService.copyNonNullProperties(convertedEntity, dbAddress);
        return entityDTOConverter.convertToDto(addressRepository.save(dbAddress), new AddressDTO());
    }

    public DTOEntity create(AddressDTO addressDTO) {
        Address dbAddress = addressRepository.save((Address) entityDTOConverter.convertToEntity(new Address(), addressDTO));
        return entityDTOConverter.convertToDto(dbAddress, addressDTO);
    }
}
