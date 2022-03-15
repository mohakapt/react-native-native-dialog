//
//  Extensions.swift
//  NativeDialog
//
//  Created by Heysem Katibi on 3/4/19.
//  Copyright © 2019 Facebook. All rights reserved.
//

import Foundation

import UIKit

extension UIColor {
  convenience init(hexString: String, alpha: CGFloat = 1.0) {

    let hexString: String = hexString.trimmingCharacters(in: CharacterSet.whitespacesAndNewlines)
    let scanner = Scanner(string: hexString)

    if (hexString.hasPrefix("#")) {
      scanner.scanLocation = 1
    }

    var color: UInt32 = 0
    scanner.scanHexInt32(&color)

    let mask = 0x000000FF

    let r = Int(color >> 16) & mask
    let g = Int(color >> 8) & mask
    let b = Int(color) & mask

    let red = CGFloat(r) / 255.0
    let green = CGFloat(g) / 255.0
    let blue = CGFloat(b) / 255.0

    self.init(red: red, green: green, blue: blue, alpha: alpha)
  }

  func toHexString() -> String {
    var r: CGFloat = 0
    var g: CGFloat = 0
    var b: CGFloat = 0
    var a: CGFloat = 0

    getRed(&r, green: &g, blue: &b, alpha: &a)
    let rgb: Int = (Int)(r * 255) << 16 | (Int)(g * 255) << 8 | (Int)(b * 255) << 0
    return String(format: "# % 06x", rgb)
  }
}

extension UIViewController {
  fileprivate func getTopViewController(base: UIViewController? = UIApplication.shared.keyWindow?.rootViewController) -> UIViewController? {
    if let nav = base as? UINavigationController {
      return getTopViewController(base: nav.visibleViewController)
    } else if let tab = base as? UITabBarController, let selected = tab.selectedViewController {
      return getTopViewController(base: selected)
    } else if let presented = base?.presentedViewController {
      return getTopViewController(base: presented)
    }
    return base
  }

  var topViewController: UIViewController? {
    return getTopViewController(base: self)
  }
}


extension UIApplication {
  var topViewController: UIViewController? {
    var keyWindow: UIWindow?
    if #available(iOS 13.0, *) {
      keyWindow = self.connectedScenes
        .filter({$0.activationState == .foregroundActive})
        .compactMap({$0 as? UIWindowScene})
        .first?.windows
        .filter({$0.isKeyWindow}).first
    } else {
      keyWindow = self.keyWindow
    }

    guard let keyWindow = keyWindow else { return nil }

    return keyWindow.rootViewController?.topViewController
  }

  var rootViewController: UIViewController? {
    var keyWindow: UIWindow?
    if #available(iOS 13.0, *) {
      keyWindow = self.connectedScenes
        .filter({$0.activationState == .foregroundActive})
        .compactMap({$0 as? UIWindowScene})
        .first?.windows
        .filter({$0.isKeyWindow}).first
    } else {
      keyWindow = self.keyWindow
    }

    guard let keyWindow = keyWindow else { return nil }

    return keyWindow.rootViewController
  }
}
