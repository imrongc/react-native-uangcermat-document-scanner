package com.uangcermatdocumentscanner;

import android.app.Activity;
import android.util.Log;

import com.uangcermatdocumentscanner.views.MainView;
import com.uangcermatdocumentscanner.views.OpenNoteCameraView;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.common.MapBuilder;

import java.util.Map;
import javax.annotation.Nullable;

public class UangcermatDocumentScannerViewManager extends ViewGroupManager<MainView> {

    private static final String TAG = "UangcermatDocumentScannerViewManager";
    private static final String REACT_CLASS = "UangcermatDocumentScanner";
    private MainView view = null;

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected MainView createViewInstance(final ThemedReactContext reactContext) {
        view = new MainView(reactContext, (Activity) reactContext.getBaseContext());
        view.setOnProcessingListener(new OpenNoteCameraView.OnProcessingListener() {
            @Override
            public void onProcessingChange(WritableMap data) {
                dispatchEvent(reactContext, "onProcessingChange", data);
            }
        });

        view.setOnScannerListener(new OpenNoteCameraView.OnScannerListener() {
            @Override
            public void onPictureTaken(WritableMap data) {
                dispatchEvent(reactContext, "onPictureTaken", data);
            }
        });

        view.setOnCameraReadyListener(new OpenNoteCameraView.OnCameraReadyListener() {
            @Override
            public void onCameraReady(WritableMap data) {
                dispatchEvent(reactContext, "onDeviceSetup", data);
            }
        });

        return view;
    }

    private void dispatchEvent(ReactContext reactContext, String eventName, @Nullable WritableMap params) {
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params.copy());
        Log.d(TAG, "dispatchEvent: " + eventName + " " + params);
    }

    @Override
    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
        return MapBuilder.<String, Object>builder()
            .put("onPictureTaken", MapBuilder.of("registrationName", "onPictureTaken"))
            .put("onProcessingChange", MapBuilder.of("registrationName", "onProcessingChange"))
            .put("onDeviceSetup", MapBuilder.of("registrationName", "onDeviceSetup"))
            .build();
    }

    @ReactProp(name = "documentAnimation", defaultBoolean = false)
    public void setDocumentAnimation(MainView view, boolean animate) {
        // TODO: What is documentAnimation?
    }

    @ReactProp(name = "overlayColor")
    public void setOverlayColor(MainView view, String rgbaColor) {
        view.setOverlayColor(rgbaColor);
    }

    @ReactProp(name = "saveOnDevice", defaultBoolean = false)
    public void setSaveOnDevice(MainView view, Boolean saveOnDevice) {
        view.setSaveOnDevice(saveOnDevice);
    }

    @ReactProp(name = "detectionCountBeforeCapture", defaultInt = 15)
    public void setDetectionCountBeforeCapture(MainView view, int numberOfRectangles) {
        view.setDetectionCountBeforeCapture(numberOfRectangles);
    }

    @ReactProp(name = "durationBetweenCaptures", defaultDouble = 0)
    public void setDurationBetweenCaptures(MainView view, double durationBetweenCaptures) {
        view.setDurationBetweenCaptures(durationBetweenCaptures);
    }

    @ReactProp(name = "enableTorch", defaultBoolean = false)
    public void setEnableTorch(MainView view, Boolean enable) {
        view.setEnableTorch(enable);
    }

    @ReactProp(name = "manualOnly", defaultBoolean = false)
    public void setManualOnly(MainView view, Boolean manualOnly) {
        view.setManualOnly(manualOnly);
    }

    @ReactProp(name = "brightness", defaultDouble = 10)
    public void setBrightness(MainView view, double brightness) {
        view.setBrightness(brightness);
    }

    @ReactProp(name = "contrast", defaultDouble = 1)
    public void setContrast(MainView view, double contrast) {
        view.setContrast(contrast);
    }
}