import SwiftUI
import ComposeApp
import FirebaseCore
import FirebaseAnalytics

class AppDelegate: NSObject, UIApplicationDelegate {
    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil
    ) -> Bool {
        FirebaseApp.configure()
        KoinInitializerKt.doInitKoin()
        initializeAnalytics()
        return true
    }

    private func initializeAnalytics() {
        AnalyticsHelper.shared.initialize { name, parameters in
            var firebaseParams: [String: Any] = [:]
            for (key, value) in parameters {
                if let stringValue = value as? String {
                    firebaseParams[key] = stringValue
                } else if let numberValue = value as? NSNumber {
                    firebaseParams[key] = numberValue
                } else {
                    firebaseParams[key] = String(describing: value)
                }
            }
            Analytics.logEvent(name, parameters: firebaseParams)
        }
    }
}

@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
