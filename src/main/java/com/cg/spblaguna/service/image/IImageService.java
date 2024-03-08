package com.cg.spblaguna.service.image;

import com.cg.spblaguna.model.Image;
import com.cg.spblaguna.model.dto.res.ImageResDTO;
import com.cg.spblaguna.service.IGeneralService;

import java.util.List;

public interface IImageService extends IGeneralService<Image, Long> {
    List<ImageResDTO> findAllImageResDTOs();
}
