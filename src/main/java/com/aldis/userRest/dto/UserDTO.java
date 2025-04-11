package com.aldis.userRest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @JsonProperty(value = "id", required = true)
    private Long id;

    @JsonProperty(value = "userName", required = true)
    @NotEmpty(message = "userName is required")
    private String userName;

    @JsonProperty(value = "password", required = true)
    @NotEmpty(message = "password is required")
    private String password;


}
