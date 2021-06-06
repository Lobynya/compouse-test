//
//  BaseViewController.swift
//  ios-app
//
//  Created by Andrey Dorofeev on 23.12.2020.
//  Copyright © 2020 IceRock Development. All rights reserved.
//

import UIKit
import MultiPlatformLibrary

class BaseViewController<VM: ViewModel>: MVVMController<VM> {

    //Call on viewDidLoad if neededd
    func setupKeyboardObservers() {
        NotificationCenter.default.addObserver(self,
                                               selector: #selector(_keyboardWillShow(notification:)),
                                               name: UIResponder.keyboardWillShowNotification, object: nil)
        
        NotificationCenter.default.addObserver(self,
                                               selector: #selector(_keyboardWillHide(notification:)),
                                               name: UIResponder.keyboardWillHideNotification, object: nil)
    }
    
    @objc
    private func _keyboardWillShow(notification: Notification) {
        if let frame: CGRect = notification.userInfo?[UIResponder.keyboardFrameEndUserInfoKey] as? CGRect {
            self.keyboardWillShow(frame: frame)
        }
    }
    
    @objc
    private func _keyboardWillHide(notification: Notification) {
        self.keyboardWillHide()
    }

    func setActivityIndicatorHidden(_ hidden: Bool) {
        if !hidden {
            self.view.endEditing(true)
            showProgress()
        } else {
            hideProgress()
        }
    }

    func bindLoading(_ liveData: LiveData<KotlinBoolean>) {
        liveData.bind(view: self.view!, setter: { [weak self] _, value in
            guard let isLoading = value as? KotlinBoolean? else { return }
            self?.setActivityIndicatorHidden(isLoading?.boolValue ?? false)
        })
    }

    //TODO: Override if use
    func keyboardWillShow(frame: CGRect) { }

    //TODO: Override if use
    func keyboardWillHide() { }

    // Implementation depends on project UI and requirements
    func showProgress() { }

    func hideProgress() { }
}
