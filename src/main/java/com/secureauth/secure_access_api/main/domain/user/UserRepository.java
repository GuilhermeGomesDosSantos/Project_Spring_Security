package com.secureauth.secure_access_api.main.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository <User, Long> {
    UserDetails findByLogin(String login);
}
