import Foundation

@objc public class SecureShare: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
