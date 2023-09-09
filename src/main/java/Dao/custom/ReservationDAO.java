package Dao.custom;

import Dao.CrudDAO;
import entity.Reservation;
import org.hibernate.Session;

public interface ReservationDAO extends CrudDAO<Reservation,String> {
    Integer getReservationCount(String roomTypeId, Session session);
}
