package com.stackroute.menuservice.service;

import com.stackroute.menuservice.model.Menu;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface MenuService {
    Menu addMenu(String menu, MultipartFile img);



    List<Menu> getAllMenuDetails();

    boolean deleteMenuById(long menu_id);

    Menu updateMenu(Menu menu);
}