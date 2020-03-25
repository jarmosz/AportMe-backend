package com.aportme.aportme.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication(scanBasePackages = { "com.*"})
public class AportMeBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AportMeBackendApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}

//	@Override
//	public void run(ApplicationArguments args) throws Exception {
//		userRepository.deleteAll();
//		List<ApplicationUser> Users = Stream.of(
//				new ApplicationUser(1L, "user@user.pl", bCryptPasswordEncoder.encode("password"), true, Role.ADMIN),
//				new ApplicationUser(2L, "fundation@fundation.pl", bCryptPasswordEncoder.encode("password"), true, Role.FOUNDATION),
//				new ApplicationUser(3L, "wojtekzj3bs@ssiepale.pl", passwordEncoder.encode("password"), true, Role.FOUNDATION),
//				new ApplicationUser(4L, "admin@admin.pl", passwordEncoder.encode("password"), true, Role.ADMIN)
//		).collect(Collectors.toList());
//
//		userRepository.saveAll(Users);
//	}

//}
