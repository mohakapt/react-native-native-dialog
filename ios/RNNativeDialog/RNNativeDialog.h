#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>

static NSString *const POSITIVE_BUTTON = @"native_dialog__positive_button";
static NSString *const NEGATIVE_BUTTON = @"native_dialog__negative_button";
static NSString *const NEUTRAL_BUTTON = @"native_dialog__neutral_button";
static NSString *const DISMISS_DIALOG = @"native_dialog__dismiss_dialog";

@interface RNNativeDialog: RCTEventEmitter <RCTBridgeModule>

@end
