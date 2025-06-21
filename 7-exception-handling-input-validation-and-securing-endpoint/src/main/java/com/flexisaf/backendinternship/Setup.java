package com.flexisaf.backendinternship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.flexisaf.backendinternship.constant.ERole;
import com.flexisaf.backendinternship.entity.RoleEntity;
import com.flexisaf.backendinternship.entity.UserEntity;
import com.flexisaf.backendinternship.repository.RoleRepository;
import com.flexisaf.backendinternship.repository.UserRepository;
import com.github.javafaker.Faker;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class Setup implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        Optional<RoleEntity> optRoleAdmin = roleRepository.findByName(ERole.ROLE_ADMIN);
        if(optRoleAdmin.isEmpty()){
            RoleEntity role = new RoleEntity();
            role.setName(ERole.ROLE_ADMIN);
            roleRepository.save(role);
        }

        Optional<RoleEntity> optRoleOwn = roleRepository.findByName(ERole.ROLE_MODERATOR);
        if(optRoleOwn.isEmpty()){
            RoleEntity role = new RoleEntity();
            role.setName(ERole.ROLE_MODERATOR);
            roleRepository.save(role);
        }

        Optional<RoleEntity> optRoleMe = roleRepository.findByName(ERole.ROLE_USER);
        if(optRoleMe.isEmpty()){
            RoleEntity role = new RoleEntity();
            role.setName(ERole.ROLE_USER);
            roleRepository.save(role);
        }

        Faker faker = new Faker();

        List<String> genders = List.of("Male", "Female");

        for (int i = 0; i < 10; i++) {
            UserEntity sign = new UserEntity();
            sign.setCreatedAt(LocalDateTime.now());
            sign.setDob(LocalDate.now());
            sign.setEmail(faker.internet().emailAddress());
            sign.setFirstName(faker.name().firstName());
            sign.setMiddleName(faker.name().lastName());
            sign.setLastName(faker.name().lastName());
            sign.setGender(genders.get(new Random().nextInt(genders.size())));
            sign.setPassword(faker.lorem().word());

            Optional<UserEntity> user = userRepository.findByEmail(sign.getEmail());

            if (user.isEmpty()) {
                userRepository.save(sign);
            }
            
        }
    }
}
