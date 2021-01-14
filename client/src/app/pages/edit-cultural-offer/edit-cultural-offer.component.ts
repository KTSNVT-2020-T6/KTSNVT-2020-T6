import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Component, Inject, OnInit} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { CulturalOffer } from '../model/CulturalOffer';
import { Type } from '../model/Type';
import { CulturalOfferDetailsService } from '../services/cultural-offer-details/cultural-offer-details.service';
import { ImageService } from '../services/image/image.service';
import { TypeService } from '../services/type/type.service';

@Component({
  selector: 'app-edit-cultural-offer',
  templateUrl: './edit-cultural-offer.component.html',
  styleUrls: ['./edit-cultural-offer.component.scss']
})
export class EditCulturalOfferComponent implements OnInit {
  form!: FormGroup;
  types: Type[] = [];
  newType!: Type; 
  placeLon: any;
  placeLat: any;
  city: any;
  todayDate: any;

  selectedFiles!: FileList;
  progressInfos: any[] = [];

  constructor(private fb: FormBuilder,
    private culturalOfferService: CulturalOfferDetailsService,
    private typeService: TypeService,
    private router: Router,
    private toastr: ToastrService,
    private imageService: ImageService,public dialogRef: MatDialogRef<EditCulturalOfferComponent>,
    @Inject(MAT_DIALOG_DATA) public co: CulturalOffer) { 
      this.createForm();
      this.form.patchValue(this.co);
      this.todayDate = new Date();
    }


  ngOnInit(): void {
    this.typeService.getAll().subscribe(
      res => {
        this.types = res.body as Type[];
      }
      );
  }

  onSelection(event:any) {
    this.co.typeDTO = event; 
    }

  createForm() {
    this.form = this.fb.group({
      'name': ['', Validators.required],
      'description': ['', Validators.required],
      'typeDTO':['', Validators.required],
      'image':[''],
      'date':['',Validators.required]
    })
  };

  saveChanges(){
  
    this.co.lat = this.placeLat;
    this.co.lon = this.placeLon;
    this.co.city = this.city;
  
    
    if(this.placeLat === undefined || this.placeLon === undefined){
      return;
    }
    if(this.city === undefined || this.city === null){
      return;
    }    
      this.co.name = this.form.value['name'];
      this.co.description = this.form.value['description'];
      this.co.date = this.form.value['date'];

      this.culturalOfferService.edit(this.co).subscribe(
        result => {
          this.toastr.success('Cultural offer information saved!');
          this.form.reset();
          this.dialogRef.close();
        },
        error => {
          this.toastr.error("Error saving data!");
        });

    }

  cancel(){
    this.dialogRef.close();
  }

  selectFiles(event: any) {
    this.progressInfos = [];
    this.selectedFiles = event.target.files;
    this.uploadFiles();
  }

  uploadFiles() {  
    for (let i = 0; i < this.selectedFiles.length; i++) {
      this.upload(i, this.selectedFiles[i]);
    }
  }

  upload(idx: number, file: any) {
    const formData = new FormData();
    formData.append('file', file);
    this.imageService.add(formData).subscribe(
      result => {
        this.progressInfos[idx] = { fileName: file.name };
        // add new image to the list
        this.co.imageDTO?.push({id: result, description:'', relativePath:''});
      },
      error => {
        this.toastr.error("Error saving image! Choose different one!");
      });
  }

  placeSelected(place: any){
    this.placeLat = place.properties.lat;
    this.placeLon = place.properties.lon;
    this.city = place.properties.address_line1 + " " + place.properties.address_line2;
    }
}


