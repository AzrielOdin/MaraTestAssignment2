package Shared.transferobjects;

import java.io.Serializable;

public class Response implements Serializable {
  private final String type;
  private final Object payload;

  public Response(String type, Object payload) {
    this.type = type;
    this.payload = payload;
  }

  public String getType() {
    return type;
  }

  public Object getPayload() {
    return payload;
  }
}
