package com.online.store.server.services.implementations;

import com.online.store.server.exceptions.DuplicateElementException;
import com.online.store.server.exceptions.ResourceNotFoundException;
import com.online.store.server.models.Role;
import com.online.store.server.repositories.RoleRepository;
import com.online.store.server.services.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements BaseService<Role, Integer> {
    private final RoleRepository roleRepository;

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role getById(Integer id) {
        return roleRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Role with id %d was not found.", id))
        );
    }

    @Override
    public Role create(Role role) {
        if (roleRepository.existsByName(role.getName())) {
            throw new DuplicateElementException(String.format("Role with name %s already exists.", role.getName()));
        }
        return roleRepository.save(role);
    }

    @Override
    public Role update(Role role, Integer id) {
        getById(id);
        role.setId(id);
        return roleRepository.save(role);
    }

    @Override
    public Role delete(Integer id) {
        Role role = getById(id);
        roleRepository.delete(role);
        return role;
    }
}
