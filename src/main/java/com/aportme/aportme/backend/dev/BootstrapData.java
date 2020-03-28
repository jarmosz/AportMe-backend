package com.aportme.aportme.backend.dev;

import com.aportme.aportme.backend.entity.Address;
import com.aportme.aportme.backend.entity.foundation.FoundationInfo;
import com.aportme.aportme.backend.entity.user.Role;
import com.aportme.aportme.backend.entity.user.User;
import com.aportme.aportme.backend.entity.user.UserInfo;
import com.aportme.aportme.backend.repository.AddressRepository;
import com.aportme.aportme.backend.repository.FoundationInfoRepository;
import com.aportme.aportme.backend.repository.UserInfoRepository;
import com.aportme.aportme.backend.repository.UserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BootstrapData {

    private final UserRepository userRepository;
    private final FoundationInfoRepository foundationInfoRepository;
    private final UserInfoRepository userInfoRepository;
    private final AddressRepository addressRepository;

    public void createUsers() {
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setEmail("user@user.pl");
            user.setPassword("user1234");
            user.setRole(Role.USER);
            userRepository.save(user);
            createUserInfo(user);
        }
        for (int i = 0; i < 5; i++) {
            User foundation = new User();
            foundation.setEmail("foundation@foundation.pl");
            foundation.setPassword("foundation1234");
            foundation.setRole(Role.FOUNDATION);
            userRepository.save(foundation);
            createFoundationInfo(foundation);
        }
    }

    private void createUserInfo(User user) {
        UserInfo userAdditionalInfo = new UserInfo();
        userAdditionalInfo.setPhoneNumber("123456789");
        userAdditionalInfo.setName("Tomek");
        userAdditionalInfo.setSurname("Nowak");
        userAdditionalInfo.setAddress(createUserAddress());
        userAdditionalInfo.setUser(user);
        userInfoRepository.save(userAdditionalInfo);
    }

    private void createFoundationInfo(User user) {
        FoundationInfo foundationInfo = new FoundationInfo();
        foundationInfo.setName("Koci Szczecin");
        foundationInfo.setNip("1111222233");
        foundationInfo.setAddress(createFoundationAddress());
        foundationInfo.setUser(user);
        foundationInfoRepository.save(foundationInfo);
    }

    private Address createFoundationAddress() {
        Address address = new Address();
        address.setCity("Szczecin");
        address.setStreet("Krzywoustego");
        address.setHouseNumber("76");
        address.setZipCode("71-243");
        address.setFlatNumber("15");
        addressRepository.save(address);
        return address;
    }

    private Address createUserAddress() {
        Address address = new Address();
        address.setCity("Poznan");
        address.setStreet("Polwiejska");
        address.setHouseNumber("24");
        address.setZipCode("61-111");
        address.setFlatNumber("4");
        addressRepository.save(address);
        return address;
    }
}
