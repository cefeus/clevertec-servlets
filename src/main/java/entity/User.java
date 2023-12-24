package entity;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class User {

    private UUID id;
    private String name;
    private String surname;
    private String email;
    private Integer age;

}
