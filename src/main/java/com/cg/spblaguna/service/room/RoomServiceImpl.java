package com.cg.spblaguna.service.room;

import com.cg.spblaguna.model.Image;
import com.cg.spblaguna.model.KindOfRoom;
import com.cg.spblaguna.model.PerType;
import com.cg.spblaguna.model.Room;
import com.cg.spblaguna.model.dto.req.RoomReqDTO;
import com.cg.spblaguna.model.dto.res.RoomResDTO;
import com.cg.spblaguna.model.enumeration.EImageType;
import com.cg.spblaguna.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoomServiceImpl {
    @Autowired
    private IRoomRepository roomRepository;

    @Autowired
    private IKindOfRoomRepository kindOfRoomRepository;

    @Autowired
    private IPerTypeRepository perTypeRepository;

    @Autowired
    private IRateRespository rateRespository;

    @Autowired
    private IImageRepository imageRepository;

    public List<RoomResDTO> getRooms() {
        List<Room> rooms = roomRepository.findAll();
        return rooms.stream().map(Room::toRoomResDto).collect(Collectors.toList());
    }

    public RoomResDTO save(RoomReqDTO roomReqDTO) {
        KindOfRoom kindOfRoom = kindOfRoomRepository.findById(roomReqDTO.getKindOfRoomId()).get();
        PerType perType = perTypeRepository.findById(roomReqDTO.getPerTypId()).get();

        Room room = new Room(roomReqDTO.getName(),roomReqDTO.getRoomType()
                ,roomReqDTO.getStatusRoom(),
                roomReqDTO.getViewType(),roomReqDTO.getPricePerNight()
                ,roomReqDTO.getAcreage(),
                roomReqDTO.getSleep(),roomReqDTO.getDescription(),
                roomReqDTO.getUtilitie(),kindOfRoom,perType);
        Room roomUpdated = roomRepository.save(room);


        List<Image> images = new ArrayList<>();
        roomReqDTO.getImageIds().forEach(s -> {
            Image image = imageRepository.findById(s).get();
            image.setImageType(EImageType.ROOM);
            image.setIdResource(roomUpdated.getId());
            image.setRoom(roomUpdated);
            imageRepository.save(image);

            images.add(image);
        });
        room.setImages(images);
        // vì sao không trong room khong thể lấy List<Image> images
        return room.toRoomResDto();
    }

    public RoomResDTO update(RoomReqDTO roomReqDTO){
        KindOfRoom kindOfRoom = kindOfRoomRepository.findById(roomReqDTO.getKindOfRoomId()).get();
        PerType perType = perTypeRepository.findById(roomReqDTO.getPerTypId()).get();
        Room room = roomRepository.findById(roomReqDTO.getId()).orElseThrow();
        room.setName(roomReqDTO.getName());
        room.setRoomType(roomReqDTO.getRoomType());
        room.setStatusRoom(roomReqDTO.getStatusRoom());
        room.setViewType(roomReqDTO.getViewType());
        room.setPricePerNight(roomReqDTO.getPricePerNight());
        room.setAcreage(roomReqDTO.getAcreage());
        room.setDescription(roomReqDTO.getDescription());
        room.setUtilitie(roomReqDTO.getUtilitie());
        room.setKingOfRoom(kindOfRoom);
        room.setPerType(perType);

        Room roomUpdated = roomRepository.save(room);

        List<Image> images = new ArrayList<>();
        roomReqDTO.getImageIds().forEach(s -> {
            Image image = imageRepository.findById(s).get();
            image.setImageType(EImageType.ROOM);
            image.setIdResource(roomUpdated.getId());
            image.setRoom(roomUpdated);
            imageRepository.save(image);

            images.add(image);
        });
        room.setImages(images);
        return room.toRoomResDto();
    }

    public void delete(Long id) {
        Room room = roomRepository.findById(id).get();

        List<Image> images = room.getImages();
        imageRepository.deleteAll(images);

        roomRepository.delete(room);
    }


    public Optional<Room> findById(Long id) {
        return roomRepository.findById(id);
    }

    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    public void change(Room room) {
        roomRepository.save(room);
    }
}
