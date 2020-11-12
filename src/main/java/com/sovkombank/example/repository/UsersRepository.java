package com.sovkombank.example.repository;

import com.sovkombank.example.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
    public Users findUsersByLogin(String login);
    public Users findUsersByUserId(Long userId);
}
