import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {Currency} from "./Currency";

@Injectable({providedIn: 'root'})
export class CurrencyService {

  constructor(private http: HttpClient) {

  }

  public getCurrencyData(startDate: string, endDate: string): Observable<Array<Currency>> {
    return this.http.get<Array<Currency>>(`http://localhost:8080/getData/${startDate}/${endDate}`);
  }
}
