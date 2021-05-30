import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment";

const baseUrl = environment.apiBaseUrl;


@Injectable({
    providedIn: 'root'
})

export class UrlService{

    //serverBaseUrl = environment.apiBaseUrl;
    shortenUrl = "shortenUrl";
    getAllUrl = "getAllUrls";

    constructor(private http: HttpClient) { }

    shortenOriginalUrl(data:any){
        return this.http.post<JSON>(baseUrl+this.shortenUrl, data);
    }

    getAllUrls(data:any): Observable<any> {
        return this.http.post<JSON>(baseUrl+this.getAllUrl, data);
      }
}