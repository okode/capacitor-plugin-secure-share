export interface SecureSharePlugin {
  /**
   * @description      Save data to be shared to other apps.
   *
   * @param            data string to string map holding the data to be saved
   *
   * @returns          A promise of void
   *
   * @errors           Error message from the cordova plugin
   */
  save(data: Record<string, string>): Promise<void>;

  /**
   * @description      Get data saved in secure share storage
   *
   * @returns          A promise of a string to string map with the data saved. An empty map will be returned if no data is found
   *
   * @errors           Error message from the cordova plugin
   */
  retrieve(): Promise<Record<string, string>>;


  /**
   * @description      Get data saved in secure share storage
   *
   * @param            pacakgeName package to retrieve shared data from
   *
   * @returns          A promise of a string to string map with the data saved
   *
   * @errors           Error message from the cordova plugin
   */
  retrieveFrom(data: { packageName: string }): Promise<Record<string, string>>;

}
