import { Component, EventEmitter, Output } from "@angular/core";
import { FormControl, FormGroup, FormGroupDirective, Validators } from "@angular/forms";
import { environment } from "src/environments/environment";
import { UrlService } from "../service/url.service";

@Component({
  selector:'urlInput',
  templateUrl: './urlInput.component.html',
  styleUrls: ['./urlInput.component.scss']
})

export class UrlInputComponent{
  
  urlRegex = /^[A-Za-z][A-Za-z\d.+-]*:\/*(?:\w+(?::\w+)?@)?[^\s/]+(?::\d+)?(?:\/[\w#!:.?+=&%@\-/]*)?$/;
  shortenedUrl:any = null;
  serverBaseUrl = environment.apiBaseUrl;
  
  urlform: FormGroup = new FormGroup({
    url: new FormControl(null, [Validators.required, Validators.pattern(this.urlRegex)])
  });

  @Output() submitEM = new EventEmitter();
  
  constructor(private urlService:UrlService){
    
  }
  
  ngOnInit(){

  }
  
  
  submit(formDirective: FormGroupDirective) {
    if (this.urlform.valid) {
      this.urlService.shortenOriginalUrl(this.urlform.value).subscribe(data=>{
        this.shortenedUrl = data;
        this.urlform.reset();
        this.submitEM.emit();
      }); 
    }
    formDirective.resetForm();
    this.urlform.markAsPristine();
    this.urlform.markAsUntouched();
    this.urlform.updateValueAndValidity();
  }

  triggerFetch(){
    this.submitEM.emit();
  }
}