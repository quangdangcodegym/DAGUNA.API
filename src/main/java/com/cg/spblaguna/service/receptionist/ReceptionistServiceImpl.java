package com.cg.spblaguna.service.receptionist;

import com.cg.spblaguna.model.Room;
import com.cg.spblaguna.model.User;
import com.cg.spblaguna.model.dto.req.ReceptionistReqDTO;
import com.cg.spblaguna.model.dto.req.RoomReqDTO;
import com.cg.spblaguna.model.dto.res.RoomResDTO;
import com.cg.spblaguna.model.enumeration.ELockStatus;
import com.cg.spblaguna.model.enumeration.ERole;
import com.cg.spblaguna.repository.IReceptionistRepository;
import com.cg.spblaguna.service.user.IUserServiceImpl;
import com.cg.spblaguna.service.user.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReceptionistServiceImpl implements IReceptionistService{

    @Autowired
    private IReceptionistRepository receptionistRepository;
    @Autowired
    private IUserServiceImpl userService;


    @Override
    public List<User> findAll() {
        return receptionistRepository.findUsersByERole(ERole.RECEPTIONIST);
    }

    @Override
    public Page<User> findUsersByRole(ERole eRole, Pageable pageable) {
        return receptionistRepository.findUsersByERole(eRole, pageable);
    }

    @Override
    public ResponseEntity<?> create(ReceptionistReqDTO receptionistReqDTO) {
        return null;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void save(User user) {

    }

    @Override
    public void deleteById(Long id) {

    }
    public void changeUser(User user) {
        receptionistRepository.save(user);
    }


    public ResponseEntity<?> updateReceptionists(@PathVariable Long id, ReceptionistReqDTO receptionistReqDTO) {
        User user = userService.findById(id).get();
        user.setReceptionistInfo(receptionistReqDTO.getReceptionistInfo())
                .setReceptionistName(receptionistReqDTO.getReceptionistName())
                .setDob(receptionistReqDTO.getDob())
                .setAddress(receptionistReqDTO.getAddress())
                .setPhone(receptionistReqDTO.getPhone())
                .setEmail(receptionistReqDTO.getEmail())
                .setCreateAt(LocalDate.now())
                .setUserImages(receptionistReqDTO.getAvatarImgId());
        userService.save(user);
        return new ResponseEntity<>( HttpStatus.OK);
    }
}
