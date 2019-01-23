//
//  RNNativeDialog.m
//  react-native-native-dialog
//
//  Created by Haytham Katby on 12/2/18.
//  Copyright © 2018 Facebook. All rights reserved.
//

#import "React/RCTBridgeModule.h"
#import "React/RCTEventEmitter.h"

@interface RCT_EXTERN_REMAP_MODULE(RNNativeDialog, NativeDialog, RCTEventEmitter)
RCT_EXTERN_METHOD(showDialog:(NSDictionary* ) options)
RCT_EXTERN_METHOD(showInputDialog:(NSDictionary* ) options)
RCT_EXTERN_METHOD(showItemsDialog:(NSDictionary* ) options)
RCT_EXTERN_METHOD(showProgressDialog:(NSDictionary* ) options)
@end
