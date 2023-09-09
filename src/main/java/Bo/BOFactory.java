package Bo;

import Bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory() {
    }
    public static BOFactory getBoFactory() {
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }
    public enum BOTypes {
        TENANTS,ROOMS,USER,RESERVATION,QUERY
    }
    public SuperBO getBO(BOTypes types){
        switch (types) {
            case TENANTS:
                return new TenantsBOImpl();
            case ROOMS:
                return new RoomBOImpl();
            case USER:
                return new UserBOImpl();
            case RESERVATION:
                return new ReservationBOImpl();
            case QUERY:
                return new QueryBOImpl();
            default:
                return null;
        }
    }

}
