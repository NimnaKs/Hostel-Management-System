package Dao.custom.impl;

import Dao.custom.ReservationDAO;
import entity.Reservation;
import entity.Tenant;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAOImpl implements ReservationDAO {
    @Override
    public ArrayList<Reservation> getAll(Session session) throws SQLException, ClassNotFoundException {
        Query query = session.createQuery("FROM Reservation ", Reservation.class);
        List<Reservation> list = query.list();
        return (ArrayList<Reservation>) list;
    }

    @Override
    public boolean save(Reservation reservation, Session session) throws SQLException, ClassNotFoundException {
        session.save(reservation);
        return true;
    }

    @Override
    public boolean update(Reservation reservation, Session session) throws SQLException, ClassNotFoundException {
        session.update(reservation);
        return true;
    }

    @Override
    public boolean exits(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id, Session session) throws SQLException, ClassNotFoundException {
        Reservation reservation = new Reservation();
        reservation.setRes_id(id);
        session.delete(reservation);
        return true;
    }

    @Override
    public String generateNewId(Session session) throws SQLException, ClassNotFoundException {
        Query query = session.createQuery("SELECT res_id FROM Reservation ORDER BY res_id DESC");
        query.setMaxResults(1);
        List results = query.list();
        return (results.size() == 0) ? null : (String) results.get(0);
    }

    @Override
    public Reservation search(String id, Session session) throws SQLException, ClassNotFoundException {
        return session.get(Reservation.class, id);
    }

    @Override
    public Integer getReservationCount(String roomTypeId, Session session) {
        String hql = "SELECT COUNT(*) FROM Reservation r WHERE r.room.room_type_id = :roomId";
        Query<Long> query = session.createQuery(hql, Long.class);
        query.setParameter("roomId", roomTypeId);
        Long count = query.uniqueResult();
        return Math.toIntExact(count);
    }
}
