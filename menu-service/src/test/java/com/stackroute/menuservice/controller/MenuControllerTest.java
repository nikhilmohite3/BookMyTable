package com.stackroute.menuservice.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.menuservice.model.Menu;
import com.stackroute.menuservice.service.MenuServiceImpl;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import org.bson.types.Binary;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {MenuController.class})
@ExtendWith(SpringExtension.class)
class MenuControllerTest {
    @Autowired
    private MenuController menuController;

    @MockBean
    private MenuServiceImpl menuServiceImpl;
    @Test
    @Disabled("TODO: Complete this test")
    void testAdd() throws Exception {


        MenuController menuController = new MenuController();
        menuController.add("Menu", new MockMultipartFile("Name", new ByteArrayInputStream("AAAAAAAA".getBytes("UTF-8"))));
    }


    @Test
    void testDelete() throws Exception {
        when(menuServiceImpl.deleteMenuById(anyLong())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/menu/delete/{menu_id}", 1L);
        ResultActions resultActions = MockMvcBuilders.standaloneSetup(menuController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
        ContentResultMatchers contentResult = MockMvcResultMatchers.content();
        resultActions.andExpect(contentResult.string(Boolean.TRUE.toString()));
    }

    @Test
    void testDelete2() throws Exception {
        when(menuServiceImpl.deleteMenuById(anyLong())).thenReturn(true);
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/menu/delete/{menu_id}", 1L);
        deleteResult.characterEncoding("Encoding");
        ResultActions resultActions = MockMvcBuilders.standaloneSetup(menuController)
                .build()
                .perform(deleteResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
        ContentResultMatchers contentResult = MockMvcResultMatchers.content();
        resultActions.andExpect(contentResult.string(Boolean.TRUE.toString()));
    }


    @Test
    void testGetMenu() throws Exception {
        when(menuServiceImpl.getAllMenuDetails()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/menu/");
        MockMvcBuilders.standaloneSetup(menuController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }


    @Test
    void testGetMenu2() throws Exception {
        Menu menu = new Menu();
        menu.setMenu_description("Menu description");
        menu.setMenu_id(1L);
        menu.setMenu_name("Menu name");
        menu.setMenu_photo(new Binary("AAAAAAAA".getBytes("UTF-8")));
        menu.setMenu_price(10.0d);

        ArrayList<Menu> menuList = new ArrayList<>();
        menuList.add(menu);
        when(menuServiceImpl.getAllMenuDetails()).thenReturn(menuList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/menu/");
        MockMvcBuilders.standaloneSetup(menuController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"menu_id\":1,\"menu_name\":\"Menu name\",\"menu_price\":10.0,\"menu_photo\":{\"type\":0,\"data\":\"QUFBQUFBQUE=\""
                                        + "},\"menu_description\":\"Menu description\"}]"));
    }


    @Test
    void testUpdate() throws Exception {
        Menu menu = new Menu();
        menu.setMenu_description("Menu description");
        menu.setMenu_id(1L);
        menu.setMenu_name("Menu name");
        menu.setMenu_photo(new Binary("AAAAAAAA".getBytes("UTF-8")));
        menu.setMenu_price(10.0d);
        when(menuServiceImpl.updateMenu((Menu) any())).thenReturn(menu);

        Menu menu1 = new Menu();
        menu1.setMenu_description("Menu description");
        menu1.setMenu_id(1L);
        menu1.setMenu_name("Menu name");
        menu1.setMenu_photo(null);
        menu1.setMenu_price(10.0d);
        String content = (new ObjectMapper()).writeValueAsString(menu1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/menu/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(menuController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"menu_id\":1,\"menu_name\":\"Menu name\",\"menu_price\":10.0,\"menu_photo\":{\"type\":0,\"data\":\"QUFBQUFBQUE=\"}"
                                        + ",\"menu_description\":\"Menu description\"}"));
    }
}

