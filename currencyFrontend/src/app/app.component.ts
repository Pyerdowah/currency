import {Component, OnInit} from '@angular/core';
import {CurrencyService} from "./currency/CurrencyService.service";
import {formatDate} from "@angular/common";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit{
  title = 'currencyFrontend';
  currencyArray: any
  todayDate: any;
  weekAgoDate: any;
  show: any
  element: any
  errorMessage: any
  constructor(private currencyService: CurrencyService) {
    this.todayDate = formatDate(new Date(), 'yyyy-MM-dd', 'en')
    let helper = new Date;
    let weekLength = 7;
    this.weekAgoDate = formatDate(new Date(helper.getFullYear(), helper.getMonth(), helper.getDate() - weekLength), 'yyyy-MM-dd', 'en')
  }

  ngOnInit(): void {
    this.show = false
    this.getData(this.weekAgoDate, this.todayDate)
  }

  public getData(startDate: string, endDate: string): void{
    this.currencyService.getCurrencyData(startDate, endDate)
      .subscribe(
        (response) => {
          this.currencyArray = response
        },
        (error: HttpErrorResponse) => {
          this.setErrorMessage(error)
          this.show = true
          setInterval(() => {this.resetShow()}, 5000)
          this.setDefaultValuesToDateInput()
        }
    )
  }

  private setErrorMessage(error: HttpErrorResponse) {
    if (error.message.includes('404')) {
      this.errorMessage = 'No data in this time range!'
    }
    else {
      this.errorMessage = 'Wrong data range!'
    }
  }

  private setDefaultValuesToDateInput() {
    this.element = document.getElementById('startDate') as HTMLElement;
    const startDate = this.element
    startDate.value = this.weekAgoDate
    this.element = document.getElementById('endDate') as HTMLElement;
    const endDate = this.element
    endDate.value = this.todayDate
  }

  private resetShow() {
    this.show = false
  }
}
