package com.stackroute.tableservice.servicetest;

import com.stackroute.tableservice.exception.TableNumberExceedException;
import com.stackroute.tableservice.model.Table;
import com.stackroute.tableservice.repository.TableRepository;
import com.stackroute.tableservice.serice.TableService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
public class TableServiceTest {

    @Autowired
    private TableService tableService;

    @MockBean
    private TableRepository tableRepository;

    @Test
    public void saveTest() throws TableNumberExceedException {
        Table table=new Table("hsg53","flash@gmail.com",25,45);
        Mockito.when(tableRepository.save(table)).thenReturn(table);
        //assertThrows(TableNumberExceedException.class,()->{tableService.saveTable(table);});
        assertThat(tableService.saveTable(table)).isEqualTo(table);
    }

    @Test
    public void saveErrorTest() throws TableNumberExceedException {
        Table table=new Table("hsg53","flash@gmail.com",35,45);
        //Mockito.when(tableRepository.save(table)).thenReturn(table);
        assertThrows(TableNumberExceedException.class,()->{tableService.saveTable(table);});
        //assertThat(tableService.saveTable(table)).isEqualTo(table);
    }

    @Test
    public void updateTest() throws TableNumberExceedException {
        Table table=new Table("ywr3","santa@gmail.com",23,54);
        Mockito.when(tableRepository.findById(table.getRestaurant_id())).thenReturn(Optional.of(table));
        table.setNumber_of_tables(24);
        Mockito.when(tableRepository.save(table)).thenReturn(table);
        assertThat(tableService.updateTable(table,table.getRestaurant_id())).isEqualTo(table);
    }

    @Test
    public void updateErrorTest() throws TableNumberExceedException {
        Table table=new Table("ywr3","santa@gmail.com",23,54);
        Mockito.when(tableRepository.findById(table.getRestaurant_id())).thenReturn(Optional.of(table));
        table.setNumber_of_tables(34);
        //Mockito.when(tableRepository.save(table)).thenReturn(table);
        //assertThat(tableService.updateTable(table,table.getRestaurant_id())).isEqualTo(table);
        assertThrows(TableNumberExceedException.class,()-> {tableService.updateTable(table,table.getRestaurant_id());});
    }
}
