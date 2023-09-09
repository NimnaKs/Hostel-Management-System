package Bo.custom.impl;

import Bo.custom.TenantBO;
import Bo.util.Converter;
import Dao.DAOFactory;
import Dao.custom.TenantDAO;
import dto.TenantDto;
import entity.Tenant;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.FactoryConfiguration;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class TenantsBOImpl implements TenantBO {
    private final TenantDAO tenantDAO;

    public TenantsBOImpl() {
        tenantDAO = (TenantDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.TENANT);
    }

    @Override
    public void saveTenant(TenantDto tenantDto) {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.getTransaction();
        try (session) {
            transaction.begin();
            tenantDAO.save(Converter.getInstance().toTenantEntity(tenantDto), session);
            transaction.commit();
        } catch (RuntimeException exception) {
            transaction.rollback();
            throw new RuntimeException("Student not added");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateTenant(TenantDto tenantDto) {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.getTransaction();
        try (session) {
            transaction.begin();
            tenantDAO.update(Converter.getInstance().toTenantEntity(tenantDto), session);
            transaction.commit();
        } catch (RuntimeException exception) {
            transaction.rollback();
            throw new RuntimeException("Student not updated");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TenantDto> getAllTenants() {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        try (session) {
            List<Tenant> studentList = tenantDAO.getAll(session);
            if (studentList.size() > 0) {
                return studentList.stream().map(tenant -> Converter.getInstance().toTenantDto(tenant)).collect(Collectors.toList());
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("Empty Student list!");
    }

    @Override
    public String getLastId() {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        try (session) {
            String lastId = tenantDAO.generateNewId(session);
            if (lastId == null) {
                return "T001";
            } else {
                String[] split = lastId.split("[T]");
                int lastDigits = Integer.parseInt(split[1]);
                lastDigits++;
                return (String.format("T%03d", lastDigits));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteTenant(String studentId) {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        Transaction transaction = session.getTransaction();
        try (session) {
            transaction.begin();
            tenantDAO.delete(studentId, session);
            transaction.commit();
        } catch (RuntimeException exception) {
            transaction.rollback();
            throw new RuntimeException("Student not Deleted");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TenantDto viewTenant(TenantDto tenantDto) {
        Session session = FactoryConfiguration.getFactoryConfiguration().getSession();
        try (session) {
            Tenant tenant = tenantDAO.search(tenantDto.getStudent_id(), session);
            if (tenant != null) return Converter.getInstance().toTenantDto(tenant);
            throw new RuntimeException("Student not found");
        } catch (RuntimeException exception) {
            throw new RuntimeException(exception.getMessage());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
