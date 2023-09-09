package Bo.custom.impl;

import Bo.custom.RoomBO;
import Bo.util.Converter;
import Dao.DAOFactory;
import Dao.custom.RoomDAO;
import Dao.custom.TenantDAO;
import dto.RoomDto;
import entity.Room;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.FactoryConfiguration;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class RoomBOImpl implements RoomBO {

    private final RoomDAO roomDAO;

    public RoomBOImpl() {
        roomDAO = (RoomDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ROOM);
    }
    @Override
    public void saveRoom(RoomDto roomDto) {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.getTransaction();
        try (session) {
            transaction.begin();
            roomDAO.save(Converter.getInstance().toRoomEntity(roomDto), session);
            transaction.commit();
        } catch (RuntimeException exception) {
            transaction.rollback();
            throw new RuntimeException(exception);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateRoom(RoomDto roomDto) {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.getTransaction();
        try (session) {
            transaction.begin();
            roomDAO.update(Converter.getInstance().toRoomEntity(roomDto), session);
            transaction.commit();
        } catch (RuntimeException exception) {
            transaction.rollback();
            throw new RuntimeException(exception);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteRoom(String id) {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.getTransaction();
        try (session) {
            transaction.begin();
            roomDAO.delete(id, session);
            transaction.commit();
        } catch (RuntimeException exception) {
            transaction.rollback();
            throw new RuntimeException(exception);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String generateNextId() {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        String lastId = null;
        try (session){
            lastId = roomDAO.generateNewId(session);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (lastId == null) {
            return "RM-0001";
        } else {
            String[] split = lastId.split("[R][M][-]");
            int lastDigits = Integer.parseInt(split[1]);
            lastDigits++;
            return (String.format("RM-%04d", lastDigits));
        }
    }

    @Override
    public List<RoomDto> getAllRooms() {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        List<Room> roomList = null;
        try {
            roomList = roomDAO.getAll(session);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (roomList.size() > 0) {
            return roomList.stream().map(room -> Converter.getInstance().toRoomDto(room)).collect(Collectors.toList());
        }
        throw new RuntimeException("Empty Room list!");
    }

    @Override
    public RoomDto viewRoom(String id) {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Room entity = null;
        try {
            entity = roomDAO.search(id, session);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (entity != null) {
            return Converter.getInstance().toRoomDto(entity);
        }
        throw new RuntimeException("Room not found!");
    }

    @Override
    public String getRoomQty() {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.getTransaction();
        Integer count;
        try (session) {
            transaction.begin();
            count=roomDAO.getRoomCount(session);
            transaction.commit();
        } catch (RuntimeException exception) {
            transaction.rollback();
            throw new RuntimeException(exception);
        }
        return String.valueOf(count);
    }
}
