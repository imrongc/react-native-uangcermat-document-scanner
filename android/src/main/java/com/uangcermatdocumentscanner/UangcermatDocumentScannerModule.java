package com.uangcermatdocumentscanner;

import com.uangcermatdocumentscanner.views.MainView;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.NativeViewHierarchyManager;
import com.facebook.react.uimanager.UIBlock;
import com.facebook.react.uimanager.UIManagerModule;

@ReactModule(name = UangcermatDocumentScannerModule.NAME)
public class UangcermatDocumentScannerModule extends ReactContextBaseJavaModule {
  public static final String NAME = "UangcermatDocumentScanner";

  public UangcermatDocumentScannerModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  @NonNull
  public String getName() {
    return NAME;
  }


  @ReactMethod
  public void capture(final int viewTag) {
    final ReactApplicationContext context = getReactApplicationContext();
    UIManagerModule uiManager = context.getNativeModule(UIManagerModule.class);
    uiManager.addUIBlock(new UIBlock() {
      @Override
      public void execute(NativeViewHierarchyManager nativeViewHierarchyManager) {
        try {
          MainView view = (MainView) nativeViewHierarchyManager.resolveView(viewTag);
          view.capture();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }
}
