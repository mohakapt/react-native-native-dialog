//
//  NativeDialog.swift
//  RNNativeDialog
//
//  Created by Heysem Katibi on 1/23/19.
//  Copyright © 2019 Facebook. All rights reserved.
//

import Foundation
import PopupDialog

@objc(NativeDialog)
class NativeDialog: RCTEventEmitter {

  override func supportedEvents() -> [String]! {
    return ["native_dialog__positive_button", "native_dialog__negative_button", "native_dialog__neutral_button", "native_dialog__dismiss_dialog"]
  }

  @objc
  override static func requiresMainQueueSetup() -> Bool {
    return true
  }

  @objc(showDialog:)
  func showDialog(options: [String: Any]) {
    let title = options["title"] as? String
    let message = options["message"] as? String
    let cancellable = options["cancellable"] as? Bool ?? true
    let cancelOnTouchOutside = options["cancelOnTouchOutside"] as? Bool ?? true
    let themeString = options["theme"] as? String ?? "light"
    let theme = Theme(rawValue: themeString)
    let accentColor = UIColor(hexString: options["accentColor"] as? String ?? "#007aff")
    var buttonAlignment = NSLayoutConstraint.Axis.horizontal
    var transitionStyle = PopupDialogTransitionStyle.bounceUp
    let preferredWidth = options["preferredWidth"] as? CGFloat ?? 340
    let hideStatusBar = options["hideStatusBar"] as? Bool ?? false

    if let alignment = options["buttonAlignment"] as? String {
      switch alignment.lowercased() {
      case "vertical":
        buttonAlignment = .vertical
        break
      case "horizontal":
        buttonAlignment = .horizontal
        break
      default:
        break
      }
    }

    if let transition = options["transitionStyle"] as? String {
      switch transition.lowercased() {
      case "bounceup":
        transitionStyle = .bounceUp
        break
      case "bouncedown":
        transitionStyle = .bounceDown
        break
      case "zoomin":
        transitionStyle = .zoomIn
        break
      case "fadein":
        transitionStyle = .fadeIn
        break
      default:
        break
      }
    }

    if theme == .dark {
      // Customize the container view appearance
      let pcv = PopupDialogContainerView.appearance()
      pcv.backgroundColor = UIColor(hexString: "#333333")

      // Customize dialog appearance
      let pv = PopupDialogDefaultView.appearance()
      pv.titleColor = UIColor(white: 1, alpha: 1)
      pv.messageColor = UIColor(white: 0.8, alpha: 1)

      // Customize default button appearance
      let db = DefaultButton.appearance()
      db.titleColor = accentColor
      db.buttonColor = UIColor(white: 1, alpha: 0.04)
      db.separatorColor = UIColor(white: 1, alpha: 0.08)

      // Customize cancel button appearance
      let cb = CancelButton.appearance()
      cb.titleColor = accentColor.withAlphaComponent(0.8)
      cb.buttonColor = UIColor(white: 1, alpha: 0.04)
      cb.separatorColor = UIColor(white: 1, alpha: 0.08)
    } else {
      // Customize the container view appearance
      let pcv = PopupDialogContainerView.appearance()
      pcv.backgroundColor = .white

      // Customize dialog appearance
      let pv = PopupDialogDefaultView.appearance()
      pv.titleColor = UIColor(white: 0, alpha: 1)
      pv.messageColor = UIColor(white: 0.2, alpha: 1)

      // Customize default button appearance
      let db = DefaultButton.appearance()
      db.titleColor = accentColor
      db.buttonColor = UIColor(white: 0, alpha: 0.04)
      db.separatorColor = UIColor(white: 0, alpha: 0.08)

      // Customize cancel button appearance
      let cb = CancelButton.appearance()
      cb.titleColor = accentColor.withAlphaComponent(0.8)
      cb.buttonColor = UIColor(white: 0, alpha: 0.04)
      cb.separatorColor = UIColor(white: 0, alpha: 0.08)
    }

    // Create the dialog
    let popup = PopupDialog(title: title, message: message, image: nil, buttonAlignment: buttonAlignment, transitionStyle: transitionStyle, preferredWidth: preferredWidth, tapGestureDismissal: cancelOnTouchOutside, panGestureDismissal: cancellable, hideStatusBar: hideStatusBar, completion: nil)

    if let title = options["positiveButton"] as? String {
      let button = DefaultButton(title: title) {
        self.sendEvent(withName: "native_dialog__positive_button", body: nil)
      }
      popup.addButton(button)
    }

    if let title = options["negativeButton"] as? String {
      let button = CancelButton(title: title) {
        self.sendEvent(withName: "native_dialog__negative_button", body: nil)
      }
      popup.addButton(button)
    }

    if let title = options["neutralButton"] as? String {
      let button = DefaultButton(title: title) {
        self.sendEvent(withName: "native_dialog__neutral_button", body: nil)
      }
      popup.addButton(button)
    }

    let viewConroller = UIApplication.shared.keyWindow?.rootViewController
    viewConroller?.present(popup, animated: true, completion: nil)
  }

  @objc(showInputDialog:)
  func showInputDialog(options: [String: Any]) {

  }

  @objc(showItemsDialog:)
  func showItemsDialog(options: [String: Any]) {

  }

  @objc(showProgressDialog:)
  func showProgressDialog(options: [String: Any]) {

  }
}

enum Theme: String {
  case light
  case dark
}
