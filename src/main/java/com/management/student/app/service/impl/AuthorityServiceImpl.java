package com.management.student.app.service.impl;

import com.management.student.app.enumeration.RoleType;
import com.management.student.app.model.Role;
import com.management.student.app.model.User;
import com.management.student.app.repository.RoleRepository;
import com.management.student.app.service.AuthorityService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthorityServiceImpl implements AuthorityService {
    private final RoleRepository roleRepository;

    public AuthorityServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Set<Role> findAuthorityByEmail(String userEmail) {
        Set<Role> roleSet = new HashSet<> ();
        Optional<Role> role = roleRepository.findByName (RoleType.ROLE_USER.name ());
        role.ifPresent (roleSet::add);
        if (userEmail.split ("@")[1].equals ("admin.az")) {
            role = roleRepository.findByName (RoleType.ROLE_ADMIN.name ());
            role.ifPresent (roleSet::add);
        }
        return roleSet;
    }

    @Override
    public Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<> ();
        user.getRoles ().forEach (role -> authorities.add(new SimpleGrantedAuthority( role.getName())));
        return authorities;
    }
}
