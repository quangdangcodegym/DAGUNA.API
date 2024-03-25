package com.cg.spblaguna.service.user;

import com.cg.spblaguna.model.User;
import com.cg.spblaguna.service.IGeneralService;

public interface IUserServiceImpl extends IGeneralService<User,Long> {

    User findUserByEmailAndPhone(String email, String phone);
}
