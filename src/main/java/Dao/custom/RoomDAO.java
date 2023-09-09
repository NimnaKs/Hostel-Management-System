package Dao.custom;

import Dao.CrudDAO;
import entity.Room;
import org.hibernate.Session;

public interface RoomDAO extends CrudDAO<Room,String> {
    Integer getRoomCount(Session session);
}
