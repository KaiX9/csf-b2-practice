import { HttpClient } from "@angular/common/http";
import { Injectable, inject } from "@angular/core";
import { Archive, Upload } from "./models";
import { Observable } from "rxjs";

const URL_UPLOAD = '/upload'
const URL_BUNDLE = '/bundle'
const URL_ALLBUNDLES = '/bundles'

@Injectable()
export class ArchiveService {

    http = inject(HttpClient)

    upload(u: Upload, f: File): Observable<any> {
        const formData = new FormData();
        formData.set('name', u.name);
        formData.set('title', u.title);
        formData.set('comments', u.comments);
        formData.set('archive', f);

        return this.http.post<any>(URL_UPLOAD, formData);
    }

    getBundleByBundleId(bundleId: string): Observable<Archive> {
        return this.http.get<Archive>(`${URL_BUNDLE}/${bundleId}`);
    }

    getBundles(): Observable<Archive[]> {
        return this.http.get<Archive[]>(URL_ALLBUNDLES);
    }
}