package com.cg.spblaguna.service.receptionist;

import com.cg.spblaguna.model.User;
import com.cg.spblaguna.model.dto.req.ReceptionistReqDTO;
import com.cg.spblaguna.model.dto.req.RoomReqDTO;
import com.cg.spblaguna.model.dto.req.SearchBarReceptionistReqDTO;
import com.cg.spblaguna.model.dto.res.ReceptionistResDTO;
import com.cg.spblaguna.model.dto.res.RoomResDTO;
import com.cg.spblaguna.model.enumeration.ERole;
import com.cg.spblaguna.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IReceptionistService extends IGeneralService<User,Long> {

    ReceptionistResDTO update(ReceptionistReqDTO receptionistReqDTO);
    Page<ReceptionistResDTO> findReceptionistResDTOByRole(ERole role, Pageable pageable);


    ReceptionistResDTO create(ReceptionistReqDTO receptionistReqDTO);


    void changeUser(User receptionistReqDTO1);

    ReceptionistResDTO findReceptionistByIdDTO(Long id);

    List<ReceptionistResDTO> searchBarRoomReqDTO(SearchBarReceptionistReqDTO searchBarReceptionistReqDTO);
}
