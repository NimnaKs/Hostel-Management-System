package Dao.custom.impl;

import Dao.custom.RoomDAO;
import entity.Room;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomDAOImpl implements RoomDAO {
    @Override
    public ArrayList<Room> getAll(Session session) throws SQLException, ClassNotFoundException {
        try (session) {
            String sql = "FROM Room";
            Query query = session.createQuery(sql);
            List list = query.list();
            return (ArrayList<Room>) list;
        }
    }

    @Override
    public boolean save(Room room, Session session) throws SQLException, ClassNotFoundException {
        session.save(room);
        return true;
    }

    @Override
    public boolean update(Room room, Session session) throws SQLException, ClassNotFoundException {
        session.update(room);
        return true;
    }

    @Override
    public boolean exits(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id, Session session) throws SQLException, ClassNotFoundException {
        Room room = session.get(Room.class, id);
        if (room != null) {
            session.delete(room);
            return true;
        }
        return false; // Entity not found, return false or handle it accordingly
    }

    @Override
    public String generateNewId(Session session) throws SQLException, ClassNotFoundException {
        try (session) {
            Query query = session.createQuery("SELECT room_type_id FROM Room ORDER BY room_type_id DESC");
            query.setMaxResults(1);
            List results = query.list();
            return (results.size() == 0) ? null : (String) results.get(0);
        }
    }

    @Override
    public Room search(String id, Session session) throws SQLException, ClassNotFoundException {
        try (session) {
            return session.get(Room.class, id);
        }
    }

    @Override
    public Integer getRoomCount(Session session) {
        String hql = "SELECT COUNT(qty) FROM Room";
        Query<Long> query = session.createQuery(hql, Long.class);
        Long count = query.uniqueResult();
        return Math.toIntExact(count);
    }
}
