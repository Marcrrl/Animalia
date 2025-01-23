import { ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { ExploreContainerComponentModule } from '../explore-container/explore-container.module';

import { HospitalesPage } from './hospitales.page';

describe('HospitalesPage', () => {
  let component: HospitalesPage;
  let fixture: ComponentFixture<HospitalesPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [HospitalesPage],
      imports: [IonicModule.forRoot(), ExploreContainerComponentModule]
    }).compileComponents();

    fixture = TestBed.createComponent(HospitalesPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
