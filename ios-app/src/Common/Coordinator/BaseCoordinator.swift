//
//  BaseCoordinator.swift
//  ios-app
//
//  Created by Gusev Aleksandr on 20.01.2021.
//  Copyright © 2021 IceRock Development. All rights reserved.
//

import Foundation
import UIKit
import MultiPlatformLibrary

class BaseCoordinator: NSObject, Coordinator, UINavigationControllerDelegate {
    var childCoordinators: [Coordinator] = []
    var completionHandler: (() -> ())?
    fileprivate var clearHandler: (() -> ())? = nil
    
    let window: UIWindow
    let factory: SharedFactory
    
    var navigationController: UINavigationController?
    
    init(window: UIWindow, factory: SharedFactory) {
        self.window = window
        self.factory = factory
    }
    
    func addDependency<Child>(_ coordinator: Child, completion: (() -> Void)? = nil) -> Child where Child : BaseCoordinator {
        for element in childCoordinators.compactMap({ $0 as? Child }) {
            if element === coordinator { return element }
        }
        coordinator.completionHandler = { [weak self, weak coordinator] in
            self?.removeDependency(coordinator)
            completion?()
        }
        childCoordinators.append(coordinator)
        return coordinator
    }
    
    func clear() {
        clearHandler?()
        childCoordinators.forEach {
            $0.clear()
        }
        childCoordinators.removeAll()
    }
    
    private func removeDependency(_ coordinator: Coordinator?) {
        clearHandler?()
        guard
            childCoordinators.isEmpty == false,
            let coordinator = coordinator
        else { return }
        
        for (index, element) in childCoordinators.enumerated() {
            if element === coordinator {
                childCoordinators.remove(at: index)
                break
            }
        }
    }
    
    //Cases
    //1. Initial with window - create NV, etc..
    //2. Exists navcontroller,
    
    func start() {
        //
    }
    
    func beginInNewNavigation(_ controller: UIViewController) -> UINavigationController {
        let newNavigationController = UINavigationController()
        self.navigationController = newNavigationController

        newNavigationController.setViewControllers([controller], animated: false)

        self.window.rootViewController = newNavigationController
        
        self.clearHandler = { [weak self] in
            //get controllers and view models, clear them
            self?.popToRoot()
        }
        
        return newNavigationController
    }
    
    func beginInExistNavigation(_ controller: UIViewController) {
        let prevController = self.navigationController?.topViewController
        self.clearHandler = { [weak self, weak prevController] in
            //get controllers and view models, clear them
            if let prev = prevController {
                self?.popToViewController(controller: prev)
            }
        }
        navigationController?.pushViewController(controller, animated: true)
    }
    
    
    private func popBack() {
        let popVC = self.navigationController?.popViewController(animated: true)
        if let nVC = popVC {
            clearViewModels(forControllers: [nVC])
        } else {
            navigationController?.dismiss(animated: true, completion: nil)
        }
    }
    
    private func clearViewModels(forControllers controllers: [UIViewController]?) {
        let holders = (controllers ?? []).compactMap({ $0 as? ViewModelHolder })
        holders.forEach({ $0.baseViewModel?.onCleared() })
    }
    
    private func dismissModal() {
        let controllers = navigationController?.viewControllers
        navigationController?.dismiss(animated: true, completion: nil)
        clearViewModels(forControllers: controllers)
    }
    
    private func popToViewController(controller vc: UIViewController, animated: Bool = true) {
        let controllers = navigationController?.popToViewController(vc, animated: animated)
        clearViewModels(forControllers: controllers)
    }
    
    private func popToViewController(ofClass: AnyClass, animated: Bool = true) {
        if let vc = navigationController?.viewControllers.last(where: { $0.isKind(of: ofClass) }) {
            let controllers = navigationController?.popToViewController(vc, animated: animated)
            clearViewModels(forControllers: controllers)
        }
    }
    
    private func popToRoot() {
        let controllers = navigationController?.popToRootViewController(animated: true)
        clearViewModels(forControllers: controllers)
    }
    
    func currentViewController() -> UIViewController {
        guard let navController = self.navigationController else { return UIViewController() }
        return navController.topViewController ?? navController.topPresentedViewController() ?? navController
    }
}
