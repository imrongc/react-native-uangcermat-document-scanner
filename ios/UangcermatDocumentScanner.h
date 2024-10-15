
#ifdef RCT_NEW_ARCH_ENABLED
#import "RNUangcermatDocumentScannerSpec.h"

@interface UangcermatDocumentScanner : NSObject <NativeUangcermatDocumentScannerSpec>
#else
#import <React/RCTBridgeModule.h>

@interface UangcermatDocumentScanner : NSObject <RCTBridgeModule>
#endif

@end
