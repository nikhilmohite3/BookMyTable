package com.stackroute.menuservice.service;

import com.alibaba.fastjson2.JSON;
import com.stackroute.menuservice.exception.MenuNotFoundException;

import com.stackroute.menuservice.model.DatabaseSequence;
import com.stackroute.menuservice.model.Menu;
import com.stackroute.menuservice.repo.MenuRepository;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.MongoOperations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    private MongoOperations mongo;

    List<Menu> list = new ArrayList<>();

    public int getNextSequence(String seqName) {
        DatabaseSequence counter = mongo.findAndModify(query(where("_id").is(seqName)), new Update().inc("seq", 1),
                options().returnNew(true).upsert(true), DatabaseSequence.class);
        return counter.getSeq();
    }

    @Override
    public Menu addMenu(String menu, MultipartFile img) {
Menu m2 = JSON.parseObject(menu,Menu.class);
        if(img != null){
            try {
                m2.setMenu_photo(new Binary(BsonBinarySubType.BINARY, img.getBytes()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Menu m = menuRepository.findByMenuId(m2.getMenu_id());
        if (m == null) {
//            venue.setVenueId(getNextSequence(Venue.SEQUENCE_NAME));
            m2.setMenu_id(getNextSequence(Menu.SEQUENCE_NAME));
            return menuRepository.save(m2);
        } else {
            throw new MenuNotFoundException("Menu with " + m2.getMenu_id() + " does exist.");
        }

    }

//


    @Override
    public List<Menu> getAllMenuDetails() {
        List<Menu> m = menuRepository.findAll();

        if (m.isEmpty()) {
            throw new MenuNotFoundException("Menu not found");
        }
        return m;
    }

    @Override
    public boolean deleteMenuById(long menu_id) {
        Menu m = menuRepository.findByMenuId(menu_id);
        System.out.println(m);
        if (m != null) {
//            menuRepository.deleteMenuById(menu_id);
            menuRepository.deleteById(menu_id);
            return true;

        }
        else{
            throw new MenuNotFoundException("menu with id "+menu_id+ " doesn't exist");
        }

    }

    @Override
    public Menu updateMenu(Menu menu) {
        Menu existingMenu=menuRepository.findByMenuId(menu.getMenu_id());
        System.out.println(existingMenu);
        System.out.println("----------");
        if(existingMenu==null){
            throw new MenuNotFoundException("Menu with this id " +menu.getMenu_id()+ " doesn't exist.");
        }
        else{
            return menuRepository.save(menu);


        }

    }



}