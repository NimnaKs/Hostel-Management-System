package entity;

import com.mysql.cj.jdbc.Blob;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Type;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User implements SuperEntity {
    @Id
    private String id;
    private String password;
    private String name;
    private String email;
    @Column(columnDefinition = "LONGBLOB")
    private byte[] proPic;

}
