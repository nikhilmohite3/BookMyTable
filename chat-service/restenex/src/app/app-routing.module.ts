import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ChatboxComponent } from './chatbox/chatbox.component';
import { RestaurantChatComponent } from './restaurant-chat/restaurant-chat.component';

const routes: Routes = [
  {
    component:ChatboxComponent, path:'',
  },
  {
    component:RestaurantChatComponent, path:'restaurant'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
