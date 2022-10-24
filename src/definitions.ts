export interface SecureSharePlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
