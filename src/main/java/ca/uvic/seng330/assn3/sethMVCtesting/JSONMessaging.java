package ca.uvic.seng330.assn3.sethMVCtesting;

import org.json.JSONObject;
import ca.uvic.seng330.assn3.sethMVCtestingDevices.DeviceModel;
import java.util.Date;
import java.util.UUID;

public class JSONMessaging {

  private DeviceModel device;
  private String message;
  private Date date;

  public JSONMessaging(DeviceModel device, String message) {
    this.device = device;
    this.message = message;
    this.date = new Date();
  }

  // Creates and returns a new JSONObject
  public JSONObject invoke() {
    JSONObject json = new JSONObject();

    json.put("msg_id", UUID.randomUUID());
    json.put("device_id", device.getIdentifier());
    json.put("status", device.getStatus());
    json.put("payload", message);
    json.put("created_at", date.toString());

    return json;
  }
}