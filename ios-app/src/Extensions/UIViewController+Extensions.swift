//
//  UIViewController+Extensions.swift
//  ios-app
//
//  Created by Andrew Kovalev on 02.05.2021.
//  Copyright © 2021 IceRock Development. All rights reserved.
//

import UIKit

extension UIViewController {
    func topPresentedViewController() -> UIViewController? {
        var topController = self.presentedViewController
        while let presentedViewController = topController?.presentedViewController {
            topController = presentedViewController
        }
        return topController
    }
}
