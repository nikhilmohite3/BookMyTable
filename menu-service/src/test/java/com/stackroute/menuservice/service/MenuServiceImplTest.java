package com.stackroute.menuservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.stackroute.menuservice.exception.MenuNotFoundException;
import com.stackroute.menuservice.model.DatabaseSequence;
import com.stackroute.menuservice.model.Menu;
import com.stackroute.menuservice.repo.MenuRepository;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.Binary;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UpdateDefinition;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {MenuServiceImpl.class})
@ExtendWith(SpringExtension.class)
class MenuServiceImplTest {
    @MockBean
    private MenuRepository menuRepository;

    @Autowired
    private MenuServiceImpl menuServiceImpl;

    @MockBean
    private MongoOperations mongoOperations;


    @Test
    void testGetNextSequence() {
        DatabaseSequence databaseSequence = new DatabaseSequence();
        databaseSequence.setId("42");
        databaseSequence.setSeq(1);
        when(mongoOperations.findAndModify((Query) any(), (UpdateDefinition) any(), (FindAndModifyOptions) any(),
                (Class<DatabaseSequence>) any())).thenReturn(databaseSequence);
        assertEquals(1, menuServiceImpl.getNextSequence("Seq Name"));
        verify(mongoOperations).findAndModify((Query) any(), (UpdateDefinition) any(), (FindAndModifyOptions) any(),
                (Class<DatabaseSequence>) any());
    }


    @Test
    void testGetNextSequence2() {
        when(mongoOperations.findAndModify((Query) any(), (UpdateDefinition) any(), (FindAndModifyOptions) any(),
                (Class<DatabaseSequence>) any())).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> menuServiceImpl.getNextSequence("Seq Name"));
        verify(mongoOperations).findAndModify((Query) any(), (UpdateDefinition) any(), (FindAndModifyOptions) any(),
                (Class<DatabaseSequence>) any());
    }


    @Test
    @Disabled
    void testAddMenu() throws IOException {


        menuServiceImpl.addMenu("Menu",
                new MockMultipartFile("Name", new ByteArrayInputStream("AAAAAAAA".getBytes("UTF-8"))));
    }


    @Test
    @Disabled
    void testAddMenu2() throws IOException {


        menuServiceImpl.addMenu("",
                new MockMultipartFile("Name", new ByteArrayInputStream("AAAAAAAA".getBytes("UTF-8"))));
    }


    @Test
    void testGetAllMenuDetails() {
        when(menuRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(MenuNotFoundException.class, () -> menuServiceImpl.getAllMenuDetails());
        verify(menuRepository).findAll();
    }


    @Test
    void testGetAllMenuDetails2() throws UnsupportedEncodingException {
        Menu menu = new Menu();
        menu.setMenu_description("Menu not found");
        menu.setMenu_id(1L);
        menu.setMenu_name("Menu not found");
        menu.setMenu_photo(new Binary("AAAAAAAA".getBytes("UTF-8")));
        menu.setMenu_price(10.0d);

        ArrayList<Menu> menuList = new ArrayList<>();
        menuList.add(menu);
        when(menuRepository.findAll()).thenReturn(menuList);
        List<Menu> actualAllMenuDetails = menuServiceImpl.getAllMenuDetails();
        assertSame(menuList, actualAllMenuDetails);
        assertEquals(1, actualAllMenuDetails.size());
        verify(menuRepository).findAll();
    }


    @Test
    void testGetAllMenuDetails3() {
        when(menuRepository.findAll()).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> menuServiceImpl.getAllMenuDetails());
        verify(menuRepository).findAll();
    }


    @Test
    void testDeleteMenuById() throws UnsupportedEncodingException {
        Menu menu = new Menu();
        menu.setMenu_description("Menu description");
        menu.setMenu_id(1L);
        menu.setMenu_name("Menu name");
        menu.setMenu_photo(new Binary("AAAAAAAA".getBytes("UTF-8")));
        menu.setMenu_price(10.0d);
        doNothing().when(menuRepository).deleteById((Long) any());
        when(menuRepository.findByMenuId(anyLong())).thenReturn(menu);
        assertTrue(menuServiceImpl.deleteMenuById(1L));
        verify(menuRepository).findByMenuId(anyLong());
        verify(menuRepository).deleteById((Long) any());
    }


    @Test
    void testDeleteMenuById2() throws UnsupportedEncodingException {
        Menu menu = new Menu();
        menu.setMenu_description("Menu description");
        menu.setMenu_id(1L);
        menu.setMenu_name("Menu name");
        menu.setMenu_photo(new Binary("AAAAAAAA".getBytes("UTF-8")));
        menu.setMenu_price(10.0d);
        doThrow(new RuntimeException()).when(menuRepository).deleteById((Long) any());
        when(menuRepository.findByMenuId(anyLong())).thenReturn(menu);
        assertThrows(RuntimeException.class, () -> menuServiceImpl.deleteMenuById(1L));
        verify(menuRepository).findByMenuId(anyLong());
        verify(menuRepository).deleteById((Long) any());
    }


    @Test
    void testUpdateMenu() throws UnsupportedEncodingException {
        Menu menu = new Menu();
        menu.setMenu_description("Menu description");
        menu.setMenu_id(1L);
        menu.setMenu_name("Menu name");
        menu.setMenu_photo(new Binary("AAAAAAAA".getBytes("UTF-8")));
        menu.setMenu_price(10.0d);

        Menu menu1 = new Menu();
        menu1.setMenu_description("Menu description");
        menu1.setMenu_id(1L);
        menu1.setMenu_name("Menu name");
        menu1.setMenu_photo(new Binary("AAAAAAAA".getBytes("UTF-8")));
        menu1.setMenu_price(10.0d);
        when(menuRepository.save((Menu) any())).thenReturn(menu1);
        when(menuRepository.findByMenuId(anyLong())).thenReturn(menu);

        Menu menu2 = new Menu();
        menu2.setMenu_description("Menu description");
        menu2.setMenu_id(1L);
        menu2.setMenu_name("Menu name");
        menu2.setMenu_photo(new Binary("AAAAAAAA".getBytes("UTF-8")));
        menu2.setMenu_price(10.0d);
        assertSame(menu1, menuServiceImpl.updateMenu(menu2));
        verify(menuRepository).findByMenuId(anyLong());
        verify(menuRepository).save((Menu) any());
    }


    @Test
    void testUpdateMenu2() throws UnsupportedEncodingException {
        Menu menu = new Menu();
        menu.setMenu_description("Menu description");
        menu.setMenu_id(1L);
        menu.setMenu_name("Menu name");
        menu.setMenu_photo(new Binary("AAAAAAAA".getBytes("UTF-8")));
        menu.setMenu_price(10.0d);
        when(menuRepository.save((Menu) any())).thenThrow(new RuntimeException());
        when(menuRepository.findByMenuId(anyLong())).thenReturn(menu);

        Menu menu1 = new Menu();
        menu1.setMenu_description("Menu description");
        menu1.setMenu_id(1L);
        menu1.setMenu_name("Menu name");
        menu1.setMenu_photo(new Binary("AAAAAAAA".getBytes("UTF-8")));
        menu1.setMenu_price(10.0d);
        assertThrows(RuntimeException.class, () -> menuServiceImpl.updateMenu(menu1));
        verify(menuRepository).findByMenuId(anyLong());
        verify(menuRepository).save((Menu) any());
    }
}

