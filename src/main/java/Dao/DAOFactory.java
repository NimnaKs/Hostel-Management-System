package Dao;

import Dao.custom.impl.ReservationDAOImpl;
import Dao.custom.impl.RoomDAOImpl;
import Dao.custom.impl.TenantDAOImpl;
import Dao.custom.impl.UserDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory() {
    }
    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }
    public enum DAOTypes {
        TENANT,ROOM,USER,RESERVATION
    }
    public SuperDAO getDAO(DAOTypes types){
        switch (types) {
            case TENANT:
                return new TenantDAOImpl();
            case ROOM:
                return new RoomDAOImpl();
            case USER:
                return new UserDAOImpl();
            case RESERVATION:
                return new ReservationDAOImpl();
            default:
                return null;
        }
    }
}