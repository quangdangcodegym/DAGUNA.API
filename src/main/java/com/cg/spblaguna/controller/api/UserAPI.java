package com.cg.spblaguna.controller.api;


import com.cg.spblaguna.model.User;
import com.cg.spblaguna.model.dto.res.JwtResponse;
import com.cg.spblaguna.model.dto.res.LoginDTO;
import com.cg.spblaguna.model.dto.res.ReceptionistResDTO;
import com.cg.spblaguna.model.enumeration.ERole;
import com.cg.spblaguna.security.LGNUserDetails;
import com.cg.spblaguna.security.jwt.JwtUtils;
import com.cg.spblaguna.service.receptionist.IReceptionistService;
import com.cg.spblaguna.service.room.RoomServiceImpl;
import com.cg.spblaguna.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;


@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserAPI {

    @Autowired
    private IReceptionistService receptionistService;

    @Autowired
    private RoomServiceImpl roomService;

    @Autowired
    private IUserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserByID(@PathVariable Long id) {
        User user = userService.findById(id).get();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/user_login/{storedUserId}")
    public ResponseEntity<?> getUserIDUser(@PathVariable Long storedUserId) {
        User user = userService.findById(storedUserId).orElse(null);
        if (user != null) {
            if (user.getERole() == ERole.ROLE_RECEPTIONIST) {
                ReceptionistResDTO receptionist = receptionistService.findReceptionistByIdDTO(storedUserId);
                return ResponseEntity.ok(receptionist);
            } else if (user.getERole() == ERole.ROLE_CUSTOMER) {
                User user1 = userService.findByUser_Id (storedUserId);
                return ResponseEntity.ok(user1);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
            );
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Mật khẩu không đúng. Vui lòng nhập lạillll.");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtTokenForUser(authentication);
        LGNUserDetails userDetails = (LGNUserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        User user = userService.findById(userDetails.getId()).get();
        if (user.isUnlock()) {
            JwtResponse jwtResponse = new JwtResponse(
                    userDetails.getId(),
                    userDetails.getEmail(),
                    jwt,
                    roles
            );
            ResponseCookie springCookie = ResponseCookie.from("JWT", jwt)
                    .httpOnly(false)
                    .secure(false)
                    .sameSite("None")
                    .path("/")
                    .maxAge(60 * 1000)
//                    .domain(".localhost")
                    .domain("192.168.1.64")
                    .build();

            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.SET_COOKIE, springCookie.toString())
                    .body(jwtResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tài khoản đã bị khóa.");
        }
    }




}
