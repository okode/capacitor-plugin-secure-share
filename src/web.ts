import { WebPlugin } from '@capacitor/core';

import type { SecureSharePlugin } from './definitions';

export class SecureShareWeb extends WebPlugin implements SecureSharePlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
