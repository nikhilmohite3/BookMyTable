package com.stackroute.tableservice.serice;

import com.stackroute.tableservice.exception.RestaurantNotFoundException;
import com.stackroute.tableservice.exception.TableNumberExceedException;
import com.stackroute.tableservice.model.Table;

public interface TableService{
Table saveTable(Table table) throws TableNumberExceedException;
Table updateTable(Table table,String restaurant_id) throws TableNumberExceedException;
Table getTable(String restaurant_id) throws RestaurantNotFoundException;
}
