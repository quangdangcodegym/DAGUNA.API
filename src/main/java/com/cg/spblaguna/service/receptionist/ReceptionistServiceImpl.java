package com.cg.spblaguna.service.receptionist;

import com.cg.spblaguna.exception.ResourceNotFoundException;
import com.cg.spblaguna.model.Image;
import com.cg.spblaguna.model.User;
import com.cg.spblaguna.model.dto.req.ReceptionistReqDTO;
import com.cg.spblaguna.model.dto.req.SearchBarReceptionistReqDTO;
import com.cg.spblaguna.model.dto.res.ReceptionistResDTO;
import com.cg.spblaguna.model.enumeration.EImageType;
import com.cg.spblaguna.model.enumeration.ERole;
import com.cg.spblaguna.repository.IImageRepository;
import com.cg.spblaguna.repository.IReceptionistRepository;
import com.cg.spblaguna.service.user.IUserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReceptionistServiceImpl implements IReceptionistService {

    @Autowired
    private IReceptionistRepository receptionistRepository;
    @Autowired
    private IUserService userService;

    @Autowired
    private IImageRepository imageRepository;


    @Override
    public List<User> findAll() {
        return receptionistRepository.findUsersByERole(ERole.RECEPTIONIST);
    }

    @Override
    public ReceptionistResDTO update(ReceptionistReqDTO receptionistReqDTO) {
        User user = userService.findById(receptionistReqDTO.getId()).orElseThrow();
        user.setReceptionistInfo(receptionistReqDTO.getReceptionistInfo())
                .setReceptionistName(receptionistReqDTO.getReceptionistName())
                .setDob(receptionistReqDTO.getDob())
                .setAddress(receptionistReqDTO.getAddress())
                .setPhone(receptionistReqDTO.getPhone())
                .setEmail(receptionistReqDTO.getEmail())
                .setCreateAt(LocalDate.now());
        userService.save(user);
        List<Image> images = new ArrayList<>();
        receptionistReqDTO.getAvatarImgId().forEach(a -> {
            Image image1 = imageRepository.findById(a).get();
            image1.setImageType(EImageType.RECEPTIONIST);
            image1.setUser(user);
            imageRepository.save(image1);
            images.add(image1);
        });
        user.setUserImages(images);
        return user.toReceptionistResDTO();
    }

//    @Override
//    public Page<User> findUsersByRole(ERole eRole, Pageable pageable) {
//        return receptionistRepository.findUsersByERole(eRole, pageable);
//    }


    @Override
    public Page<ReceptionistResDTO> findReceptionistResDTOByRole(ERole role, Pageable pageable) {
        Page<ReceptionistResDTO> userList = receptionistRepository.findUsersDTOByERole(role, pageable);
        userList.stream().forEach(e -> {
            String img = userService.findById(e.getId())
                    .map(user -> !user.getUserImages().isEmpty() ? user.getUserImages().get(0).getFileUrl() : "https://res.cloudinary.com/dmrmbvvbi/image/upload/v1710381703/clinic-avatar/50d29b78-fbdc-4932-aba3-e8dffa0c6bb0.png")
                    .orElse("https://res.cloudinary.com/dmrmbvvbi/image/upload/v1710381703/clinic-avatar/50d29b78-fbdc-4932-aba3-e8dffa0c6bb0.png");
            e.setAvatarImgResDTO(img);
        });
        return userList;
    }


    @Override
    public ReceptionistResDTO create(ReceptionistReqDTO receptionistReqDTO) {
        User user = new User();
        user.setDob(receptionistReqDTO.getDob());
        user.setEmail(receptionistReqDTO.getEmail());
        user.setReceptionistName(receptionistReqDTO.getReceptionistName());
        user.setPhone(receptionistReqDTO.getPhone());
        user.setAddress(receptionistReqDTO.getAddress());
        user.setUnlock(true);
        user.setERole(ERole.RECEPTIONIST);
        user.setCreateAt(LocalDate.now());
        user.setReceptionistInfo(receptionistReqDTO.getReceptionistInfo());

        // Lưu đối tượng User vào cơ sở dữ liệu
        user = userService.save(user);

        // Tạo đối tượng ReceptionistResDTO từ đối tượng User
        ReceptionistResDTO receptionistResDTO = new ReceptionistResDTO();
        receptionistResDTO.setReceptionistName(user.getReceptionistName());
        receptionistResDTO.setDob(user.getDob());
        receptionistResDTO.setEmail(user.getEmail());
        receptionistResDTO.setPhone(user.getPhone());
        receptionistResDTO.setELockStatus(user.getELockStatus());
        receptionistResDTO.setAddress(user.getAddress());
        receptionistResDTO.setCreateAt(user.getCreateAt());
        receptionistResDTO.setReceptionistInfo(user.getReceptionistInfo());
        receptionistResDTO.setId(user.getId());

        // Xử lý danh sách hình ảnh (chỉ sửa đổi nếu cần)
        List<Image> images = new ArrayList<>();
        User finalUser = user;
        receptionistReqDTO.getAvatarImgId().forEach(s -> {
            Image image = imageRepository.findById(s).orElseThrow(() -> new ResourceNotFoundException("Image not found"));
            image.setImageType(EImageType.RECEPTIONIST);
            image.setUser(finalUser);
            imageRepository.save(image);
            images.add(image);
        });
        if (images.size() != 0) {
            receptionistResDTO.setAvatarImgResDTO(images.get(0).getFileUrl());
        }else{
            receptionistResDTO.setAvatarImgResDTO("https://res.cloudinary.com/dmrmbvvbi/image/upload/v1710381703/clinic-avatar/50d29b78-fbdc-4932-aba3-e8dffa0c6bb0.png");
        }
        return receptionistResDTO;
    }



    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public User save(User user) {

        return user;
    }

    @Override
    public void deleteById(Long id) {

    }

    public void changeUser(User user) {
        receptionistRepository.save(user);
    }


    public ReceptionistResDTO findReceptionistByIdDTO(Long id) {
        User user = receptionistRepository.findById(id).orElse(null);
        if (user == null) {
            return null;
        }
        return new ReceptionistResDTO(user);
    }

    @Override
    public List<ReceptionistResDTO> searchBarRoomReqDTO(SearchBarReceptionistReqDTO searchBarReceptionistReqDTO) {
        return null;
    }
}
