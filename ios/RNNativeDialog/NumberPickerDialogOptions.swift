//
//  NumberPickerViewController.swift
//  RNNativeDialog
//
//  Created by Heysem Katibi on 5.04.2020.
//  Copyright © 2020 Facebook. All rights reserved.
//

import Foundation
import UIKit
import PopupDialog

class NumberPickerViewController: UIViewController {

  @IBOutlet weak var titleLabel: UILabel!
  @IBOutlet weak var messageLabel: UILabel!
  @IBOutlet var pickerView: UIPickerView!

  var dialogOptions: NumberPickerDialogOptions!

  override func viewDidLoad() {
    super.viewDidLoad()
    self.updateTheme()

    titleLabel.text = dialogOptions.title
    messageLabel.text = dialogOptions.message

    pickerView.delegate = dialogOptions
  }

  override func viewWillAppear(_ animated: Bool) {
    super.viewWillAppear(animated)

    let selectedRow = self.dialogOptions.value - self.dialogOptions.minValue
    pickerView.selectRow(selectedRow, inComponent: 0, animated: false)
  }

  private func updateTheme() {
    self.view.tintColor = dialogOptions.accentColor

    if dialogOptions.theme == .dark {
      titleLabel.textColor = .white
      messageLabel.textColor = UIColor(white: 0.8, alpha: 1)

      if #available(iOS 13.0, *) {
        pickerView.overrideUserInterfaceStyle = .dark
      }
    } else {
      titleLabel.textColor = .black
      messageLabel.textColor = UIColor(white: 0.2, alpha: 1)

      if #available(iOS 13.0, *) {
        pickerView.overrideUserInterfaceStyle = .light
      }
    }
  }

}

class NumberPickerDialogOptions: DialogOptions, UIPickerViewDataSource, UIPickerViewDelegate {
  let value: Int
  let minValue: Int
  let maxValue: Int

  private var newValue: Int

  override init(options: [String: Any]) {
    self.value = options["value"] as? Int ?? 0
    self.minValue = options["minValue"] as? Int ?? 0
    self.maxValue = options["maxValue"] as? Int ?? 20

    self.newValue = self.value

    super.init(options: options)
  }

  override func buildPopupDialog() -> PopupDialog {
    let bundle = Bundle(for: NumberPickerViewController.self)
    let pickerVC = NumberPickerViewController(nibName: "NumberPickerViewController", bundle: bundle)
    pickerVC.dialogOptions = self

    let popupController = PopupDialog(viewController: pickerVC, buttonAlignment: buttonAlignment, transitionStyle: transitionStyle, preferredWidth: preferredWidth, tapGestureDismissal: cancelOnTouchOutside, panGestureDismissal: cancellable, hideStatusBar: hideStatusBar) {
      self.dismissHandler?()
    }

    injectButtons(dialog: popupController)
    return popupController
  }

  override func positiveButtonTouched() {
    self.finishHandler?(.positive, ["value": self.newValue])
  }

  override func negativeButtonTouched() {
    self.finishHandler?(.negative, ["value": self.newValue])
  }

  override func neutralButtonTouched() {
    self.finishHandler?(.neutral, ["value": self.newValue])
  }

  func numberOfComponents(in pickerView: UIPickerView) -> Int {
    return 1
  }

  func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
    return abs(self.maxValue - self.minValue) + 1
  }

  func pickerView(_ pickerView: UIPickerView, attributedTitleForRow row: Int, forComponent component: Int) -> NSAttributedString? {
    let color = (self.theme == .dark) ? UIColor.white : UIColor.black

    if #available(iOS 13.0, *) { } else {
      pickerView.subviews.filter { $0.frame.height < 1 }.forEach {
        $0.backgroundColor = color.withAlphaComponent(0.2)
      }
    }

    let title = String(self.minValue + row)
    return NSAttributedString(string: title, attributes: [.foregroundColor: color])
  }

  func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
    self.newValue = self.minValue + row
  }

}
