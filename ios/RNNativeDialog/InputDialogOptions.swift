//
//  InputDialogOptions.swift
//  RNNativeDialog
//
//  Created by Heysem Katibi on 3/6/19.
//  Copyright © 2019 Facebook. All rights reserved.
//

import Foundation
import UIKit
import PopupDialog

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
}
