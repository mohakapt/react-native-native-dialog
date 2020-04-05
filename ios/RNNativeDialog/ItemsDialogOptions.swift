//
//  ItemsDialogOptions.swift
//  RNNativeDialog
//
//  Created by Heysem Katibi on 3/6/19.
//  Copyright © 2019 Facebook. All rights reserved.
//

import Foundation
import UIKit
import PopupDialog

enum ItemsMode {
  case `default`
  case single
  case multiple
}

struct Item {
  let id: NSObject
  let title: String
}

class ItemsViewController: UIViewController, UITableViewDataSource, UITableViewDelegate {

  @IBOutlet weak var titleLabel: UILabel!
  @IBOutlet weak var messageLabel: UILabel!
  @IBOutlet weak var tableSeparator: UIView!
  @IBOutlet weak var tableView: UITableView!
  @IBOutlet weak var tableHeight: NSLayoutConstraint!

  var dialogOptions: ItemsDialogOptions!

  override func viewDidLoad() {
    super.viewDidLoad()

    self.tableView.register(UITableViewCell.self, forCellReuseIdentifier: "itemTableCell")
    self.tableView.tableFooterView = UIView(frame: CGRect(x: 0, y: 0, width: tableView.frame.size.width, height: 1))

    self.updateTheme()

    if let title = dialogOptions.title {
      titleLabel.isHidden = false
      titleLabel.text = title
    } else {
      titleLabel.isHidden = true
    }

    if let message = dialogOptions.message {
      messageLabel.isHidden = false
      messageLabel.text = message
    } else {
      messageLabel.isHidden = true
    }

    let itemCount = dialogOptions.items.count
    let cellHeight = 46
    let screenHeight = UIScreen.main.bounds.height
    let safeArea: CGFloat = 220
    let acceptableHeight = screenHeight - safeArea

    var estematedHeight = CGFloat(itemCount * cellHeight) + 1
    if estematedHeight > acceptableHeight {
      estematedHeight = acceptableHeight
    }

    self.tableHeight.constant = estematedHeight
  }

  private func updateTheme() {
    self.view.tintColor = dialogOptions.accentColor

    if dialogOptions.theme == .dark {
      titleLabel.textColor = .white
      messageLabel.textColor = UIColor(white: 0.8, alpha: 1)

      tableSeparator.backgroundColor = UIColor(white: 1, alpha: 0.08)
      tableView.indicatorStyle = .white
      tableView.separatorColor = UIColor(white: 1, alpha: 0.08)
    } else {
      titleLabel.textColor = .black
      messageLabel.textColor = UIColor(white: 0.2, alpha: 1)

      tableSeparator.backgroundColor = UIColor(white: 0, alpha: 0.08)
      tableView.separatorColor = UIColor(white: 0, alpha: 0.08)
      tableView.indicatorStyle = .black
    }
  }

  private func updateTheme(_ cell: UITableViewCell) {
    cell.tintColor = dialogOptions.accentColor
    cell.backgroundColor = .clear
    cell.textLabel?.font = UIFont.systemFont(ofSize: 15)

    if dialogOptions.theme == .dark {
      cell.textLabel?.textColor = UIColor(white: 0.9, alpha: 1)
      cell.selectedBackgroundView?.backgroundColor = UIColor(white: 1, alpha: 0.04)
    } else {
      cell.textLabel?.textColor = UIColor(white: 0.1, alpha: 1)
      cell.selectedBackgroundView?.backgroundColor = UIColor(white: 0, alpha: 0.04)
    }
  }

  func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
    return dialogOptions.items.count
  }

  func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
    let cell = tableView.dequeueReusableCell(withIdentifier: "itemTableCell", for: indexPath)
    cell.selectedBackgroundView = UIView()

    let item = dialogOptions.items[indexPath.row]
    let selected = dialogOptions.selectedIds.contains(item.id)

    cell.textLabel?.text = item.title
    cell.accessoryType = selected ? .checkmark : .none

    updateTheme(cell)
    return cell
  }

  func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
    return 46
  }

  func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
    let cell = tableView.cellForRow(at: indexPath as IndexPath)
    cell?.setSelected(false, animated: true)

    let item = dialogOptions.items[indexPath.row]

    switch dialogOptions.mode {
    case .multiple:
      if dialogOptions.selectedIds.contains(item.id) {
        dialogOptions.selectedIds.removeAll { $0 == item.id }
        cell?.accessoryType = .none
      } else {
        dialogOptions.selectedIds.append(item.id)
        cell?.accessoryType = .checkmark
      }
      break
    case .single:
      if let selectedCellId = dialogOptions.selectedIds.first {
        guard selectedCellId != item.id else {
          return
        }
        if let selectedCellIndex = dialogOptions.items.firstIndex(where: { $0.id == selectedCellId }) {
          let selectedCell = tableView.cellForRow(at: IndexPath(row: selectedCellIndex, section: 0))
          selectedCell?.accessoryType = .none
        }
      }

      dialogOptions.selectedIds.removeAll()
      dialogOptions.selectedIds.append(item.id)
      cell?.accessoryType = .checkmark
      break
    default:
      let selectedItem = dialogOptions.items[indexPath.row]
      dialogOptions.itemSelectHandler?([selectedItem.id])
      self.dismiss(animated: true, completion: nil)
      break
    }
  }
}

class ItemsDialogOptions: DialogOptions {
  let mode: ItemsMode
  let items: [Item]
  var selectedIds: [NSObject]
  var itemSelectHandler: (([NSObject]) -> Void)?

  override init(options: [String: Any]) {
    self.items = (options["items"] as? [[String: Any]])?.map({ x in
      let id = x["id"] ?? 0
      let title = x["title"] as? String ?? ""

      return Item(id: id as! NSObject, title: title)
    }) ?? []

    self.selectedIds = (options["selectedItems"] as? [Any] ?? []).map({ x in
      return x as! NSObject
    })

    switch (options["mode"] as? String ?? "default").lowercased() {
    case "single":
      self.mode = .single
      if self.selectedIds.count > 1 {
        self.selectedIds = [self.selectedIds[0]]
      }
      break
    case "multiple":
      self.mode = .multiple
      break
    default:
      self.mode = .default
      self.selectedIds = []
      break
    }

    super.init(options: options)
  }

  override func buildNativeDialog() -> UIAlertController {
    let alertController = super.buildNativeDialog()

    self.items.forEach { item in
      let action = UIAlertAction(title: item.title, style: .default) { _ in
        self.itemSelectHandler?([item.id])
      }
      alertController.addAction(action)
    }

    injectButtons(dialog: alertController)
    return alertController
  }

  override func buildPopupDialog() -> PopupDialog {
    let bundle = Bundle(for: ItemsViewController.self)
    let itemsVC = ItemsViewController(nibName: "ItemsViewController", bundle: bundle)
    itemsVC.dialogOptions = self

    let popupController = PopupDialog(viewController: itemsVC, buttonAlignment: buttonAlignment, transitionStyle: transitionStyle, preferredWidth: preferredWidth, tapGestureDismissal: cancelOnTouchOutside, panGestureDismissal: cancellable, hideStatusBar: hideStatusBar) {
      self.dismissHandler?()
    }

    injectButtons(dialog: popupController)
    return popupController
  }

  override func shouldInjectButtons() -> Bool {
    return false
  }

  override func positiveButtonTouched() {
    switch mode {
    case .multiple, .single:
      self.itemSelectHandler?(selectedIds)
      break
    default:
      super.positiveButtonTouched()
    }
  }
}
