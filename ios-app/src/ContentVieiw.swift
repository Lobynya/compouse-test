//
//  ContentVieiw.swift
//  ios-app
//
//  Created by Aleksey Lobynya on 04.07.2021.
//  Copyright © 2021 IceRock Development. All rights reserved.
//

import SwiftUI

struct ContentView: View {
    
    let viewModel: MainViewModel
    
    var body: some View {
        VStack(alignment: .leading){
                    Text("Turtle Rock")
                        .font(.title)
            HStack {
                ForEach(/*@START_MENU_TOKEN@*/0 ..< 5/*@END_MENU_TOKEN@*/) { item in
                    Text("Joshua Tree National Park")
                        .font(.subheadline)
                }
                Text("California")
                    .font(.subheadline)
            }
        }.padding()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            ContentView()
        }
    }
}
