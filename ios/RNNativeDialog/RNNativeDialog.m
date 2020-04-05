//
//  RNNativeDialog.m
//  react-native-native-dialog
//
//  Created by Haytham Katby on 12/2/18.
//  Copyright © 2018 Facebook. All rights reserved.
//

#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>

@interface RCT_EXTERN_REMAP_MODULE(RNNativeDialog, NativeDialog, RCTEventEmitter)

- (dispatch_queue_t)methodQueue
{
  return dispatch_get_main_queue();
}

RCT_EXTERN_METHOD(showDialog: (NSDictionary* )options
                  resolver: (RCTPromiseResolveBlock)resolve
                  rejecter: (RCTPromiseRejectBlock)reject)


RCT_EXTERN_METHOD(showInputDialog: (NSDictionary* )options
                  resolver: (RCTPromiseResolveBlock)resolve
                  rejecter: (RCTPromiseRejectBlock)reject)


RCT_EXTERN_METHOD(showItemsDialog:(NSDictionary* ) options
                  resolver: (RCTPromiseResolveBlock)resolve
                  rejecter: (RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(showNumberPickerDialog:(NSDictionary* ) options
                  resolver: (RCTPromiseResolveBlock)resolve
                  rejecter: (RCTPromiseRejectBlock)reject)


RCT_EXTERN_METHOD(showRatingDialog:(NSDictionary* ) options
                  resolver: (RCTPromiseResolveBlock)resolve
                  rejecter: (RCTPromiseRejectBlock)reject)
@end
