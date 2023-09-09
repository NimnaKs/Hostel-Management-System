package dto;

import com.mysql.cj.jdbc.Blob;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto implements SuperDto {
    private String id;
    private String password;
    private String name;
    private String email;
    @Column(columnDefinition = "LONGBLOB")
    private byte[] proPic;
}
