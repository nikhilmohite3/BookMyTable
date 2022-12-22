package com.stackroute.tableservice.repositorytest;
import com.stackroute.tableservice.model.Table;
import com.stackroute.tableservice.repository.TableRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TbaleRepoTest {

    @Autowired
    private TableRepository tableRepository;

    @Test
    public void saveTest(){
        Table table=new Table("frwe43","santa@gmail.com",23,54);
        tableRepository.save(table);
        assertThat(table.getRestaurant_id()).isNotNull();

    }
    @Test
    public void updateTest(){
        Table table=tableRepository.findById("frwe43").get();
        table.setNumber_of_seats(64);
        Table updatedTable=tableRepository.save(table);
        assertThat(updatedTable.getNumber_of_seats()).isEqualTo(table.getNumber_of_seats());
    }
}
