package com.aportme.aportme.backend.dev;

import com.aportme.aportme.backend.component.address.entity.Address;
import com.aportme.aportme.backend.component.address.repository.AddressRepository;
import com.aportme.aportme.backend.component.foundation.entity.FoundationInfo;
import com.aportme.aportme.backend.component.foundation.repository.FoundationInfoRepository;
import com.aportme.aportme.backend.component.pet.entity.Pet;
import com.aportme.aportme.backend.component.pet.entity.PetPicture;
import com.aportme.aportme.backend.component.pet.enums.AgeCategory;
import com.aportme.aportme.backend.component.pet.enums.AgeSuffix;
import com.aportme.aportme.backend.component.pet.enums.PetSize;
import com.aportme.aportme.backend.component.pet.enums.PetType;
import com.aportme.aportme.backend.component.user.enums.Role;
import com.aportme.aportme.backend.component.user.entity.User;
import com.aportme.aportme.backend.component.user.repository.UserRepository;
import com.aportme.aportme.backend.component.userInfo.entity.UserInfo;
import com.aportme.aportme.backend.component.pet.repository.PetRepository;
import com.aportme.aportme.backend.component.pet.repository.PictureRepository;
import com.aportme.aportme.backend.component.userInfo.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class BootstrapData implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;
    private final FoundationInfoRepository foundationInfoRepository;
    private final UserInfoRepository userInfoRepository;
    private final AddressRepository addressRepository;
    private final PetRepository petRepository;
    private final PictureRepository pictureRepository;

    private String[] names = {"Jacek", "Dawid", "Mateusz", "Wojciech"};
    private String[] surnames = {"Krakowski", "Wietrzych", "Lesiecki", "Jarmosz"};
    private String[] phoneNumbers = {"567234123", "789567345", "500400300", "567678789"};
    private String[] foundationNames = {"Schronisko", "Fundacja", "Fundacja o nazwie schronisko", "Schronisko o nazwie fundacja"};

    private String[] petNames = {"Domino", "Bogusław", "Sułtan", "Bomba", "Michał", "Mandaryna", "Marik", "Bogdan", "Pablo", "Staszek", "Frytka", "Czarek"};
    private String[] dogBreeds = {"kundel", "Labrador", "Husky", "Golden", "Owczarek niemiecki", "Mops", "Buldog", "Pudel"};
    private String[] catBreeds = {"dachowiec", "Kot perski", "Kot bengalski", "Sfinks", "Ragdoll"};
    private String[] behaviors = {"przyjazny", "musi się oswoić", "trochę agresywny", "wymaga uwagi", "wychowywać, obserwować"};
    private String[] descriptions = {"Trochę dłuższy opis dotyczący zwierzęta w którym można zawrzeć dodatkowe informacje nieuwzględnione w wyróżnionych polach"};

    private String[] base64Pictures = new BootstrapPictures().getPictures();

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        createData();
    }

    private void createData() {
        List<Address> addresses = createAddresses();
        for (int i = 0; i < 4; i++) {
            createUser("user" + i + "@gmail.com", UUID.randomUUID().toString(), phoneNumbers[i], names[i], surnames[i], addresses.get(i));
        }
        for (int i = 0; i < 4; i++) {
            createFoundation("foundation" + i + "@gmail.com", UUID.randomUUID().toString(), phoneNumbers[i], foundationNames[i], String.format("%10d", i), addresses.get(i));
        }
    }

    private List<Address> createAddresses() {
        List<Address> addresses = new ArrayList<>();
        addresses.add(createAddress("Poznań", "Półwiejska", "20", "61-681", "12"));
        addresses.add(createAddress("Sierpc", "Płocka", "40", "09-400", "50"));
        addresses.add(createAddress("Warszawa", "Domaniewska", "80", "02-672", "15"));
        addresses.add(createAddress("Płock", "Armii Krajowej", "60", "09-410", "30"));
        return addresses;
    }

    private void createUser(String email, String password, String phoneNumber, String name, String surname, Address address) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(Role.USER);
        userRepository.save(user);
        UserInfo userInfo = new UserInfo();
        userInfo.setPhoneNumber(phoneNumber);
        userInfo.setName(name);
        userInfo.setSurname(surname);
        userInfo.setAddress(address);
        userInfo.setUser(user);
        userInfoRepository.save(userInfo);
    }

    private void createFoundation(String email, String password, String phoneNumber, String name, String nip, Address address) {
        User foundation = new User();
        foundation.setEmail(email);
        foundation.setPassword(password);
        foundation.setRole(Role.FOUNDATION);
        userRepository.save(foundation);
        FoundationInfo foundationInfo = new FoundationInfo();
        foundationInfo.setName(name);
        foundationInfo.setNip(nip);
        foundationInfo.setAddress(address);
        foundationInfo.setPhoneNumber(phoneNumber);
        foundationInfo.setUser(foundation);
        foundationInfoRepository.save(foundationInfo);

        createPets(foundationInfo);
    }

    private Address createAddress(String city, String street, String houseNumber, String zipCode, String flatNumber) {
        Address address = new Address();
        address.setCity(city);
        address.setStreet(street);
        address.setHouseNumber(houseNumber);
        address.setZipCode(zipCode);
        address.setFlatNumber(flatNumber);
        addressRepository.save(address);
        return address;
    }

    private void createPets(FoundationInfo foundationInfo) {
        Random random = new Random();

        for(int i=1; i<6; i++) {
            createPet(
                    petNames[ThreadLocalRandom.current().nextInt(0, petNames.length)],
                    dogBreeds[ThreadLocalRandom.current().nextInt(0, dogBreeds.length)],
                    i,
                    AgeSuffix.values()[(int)(Math.random()*AgeSuffix.values().length)],
                    PetSize.values()[(int)(Math.random()*PetSize.values().length)],
                    PetType.DOG,
                    "",
                    behaviors[ThreadLocalRandom.current().nextInt(0, behaviors.length)],
                    behaviors[ThreadLocalRandom.current().nextInt(0, behaviors.length)],
                    random.nextBoolean(),
                    random.nextBoolean(),
                    descriptions[ThreadLocalRandom.current().nextInt(0, descriptions.length)],
                    foundationInfo
            );
        }

        for(int i=1; i<4; i++) {
            createPet(
                    petNames[ThreadLocalRandom.current().nextInt(0, petNames.length)],
                    catBreeds[ThreadLocalRandom.current().nextInt(0, catBreeds.length)],
                    i,
                    AgeSuffix.values()[(int)(Math.random()*AgeSuffix.values().length)],
                    PetSize.values()[(int)(Math.random()*PetSize.values().length)],
                    PetType.CAT,
                    "",
                    behaviors[ThreadLocalRandom.current().nextInt(0, behaviors.length)],
                    behaviors[ThreadLocalRandom.current().nextInt(0, behaviors.length)],
                    random.nextBoolean(),
                    random.nextBoolean(),
                    descriptions[ThreadLocalRandom.current().nextInt(0, descriptions.length)],
                    foundationInfo
            );
        }
    }

    private void createPet(String name, String breed, int age, AgeSuffix ageSuffix, PetSize petSize, PetType petType, String diseases, String behaviorToChildren, String behaviorToAnimals, Boolean trainingNeeded, Boolean behavioristNeeded, String description, FoundationInfo foundationInfo) {
        Pet pet = new Pet();
        pet.setAge(age);
        pet.setAgeCategory(prepareAgeCategory(age, ageSuffix));
        pet.setAgeSuffix(ageSuffix);
        pet.setBehavioristNeeded(behavioristNeeded);
        pet.setBehaviorToAnimals(behaviorToAnimals);
        pet.setBehaviorToChildren(behaviorToChildren);
        pet.setBreed(breed);
        pet.setDescription(description);
        pet.setDiseases(diseases);
        pet.setFoundationInfo(foundationInfo);
        pet.setName(name);
        pet.setSize(petSize);
        pet.setPetType(petType);
        pet.setTrainingNeeded(trainingNeeded);
        petRepository.save(pet);

        addPictures(pet);
    }

    private void addPictures(Pet pet) {
        for(int i=0; i<3; i++) {
            PetPicture picture = new PetPicture();
            picture.setPictureInBase64(base64Pictures[i]);
            picture.setPet(pet);
            pictureRepository.save(picture);
        }
    }

    private AgeCategory prepareAgeCategory(int age, AgeSuffix ageSuffix) {
        if (age <= 5 && ageSuffix.equals(AgeSuffix.MONTHS)) {
            return AgeCategory.YOUNG;
        } else if (age > 3 && ageSuffix.equals(AgeSuffix.YEARS)) {
            return AgeCategory.SENIOR;
        } else {
            return AgeCategory.NORMAL;
        }
    }
}
