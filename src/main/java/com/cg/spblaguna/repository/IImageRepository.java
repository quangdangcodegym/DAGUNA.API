package com.cg.spblaguna.repository;


import com.cg.spblaguna.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IImageRepository extends JpaRepository<Image, String> {

}
