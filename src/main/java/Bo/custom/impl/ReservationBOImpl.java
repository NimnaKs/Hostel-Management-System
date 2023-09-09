package Bo.custom.impl;

import Bo.custom.ReservationBO;
import Bo.util.Converter;
import Dao.DAOFactory;
import Dao.custom.ReservationDAO;
import Dao.custom.RoomDAO;
import Dao.custom.TenantDAO;
import dto.ReservationDto;
import entity.Reservation;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.FactoryConfiguration;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationBOImpl implements ReservationBO {
    private final ReservationDAO reservationDAO;
    private final TenantDAO tenantDAO;

    private final RoomDAO roomDAO;
    public ReservationBOImpl() {
        reservationDAO = (ReservationDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.RESERVATION);
        tenantDAO= (TenantDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.TENANT);
        roomDAO= (RoomDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ROOM);
    }

    @Override
    public ReservationDto viewReservation(String id) {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Reservation entity = null;
        try {
            entity = reservationDAO.search(id, session);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (entity != null) {
            return Converter.getInstance().toReservationDto(entity);
        }
        throw new RuntimeException("Room not found!");
    }

    @Override
    public Integer getReservationCount(String roomTypeId) {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.getTransaction();
        Integer count;
        try (session) {
            transaction.begin();
            count=reservationDAO.getReservationCount(roomTypeId,session);
            transaction.commit();
        } catch (RuntimeException exception) {
            transaction.rollback();
            throw new RuntimeException(exception);
        }
        return count;
    }

    @Override
    public void saveReservation(ReservationDto reservation) {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.getTransaction();
        try (session) {
            transaction.begin();
            reservationDAO.save(Converter.getInstance().toReservationEntity(reservation), session);
            transaction.commit();
        } catch (RuntimeException exception) {
            transaction.rollback();
            throw new RuntimeException(exception);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getLastId() {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        String lastId = null;
        try (session){
            lastId = reservationDAO.generateNewId(session);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (lastId == null) {
            return "Res-0001";
        } else {
            String[] split = lastId.split("[R][e][s][-]");
            int lastDigits = Integer.parseInt(split[1]);
            lastDigits++;
            return (String.format("Res-%04d", lastDigits));
        }
    }

    @Override
    public void deleteReservation(String id) {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.getTransaction();
        try (session) {
            transaction.begin();
            reservationDAO.delete(id, session);
            transaction.commit();
        } catch (RuntimeException exception) {
            transaction.rollback();
            throw new RuntimeException(exception);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ReservationDto> getAll() {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        List<Reservation> reservationList = null;
        try {
            reservationList = reservationDAO.getAll(session);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (reservationList.size() > 0) {
            return reservationList.stream().map(reservation -> Converter.getInstance().toReservationDto(reservation)).collect(Collectors.toList());
        }
        throw new RuntimeException("Empty Room list!");
    }

    @Override
    public void updateReservation(ReservationDto reservation) {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.getTransaction();
        try (session) {
            transaction.begin();
            reservationDAO.update(Converter.getInstance().toReservationEntity(reservation), session);
            transaction.commit();
        } catch (RuntimeException exception) {
            transaction.rollback();
            throw new RuntimeException(exception);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
