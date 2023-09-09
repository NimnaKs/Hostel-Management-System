package Dao.custom.impl;

import Dao.custom.TenantDAO;
import entity.Tenant;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TenantDAOImpl implements TenantDAO {
    @Override
    public ArrayList<Tenant> getAll(Session session) throws SQLException, ClassNotFoundException {
        Query query = session.createQuery("FROM Tenant",Tenant.class);
        List<Tenant> list = query.list();
        return (ArrayList<Tenant>) list;
    }

    @Override
    public boolean save(Tenant tenant, Session session) throws SQLException, ClassNotFoundException {
        session.save(tenant);
        return true;
    }

    @Override
    public boolean update(Tenant tenant, Session session) throws SQLException, ClassNotFoundException {
        session.update(tenant);
        return true;
    }

    @Override
    public boolean exits(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id, Session session) throws SQLException, ClassNotFoundException {
        Tenant tenant = new Tenant();
        tenant.setStudent_id(id);
        session.delete(tenant);
        return true;
    }

    @Override
    public String generateNewId(Session session) throws SQLException, ClassNotFoundException {
        Query query = session.createQuery("SELECT student_id FROM Tenant ORDER BY student_id DESC");
        query.setMaxResults(1);
        List results = query.list();
        return (results.size() == 0) ? null : (String) results.get(0);
    }

    @Override
    public Tenant search(String id, Session session) throws SQLException, ClassNotFoundException {
        return session.get(Tenant.class, id);
    }

    @Override
    public Integer getRoomCount(Session session) {
        String hql = "SELECT COUNT(id) FROM Tenant ";
        Query<Long> query = session.createQuery(hql, Long.class);
        Long count = query.uniqueResult();
        return Math.toIntExact(count);
    }
}
