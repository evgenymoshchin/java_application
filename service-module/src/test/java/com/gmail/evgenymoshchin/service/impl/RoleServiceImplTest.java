package com.gmail.evgenymoshchin.service.impl;

import com.gmail.evgenymoshchin.repository.RoleRepository;
import com.gmail.evgenymoshchin.repository.model.Role;
import com.gmail.evgenymoshchin.repository.model.RoleEnum;
import com.gmail.evgenymoshchin.service.model.RoleDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    RoleServiceImpl roleService;

    @Test
    void shouldReturnEmptyList() {
        List<RoleDTO> roles = roleService.findAll();

        assertTrue(roles.isEmpty());
    }

    @Test
    void shouldReturnRolesList() {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName(RoleEnum.ROLE_ADMINISTRATOR);
        Role role = new Role();
        role.setName(RoleEnum.ROLE_ADMINISTRATOR);

        when(roleRepository.findAll()).thenReturn(Collections.singletonList(role));

        List<RoleDTO> roles = roleService.findAll();

        assertEquals(roles.get(0).getName(), roleDTO.getName());
    }
}