package com.cg.spblaguna.repository;

import com.cg.spblaguna.model.User;
import com.cg.spblaguna.model.dto.res.ReceptionistResDTO;
import com.cg.spblaguna.model.enumeration.ERole;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IReceptionistRepository extends JpaRepository<User, Long> {
    List<User> findAllById(long id, Pageable pageable);

    @Query("select u from User u where u.eRole = :role")
    Page<User> findUsersByERole(@Param("role") ERole ERole, Pageable pageable);


    @Query("select u from User u where u.eRole = :role")
    List<User> findUsersByERole(@Param("role") ERole role);
    @Query("select new com.cg.spblaguna.model.dto.res.ReceptionistResDTO (u.id,u.receptionistName,u.dob,u.email,u.phone,u.address,u.createAt,u.receptionistInfo) from User u where u.eRole=:role")
    Page<ReceptionistResDTO> findUsersDTOByERole(@Param("role") ERole role,Pageable pageable);
}
