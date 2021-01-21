import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/pages/services/authentication/authentication.service';

@Component({
  selector: 'app-navbar-user',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarUserComponent implements OnInit {

  constructor(
    private router: Router,
    private authenticationService: AuthenticationService) { }

  ngOnInit(): void {
  }

  signOut(){
    this.authenticationService.signOut().subscribe(      
			result => {
				localStorage.removeItem('user');
				this.router.navigate(['/login']);
			}
		);
  }

}
