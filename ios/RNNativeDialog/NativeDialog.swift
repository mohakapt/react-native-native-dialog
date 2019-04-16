//
//  NativeDialog.swift
//  RNNativeDialog
//
//  Created by Heysem Katibi on 1/23/19.
//  Copyright © 2019 Facebook. All rights reserved.
//

import React
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
    guard let viewConroller = UIApplication.shared.keyWindow?.rootViewController else {
      return
    }

    let dialogOptions = DialogOptions(options: options)
    dialogOptions.positiveButtonHandler = { () in
      self.sendEvent(withName: "native_dialog__positive_button", body: nil)
    }
    dialogOptions.negativeButtonHandler = { () in
      self.sendEvent(withName: "native_dialog__negative_button", body: nil)
    }
    dialogOptions.neutralButtonHandler = { () in
      self.sendEvent(withName: "native_dialog__neutral_button", body: nil)
    }
    dialogOptions.dismissHandler = { () in
      self.sendEvent(withName: "native_dialog__dismiss_dialog", body: nil)
    }

    dialogOptions.presentDialog(in: viewConroller)
  }

  @objc(showInputDialog:)
  func showInputDialog(options: [String: Any]) {
    guard let viewConroller = UIApplication.shared.keyWindow?.rootViewController else {
      return
    }

    let dialogOptions = InputDialogOptions(options: options)
    dialogOptions.positiveButtonHandler = { () in
      self.sendEvent(withName: "native_dialog__positive_button", body: nil)
    }
    dialogOptions.negativeButtonHandler = { () in
      self.sendEvent(withName: "native_dialog__negative_button", body: nil)
    }
    dialogOptions.neutralButtonHandler = { () in
      self.sendEvent(withName: "native_dialog__neutral_button", body: nil)
    }
    dialogOptions.dismissHandler = { () in
      self.sendEvent(withName: "native_dialog__dismiss_dialog", body: nil)
    }

    dialogOptions.presentDialog(in: viewConroller)
  }

  @objc(showItemsDialog:)
  func showItemsDialog(options: [String: Any]) {

  }

  @objc(showProgressDialog:)
  func showProgressDialog(options: [String: Any]) {

  }
}
