//
//  ItemsDialogOptions.swift
//  RNNativeDialog
//
//  Created by Heysem Katibi on 3/6/19.
//  Copyright © 2019 Facebook. All rights reserved.
//

import Foundation

enum ItemsMode {
  case `default`
  case single
  case multiple
}

struct Item {
  let id: String
  let idNumber: Bool
  let title: String
}

class ItemsDialogOptions: DialogOptions {
  let mode: ItemsMode
  let items: [Item]
  let selectedIds: [String]

  override init(options: [String: Any]) {
    self.items = (options["items"] as? [[String: Any]])?.map({ x in
      let idNumber = x["id"] is Int
      let id = idNumber ? String(x["id"] as? Int ?? 0) : x["id"] as? String ?? "0"
      let title = x["title"] as? String ?? ""

      return Item(id: id, idNumber: idNumber, title: title)
    }) ?? []

    self.selectedIds = (options["selectedItems"] as? [Any] ?? []).map({ x in
      return x is Int ? String(x as? Int ?? 0) : x as? String ?? "0"
    })

    switch (options["mode"] as? String ?? "default").lowercased() {
    case "single":
      self.mode = .single
      break
    case "multiple":
      self.mode = .multiple
      break
    default:
      self.mode = .default
    }

    super.init(options: options)
  }

  override func buildNativeDialog() -> UIAlertController {
    let alertController = super.buildNativeDialog()

    self.items.forEach { item in
      let action = UIAlertAction(title: item.title, style: .default, handler: nil)
      alertController.addAction(action)
    }

    injectButtons(dialog: alertController)
    return alertController
  }

  override func shouldInjectButtons() -> Bool {
    return false
  }
}
