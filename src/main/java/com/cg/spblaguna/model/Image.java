package com.cg.spblaguna.model;

import com.cg.spblaguna.model.dto.res.ImageResDTO;
import com.cg.spblaguna.model.enumeration.EImageType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String fileName;
    private String fileFolder;
    private String fileUrl;
    private String fileType;
    private String cloudId;

    @Column(name = "image_type")
    @Enumerated(EnumType.STRING)
    private EImageType imageType;

    @Column(name = "id_image_or_id_user")
    private Long idResource;


    @ManyToOne
    @JoinColumn(name = "id_image_or_id_user", insertable = false, updatable = false)
    private Room room;

    @ManyToOne
    @JoinColumn(name = "id_image_or_id_user", insertable = false, updatable = false)
    private User user;
    public ImageResDTO toImageResDTO(){
        return new ImageResDTO(this.id, this.fileUrl);
    }
}