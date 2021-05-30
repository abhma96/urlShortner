import { ThrowStmt } from "@angular/compiler";
import { AfterViewInit, Component, Input, SimpleChanges, ViewChild } from "@angular/core";
import {MatPaginator, PageEvent} from '@angular/material/paginator';
import { MatSort } from "@angular/material/sort";
import {MatTableDataSource} from '@angular/material/table';
import { environment } from "src/environments/environment";
import { UrlService } from "../service/url.service";

export interface URLGridResponse {
  urlId: string;
  originalUrl: string;
  shortenedUrl: string;
  counter: number;
}

export interface URLGridRequest {
  column: string;
  sortOrder: string;
  pageNo: number;
  pageSize: number;
}

@Component({
  selector: 'urlTable',
  templateUrl: './urlTable.component.html',
  styleUrls: ['./urlTable.component.scss']
})

export class UrlTableComponent{
  displayedColumns: string[] = ['urlId', 'originalUrl', 'shortenedUrl', 'counter'];
  dataSource = new MatTableDataSource<URLGridResponse>();
  totalRecords:number;
  serverBaseUrl = environment.apiBaseUrl;
  @Input() isLoadingResults;
  
  urlGridRequest: URLGridRequest = {
    "column": "urlId",
    "sortOrder": "asc",
    "pageNo": 0,
    "pageSize": 5
  }
  
  // @ViewChild(MatPaginator)
  // paginator!: MatPaginator;
  
  
  constructor(private urlService:UrlService){
    this.totalRecords = 0;
    this.isLoadingResults=false;
  }
  
  ngOnInit(){
    
  }
  
  ngOnChanges(changes: SimpleChanges){
    this.fetchGridData(this.urlGridRequest);
  }
  
  fetchGridData(urlGridRequest?:URLGridRequest){
    this.isLoadingResults = true;
    if(urlGridRequest){
      this.urlGridRequest = urlGridRequest;
    }
    setTimeout(() => 
    {
      this.urlService.getAllUrls(this.urlGridRequest).subscribe(data=>{
        this.dataSource=data.result;
        this.totalRecords=data.totalRecords;
        this.isLoadingResults = false;
      },error=>{
        this.isLoadingResults = false;
      }
      );
    },
    1000);
    //this.isLoadingResults = false;
    
  }
  
  pageData(pageEvent:PageEvent){
    
    this.urlGridRequest.pageNo=pageEvent.pageIndex;
    this.urlGridRequest.pageSize=pageEvent.pageSize;
    this.fetchGridData(this.urlGridRequest);
  }
  
  sortData(pageEvent:any){
    this.urlGridRequest.column=pageEvent.active;
    this.urlGridRequest.sortOrder=pageEvent.direction;
    this.fetchGridData(this.urlGridRequest);
  }
  
}