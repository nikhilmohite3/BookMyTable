package com.stackroute.menuservice.repo;
import com.stackroute.menuservice.model.Menu;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface MenuRepository extends MongoRepository<Menu,Long> {

    @Query("{menu_id :?0}")
    Menu findByMenuId(long menu_id);
    @Query("{menu_id :?1}")
    Menu updateMenuById( Menu menu,long menu_id);

//    @Query("{menu_id :?0}")
//    void deleteMenuById(long menu_id);

}