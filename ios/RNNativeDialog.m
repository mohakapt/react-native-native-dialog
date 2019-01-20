//
//  RNNativeDialog.m
//  react-native-native-dialog
//
//  Created by Haytham Katby on 12/2/18.
//  Copyright © 2018 Facebook. All rights reserved.
//

#import "RNNativeDialog.h"

@implementation RNNativeDialog

RCT_EXPORT_MODULE(RNNativeDialog)

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

- (NSArray<NSString *> *)supportedEvents {
    return @[@"native_dialog__positive_button",
             @"native_dialog__negative_button",
             @"native_dialog__neutral_button",
             @"native_dialog__dismiss_dialog"];
}

RCT_EXPORT_METHOD(showDialog:(NSDictionary* ) options) {
    
}

RCT_EXPORT_METHOD(showInputDialog:(NSDictionary* ) options) {
}

RCT_EXPORT_METHOD(showItemsDialog:(NSDictionary* ) options) {
}

RCT_EXPORT_METHOD(showProgressDialog:(NSDictionary* ) options) {
}

@end
  
