import { HttpClient } from "@angular/common/http";
import { Injectable, inject } from "@angular/core";
import { Upload } from "./models";
import { Observable } from "rxjs";

const URL = 'http://localhost:8080/upload'

@Injectable()
export class ArchiveService {

    http = inject(HttpClient)

    upload(u: Upload, f: File): Observable<any> {
        const formData = new FormData();
        formData.set('name', u.name);
        formData.set('title', u.title);
        formData.set('comments', u.comments);
        formData.set('archive', f);

        return this.http.post<any>(URL, formData);
    }
}