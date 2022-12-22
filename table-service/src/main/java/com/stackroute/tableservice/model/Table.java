package com.stackroute.tableservice.model;




import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//import lombok.ToString;



@Setter
@Getter
//@ToString
@NoArgsConstructor

@Document(collection = "Table")
public class Table {


	@Id
	private String restaurant_id;
	private String owner_email_id;
	private int number_of_tables;
	private int number_of_seats;

	public Table(String restaurant_id, String owner_email_id, int number_of_tables, int number_of_seats) {
		this.restaurant_id = restaurant_id;
		this.owner_email_id = owner_email_id;
		this.number_of_tables = number_of_tables;
		this.number_of_seats = number_of_seats;
	}
}
