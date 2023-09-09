package Dao.custom;

import Dao.CrudDAO;
import entity.Tenant;
import org.hibernate.Session;

public interface TenantDAO extends CrudDAO<Tenant,String> {
    Integer getRoomCount(Session session);
}
