package com.cg.spblaguna.service;

import com.cg.spblaguna.model.User;

import java.util.List;
import java.util.Optional;

public interface IGeneralService <E, T>{
    List<E> findAll();

    Optional<E> findById(T id);

    User save(E e);

    void deleteById(T id);
}
