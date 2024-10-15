import React, { useEffect, useRef } from 'react';
import {
  DeviceEventEmitter,
  findNodeHandle,
  Platform,
  requireNativeComponent,
  type RegisteredStyle,
  type ViewStyle,
} from 'react-native';

const RNPdfScanner = requireNativeComponent('UangcermatDocumentScanner');

export interface PictureTaken {
  rectangleCoordinates?: object;
  croppedImage: string;
  initialImage: string;
  width: number;
  height: number;
}

interface PdfScannerProps {
  onPictureTaken?: (event: any) => void;
  onRectangleDetect?: (event: any) => void;
  onDeviceSetup?: (event: any) => void;
  onProcessing?: () => void;
  quality?: number;
  overlayColor?: number | string;
  enableTorch?: boolean;
  useFrontCam?: boolean;
  saturation?: number;
  brightness?: number;
  contrast?: number;
  detectionCountBeforeCapture?: number;
  durationBetweenCaptures?: number;
  detectionRefreshRateInMS?: number;
  documentAnimation?: boolean;
  noGrayScale?: boolean;
  manualOnly?: boolean;
  style?: ViewStyle | RegisteredStyle<ViewStyle>;
  useBase64?: boolean;
  saveInAppDocument?: boolean;
  captureMultiple?: boolean;
}

const PdfScanner: React.FC<PdfScannerProps> = (props) => {
  const scannerRef = useRef<any>(null);
  const scannerHandle = useRef<number | null>(null);

  const sendOnPictureTakenEvent = (event: any) => {
    if (!props.onPictureTaken) return null;
    return props.onPictureTaken(event.nativeEvent);
  };

  const sendOnRectangleDetectEvent = (event: any) => {
    if (!props.onRectangleDetect) return null;
    return props.onRectangleDetect(event.nativeEvent);
  };

  const sendOnDeviceSetupEvent = (event: any) => {
    if (!props.onDeviceSetup) return null;
    return props.onDeviceSetup(event.nativeEvent);
  };

  const getImageQuality = () => {
    if (!props.quality) return 0.8;
    if (props.quality > 1) return 1;
    if (props.quality < 0.1) return 0.1;
    return props.quality;
  };

  const _setReference = (ref: any) => {
    if (ref) {
      scannerRef.current = ref;
      scannerHandle.current = findNodeHandle(ref);
    } else {
      scannerRef.current = null;
      scannerHandle.current = null;
    }
  };

  // const capture = () => {
  //   if (scannerHandle.current) {
  //     ScannerManager.capture(scannerHandle.current);
  //   }
  // };

  useEffect(() => {
    let onPictureTakenListener: any;
    let onProcessingChangeListener: any;
    let onDeviceSetupListener: any;

    if (Platform.OS === 'android') {
      if (props.onPictureTaken) {
        onPictureTakenListener = DeviceEventEmitter.addListener(
          'onPictureTaken',
          props.onPictureTaken
        );
      }
      if (props.onProcessing) {
        onProcessingChangeListener = DeviceEventEmitter.addListener(
          'onProcessingChange',
          props.onProcessing
        );
      }
      if (props.onDeviceSetup) {
        onDeviceSetupListener = DeviceEventEmitter.addListener(
          'onDeviceSetup',
          props.onDeviceSetup
        );
      }
    }

    return () => {
      // Cleanup listeners on unmount
      if (onPictureTakenListener) onPictureTakenListener.remove();
      if (onProcessingChangeListener) onProcessingChangeListener.remove();
      if (onDeviceSetupListener) onDeviceSetupListener.remove();
    };
  }, [props.onPictureTaken, props.onProcessing, props.onDeviceSetup]);

  return (
    <RNPdfScanner
      ref={_setReference}
      onPictureTaken={sendOnPictureTakenEvent}
      onRectangleDetect={sendOnRectangleDetectEvent}
      onDeviceSetup={sendOnDeviceSetupEvent}
      useFrontCam={props.useFrontCam || false}
      brightness={props.brightness || 0}
      saturation={props.saturation || 1}
      contrast={props.contrast || 1}
      quality={getImageQuality()}
      detectionCountBeforeCapture={props.detectionCountBeforeCapture || 5}
      durationBetweenCaptures={props.durationBetweenCaptures || 0}
      detectionRefreshRateInMS={props.detectionRefreshRateInMS || 50}
      {...props}
    />
  );
};

export default PdfScanner;
