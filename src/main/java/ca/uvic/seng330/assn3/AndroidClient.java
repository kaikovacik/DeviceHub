package ca.uvic.seng330.assn3;

import org.json.JSONObject;

public class AndroidClient implements Client {

  private Mediator network;

  public AndroidClient(Mediator network) {
    this.network = network;
    this.network.addClient(this);
  }

  public void notify(JSONObject pMsg) {
    System.out.println(pMsg);
  }
}