package com.cg.spblaguna.service.user;

import com.cg.spblaguna.model.User;

import com.cg.spblaguna.model.dto.req.CustomerReqDTO;
import com.cg.spblaguna.model.dto.req.EmailReqDTO;
import com.cg.spblaguna.model.dto.req.ForgotPassword;
import com.cg.spblaguna.model.dto.req.UserReqDTO;
import com.cg.spblaguna.service.IGeneralService;
import jakarta.transaction.Transactional;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;



public interface IUserService extends IGeneralService<User,Long>, UserDetailsService {

    User findUserByEmailAndPhone(String email, String phone);

    User findByUser_Id(Long id);
    boolean forgotPassword(ForgotPassword forgotPassword);
    void register(UserReqDTO userReqDTO);
    boolean confirmEmail(EmailReqDTO emailReqDTO);
}