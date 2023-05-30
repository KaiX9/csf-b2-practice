import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { UploadformComponent } from './components/uploadform.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule, Routes } from '@angular/router';
import { SummaryComponent } from './components/summary.component';
import { ArchiveService } from './archive.service';
import { DetailsComponent } from './components/details.component';

const routes: Routes = [
  { path: '', component: SummaryComponent },
  { path: 'upload', component: UploadformComponent },
  { path: 'bundle/:bundleId', component: DetailsComponent },
  { path: '**', redirectTo: '/', pathMatch: 'full' }
]

@NgModule({
  declarations: [
    AppComponent,
    UploadformComponent,
    SummaryComponent,
    DetailsComponent
  ],
  imports: [
    BrowserModule, 
    ReactiveFormsModule, 
    HttpClientModule,
    RouterModule.forRoot(routes, { useHash: true })
  ],
  providers: [ArchiveService],
  bootstrap: [AppComponent]
})
export class AppModule { }
