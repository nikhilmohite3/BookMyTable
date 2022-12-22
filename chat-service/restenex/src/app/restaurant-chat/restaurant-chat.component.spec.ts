import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RestaurantChatComponent } from './restaurant-chat.component';

describe('RestaurantChatComponent', () => {
  let component: RestaurantChatComponent;
  let fixture: ComponentFixture<RestaurantChatComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RestaurantChatComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RestaurantChatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
