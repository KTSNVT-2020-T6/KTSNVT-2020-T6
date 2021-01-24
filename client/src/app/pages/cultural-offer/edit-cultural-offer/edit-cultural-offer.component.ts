import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Component, Inject, OnInit} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { CulturalOffer } from '../../../core/model/CulturalOffer';
import { Type } from '../../../core/model/Type';
import { CulturalOfferDetailsService } from '../../../core/services/cultural-offer-details/cultural-offer-details.service';
import { ImageService } from '../../../core/services/image/image.service';
import { TypeService } from '../../../core/services/type/type.service';

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
  loading: boolean = false;;

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
      'date':['']
    })
  };

  saveChanges(){
    if(this.form.value['name'] === '')
      return;
    if(this.form.value['description'] === '')
      return;

    if(!(this.placeLat === undefined || this.placeLon === undefined)){
      this.co.lat = this.placeLat;
      this.co.lon = this.placeLon;
      this.co.city = this.city;
    }    
      this.co.name = this.form.value['name'];
      this.co.description = this.form.value['description'];
      this.co.date = this.form.value['date'];

      this.loading = true;
      this.culturalOfferService.edit(this.co).subscribe(
        result => {
          this.loading = false;
          this.toastr.success('Cultural offer information saved!');
          this.form.reset();
          this.dialogRef.close();
          
        },
        error => {
          this.loading = false;
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


