package ru.roon.sprint7.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Courier {
    private Long id;
    private String login;
    private String password;
    private String firstName;

}
