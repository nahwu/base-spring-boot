package com.nahwu.base.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class AuthorDTO {

    private String authorId;

    private String name;

    private Date birthday;

    @JsonCreator
    public AuthorDTO(String authorId, String name, Date birthday) {
        this.authorId = authorId;
        this.name = name;
        this.birthday = birthday;
    }
}
