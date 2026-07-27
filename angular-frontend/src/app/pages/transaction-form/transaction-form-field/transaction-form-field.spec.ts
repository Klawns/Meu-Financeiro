import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransactionFormField } from './transaction-form-field';

describe('TransactionFormField', () => {
  let component: TransactionFormField;
  let fixture: ComponentFixture<TransactionFormField>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TransactionFormField],
    }).compileComponents();

    fixture = TestBed.createComponent(TransactionFormField);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
