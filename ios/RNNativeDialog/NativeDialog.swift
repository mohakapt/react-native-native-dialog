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
    guard let viewConroller = UIApplication.shared.keyWindow?.rootViewController else {
      return
    }

    let dialogOptions = DialogOptions(options: options)
    dialogOptions.buttonHandler = { (button, extra) in
      switch button {
      case .positive:
        self.sendEvent(withName: "native_dialog__positive_button", body: nil)
        break
      case .negative:
        self.sendEvent(withName: "native_dialog__negative_button", body: nil)
        break
      case .neutral:
        self.sendEvent(withName: "native_dialog__neutral_button", body: nil)
        break
      }
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
    dialogOptions.buttonHandler = { (button, extra) in
      switch button {
      case .positive:
        self.sendEvent(withName: "native_dialog__positive_button", body: extra)
        break
      case .negative:
        self.sendEvent(withName: "native_dialog__negative_button", body: extra)
        break
      case .neutral:
        self.sendEvent(withName: "native_dialog__neutral_button", body: extra)
        break
      }
    }

    dialogOptions.dismissHandler = { () in
      self.sendEvent(withName: "native_dialog__dismiss_dialog", body: nil)
    }

    dialogOptions.presentDialog(in: viewConroller)
  }

  @objc(showItemsDialog:)
  func showItemsDialog(options: [String: Any]) {
    guard let viewConroller = UIApplication.shared.keyWindow?.rootViewController else {
      return
    }

    let dialogOptions = ItemsDialogOptions(options: options)
    dialogOptions.itemSelectHandler = { (selectedIds) in
      self.sendEvent(withName: "native_dialog__positive_button", body: selectedIds)
    }

    dialogOptions.dismissHandler = { () in
      self.sendEvent(withName: "native_dialog__dismiss_dialog", body: nil)
    }

    dialogOptions.presentDialog(in: viewConroller)
  }

  @objc(showProgressDialog:)
  func showProgressDialog(options: [String: Any]) {

  }

  @objc(showTipDialog:)
  func showTipDialog(options: [String: Any]) {

  }

  @objc(showDatePickerDialog:)
  func showDatePickerDialog(options: [String: Any]) {

  }

  @objc(showNumberPickerDialog:)
  func showNumberPickerDialog(options: [String: Any]) {

  }

  @objc(showRatingDialog:)
  func showRatingDialog(options: [String: Any]) {

  }
}
