package com.cg.spblaguna.service.receptionist;

import com.cg.spblaguna.model.Room;
import com.cg.spblaguna.model.User;
import com.cg.spblaguna.model.dto.req.ReceptionistReqDTO;
import com.cg.spblaguna.model.dto.req.RoomReqDTO;
import com.cg.spblaguna.model.dto.res.RoomResDTO;
import com.cg.spblaguna.model.enumeration.ELockStatus;
import com.cg.spblaguna.model.enumeration.ERole;
import com.cg.spblaguna.repository.IReceptionistRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReceptionistServiceImpl implements IReceptionistService{

    @Autowired
    private IReceptionistRepository receptionistRepository;


    @Override
    public List<User> findAll() {
        return receptionistRepository.findUsersByERole(ERole.RECEPTIONIST);
    }

    @Override
    public Page<User> findUsersByRole(ERole eRole, Pageable pageable) {
        return receptionistRepository.findUsersByERole(eRole, pageable);
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

    @Override
    public ResponseEntity<?> create(ReceptionistReqDTO receptionistReqDTO) {

//        String password = RandomCode.generateRandomCode(6);
        // Tạo tiêu đề và nội dung email
//        String title = "Chúc mừng! Tài khoản đã được tạo thành công";
//        String body = SendEmail.EmailRegisterDoctor(receptionistReqDTO.getReceptionistName(), password, receptionistReqDTO.getEmail());
        User user = new User();
        user.setEmail(receptionistReqDTO.getEmail());
        user.setDob(receptionistReqDTO.getDob());
        user.setReceptionistName(receptionistReqDTO.getReceptionistName());
        user.setCreateAt(LocalDate.now());
        user.setPhone(receptionistReqDTO.getPhone());
        user.setReceptionistInfo(receptionistReqDTO.getReceptionistInfo());
        user.setAddress(receptionistReqDTO.getAddress());
        user.setERole(ERole.RECEPTIONIST);
        user.setUnlock(true);
        User savedUser = receptionistRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
}
