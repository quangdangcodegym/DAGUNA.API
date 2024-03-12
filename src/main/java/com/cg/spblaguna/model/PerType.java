package com.cg.spblaguna.model;

import com.cg.spblaguna.model.dto.res.KindOfRoomResDTO;
import com.cg.spblaguna.model.dto.res.PerTypeResDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "per_types")
public class PerType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    public PerTypeResDTO toPerTypeResDTO(){
        return new PerTypeResDTO(this.id, this.name);
    }
}
