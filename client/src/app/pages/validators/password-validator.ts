import { AbstractControl, FormGroup, ValidatorFn } from '@angular/forms';

export function validatePasswordRepeat(group: FormGroup): ValidatorFn {
	return (c: AbstractControl) =>{
		const password = group.controls.password.value;
  		const passwordConfirmation = group.controls.repeatPassword.value;
		console.log(password);
		console.log(passwordConfirmation);
  		return password === passwordConfirmation ? null : { passwordsNotEqual: true }  
	}
}