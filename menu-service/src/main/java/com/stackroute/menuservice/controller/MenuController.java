package com.stackroute.menuservice.controller;

import com.stackroute.menuservice.model.Menu;
import com.stackroute.menuservice.service.MenuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/menu")
public class MenuController {

    private ResponseEntity responseEntity;

    @Autowired
    MenuServiceImpl menuServiceImpl;

    @PostMapping("/menu/add")
    public  ResponseEntity<?> add(@RequestParam String menu, @RequestParam MultipartFile img) throws  Exception{
        responseEntity=new ResponseEntity<>(menuServiceImpl.addMenu(menu, img),HttpStatus.OK);
        return  responseEntity;
    }

    @GetMapping("/")
    public  ResponseEntity<?> getMenu() throws  Exception{
        responseEntity=new ResponseEntity<>(menuServiceImpl.getAllMenuDetails(),HttpStatus.OK);
        return  responseEntity;
    }

    @PutMapping("/update")
    public  ResponseEntity<?> update(@RequestBody Menu menu) throws  Exception{
        responseEntity=new ResponseEntity<>(menuServiceImpl.updateMenu(menu),HttpStatus.OK);
        return  responseEntity;
    }

    @DeleteMapping("/delete/{menu_id}")
    public  ResponseEntity<?> delete(@PathVariable long menu_id) throws  Exception{
        responseEntity=new ResponseEntity<>(menuServiceImpl.deleteMenuById(menu_id),HttpStatus.OK);
        return  responseEntity;
        //jh
    }
}