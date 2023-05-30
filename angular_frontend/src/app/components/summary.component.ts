import { Component, OnInit, inject } from '@angular/core';
import { ArchiveService } from '../archive.service';
import { Archive } from '../models';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-summary',
  templateUrl: './summary.component.html',
  styleUrls: ['./summary.component.css']
})
export class SummaryComponent implements OnInit {

  archiveSvc = inject(ArchiveService)
  bundles$!: Observable<Archive[]>
  router = inject(Router)

  ngOnInit(): void {
    this.bundles$ = this.archiveSvc.getBundles();
  }

  displayBundle(bundleId: string) {
    this.router.navigate(['/bundle', bundleId]);
  }

}
