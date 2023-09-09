package Bo.custom;

import Bo.SuperBO;
import dto.ReservationDto;

import java.util.List;

public interface ReservationBO extends SuperBO {
    ReservationDto viewReservation(String text);

    Integer getReservationCount(String roomTypeId);

    void saveReservation(ReservationDto reservation);

    String getLastId();

    void deleteReservation(String text);

    List<ReservationDto> getAll();

    void updateReservation(ReservationDto reservation);

    String getUnfreeRooms();

    String getIncome();
}
