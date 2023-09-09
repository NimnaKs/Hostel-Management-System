package dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {
    private String id;
    private String password;
    private String name;
    private String email;
    @Column(columnDefinition = "LONGBLOB")
    private byte[] proPic;
}
