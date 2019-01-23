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

    let image = UIImage(named: "pexels-photo-103290")

    // Create the dialog
    let popup = PopupDialog(title: title, message: message, image: image)

    if let title = options["positiveButton"] as? String {
      let button = DefaultButton(title: title) {
      }
      popup.addButton(button)
    }

    if let title = options["negativeButton"] as? String {
      let button = CancelButton(title: title) {
      }
      popup.addButton(button)
    }

    if let title = options["neutralButton"] as? String {
      let button = DefaultButton(title: title) {
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
