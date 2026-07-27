import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransactionsGroup } from './transactions-group';

describe('TransactionsGroup', () => {
  let component: TransactionsGroup;
  let fixture: ComponentFixture<TransactionsGroup>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TransactionsGroup],
    }).compileComponents();

    fixture = TestBed.createComponent(TransactionsGroup);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
