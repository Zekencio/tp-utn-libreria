import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ProfileToggleService {
  private toggleSubject = new Subject<void>();
  readonly toggle$ = this.toggleSubject.asObservable();

  requestToggle(): void {
    this.toggleSubject.next();
  }
}
