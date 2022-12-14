import { WebPlugin } from '@capacitor/core';

import type { SecureSharePlugin } from './definitions';

export class SecureShareWeb extends WebPlugin implements SecureSharePlugin {

  save(data: Record<string, string>): Promise<void> {
    console.log(data);
    throw this.unimplemented('Not implemented on web.');
  }

  retrieve(): Promise<Record<string, string>> {
    throw this.unimplemented('Not implemented on web.');
  }

  retrieveFrom(data: { packageName: string }): Promise<Record<string, string>> {
    console.log(data);
    throw this.unimplemented('Not implemented on web.');
  }

}
