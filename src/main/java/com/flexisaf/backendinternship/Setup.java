package com.flexisaf.backendinternship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.flexisaf.backendinternship.constant.ERole;
import com.flexisaf.backendinternship.entity.RoleEntity;
import com.flexisaf.backendinternship.repository.RoleRepository;

import java.util.Optional;

@Service
public class Setup implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

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
    }
}
