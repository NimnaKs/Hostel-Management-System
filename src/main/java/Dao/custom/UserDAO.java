package Dao.custom;

import Dao.CrudDAO;
import entity.User;
import org.hibernate.Session;

public interface UserDAO extends CrudDAO<User,String> {}

