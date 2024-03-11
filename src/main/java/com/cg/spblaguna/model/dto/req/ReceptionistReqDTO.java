package com.cg.spblaguna.model.dto.req;

import com.cg.spblaguna.model.Image;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReceptionistReqDTO {
    private Long id;
    @NotBlank
    @Length(min = 5, max = 50)
    private String receptionistName;
    private LocalDate dob;
    @NotBlank(message = "email không được để trống")
    private String email;
    @NotBlank(message = "phone không được để trống")
    private String phone;
    @NotBlank(message = "địa chỉ không được để trống")
    private String address;
    private LocalDate createAt;
    private List<Image> avatarImgId;
    @NotBlank(message = "Hãy thêm tiểu sử của lễ tân")
    private String receptionistInfo;

}
