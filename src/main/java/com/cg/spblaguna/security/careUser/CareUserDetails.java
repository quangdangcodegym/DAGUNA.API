//package com.cg.spblaguna.security.careUser;
//
//
//import com.cg.spblaguna.model.User;
//import com.cg.spblaguna.model.enumeration.ERole;
//import lombok.AllArgsConstructor;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//public class CareUserDetails implements UserDetails {
//    private Long id;
//    private String email;
//    private String password;
//    private ERole eRole;
//    private Collection<GrantedAuthority> authorities;
//
//    public static CareUserDetails buildUserDetails(CareUserDetails careUserDetails){
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(careUserDetails.getRole().name());
//        authorities.add(authority);
//        return new CareUserDetails(
//                careUserDetails.getId(),
//                careUserDetails.getEmail(),
//                careUserDetails.getPassword(),
//                careUserDetails.getRole(),
//                authorities);
//
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return authorities;
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//
//    public ERole getRole(){
//        return eRole;
//    }
//
//    @Override
//    public String getUsername() {
//        return email;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}
