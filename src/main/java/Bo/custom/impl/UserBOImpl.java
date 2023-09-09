package Bo.custom.impl;

import Bo.custom.UserBO;
import Dao.DAOFactory;
import Dao.custom.UserDAO;
import dto.UserDto;
import entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.FactoryConfiguration;

import java.sql.SQLException;

public class UserBOImpl implements UserBO {

    UserDAO userDAO= (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);
    @Override
    public void updateTenant(UserDto userDto) {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        User user = new User();
        user.setId(userDto.getId());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setProPic(userDto.getProPic());
        Transaction transaction = session.getTransaction();
        try (session) {
            transaction.begin();
            userDAO.update(user, session);
            transaction.commit();
        } catch (RuntimeException exception) {
            transaction.rollback();
            throw new RuntimeException(exception);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserDto viewCredentials(String id) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        User entity = userDAO.search(id, session);
        if (entity != null) {
            UserDto dto = new UserDto();
            dto.setId(entity.getId());
            dto.setPassword(entity.getPassword());
            return dto;
        }
        throw new RuntimeException("User Not Found !");
    }

    @Override
    public UserDto getUserDetails(String userId) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        User entity = userDAO.search(userId, session);
        if (entity != null) {
            UserDto dto = new UserDto();
            dto.setId(entity.getId());
            dto.setPassword(entity.getPassword());
            dto.setEmail(entity.getEmail());
            dto.setProPic(entity.getProPic());
            dto.setName(entity.getName());
            return dto;
        }
        throw new RuntimeException("User Not Found !");
    }

    @Override
    public void saveUserDetails(UserDto userDto) {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        User user = new User();
        user.setId(userDto.getId());
        user.setPassword(userDto.getPassword());

        Transaction transaction = session.getTransaction();
        try (session) {
            transaction.begin();
            userDAO.save(user, session);
            transaction.commit();
        } catch (RuntimeException exception) {
            transaction.rollback();
            throw new RuntimeException(exception);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
