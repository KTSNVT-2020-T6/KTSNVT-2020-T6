import { ComponentFixture, inject, TestBed } from '@angular/core/testing';
import {  ReactiveFormsModule } from '@angular/forms';

import { async } from '@angular/core/testing';

import { OverlayContainer, ToastrModule, ToastrService } from 'ngx-toastr';
import { MatDialog, MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ConfirmationComponent } from './confirmation.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserDynamicTestingModule } from '@angular/platform-browser-dynamic/testing';
import { MaterialModule } from '../material-module';
import { of } from 'rxjs';

describe('ConfirmationComponent', () => {
    let dialog: MatDialog;
    let overlayContainer: OverlayContainer;
    let component: ConfirmationComponent;
    let fixture: ComponentFixture<ConfirmationComponent>;
    
    const mockDialogRef = {
      close: jasmine.createSpy('close')};
      //.and.returnValue(of(true))};
  
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        imports: [
          BrowserAnimationsModule,
          ReactiveFormsModule,
          MatDialogModule,
          MaterialModule
          
        ],
        providers: [
          { provide: MatDialogRef, useValue: mockDialogRef },
          {
            provide: MAT_DIALOG_DATA,
            useValue: {
              title: 'Confirm dialog',
              message: 'Are you sure?'
            }
          }
        ],
        declarations: [ConfirmationComponent],
      });
  
      TestBed.overrideModule(BrowserDynamicTestingModule, {
        set: {
          entryComponents: [ConfirmationComponent]
        }
      });
  
      TestBed.compileComponents();
    }));
  
    beforeEach(inject([MatDialog, OverlayContainer],
      (d: MatDialog, oc: OverlayContainer) => {
        dialog = d;
        overlayContainer = oc;
      })
    );
  
    afterEach(() => {
      overlayContainer.ngOnDestroy();
    });
  
    beforeEach(() => {
      fixture = TestBed.createComponent(ConfirmationComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  
    it('should create', () => {
      expect(component).toBeTruthy();
    });
  
  
    it('onDismiss should close the dialog', () => {
      component.onDismiss();
      expect(mockDialogRef.close).toHaveBeenCalledWith(false);
    });
    it('onConfirm should close the dialog', () => {
      component.onConfirm();
      expect(mockDialogRef.close).toHaveBeenCalledWith(true);
    });
  
  });