package util;

import dto.UserDto;
import lombok.Builder;
import lombok.Data;

@Builder(setterPrefix = "with")
@Data
public class UserTestBuilder {

    @Builder.Default
    public  String name = "Имя";
    @Builder.Default
    public  String surname = "Фамилия";
    @Builder.Default
    public  String email = "пример@gmail.com";
    @Builder.Default
    public  Integer age = 123;

    public UserDto buildUserDto() {
        return new UserDto(name, surname, email, age);
    }
}
