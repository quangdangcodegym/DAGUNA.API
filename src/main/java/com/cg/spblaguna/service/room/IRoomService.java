package com.cg.spblaguna.service.room;

import com.cg.spblaguna.model.Room;
import com.cg.spblaguna.model.dto.req.RoomFindAvailableRoom;
import com.cg.spblaguna.model.dto.req.RoomInfoReqDTO;
import com.cg.spblaguna.model.dto.req.RoomReqDTO;
import com.cg.spblaguna.model.dto.req.SearchBarRoomReqDTO;
import com.cg.spblaguna.model.dto.res.RoomResDTO;
import com.cg.spblaguna.model.enumeration.ERoomType;
import com.cg.spblaguna.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface IRoomService extends IGeneralService<Room,Long> {
    List<Room> findAllByUser_Unlock(boolean user_unlock);
    Room findByUser_Id(Long id);
    void update(Room room);

    List<RoomResDTO> getRooms();
    RoomResDTO save(RoomReqDTO roomReqDTO) throws RuntimeException;
    RoomResDTO update(RoomReqDTO roomReqDTO);
    void delete(Long id);
    Page<RoomResDTO> searchBarRoomReqDTO(SearchBarRoomReqDTO searchBarRoomReqDTO, Pageable pageable);
    void change(Room room);

    Page<RoomResDTO> filterRooms(String kw, ERoomType roomType,
            Pageable pageable);

    Page<RoomResDTO> filterRoomsByPrice(String kw, ERoomType eRoomType, BigDecimal minPrice, BigDecimal maxPrice, Pageable pagingSort);

    RoomResDTO findByIdDTO(Long id);

    RoomResDTO updateRoom_updateRoomReal(RoomInfoReqDTO roomInfoReqDTO);


//    List<RoomResDTO> searchBarRoomHeader(SearchBarRoomReqDTO searchBarRoomReqDTO);


    List<RoomFindAvailableRoom> findAvailableRoom(LocalDateTime selectFirstDay, LocalDateTime selectLastDay);


    List<RoomFindAvailableRoom> findAvailableRoomHavePer(LocalDateTime selectFirstDay, LocalDateTime selectLastDay, Long current);

    Page<RoomResDTO> findAvailableRoomHavePerWithPageable(LocalDateTime selectFirstDay, LocalDateTime selectLastDay, Long current, Pageable pageable);
}
