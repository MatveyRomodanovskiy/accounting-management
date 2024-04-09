package telran.accounting.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PasswordUpdateDataDto(@NotEmpty @Email String email, @NotNull @Size(min=8)String password) {

}
