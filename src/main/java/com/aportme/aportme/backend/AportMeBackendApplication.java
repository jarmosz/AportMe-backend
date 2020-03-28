package com.aportme.aportme.backend;

import com.aportme.aportme.backend.dev.BootstrapData;
import com.aportme.aportme.backend.repository.AddressRepository;
import com.aportme.aportme.backend.repository.FoundationInfoRepository;
import com.aportme.aportme.backend.repository.UserInfoRepository;
import com.aportme.aportme.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AportMeBackendApplication implements ApplicationRunner {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private FoundationInfoRepository foundationInfoRepository;
	@Autowired
	private UserInfoRepository userInfoRepository;
	@Autowired
	private AddressRepository addressRepository;


	public static void main(String[] args) {
		SpringApplication.run(AportMeBackendApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		BootstrapData bootstrapData = new BootstrapData(userRepository, foundationInfoRepository, userInfoRepository, addressRepository);
		bootstrapData.createUsers();
	}
}
