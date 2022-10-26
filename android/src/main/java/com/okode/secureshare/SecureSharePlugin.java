package com.okode.secureshare;

import com.okode.secureshare.SecureShareHelper;

import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.JSObject;
import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;

@CapacitorPlugin(name = "SecureShare")
public class SecureSharePlugin extends Plugin {

    private final static String TAG = "SECSHARE";
    private SecureShareHelper ssHelper;

    @PluginMethod
    public void save(PluginCall call) {
      try {
        getSSHelper().save(mapFromJSONObject(call.getData()));
      } catch (SecureShareHelper.SecureShareException e) {
        call.reject(e.getCause().getMessage());
      }
      call.resolve();
    }

    @PluginMethod
    public void retrieve(PluginCall call) {
      try {
        Map<String, String> result = getSSHelper().retrieve();
        call.resolve(fromJSONObject(jsonObjectFromMap(result)));
      } catch (SecureShareHelper.SecureShareException e) {
        call.reject(e.getCause().getMessage());
      }
    }

    @PluginMethod
    public void retrieveFrom(PluginCall call) {
      String packageName = call.getString("packageName");
      Map<String, String> result = getSSHelper().retrieveFrom(packageName);
      call.resolve(fromJSONObject(jsonObjectFromMap(result)));
    }

    private Map<String, String> mapFromJSONObject(JSONObject jsonObject) {
      Map<String, String> map = new HashMap<>();
      Iterator<String> keysIterator = jsonObject.keys();
      while (keysIterator.hasNext()) {
        String key = keysIterator.next();
        String value = null;
        try {
          value = jsonObject.getString(key);
          map.put(key, value);
        } catch (JSONException e) {
          Log.e(TAG, "Ignoring. Cannot get string value for key: " + key);
        }
      }
      return map;
    }

    private JSONObject jsonObjectFromMap(Map<String, String> map) {
      JSONObject jsonObject = new JSONObject();
      for (Map.Entry<String, String> entry : map.entrySet()) {
        try {
          jsonObject.put(entry.getKey(), entry.getValue());
        } catch (JSONException e) {
          Log.e(TAG, "Cannot convert to JSONObject key " + entry.getKey() + " with value " + entry.getValue());
        }
      }
      return jsonObject;
    }

    private SecureShareHelper getSSHelper() {
      if (ssHelper == null) {
        ssHelper = new SecureShareHelper(this.getActivity().getApplicationContext());
      }
      return ssHelper;
    }

    private JSObject fromJSONObject(JSONObject json) {
      try {
        return JSObject.fromJSONObject(json);
      } catch (JSONException e) {
        return null;
      }
    }
}
