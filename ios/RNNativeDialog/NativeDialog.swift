//
//  NativeDialog.swift
//  RNNativeDialog
//
//  Created by Heysem Katibi on 1/23/19.
//  Copyright © 2019 Facebook. All rights reserved.
//

import Foundation
import UIKit
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
    let opts = DialogOptions(options: options)

    let viewConroller = UIApplication.shared.keyWindow?.rootViewController

    if opts.preferredStyle != .popupDialog {
      let alert = UIAlertController(title: opts.title, message: opts.message, preferredStyle: opts.preferredStyle == .alert ? .alert : .actionSheet)

      if let title = opts.positiveButton {
        let action = UIAlertAction(title: title, style: opts.positiveButtonStyle) { (_) in
          self.sendEvent(withName: "native_dialog__positive_button", body: nil)
        }
        alert.addAction(action)
      }

      if let title = opts.negativeButton {
        let action = UIAlertAction(title: title, style: opts.negativeButtonStyle) { (_) in
          self.sendEvent(withName: "native_dialog__negative_button", body: nil)
        }
        alert.addAction(action)
      }

      if let title = opts.neutralButton {
        let action = UIAlertAction(title: title, style: opts.neutralButtonStyle) { (_) in
          self.sendEvent(withName: "native_dialog__neutral_button", body: nil)
        }
        alert.addAction(action)
      }

      viewConroller?.present(alert, animated: true)
      alert.view.tintColor = opts.accentColor
    } else {
      if opts.theme == .dark {
        // Customize the container view appearance
        let pcv = PopupDialogContainerView.appearance()
        pcv.backgroundColor = UIColor(hexString: "#333333")

        // Customize dialog appearance
        let pv = PopupDialogDefaultView.appearance()
        pv.titleColor = UIColor(white: 1, alpha: 1)
        pv.messageColor = UIColor(white: 0.8, alpha: 1)

        // Customize default button appearance
        let db = DefaultButton.appearance()
        db.titleColor = opts.accentColor
        db.buttonColor = UIColor(white: 1, alpha: 0.04)
        db.separatorColor = UIColor(white: 1, alpha: 0.08)

        // Customize cancel button appearance
        let cb = CancelButton.appearance()
        cb.titleColor = opts.accentColor.withAlphaComponent(0.8)
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
        db.titleColor = opts.accentColor
        db.buttonColor = UIColor(white: 0, alpha: 0.04)
        db.separatorColor = UIColor(white: 0, alpha: 0.08)

        // Customize cancel button appearance
        let cb = CancelButton.appearance()
        cb.titleColor = opts.accentColor.withAlphaComponent(0.8)
        cb.buttonColor = UIColor(white: 0, alpha: 0.04)
        cb.separatorColor = UIColor(white: 0, alpha: 0.08)
      }

      let popup = PopupDialog(title: opts.title, message: opts.message, image: nil, buttonAlignment: opts.buttonAlignment, transitionStyle: opts.transitionStyle, preferredWidth: opts.preferredWidth, tapGestureDismissal: opts.cancelOnTouchOutside, panGestureDismissal: opts.cancellable, hideStatusBar: opts.hideStatusBar) {
        self.sendEvent(withName: "native_dialog__dismiss_dialog", body: nil)
      }

      if let title = opts.positiveButton {
        let button = DefaultButton(title: title) {
          self.sendEvent(withName: "native_dialog__positive_button", body: nil)
        }
        popup.addButton(button)
      }

      if let title = opts.negativeButton {
        let button = CancelButton(title: title) {
          self.sendEvent(withName: "native_dialog__negative_button", body: nil)
        }
        popup.addButton(button)
      }

      if let title = opts.neutralButton {
        let button = DefaultButton(title: title) {
          self.sendEvent(withName: "native_dialog__neutral_button", body: nil)
        }
        popup.addButton(button)
      }

      viewConroller?.present(popup, animated: true, completion: nil)
    }
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

enum DialogStyle: String {
  case popupDialog
  case alert
  case actionSheet
}

struct DialogOptions {
  let title: String?
  let message: String?
  let cancellable: Bool
  let cancelOnTouchOutside: Bool
  let theme: Theme
  let accentColor: UIColor
  let buttonAlignment: NSLayoutConstraint.Axis
  let transitionStyle: PopupDialogTransitionStyle
  let preferredWidth: CGFloat
  let hideStatusBar: Bool
  let preferredStyle: DialogStyle
  let positiveButton: String?
  let negativeButton: String?
  let neutralButton: String?
  let positiveButtonStyle: UIAlertAction.Style
  let negativeButtonStyle: UIAlertAction.Style
  let neutralButtonStyle: UIAlertAction.Style

  init(options: [String: Any]) {
    self.title = options["title"] as? String
    self.message = options["message"] as? String
    self.cancellable = options["cancellable"] as? Bool ?? true
    self.cancelOnTouchOutside = options["cancelOnTouchOutside"] as? Bool ?? true
    let themeString = options["theme"] as? String ?? "light"
    self.theme = Theme(rawValue: themeString)!
    self.accentColor = UIColor(hexString: options["accentColor"] as? String ?? "#007aff")
    self.preferredWidth = options["preferredWidth"] as? CGFloat ?? 340
    self.hideStatusBar = options["hideStatusBar"] as? Bool ?? false
    let preferredStyleString = options["preferredStyle"] as? String ?? "popupDialog"
    self.preferredStyle = DialogStyle(rawValue: preferredStyleString)!
    self.positiveButton = options["positiveButton"] as? String
    self.negativeButton = options["negativeButton"] as? String
    self.neutralButton = options["neutralButton"] as? String

    switch (options["buttonAlignment"] as? String ?? "default").lowercased() {
    case "vertical":
      self.buttonAlignment = .vertical
      break
    case "horizontal":
      self.buttonAlignment = .horizontal
      break
    default:
      var buttons = 0
      if let _ = self.positiveButton {
        buttons += 1
      }
      if let _ = self.negativeButton {
        buttons += 1
      }
      if let _ = self.neutralButton {
        buttons += 1
      }
      self.buttonAlignment = buttons > 2 ? .vertical : .horizontal
      break
    }

    switch (options["transitionStyle"] as? String ?? "bounceUp").lowercased() {
    case "bouncedown":
      self.transitionStyle = .bounceDown
      break
    case "zoomin":
      self.transitionStyle = .zoomIn
      break
    case "fadein":
      self.transitionStyle = .fadeIn
      break
    default:
      self.transitionStyle = .bounceUp
      break
    }

    switch (options["positiveButtonStyle"] as? String ?? "default").lowercased() {
    case "cancel":
      self.positiveButtonStyle = .cancel
      break
    case "destructive":
      self.positiveButtonStyle = .destructive
      break
    default:
      self.positiveButtonStyle = .default
      break
    }

    switch (options["negativeButtonStyle"] as? String ?? "default").lowercased() {
    case "cancel":
      self.negativeButtonStyle = .cancel
      break
    case "destructive":
      self.negativeButtonStyle = .destructive
      break
    default:
      self.negativeButtonStyle = .default
      break
    }

    switch (options["neutralButtonStyle"] as? String ?? "default").lowercased() {
    case "cancel":
      self.neutralButtonStyle = .cancel
      break
    case "destructive":
      self.neutralButtonStyle = .destructive
      break
    default:
      self.neutralButtonStyle = .default
      break
    }

  }
}
