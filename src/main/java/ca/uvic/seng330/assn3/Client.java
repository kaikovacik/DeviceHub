package ca.uvic.seng330.assn3;

import org.json.JSONObject;

public class Client {

  private Mediator network;

  public Client(Mediator network) {
    this.network = network;
    this.network.addClient(this);
  }

  public void notify(JSONObject pMsg) {
    System.out.println(pMsg);
  }
}