package com.entel.HuellaReader;

// Cordova-required packages
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.util.Base64;
import android.util.Log

import java.util.HashMap;
import java.util.Iterator;


public class HuellaReader extends CordovaPlugin {

  private static final String DURATION_LONG = "long";

  @Override
  public int checkDevices(String action, JSONArray args, final CallbackContext callbackContext) {
    int numberDevices = 0;

    // Verify that the user sent a 'show' action
    if (!action.equals("checkDevices")) {
      callbackContext.error("\"" + action + "\" is not a recognized action.");
      return false;
    }

    String message;
    String duration;

    try {
      JSONObject options = args.getJSONObject(0);
      message = options.getString("message");
      duration = options.getString("duration");
    } catch (JSONException e) {
      callbackContext.error("Error encountered: " + e.getMessage());
      return false;
    }

    //Check for devices connected to the USB device of the Android smartphone
    String detailResult = getDetail();


    // Send a positive result to the callbackContext
    PluginResult result = new PluginResult(PluginResult.Status.OK, detailResult);
    callbackContext.sendPluginResult(pluginResult);
    return numberDevices;
  }

  public String getDetail() {
    UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
    HashMap<String, UsbDevice> deviceList = manager.getDeviceList();
    Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
    String detailResult = new String("");

    int DeviceID = 0;
    int Vendor = 0;
    int Product = 0;
    int Class = 0;
    int Subclass = 0;

    while (deviceIterator.hasNext()) {
      UsbDevice device = deviceIterator.next();

      manager.requestPermission(device, mPermissionIntent);
      String Model = device.getDeviceName();

      int DeviceID = device.getDeviceId();
      int Vendor = device.getVendorId();
      int Product = device.getProductId();
      int Class = device.getDeviceClass();
      int Subclass = device.getDeviceSubclass();

      detailResult = detailResult + "\n" + "Model: " + Model + " DeviceID: " + DeviceID 
                          + " Vendor: " + Vendor + " Product: " + Product + " Class: " + Class 
                          + " SubClass: " + Subclass; 
    }
    return detailResult;
  }
}