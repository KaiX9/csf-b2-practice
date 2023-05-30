import { Component, ElementRef, OnInit, ViewChild, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ArchiveService } from '../archive.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-uploadform',
  templateUrl: './uploadform.component.html',
  styleUrls: ['./uploadform.component.css']
})
export class UploadformComponent implements OnInit {

  form!: FormGroup
  fb = inject(FormBuilder)
  router = inject(Router)
  archiveSvc = inject(ArchiveService)

  @ViewChild('uploadZip')
  uploadZip!: ElementRef

  ngOnInit(): void {
    this.form = this.createForm();
  }

  createForm(): FormGroup {
    return this.fb.group({
      name: this.fb.control<string>('', [ Validators.required ]),
      title: this.fb.control<string>('', [ Validators.required ]),
      comments: this.fb.control<string>(''),
      file: this.fb.control<File | null>(null, [ Validators.required ])
    })
  }

  upload() {
    const f: File = this.uploadZip.nativeElement.files[0];
    const data = this.form.value;
    console.info(">>> data: ", data);
    console.info(">>> file: ", f);

    this.archiveSvc.upload(data, f).subscribe(
      result => {
        console.info('>>: ', result);
        this.router.navigate(['/bundle', result.bundleId]);
      },
      error => {
        alert(JSON.stringify(error.error));
      }
    );
  }

}
