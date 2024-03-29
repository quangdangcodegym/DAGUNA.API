package com.cg.spblaguna.service.user;

import com.cg.spblaguna.model.User;
import com.cg.spblaguna.model.dto.req.CustomerReqDTO;
import com.cg.spblaguna.model.dto.req.EmailReqDTO;
import com.cg.spblaguna.model.dto.req.ForgotPassword;
import com.cg.spblaguna.model.dto.req.UserReqDTO;
import com.cg.spblaguna.model.enumeration.ERole;
import com.cg.spblaguna.repository.IUserRepository;
import com.cg.spblaguna.security.LGNUserDetails;
import com.cg.spblaguna.util.EmailUtil;
import com.cg.spblaguna.util.PasswordEncryptionUtil;
import com.cg.spblaguna.util.RandomCode;
import com.cg.spblaguna.util.SendEmail;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional

public class UserServiceImpl implements IUserService {
    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private IUserRepository iUserRepository;
    @Override
    public List<User> findAll() {
        return iUserRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return iUserRepository.findById(id);
    }

    @Override
    public User save(User user) {
        iUserRepository.save(user);
        return user;
    }

    @Override
    public void deleteById(Long id) {
        iUserRepository.deleteById(id);
    }

    @Override
    public User findUserByEmailAndPhone(String email, String phone) {
        return iUserRepository.findUserByEmailAndPhone(email, phone);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user =  iUserRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return LGNUserDetails.buildUserDetails(user);

    }

    public User findByUser_Id(Long id) {
        return iUserRepository.findUsersById(id);
    }

    @Override
    public boolean forgotPassword(ForgotPassword forgotPassword) {
        User user = iUserRepository.findUserByEmail(forgotPassword.getEmail()).get();
        return user != null && (forgotPassword.getCode()).equals(user.getCode());
    }

    @Override
    public void register(UserReqDTO userReqDTO) {
        User user = new User();
        user.setEmail(user.getEmail());
        user.setPassword(PasswordEncryptionUtil.encryptPassword(userReqDTO.getPassword()));
        user.setERole(ERole.ROLE_RECEPTIONIST);
        iUserRepository.save(user);
    }



    @Override
    public boolean confirmEmail(EmailReqDTO emailReqDTO) {
        User user = iUserRepository.findUserByEmail(emailReqDTO.getEmail()).get();
        if (user != null) {
            String email=emailReqDTO.getEmail();
            String title="Yêu cầu đặt lại mật khẩu";
            String code = RandomCode.generateRandomCode(6);
            user.setCode(code);
            iUserRepository.save(user);
            String body= SendEmail.EmailResetPassword(user.getEmail(),code);
            emailUtil.sendEmail(email,title,body);
            return true;
        } else {
            return false;
        }
    }
}
