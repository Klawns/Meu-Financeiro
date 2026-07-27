import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransactionsFloatingActions } from './transactions-floating-actions';

describe('TransactionsFloatingActions', () => {
  let component: TransactionsFloatingActions;
  let fixture: ComponentFixture<TransactionsFloatingActions>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TransactionsFloatingActions],
    }).compileComponents();

    fixture = TestBed.createComponent(TransactionsFloatingActions);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
