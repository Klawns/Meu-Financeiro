import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransactionsItem } from './transactions-item';

describe('TransactionsItem', () => {
  let component: TransactionsItem;
  let fixture: ComponentFixture<TransactionsItem>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TransactionsItem],
    }).compileComponents();

    fixture = TestBed.createComponent(TransactionsItem);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
