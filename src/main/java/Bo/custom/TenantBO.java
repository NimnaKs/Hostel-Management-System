package Bo.custom;

import Bo.SuperBO;
import dto.TenantDto;

import java.util.List;

public interface TenantBO extends SuperBO{
    void saveTenant(TenantDto tenantDto);

    void updateTenant(TenantDto tenantDto);

    List<TenantDto> getAllTenants();

    String getLastId();

    void deleteTenant(String studentId);

    TenantDto viewTenant(TenantDto tenantDto);
}
