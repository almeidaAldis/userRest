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

    @JsonProperty(value = "name", required = true)
    @NotEmpty(message = "Name is required")
    private String name;

    @JsonProperty(value = "lastName", required = true)
    @NotEmpty(message = "lastName is required")
    private String lastName;

    @JsonProperty(value = "age", required = true)
    @NotNull(message = "age cannot be null")
    private Integer age;
}
