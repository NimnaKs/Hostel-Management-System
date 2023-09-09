package Bo.custom;

import Bo.SuperBO;
import dto.RoomDto;

import java.util.List;

public interface RoomBO extends SuperBO {
    void saveRoom(RoomDto roomDto);

    void updateRoom(RoomDto roomDto);

    void deleteRoom(String text);

    String generateNextId();

    List<RoomDto> getAllRooms();

    RoomDto viewRoom(String text);
}
