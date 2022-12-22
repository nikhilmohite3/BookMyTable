package com.stackroute.tableservice.serice;

import com.stackroute.tableservice.exception.RestaurantNotFoundException;
import com.stackroute.tableservice.exception.TableNumberExceedException;
import com.stackroute.tableservice.model.Table;
import com.stackroute.tableservice.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TableServiceImpl implements TableService{

    @Autowired
    private TableRepository tableRepository;

    //As a owner i should be able to add the number of tables and number of seats
    @Override
    public Table saveTable(Table table) throws TableNumberExceedException {

        if (table.getNumber_of_tables()>30){
            throw new TableNumberExceedException();

        }
        Table table1=tableRepository.save(table);

        return table1;
    }

    @Override
    public Table updateTable(Table table, String restaurant_id) throws TableNumberExceedException{
        Optional<Table> table1= Optional.ofNullable(tableRepository.findById(restaurant_id)).get();
        if (table.getNumber_of_tables()>30){
            throw new TableNumberExceedException();
        }
        table.setRestaurant_id(table1.get().getRestaurant_id());

        return tableRepository.save(table);
    }

    @Override
    public Table getTable(String restaurant_id) throws RestaurantNotFoundException {
        if( tableRepository.findById(restaurant_id)!=null){
            return tableRepository.findById(restaurant_id).get();
        }
        throw new RestaurantNotFoundException();
    }


}
