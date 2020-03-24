package com.aportme.aportme.backend;

import com.aportme.aportme.backend.entity.User;
import com.aportme.aportme.backend.repository.UserRepository;
import com.aportme.aportme.backend.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class AportMeBackendApplication implements ApplicationRunner {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(AportMeBackendApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		userRepository.deleteAll();
		List<User> Users = Stream.of(
				new User(1L, "user@user.pl", passwordEncoder.encode("password"), true, Role.ADMIN),
				new User(2L, "fundation@fundation.pl", passwordEncoder.encode("password"), true, Role.FOUNDATION),
				new User(3L, "wojtekzj3bs@ssiepale.pl", passwordEncoder.encode("password"), true, Role.FOUNDATION),
				new User(4L, "admin@admin.pl", passwordEncoder.encode("password"), true, Role.ADMIN)
		).collect(Collectors.toList());

		userRepository.saveAll(Users);
	}

}
