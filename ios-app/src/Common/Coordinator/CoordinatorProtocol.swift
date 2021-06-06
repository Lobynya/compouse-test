//
//  CoordinatorProtocol.swift
//  ios-app
//
//  Created by Gusev Aleksandr on 20.01.2021.
//  Copyright © 2021 IceRock Development. All rights reserved.
//

protocol Coordinator: AnyObject {
    var completionHandler: (()->())? { get }
    func start()
    func clear()
}
