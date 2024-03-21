package com.cg.spblaguna.controller.api;

import com.cg.spblaguna.model.Room;
import com.cg.spblaguna.model.User;
import com.cg.spblaguna.model.dto.req.ReceptionistReqDTO;
import com.cg.spblaguna.model.dto.req.RoomReqDTO;
import com.cg.spblaguna.model.dto.res.ReceptionistResDTO;
import com.cg.spblaguna.model.dto.res.RoomResDTO;
import com.cg.spblaguna.model.enumeration.ELockStatus;
import com.cg.spblaguna.model.enumeration.ERole;
import com.cg.spblaguna.model.enumeration.EStatusRoom;
import com.cg.spblaguna.service.receptionist.IReceptionistService;
import com.cg.spblaguna.service.user.UserService;
import com.cg.spblaguna.util.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/receptionists")
@CrossOrigin(origins = "*")
public class ReceptionistAPI {

    @Autowired
    private IReceptionistService receptionistService;
    @Autowired
    private UserService userService;

    @Autowired
    private AppUtils appUtils;
    /**
     * url: /api/receptionists?page=1&size=10
     * @param pageable
     * @return
     */

    @GetMapping()
    public ResponseEntity<?> getAllReceptionists(Pageable pageable) {
        Page<ReceptionistResDTO> receptionists = receptionistService.findReceptionistResDTOByRole(ERole.RECEPTIONIST, pageable);
        return new ResponseEntity<>(receptionists, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReceptionistById(@PathVariable Long id) {
        ReceptionistResDTO receptionistResDTO = receptionistService.findReceptionistByIdDTO(id);
        if (receptionistResDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(receptionistResDTO);
    }




//    @PatchMapping("/update/{id}")
//    public ResponseEntity<?> updateReceptionists(@PathVariable Long id, @RequestBody ReceptionistReqDTO receptionistReqDTO) {
//        User user = userService.findById(id).get();
//        user.setReceptionistInfo(receptionistReqDTO.getReceptionistInfo())
//                .setReceptionistName(receptionistReqDTO.getReceptionistName())
//                .setDob(receptionistReqDTO.getDob())
//                .setAddress(receptionistReqDTO.getAddress())
//                .setPhone(receptionistReqDTO.getPhone())
//                .setEmail(receptionistReqDTO.getEmail())
//                .setCreateAt(LocalDate.now())
//                .setUserImages(receptionistReqDTO.getAvatarImgId());
//        userService.save(user);
//        user.toReceptionistResDTO();
//        return new ResponseEntity<>(user.toReceptionistResDTO(), HttpStatus.OK);
//    }

    @PutMapping("/{id}")
//    @PreAuthorize("hasAnyRole('MODIFIER')")
    public ResponseEntity<?> updateReceptionist(@PathVariable Long id, @RequestBody ReceptionistReqDTO receptionistReqDTO) {
        receptionistReqDTO.setId(id);
        return new ResponseEntity<>(receptionistService.update(receptionistReqDTO), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createReceptionist(@Validated  @RequestBody ReceptionistReqDTO receptionistReqDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(appUtils.getValidationErrorJson(bindingResult));
        }
        return new ResponseEntity<>(  receptionistService.create(receptionistReqDTO),HttpStatus.OK);
    }

    @PatchMapping("/lock/{id}")
    public ResponseEntity<?> lockReceptionist(@PathVariable Long id){
        User user = userService.findById(id).get();
        user.setELockStatus(ELockStatus.LOCK);
        receptionistService.changeUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PatchMapping("/open/{id}")
    public ResponseEntity<?> openReceptionist(@PathVariable Long id){
        User user = userService.findById(id).get();
        user.setELockStatus(ELockStatus.LOCK);
        receptionistService.changeUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAnyRole('MODIFIER', 'ADMIN')")
    public ResponseEntity<?> deleteReceptionist(@PathVariable Long id) {
        userService.deleteById(id);
        return new ResponseEntity<>(new HashMap<>(), HttpStatus.OK);
    }
}
