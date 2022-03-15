//
//  RatingViewController.swift
//  NativeDialog
//
//  Created by Heysem Katibi on 5.04.2020.
//  Copyright © 2020 Facebook. All rights reserved.
//

import Foundation
import UIKit
import PopupDialog
import RatingStar
import Cosmos

enum RatingMode {
  case rose
  case bar
}

class RatingViewController: UIViewController, UIGestureRecognizerDelegate {

  @IBOutlet weak var titleLabel: UILabel!
  @IBOutlet weak var messageLabel: UILabel!
  @IBOutlet var bottomMargin: NSLayoutConstraint!
  @IBOutlet var ratingStar: UIRatingStar!
  @IBOutlet var cosmosView: CosmosView!

  var dialogOptions: RatingDialogOptions!

  override func viewDidLoad() {
    super.viewDidLoad()
    self.updateTheme()

    titleLabel.text = dialogOptions.title
    messageLabel.text = dialogOptions.message


    ratingStar.value = dialogOptions.value
    ratingStar.delegate = dialogOptions

    cosmosView.rating = Double(dialogOptions.value)
    cosmosView.settings.starSize = 42
    cosmosView.didFinishTouchingCosmos = { newValue in self.dialogOptions.ratingChanged(newValue: Int(newValue))}

    if dialogOptions.mode == .bar {
      cosmosView.isHidden = false
      ratingStar.isHidden = true
      bottomMargin.constant = 30
    } else {
      cosmosView.isHidden = true
      ratingStar.isHidden = false
      bottomMargin.constant = 6
    }
  }

  private func updateTheme() {
    let accentColor = dialogOptions.accentColor ?? UIApplication.shared.rootViewController?.view.tintColor ?? UIColor(hexString: "#007aff")
    let textColor: UIColor
    let messageColor: UIColor
    let starColor: UIColor

    switch dialogOptions.theme {
    case .dark:
      textColor = .white
      messageColor = UIColor(white: 0.8, alpha: 1)
      starColor = UIColor.white.withAlphaComponent(0.2)
      break

    case .light:
      textColor = .black
      messageColor = UIColor(white: 0.2, alpha: 1)
      starColor = UIColor.black.withAlphaComponent(0.2)
      break

    default:
      if #available(iOS 13.0, *) {
        textColor = .label
        messageColor = .tertiaryLabel
        starColor = UIColor.label.withAlphaComponent(0.2)
      } else {
        textColor = .black
        messageColor = UIColor(white: 0.2, alpha: 1)
        starColor = UIColor.black.withAlphaComponent(0.2)
      }
      break
    }

    self.view.tintColor = accentColor

    ratingStar.selectionColor = accentColor
    cosmosView.backgroundColor = .clear
    cosmosView.settings.filledColor = accentColor
    cosmosView.settings.filledBorderColor = accentColor

    titleLabel.textColor = textColor
    messageLabel.textColor = messageColor

    ratingStar.starColor = starColor
    cosmosView.settings.emptyColor = starColor
    cosmosView.settings.emptyBorderColor = starColor
  }

}

class RatingDialogOptions: DialogOptions, UIRatingStarDelegate {
  let mode: RatingMode
  let value: Int

  private var newValue: Int

  override init(options: [String: Any]) {
    switch (options["mode"] as? String ?? "rose").lowercased() {
    case "rose":
      self.mode = .rose
      break
    default:
      self.mode = .bar
      break
    }

    self.value = options["value"] as? Int ?? 0
    self.newValue = self.value

    super.init(options: options)
  }

  override func buildPopupDialog() -> PopupDialog {
    let bundle = Bundle(for: RatingViewController.self)
    let ratingVC = RatingViewController(nibName: "RatingViewController", bundle: bundle)
    ratingVC.dialogOptions = self

    let popupController = PopupDialog(viewController: ratingVC, buttonAlignment: buttonAlignment, transitionStyle: transitionStyle, preferredWidth: preferredWidth, tapGestureDismissal: cancelOnTouchOutside, panGestureDismissal: cancellable, hideStatusBar: hideStatusBar) {
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

  func ratingChanged(newValue: Int) {
    self.newValue = newValue
  }
}
