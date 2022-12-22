import { Component } from '@angular/core';
import { ApiService } from '../api.service';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';

@Component({
  selector: 'app-chatbox',
  templateUrl: './chatbox.component.html',
  styleUrls: ['./chatbox.component.css']
})
export class ChatboxComponent {
constructor(private api:ApiService) {}

  allRestaurents:any[] = []
  ngOnInit() {
      this.api.getAllRestaurents().subscribe(data => {
          this.allRestaurents = data
      })
  }

  allUsers: any[] = [];
  selectedUserName: string = "";
  selecteduserEmail: string = "";
  loggedInUser: string = "nikhilmohite3@gmail.com";
  message:string = "";
  messages: any[] = [];
  messageObj: any = {
    message: this.message,
    senderEmailId: "nikhilmohite3@gmail.com",
    receiverEmailId: this.selecteduserEmail,
    chatDate: new Date(),
    chatId: '',
    userName: '',
    status: ''
  }
  stompClient?: Stomp.Client;
  userConnected: boolean = false;
  channel: string="";

  connectChat(){
    var socket = new SockJS('http://localhost:9001/chat-endpoint');
    this.stompClient = Stomp.over(socket);
    this.stompClient.connect({}, (frame)=> {
        this.userConnected = true;
        console.log('Connected: ' + frame);
        this.stompClient!.subscribe(`/user/private/${this.channel}`, (messageData) =>{
          console.log(messageData)
          this.messages.push(JSON.parse(messageData.body));
        });
    });
  }

  getMessages(){
    this.api.getMessages(this.channel).subscribe(data=>{
      this.messages = data;
    },
      err=>{}
      )
  }

  selectedChat(name: string, email: string){
    this.selectedUserName = name;
    this.selecteduserEmail = email;
    if(this.selecteduserEmail > this.messageObj.senderEmailId){
      this.channel = this.selecteduserEmail+""+this.messageObj.senderEmailId;
      console.log(this.channel);
    }
    else{
      this.channel = this.messageObj.senderEmailId+""+this.selecteduserEmail;
      console.log(this.channel);
    }
    this.getMessages();
    this.connectChat();
  }

  sendMessage(){
    this.messageObj.receiverEmailId = this.selecteduserEmail;
    this.messageObj.message = this.message
    this.stompClient!.send(`/app/private-message/${this.channel}`, {}, JSON.stringify(this.messageObj));
    this.message = ""
  }
}
