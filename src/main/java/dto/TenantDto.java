package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class TenantDto{

    private String student_id;
    private String name;
    private String address;
    private String contact_no;
    private Date dob;
    private String gender;
   @ToString.Exclude
   private final List<ReservationDto> reservationList = new ArrayList<>();
}
