import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardGreetingHeader } from './dashboard-greeting-header';

describe('DashboardGreetingHeader', () => {
  let component: DashboardGreetingHeader;
  let fixture: ComponentFixture<DashboardGreetingHeader>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DashboardGreetingHeader],
    }).compileComponents();

    fixture = TestBed.createComponent(DashboardGreetingHeader);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
