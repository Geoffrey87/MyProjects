import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'iban',
  standalone: true,
})
export class IbanPipe implements PipeTransform {
  transform(value: string): string {
    if (!value) return '';
    return value.replace(/(.{4})/g, '$1 ').trim();
  }
}
