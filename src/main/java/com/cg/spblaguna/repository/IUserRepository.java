package com.cg.spblaguna.repository;

import com.cg.spblaguna.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository  extends JpaRepository<User, Long> {
    User findUserByEmailAndPhone(String email, String phone);
}
