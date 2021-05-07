package com.gmail.evgenymoshchin.service.impl;

import com.gmail.evgenymoshchin.repository.RoleRepository;
import com.gmail.evgenymoshchin.repository.model.Role;
import com.gmail.evgenymoshchin.service.RoleService;
import com.gmail.evgenymoshchin.service.model.RoleDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public List<RoleDTO> findAll() {
        List<Role> roles = roleRepository.findAll();
        List<RoleDTO> roleDTOS = new ArrayList<>();
        for (Role role : roles) {
            roleDTOS.add(convertRoleToDTO(role));
        }
        return roleDTOS;
    }

    private RoleDTO convertRoleToDTO(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName(role.getName());
        return roleDTO;
    }
}
