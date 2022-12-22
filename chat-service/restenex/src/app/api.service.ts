import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private httpClient:HttpClient) { }
  getAllRestaurents() {
    return this.httpClient.get<any[]>("http://localhost:9090/api/v2/Bangalore")
  }

  getMessages(chatId: string){
    return this.httpClient.get<any[]>("http://localhost:9090/api/v2/getAllRestaurant")
  }

  getUsers() {
    return this.httpClient.get<any[]>("http://localhost:9090/api/v2/getAllRestaurant")
  }
}
