import { registerPlugin } from '@capacitor/core';

import type { SecureSharePlugin } from './definitions';

const SecureShare = registerPlugin<SecureSharePlugin>('SecureShare', {
  web: () => import('./web').then(m => new m.SecureShareWeb()),
});

export * from './definitions';
export { SecureShare };
