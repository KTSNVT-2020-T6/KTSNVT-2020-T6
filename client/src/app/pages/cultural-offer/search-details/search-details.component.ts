import { Component, OnInit, Input, Output} from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { Router, ActivatedRoute } from '@angular/router';
import { EventEmitter } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { SearchDetails } from '../../../core/model/SearchDetails';


@Component({
  selector: 'app-search-details',
  templateUrl: './search-details.component.html',
  styleUrls: ['./search-details.component.scss']
})
export class SearchDetailsComponent implements OnInit {

  searchDetails: SearchDetails = {content: '', city: ''};
  city: any;
  searchForm!: FormGroup;

  @Output() done = new EventEmitter<SearchDetails>();

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private toastr: ToastrService,
    private dialogRef: MatDialogRef<SearchDetailsComponent>)
    {
      this.createForm();
    }

  ngOnInit(): void {
  }

  createForm(): void{
    this.searchForm = this.fb.group({
      content: [''],
      city: ['']
       });
    }

  search(): void{
    this.searchDetails.content = this.searchForm.value.content;
    this.searchDetails.city = this.searchForm.value.city;
    this.done.emit(this.searchDetails);
    this.dialogRef.close();
  }

}
