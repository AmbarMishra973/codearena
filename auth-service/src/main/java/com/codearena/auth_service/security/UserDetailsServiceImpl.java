package com.codearena.auth_service.security;

import com.codearena.auth_service.entity.User;
import com.codearena.auth_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("🔍 Loading user by email: " + email);

        User user = userRepository.findByEmail(email)  // ✅ lookup by email
                .orElseThrow(() -> {
                    System.out.println("❌ User not found: " + email);
                    return new UsernameNotFoundException("User Not Found with email: " + email);
                });

        System.out.println("✅ Found user: " + user.getUsername());
        System.out.println("🔐 Stored password (hashed): " + user.getPassword());
        return UserDetailsImpl.build(user);
    }

}
