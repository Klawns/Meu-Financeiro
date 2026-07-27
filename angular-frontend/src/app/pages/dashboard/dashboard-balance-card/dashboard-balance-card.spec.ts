import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardBalanceCard } from './dashboard-balance-card';

describe('DashboardBalanceCard', () => {
  let component: DashboardBalanceCard;
  let fixture: ComponentFixture<DashboardBalanceCard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DashboardBalanceCard],
    }).compileComponents();

    fixture = TestBed.createComponent(DashboardBalanceCard);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
