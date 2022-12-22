package com.stackroute.tableservice.controller;


import com.stackroute.tableservice.exception.RestaurantNotFoundException;
import com.stackroute.tableservice.exception.TableNumberExceedException;
import com.stackroute.tableservice.serice.TableService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.stackroute.tableservice.model.Table;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/table")
public class TableController {


	//Logger logger= LoggerFactory.getLogger(TableController.class);
	 private TableService tableService;

	 @Autowired
	public TableController(TableService tableService) {
		this.tableService = tableService;
	}

	@PostMapping("/")
	public ResponseEntity<String> createTableDetails(@RequestBody Table table)  {

		try {
			this.tableService.saveTable(table);
			return ResponseEntity.ok("created successfully");
		}catch(TableNumberExceedException e){
			e.printStackTrace();
			return new ResponseEntity<>("restaurant have limited table", HttpStatus.CONFLICT);
		}catch(RuntimeException e){
			e.printStackTrace();
			return new ResponseEntity<>("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
		}


	}
	@GetMapping("/getTable/{restaurant_id}")
	public ResponseEntity<?> getAllTables(@PathVariable String restaurant_id) {
		try {
			return ResponseEntity.ok(tableService.getTable(restaurant_id));
		} catch (RestaurantNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<>("No restaurant found", HttpStatus.CONFLICT);

		}catch(RuntimeException e){
			e.printStackTrace();
			return new ResponseEntity<>("No restaurant found", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{restaurant_id}")
	public ResponseEntity<String> updateTableDetails(@RequestBody Table table, @PathVariable String restaurant_id)   {

		try {
			log.debug("Request {}", restaurant_id);
			Table updatedetail = tableService.updateTable(table, restaurant_id);
			log.debug("Response {}", updatedetail);
			return ResponseEntity.ok("updated successfully");
		} catch (TableNumberExceedException e) {
			e.printStackTrace();
			return new ResponseEntity<>("restaurant have limited table", HttpStatus.CONFLICT);

		}catch(RuntimeException e){
			e.printStackTrace();
			return new ResponseEntity<>("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
