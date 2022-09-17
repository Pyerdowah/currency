import {Component, OnInit} from '@angular/core';
import {CurrencyService} from "./currency/CurrencyService.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'currencyFrontend';
  currencyArray: any
  show: any
  constructor(private currencyService: CurrencyService) {
  }

  ngOnInit(): void {
    this.show = false;
  }

  getData(startDate: string, endDate: string): void{
    this.currencyService.getCurrencyData(startDate, endDate)
      .subscribe(
        (response) => {
          this.currencyArray = response
          this.show = true;
        }
    )
  }
}
