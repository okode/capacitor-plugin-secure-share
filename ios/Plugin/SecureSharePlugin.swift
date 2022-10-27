import Foundation
import Capacitor
import KeychainAccess

@objc(SecureSharePlugin)
public class SecureSharePlugin: CAPPlugin {

    private static let KEYCHAIN_GROUP_NAME = "secureshare"

    @objc func save(_ call: CAPPluginCall) {
        if let data = call.options {
        let keychain = self.getSharedKeyChainInstance()
            do {
                let serializedData = NSKeyedArchiver.archivedData(withRootObject: data)
                if let appID = getCurrentAppID() {
                    try keychain.set(serializedData, key: appID)
                    call.resolve()
                } else {
                    call.reject("Unexpected error. Can't get appID!")
                }
            }
            catch let error {
                call.reject(error.localizedDescription)
            }
        } else {
            call.reject("No data map provided")
        }
    }

    @objc func retrieve(_ call: CAPPluginCall){
        if let appID = getCurrentAppID() {
            let result = retrieve(from: appID, call)
            call.resolve(["data": result])
        } else {
            call.reject("Unexpected error. Can't get appID!")
        }
    }

    @objc func retrieveFrom(_ call: CAPPluginCall){
        if let appID = call.getString("packageName") {
            let result = retrieve(from: appID, call)
            call.resolve(["data": result])
        } else {
            call.resolve(["data": [:]])
        }
    }

    private func retrieve(from: String, _ call: CAPPluginCall) -> Dictionary<String, String> {
        var result: Dictionary<String, String> = [:];
        let keychain = self.getSharedKeyChainInstance()
        do {
            if let data = try keychain.getData(from) {
                if let deserializedData = NSKeyedUnarchiver.unarchiveObject(with: data) as? Dictionary<String, String> {
                    result = deserializedData
                } else {
                    call.reject("Error parsing data from keychain")
                }
            }
        }
        catch let error {
            call.reject(error.localizedDescription)
        }
        return result;
    }

    private func getSharedKeyChainInstance() -> Keychain {
        let appIdentifierPrefix = Bundle.main.infoDictionary?["AppIdentifierPrefix"] as? String ?? ""
        return Keychain(service: "SecureShareService", accessGroup: "\(appIdentifierPrefix)\(SecureSharePlugin.KEYCHAIN_GROUP_NAME)")
    }

    private func getCurrentAppID() -> String? {
        return Bundle.main.infoDictionary?["CFBundleIdentifier"] as? String
    }
}
