import { Component, OnInit, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { ArchiveService } from '../archive.service';
import { ActivatedRoute } from '@angular/router';
import { Archive } from '../models';

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.css']
})
export class DetailsComponent implements OnInit {

  arcObs$!: Observable<Archive>
  archiveSvc = inject(ArchiveService)
  activatedRoute = inject(ActivatedRoute)

  ngOnInit(): void {
    const bundleId = this.activatedRoute.snapshot.params['bundleId'];
    this.arcObs$ = this.archiveSvc.getBundleByBundleId(bundleId);
  }
}
