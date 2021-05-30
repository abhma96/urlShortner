import { Component, ViewChild } from '@angular/core';
import { UrlTableComponent } from './urlTable-component/urlTable.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  title = 'urlShortner-ui';
  reloadTable=false;

  @ViewChild(UrlTableComponent)
  urlTableComponent!: UrlTableComponent;

  ngOnInit(){
    this.reloadTable=false;
  }

  reloadTableData(){
    this.urlTableComponent.fetchGridData();
  }
}
