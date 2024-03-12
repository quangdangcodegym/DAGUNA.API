package com.cg.spblaguna.repository;

import com.cg.spblaguna.model.Utilitie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUtilityRepository extends JpaRepository<Utilitie,Long> {

}
