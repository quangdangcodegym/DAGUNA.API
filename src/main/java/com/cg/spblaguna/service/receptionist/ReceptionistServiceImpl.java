package com.cg.spblaguna.service.receptionist;

import com.cg.spblaguna.model.User;
import com.cg.spblaguna.model.dto.req.ReceptionistReqDTO;
import com.cg.spblaguna.model.dto.res.ReceptionistResDTO;
import com.cg.spblaguna.model.enumeration.ERole;
import com.cg.spblaguna.repository.IReceptionistRepository;
import com.cg.spblaguna.service.user.IUserServiceImpl;
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
    public Page<ReceptionistResDTO> findReceptionistResDTOByRole(ERole role, Pageable pageable) {
        Page<ReceptionistResDTO> userList = receptionistRepository.findUsersDTOByERole(role,pageable);
        userList.stream().forEach(e->{
            String img = userService.findById(e.getId())
                    .map(user -> !user.getUserImages().isEmpty() ? user.getUserImages().get(0).getFileUrl() : "https://bit.ly/499RjtL")
                    .orElse("https://bit.ly/499RjtL");
            e.setAvatarImg(img);
        });
        return userList;
    }

    @Override
    public ResponseEntity<?> create(ReceptionistReqDTO receptionistReqDTO) {
        ReceptionistResDTO receptionistResDTO = new ReceptionistResDTO();
        receptionistResDTO.setReceptionistName(receptionistReqDTO.getReceptionistName());
        receptionistResDTO.setDob(receptionistReqDTO.getDob());
        receptionistResDTO.setEmail(receptionistReqDTO.getEmail());
        receptionistResDTO.setPhone(receptionistReqDTO.getPhone());
        receptionistResDTO.setAddress(receptionistReqDTO.getAddress());
        receptionistResDTO.setCreateAt(LocalDate.now());
        receptionistResDTO.setReceptionistInfo(receptionistReqDTO.getReceptionistInfo());


        // Trả về ResponseEntity chứa đối tượng ReceptionistResDTO đã tạo
        return ResponseEntity.ok(receptionistResDTO);
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
