package Bo;

import Bo.custom.impl.RoomBOImpl;
import Bo.custom.impl.TenantsBOImpl;
import Bo.custom.impl.UserBOImpl;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory() {
    }
    public static BOFactory getBoFactory() {
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }
    public enum BOTypes {
        TENANTS,ROOMS,USER
    }
    public SuperBO getBO(BOTypes types){
        switch (types) {
            case TENANTS:
                return new TenantsBOImpl();
            case ROOMS:
                return new RoomBOImpl();
            case USER:
                return new UserBOImpl();
            default:
                return null;
        }
    }

}
