import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { NgCircularProgressbarModule } from '@plcoder/ng-circular-progressbar';
import { ApplicationsComponent } from './applications/applications.component';
import { AnalyticsComponent } from './analytics/analytics.component';
import { ConfigurationComponent } from './configuration/configuration.component'; 

@NgModule({
  declarations: [
    AppComponent,
    SidebarComponent,
    DashboardComponent,
    ApplicationsComponent,
    AnalyticsComponent,
    ConfigurationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    NgCircularProgressbarModule 
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
