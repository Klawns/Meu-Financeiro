import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransactionFormSubmitButton } from './transaction-form-submit-button';

describe('TransactionFormSubmitButton', () => {
  let component: TransactionFormSubmitButton;
  let fixture: ComponentFixture<TransactionFormSubmitButton>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TransactionFormSubmitButton],
    }).compileComponents();

    fixture = TestBed.createComponent(TransactionFormSubmitButton);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
