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

    @ManyToOne
    @JoinColumn(name = "id_room")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    public Image(String fileName, String fileFolder, String fileUrl, String fileType, String cloudId, EImageType imageType, Room room) {
        this.fileName = fileName;
        this.fileFolder = fileFolder;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.cloudId = cloudId;
        this.imageType = imageType;
        this.room = room;
    }

    public Image(String fileName, String fileFolder, String fileUrl, String fileType, String cloudId, EImageType imageType, User user) {
        this.fileName = fileName;
        this.fileFolder = fileFolder;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.cloudId = cloudId;
        this.imageType = imageType;
        this.user = user;
    }

    public ImageResDTO toImageResDTO(){
        return new ImageResDTO(this.id, this.fileUrl);
    }
}