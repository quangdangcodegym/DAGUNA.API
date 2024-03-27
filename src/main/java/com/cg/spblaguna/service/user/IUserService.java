package com.cg.spblaguna.service.user;

import com.cg.spblaguna.model.User;

import com.cg.spblaguna.service.IGeneralService;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;


@Service
@Transactional
public interface IUserService extends IGeneralService<User,Long> {

    User findUserByEmailAndPhone(String email, String phone);

}