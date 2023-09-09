package Dao;

import Dao.custom.impl.RoomDAOImpl;
import Dao.custom.impl.TenantDAOImpl;
public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory() {
    }
    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }
    public enum DAOTypes {
        TENANT,ROOM
    }
    public SuperDAO getDAO(DAOTypes types){
        switch (types) {
            case TENANT:
                return new TenantDAOImpl();
            case ROOM:
                return new RoomDAOImpl();
            default:
                return null;
        }
    }
}