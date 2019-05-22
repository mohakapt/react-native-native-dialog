//
//  DialogOptions.swift
//  RNNativeDialog
//
//  Created by Heysem Katibi on 3/6/19.
//  Copyright © 2019 Facebook. All rights reserved.
//

import Foundation
import UIKit
import PopupDialog

enum Theme: String {
  case light
  case dark
}

enum DialogStyle: String {
  case popupDialog
  case alert
  case actionSheet
}

enum DialogButton: String {
  case positive
  case negative
  case neutral
}

class DialogOptions: NSObject {
  let theme: Theme
  let accentColor: UIColor

  let title: String?
  let message: String?

  let cancellable: Bool
  let cancelOnTouchOutside: Bool
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

  var buttonHandler: ((DialogButton, [String: String?]?) -> Void)?
  var dismissHandler: (() -> Void)?

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

  func presentDialog(in viewController: UIViewController) {
    if preferredStyle != .popupDialog {
      let dialog = buildNativeDialog()
      viewController.present(dialog, animated: true)
      dialog.view.tintColor = accentColor
    } else {
      updateTheme()
      let dialog = buildPopupDialog()
      viewController.present(dialog, animated: true)
    }
  }

  func buildNativeDialog() -> UIAlertController {
    let alertController = UIAlertController(title: title, message: message, preferredStyle: preferredStyle == .alert ? .alert : .actionSheet)
    injectButtons(dialog: alertController)
    return alertController
  }

  func buildPopupDialog() -> PopupDialog {
    let popupController = PopupDialog(title: title, message: message, image: nil, buttonAlignment: buttonAlignment, transitionStyle: transitionStyle, preferredWidth: preferredWidth, tapGestureDismissal: cancelOnTouchOutside, panGestureDismissal: cancellable, hideStatusBar: hideStatusBar) {
      self.dismissHandler?()
    }

    injectButtons(dialog: popupController)
    return popupController
  }

  func injectButtons(dialog: UIAlertController) {
    if let title = positiveButton {
      let action = UIAlertAction(title: title, style: positiveButtonStyle) { (_) in self.positiveButtonTouched() }
      dialog.addAction(action)
    }

    if let title = negativeButton {
      let action = UIAlertAction(title: title, style: negativeButtonStyle) { (_) in self.negativeButtonTouched() }
      dialog.addAction(action)
    }

    if let title = neutralButton {
      let action = UIAlertAction(title: title, style: neutralButtonStyle) { (_) in self.neutralButtonTouched() }
      dialog.addAction(action)
    }
  }

  func injectButtons(dialog: PopupDialog) {
    let buildButton = { (title: String, style: UIAlertAction.Style, handler: (() -> Void)?) -> PopupDialogButton in
      switch style {
      case .default:
        return DefaultButton(title: title, action: handler)
      case .cancel:
        return CancelButton(title: title, action: handler)
      case .destructive:
        return DestructiveButton(title: title, action: handler)
      }
    }

    if let title = positiveButton {
      let button = buildButton(title, positiveButtonStyle) { () in self.positiveButtonTouched() }
      dialog.addButton(button)
    }

    if let title = negativeButton {
      let button = buildButton(title, negativeButtonStyle) { () in self.negativeButtonTouched() }
      dialog.addButton(button)
    }

    if let title = neutralButton {
      let button = buildButton(title, neutralButtonStyle) { () in self.neutralButtonTouched() }
      dialog.addButton(button)
    }
  }

  func positiveButtonTouched() {
    self.buttonHandler?(.positive, nil)
  }

  func negativeButtonTouched() {
    self.buttonHandler?(.negative, nil)
  }

  func neutralButtonTouched() {
    self.buttonHandler?(.neutral, nil)
  }

  func updateTheme() {
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

      // Customize cancel button appearance
      let eb = DestructiveButton.appearance()
      eb.buttonColor = UIColor(white: 1, alpha: 0.04)
      eb.separatorColor = UIColor(white: 1, alpha: 0.08)
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

      // Customize cancel button appearance
      let eb = DestructiveButton.appearance()
      eb.buttonColor = UIColor(white: 0, alpha: 0.04)
      eb.separatorColor = UIColor(white: 0, alpha: 0.08)
    }

  }
}
