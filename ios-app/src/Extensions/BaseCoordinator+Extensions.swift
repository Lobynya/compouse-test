//
//  BaseCoordinator+Extensions.swift
//  ios-app
//
//  Created by Andrew Kovalev on 02.05.2021.
//  Copyright © 2021 IceRock Development. All rights reserved.
//

import UIKit

extension BaseCoordinator {
    func showDefaultAlertDialog(title: String?, message: String?) {
        let alert = UIAlertController(title: title,
                                      message: message,
                                      preferredStyle: .alert)

        navigationController?.present(alert, animated: true)
    }
}
