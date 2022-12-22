package com.stackroute.menuservice.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "database_sequence")
public class DatabaseSequence {

    @Id
    private String id;

    private int seq;
}
