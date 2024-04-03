package com.cg.spblaguna.repository;

import com.cg.spblaguna.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface IUserRepository  extends JpaRepository<User, Long> {
    User findUserByEmailAndPhone(String email, String phone);
    Optional<User> findUserByEmail(String email);
    User findUsersById(Long id);
}
