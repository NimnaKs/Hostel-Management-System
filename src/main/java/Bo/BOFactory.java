package Bo;

import Bo.custom.impl.TenantsBOImpl;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory() {
    }
    public static BOFactory getBoFactory() {
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }
    public enum BOTypes {
        TENANTS
    }
    public SuperBO getBO(BOTypes types){
        switch (types) {
            case TENANTS:
                return new TenantsBOImpl();
            default:
                return null;
        }
    }

}
