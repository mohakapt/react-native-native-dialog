//
//  InputDialogOptions.swift
//  NativeDialog
//
//  Created by Heysem Katibi on 3/6/19.
//  Copyright © 2019 Facebook. All rights reserved.
//

import Foundation
import UIKit
import PopupDialog

class InputViewController: UIViewController {

  @IBOutlet weak var titleLabel: UILabel!
  @IBOutlet weak var messageLabel: UILabel!
  @IBOutlet weak var textField: UITextField!

  var dialogOptions: InputDialogOptions!

  override func viewDidLoad() {
    super.viewDidLoad()
    self.updateTheme()

    titleLabel.text = dialogOptions.title
    messageLabel.text = dialogOptions.message

    textField.text = dialogOptions.value
    textField.attributedPlaceholder = NSAttributedString(string: dialogOptions.placeholder ?? "",
                                                         attributes: [.foregroundColor: UIColor(white: 0.6, alpha: 1)])

    textField.keyboardType = dialogOptions.keyboardType
    textField.autocorrectionType = dialogOptions.autoCorrect ? .yes : .no
    textField.autocapitalizationType = dialogOptions.autoCapitalize
    textField.isSecureTextEntry = dialogOptions.secureTextEntry

    textField.delegate = dialogOptions

    view.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(endEditing)))
  }

  override func viewDidAppear(_ animated: Bool) {
    super.viewDidAppear(animated)

    if dialogOptions.autoFocus {
      textField.becomeFirstResponder()
    }
  }

  private func updateTheme() {
    self.view.tintColor = dialogOptions.accentColor

    if dialogOptions.theme == .dark {
      titleLabel.textColor = .white
      messageLabel.textColor = UIColor(white: 0.8, alpha: 1)
      textField.backgroundColor = UIColor(white: 1, alpha: 0.03)
      textField.textColor = .white
    } else {
      titleLabel.textColor = .black
      messageLabel.textColor = UIColor(white: 0.2, alpha: 1)
      textField.backgroundColor = UIColor(white: 0, alpha: 0.005)
      textField.textColor = .black
    }
  }

  @objc func endEditing() {
    view.endEditing(true)
  }
}

extension InputViewController: UITextFieldDelegate {

  func textFieldShouldReturn(_ textField: UITextField) -> Bool {
    endEditing()
    return true
  }
}

class InputDialogOptions: DialogOptions, UITextFieldDelegate {

  let value: String?
  let placeholder: String?
  let keyboardType: UIKeyboardType
  let maxLength: Int?
  let autoFocus: Bool
  let autoCorrect: Bool
  let autoCapitalize: UITextAutocapitalizationType
  let secureTextEntry: Bool
  let selectTextOnFocus: Bool // Not supported yet

  private var newValue: String?

  override init(options: [String: Any]) {
    self.value = options["value"] as? String
    self.placeholder = options["placeholder"] as? String
    self.maxLength = options["maxLength"] as? Int
    self.autoFocus = options["autoFocus"] as? Bool ?? false
    self.autoCorrect = options["autoCorrect"] as? Bool ?? false
    self.secureTextEntry = options["secureTextEntry"] as? Bool ?? false
    self.selectTextOnFocus = options["selectTextOnFocus"] as? Bool ?? false

    switch (options["keyboardType"] as? String ?? "default").lowercased() {
    case "number-pad":
      self.keyboardType = .numberPad
      break
    case "decimal-pad":
      self.keyboardType = .decimalPad
      break
    case "numeric":
      self.keyboardType = .numbersAndPunctuation
      break
    case "email-address":
      self.keyboardType = .emailAddress
      break
    case "phone-pad":
      self.keyboardType = .phonePad
      break
    default:
      self.keyboardType = .default
      break
    }

    switch (options["autoCapitalize"] as? String ?? "none").lowercased() {
    case "characters":
      self.autoCapitalize = .allCharacters
      break
    case "words":
      self.autoCapitalize = .words
      break
    case "sentences":
      self.autoCapitalize = .sentences
      break
    default:
      self.autoCapitalize = .none
      break
    }

    super.init(options: options)
  }

  override func buildNativeDialog() -> UIAlertController {
    let alertController = super.buildNativeDialog()

    alertController.addTextField { (textField) in
      textField.text = self.value
      textField.placeholder = self.placeholder
      textField.keyboardType = self.keyboardType
      textField.autocorrectionType = self.autoCorrect ? .yes : .no
      textField.autocapitalizationType = self.autoCapitalize
      textField.isSecureTextEntry = self.secureTextEntry
      if !self.autoFocus {
        textField.tag = -1
      }
      textField.delegate = self
    }

    return alertController
  }

  override func buildPopupDialog() -> PopupDialog {
    let bundle = Bundle(for: InputViewController.self)
    let inputVC = InputViewController(nibName: "InputViewController", bundle: bundle)
    inputVC.dialogOptions = self

    let popupController = PopupDialog(viewController: inputVC, buttonAlignment: buttonAlignment, transitionStyle: transitionStyle, preferredWidth: preferredWidth, tapGestureDismissal: cancelOnTouchOutside, panGestureDismissal: cancellable, hideStatusBar: hideStatusBar) {
      self.dismissHandler?()
    }

    injectButtons(dialog: popupController)
    return popupController
  }

  override func positiveButtonTouched() {
    self.finishHandler?(.positive, ["value": newValue])
  }

  override func negativeButtonTouched() {
    self.finishHandler?(.negative, ["value": newValue])
  }

  override func neutralButtonTouched() {
    self.finishHandler?(.neutral, ["value": newValue])
  }

  func textFieldShouldBeginEditing(_ textField: UITextField) -> Bool {
    if textField.tag == -1 {
      textField.tag = 0
      return false
    }
    return true
  }

  func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
    guard let maxLength = self.maxLength, let text = textField.text else { return true }
    let count = text.count + string.count - range.length
    return count <= maxLength
  }

  func textFieldDidEndEditing(_ textField: UITextField) {
    self.newValue = textField.text
  }
}
