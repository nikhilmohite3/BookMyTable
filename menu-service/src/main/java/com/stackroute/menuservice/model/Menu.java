package com.stackroute.menuservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@Document(value="menutable")
public class Menu {
    @Transient
    public static final String SEQUENCE_NAME = "menu_sequence";
    @Id
    private long menu_id;
    private String menu_name;
    private double menu_price ;
    private Binary menu_photo;
    private String menu_description;
}
