package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


import java.sql.Date;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Reservation {
    @Id
    private String res_id;
    private Date date;
    private String status;
    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Tenant tenant;
    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Room room;

}
