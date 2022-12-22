package com.stackroute.tableservice.controllertest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.tableservice.controller.TableController;
import com.stackroute.tableservice.exception.RestaurantNotFoundException;
import com.stackroute.tableservice.exception.TableNumberExceedException;
import com.stackroute.tableservice.model.Table;
import com.stackroute.tableservice.serice.TableService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@SpringBootTest
public class TableControllerTest {

    @Autowired
     MockMvc mockMvc;
    @Mock
    private TableService tableService;
    @InjectMocks
    private TableController tableController;



    @BeforeEach
    public void setUp(){

        mockMvc = MockMvcBuilders.standaloneSetup(tableController).build();


    }
    @Test
    public void saveTest() throws Exception {
        Table table=new Table("bvs65","jaguar@gmail.com",21,32);
        when(tableService.saveTable(table)).thenReturn(table);

        ObjectMapper objectmapper=new ObjectMapper();

        mockMvc.perform(post("/table/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectmapper.writeValueAsString(table)))
                .andExpect(status().isOk())
                .andDo(print());
    }
    @Test
    public void saveTestError() throws Exception {
        Table table=new Table("bvs65","jaguar@gmail.com",41,32);
        ObjectMapper objectmapper=new ObjectMapper();
        when(tableService.saveTable(any())).thenThrow(TableNumberExceedException.class);

        mockMvc.perform(post("/table/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectmapper.writeValueAsString(table)))
                .andExpect(status().isConflict())
                .andDo(print());
    }

    @Test
    public void saveTestParentError() throws Exception {
        Table table=new Table("bvs65","jaguar@gmil.com",21,32);
        ObjectMapper objectmapper=new ObjectMapper();
        when(tableService.saveTable(any())).thenThrow(new RuntimeException());

        mockMvc.perform(post("/table/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectmapper.writeValueAsString(table)))
                .andExpect(status().isInternalServerError())
                .andDo(print());
    }

    @Test
    public void updateTest() throws Exception {
        Table table=new Table("bvs65","jaguar@gmail.com",24,32);

        table.setNumber_of_tables(13);
        ObjectMapper objectmapper=new ObjectMapper();

        when(tableService.updateTable(table,table.getRestaurant_id())).thenReturn(table);
        mockMvc.perform(put("/table/{restaurant_id}",table.getRestaurant_id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectmapper.writeValueAsString(table)))
                .andExpect(status().isOk())
                .andDo(print());
                //.andExpect(content().string(equalTo("table number exceed")));
    }
    @Test
    public void updateTestError() throws Exception {
       Table table=new Table("bvs65","jaguar@gmail.com",24,32);

        table.setNumber_of_tables(43);
        ObjectMapper objectmapper=new ObjectMapper();

        when(tableService.updateTable(any(),any())).thenThrow(TableNumberExceedException.class);
        mockMvc.perform(put("/table/bvs65")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectmapper.writeValueAsString(table)))
                .andExpect(status().isConflict())
                .andDo(print())
                ;//.andExpect(content().string(equalTo("table number exceed")));
    }

    @Test
    public void updateTestParentError() throws Exception {
        Table table=new Table("bvs65","jaguar@gmil.com",24,32);

        table.setNumber_of_tables(23);
        ObjectMapper objectmapper=new ObjectMapper();

        when(tableService.updateTable(any(),any())).thenThrow(new RuntimeException());
        mockMvc.perform(put("/table/bvs65")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectmapper.writeValueAsString(table)))
                .andExpect(status().isInternalServerError())
                .andDo(print())
        ;//.andExpect(content().string(equalTo("table number exceed")));
    }
    @Test
    public void getTest() throws Exception {
        Table table = new Table("bvs65", "jaguar@gmail.com", 24, 32);


        ObjectMapper objectmapper = new ObjectMapper();

        when(tableService.getTable(table.getRestaurant_id())).thenReturn(table);
        mockMvc.perform(get("/table/getTable/{restaurant_id}", table.getRestaurant_id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectmapper.writeValueAsString(table)))
                .andExpect(status().isOk())
                .andDo(print());
    }
    @Test
    public void getTestError() throws Exception {
        Table table=new Table("bvs65","jaguar@gmail.com",24,32);


        ObjectMapper objectmapper=new ObjectMapper();
        when(tableService.getTable(table.getRestaurant_id())).thenThrow(RestaurantNotFoundException.class);

        mockMvc.perform(get("/table/getTable/{restaurant_id}", table.getRestaurant_id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectmapper.writeValueAsString(table)))
                .andExpect(status().isConflict())
                .andDo(print())
        ;//.andExpect(content().string(equalTo("table number exceed")));
    }
    @Test
    public void getTestParentError() throws Exception {
        Table table=new Table("bvs65","jaguar@gmil.com",24,32);


        ObjectMapper objectmapper=new ObjectMapper();

        when(tableService.getTable(table.getRestaurant_id())).thenThrow(new RuntimeException());
        mockMvc.perform(get("/table/getTable/{restaurant_id}", table.getRestaurant_id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectmapper.writeValueAsString(table)))
                .andExpect(status().isInternalServerError())
                .andDo(print())
        ;//.andExpect(content().string(equalTo("table number exceed")));
    }



    }

