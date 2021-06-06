//
//  MVVMController.swift
//  ios-app
//
//  Created by Феликс Фальковский on 08.02.2021.
//  Copyright © 2021 IceRock Development. All rights reserved.
//

import MultiPlatformLibrary
import UIKit

protocol ViewModelHolder {
    var baseViewModel: ViewModel? { get }
}

class MVVMController<VM: ViewModel>: UIViewController {
    private (set) var viewModel: VM?
    
    var deinitCallback: (() -> Void)?
    
    func bindViewModel(_ viewModel: VM) {
        loadViewIfNeeded()
        
        self.viewModel = viewModel
    }

    override func didMove(toParent parent: UIViewController?) {
        if (parent == nil) {
            let vm: VM? = viewModel
            DispatchQueue.main.async { [weak vm] in
                vm?.onCleared()
            }
        }
    }

    deinit {
        print("***DEINIT: \(self))")
        deinitCallback?()
    }
}

extension MVVMController {
    func bindControl(control: UIControl, _ event: UIControl.Event, action: ((VM) -> Void)?) {
        UIControlExtKt.setEventHandler(control, event: UInt64(event.rawValue), lambda: { [weak self] _ in
            guard let nVm = self?.viewModel else { return }
            action?(nVm)
        })
    }
}

extension MVVMController: ViewModelHolder {
    var baseViewModel: ViewModel? {
        return self.viewModel
    }
}
