package dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public record UserDto(

        @NotNull(message = "Name can't be null")
        @Pattern(regexp = "^[A-Z][A-Za-z]{0,29}$", message = "Invalid name format")
        String name,

        @NotNull(message = "Surname must not be null")
        @Pattern(regexp = "^[A-Z][A-Za-z]{0,29}$", message = "Invalid surname format")
        String surname,

        @Email(message = "Invalid email format")
        @NotNull(message = "Email can't be null")
        String email,

        @NotNull(message = "Age can't be null")
        @Min(value = 0, message = "Age can't be less than zero")
        Integer age
) {
}
