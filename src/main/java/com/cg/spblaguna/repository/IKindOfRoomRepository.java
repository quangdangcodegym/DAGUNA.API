package com.cg.spblaguna.repository;

import com.cg.spblaguna.model.KindOfRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IKindOfRoomRepository extends JpaRepository<KindOfRoom, Long> {
}
