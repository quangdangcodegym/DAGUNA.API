package com.cg.spblaguna.model;

import com.cg.spblaguna.model.dto.res.KindOfRoomResDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "kind_of_rooms")
public class KindOfRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    public KindOfRoomResDTO toKindOfRoomResDTO(){
        return new KindOfRoomResDTO(this.id, this.name);
    }
}
