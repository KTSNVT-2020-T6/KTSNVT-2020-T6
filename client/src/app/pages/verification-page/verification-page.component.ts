import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { VerificationService } from '../services/verification/verification.service';

@Component({
  selector: 'app-verification-page',
  templateUrl: './verification-page.component.html',
  styleUrls: ['./verification-page.component.scss']
})
export class VerificationPageComponent implements OnInit {
  private token: any;
  public verificationMessage: string = '';

  constructor(
    private verificationService: VerificationService,
    private route : ActivatedRoute,
    private router: Router,
    private toastr: ToastrService) { }

  ngOnInit(): void {
    this.verificationMessage = '';
    this.token = this.route.snapshot.paramMap.get('token');
    this.verificationService.verify(this.token).subscribe(
      res => {
        this.router.navigate(['login']);
        this.toastr.success('Successfully verified!');
      },error => {
        console.log(error);
        this.verificationMessage = 'Token is not valid or expired!';
      }
      
    );
  }

}
