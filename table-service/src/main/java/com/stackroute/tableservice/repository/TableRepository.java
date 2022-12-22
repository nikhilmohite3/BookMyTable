package com.stackroute.tableservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.stackroute.tableservice.model.Table;

public interface TableRepository extends MongoRepository<Table, String> {

}
