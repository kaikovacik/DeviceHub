package ca.uvic.seng330.assn3;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.json.JSONObject;

public class DataPersister {

  private FileWriter file;
  private Date date;
  
  public DataPersister() {
    this.date = new Date();
    try {
      this.file = new FileWriter("src/main/PersistedData/dataLog.txt", true);
      //file.write("Client booted on: " + date.toString() + "\r\n");
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  public void writeThis(JSONObject json) {
    try {
      file.write(json.toString() + "\r\n");
      file.flush();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  public void writeThis(String str) {
    try {
      file.write(str + "\r\n");
      file.flush();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
