package com.cg.spblaguna.service.receptionist;

import com.cg.spblaguna.model.User;
import com.cg.spblaguna.model.dto.req.ReceptionistReqDTO;
import com.cg.spblaguna.model.enumeration.ERole;
import com.cg.spblaguna.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IReceptionistService extends IGeneralService<User,Long> {


    Page<User> findUsersByRole(ERole role, Pageable pageable);


    ResponseEntity<?> create(ReceptionistReqDTO receptionistReqDTO);


    void changeUser(User receptionistReqDTO1);
}
