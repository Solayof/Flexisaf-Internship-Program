package com.flexisaf.backendinternship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.flexisaf.backendinternship.constant.ERole;
import com.flexisaf.backendinternship.constant.UserType;
import com.flexisaf.backendinternship.entity.RoleEntity;
import com.flexisaf.backendinternship.entity.UserEntity;
import com.flexisaf.backendinternship.entity.UserTypeEntity;
import com.flexisaf.backendinternship.repository.RoleRepository;
import com.flexisaf.backendinternship.repository.UserRepository;
import com.flexisaf.backendinternship.repository.UserTypeRepository;
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

    @Autowired
    private UserTypeRepository userTypeRepository;

    @Override
    public void run(String... args) throws Exception {
        Optional<RoleEntity> optRoleSuperAdmin = roleRepository.findByName(ERole.ROLE_SUPERADMIN);
        if(optRoleSuperAdmin.isEmpty()){
            RoleEntity role = new RoleEntity();
            role.setName(ERole.ROLE_SUPERADMIN);
            roleRepository.save(role);
        }

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

        Optional<RoleEntity> optRoleRead = roleRepository.findByName(ERole.READ);
        if(optRoleRead.isEmpty()){
            RoleEntity role = new RoleEntity();
            role.setName(ERole.READ);
            roleRepository.save(role);
        }

        Optional<RoleEntity> optRoleWrite = roleRepository.findByName(ERole.WRITE);
        if(optRoleWrite.isEmpty()){
            RoleEntity role = new RoleEntity();
            role.setName(ERole.WRITE);
            roleRepository.save(role);
        }


        Optional<UserTypeEntity> optTeacher = userTypeRepository.findByName(UserType.TEACHER);
        if(optTeacher.isEmpty()){
            UserTypeEntity userTypeEntity = new UserTypeEntity();
            userTypeEntity.setName(UserType.TEACHER);
            userTypeRepository.save(userTypeEntity);
        }

        Optional<UserTypeEntity> optStudent = userTypeRepository.findByName(UserType.STUDENT);
        if(optStudent.isEmpty()){
            UserTypeEntity userTypeEntity = new UserTypeEntity();
            userTypeEntity.setName(UserType.STUDENT);
            userTypeRepository.save(userTypeEntity);
        }

        Optional<UserTypeEntity> optParent = userTypeRepository.findByName(UserType.PARENT);
        if(optParent.isEmpty()){
            UserTypeEntity userTypeEntity = new UserTypeEntity();
            userTypeEntity.setName(UserType.PARENT);
            userTypeRepository.save(userTypeEntity);
        }

        Faker faker = new Faker();

        List<String> genders = List.of("Male", "Female");
        List<UserTypeEntity> userTypeEntities = userTypeRepository.findAll();

        for (int i = 0; i < 10; i++) {
            UserEntity sign = new UserEntity();
            sign.setCreatedAt(LocalDateTime.now());
            sign.setDob(LocalDate.now());
            sign.setEmail(faker.internet().emailAddress());
            sign.setFirstName(faker.name().firstName());
            sign.setMiddleName(faker.name().lastName());
            sign.setLastName(faker.name().lastName());
            sign.setGender(genders.get(new Random().nextInt(genders.size())));
            sign.setUserType(userTypeEntities.get(new Random().nextInt(userTypeEntities.size())));
            sign.setPassword(faker.lorem().word());

            Optional<UserEntity> user = userRepository.findByEmail(sign.getEmail());

            if (user.isEmpty()) {
                userRepository.save(sign);
            }
            
        }
    }
}
