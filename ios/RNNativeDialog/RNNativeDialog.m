//
//  RNNativeDialog.m
//  react-native-native-dialog
//
//  Created by Haytham Katby on 12/2/18.
//  Copyright © 2018 Facebook. All rights reserved.
//

#import "RNNativeDialog.h"

#import <React/RCTBridge.h>
#import <React/RCTConvert.h>
#import <React/RCTEventDispatcher.h>
#import <React/RCTLog.h>

@implementation RNNativeDialog {

}

@synthesize bridge = _bridge;

RCT_EXPORT_MODULE()

- (dispatch_queue_t)methodQueue {
    return dispatch_get_main_queue();
}

- (NSArray<NSString *> *)supportedEvents {
    return @[POSITIVE_BUTTON, NEGATIVE_BUTTON, NEUTRAL_BUTTON, DISMISS_DIALOG];
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
