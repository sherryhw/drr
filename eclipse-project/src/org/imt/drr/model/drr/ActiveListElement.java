package org.imt.drr.model.drr;

import java.util.Vector;

import org.imt.drr.model.Packet;

public class ActiveListElement {
  private Vector<Packet> flowQueue;
  private int flowId;
  
  public ActiveListElement(Vector<Packet> flowQueue, int flowId) {
    super();
    this.flowQueue = flowQueue;
    this.flowId = flowId;
  }

  public Vector<Packet> getFlowQueue() {
    return flowQueue;
  }

  public int getFlowId() {
    return flowId;
  }

  
}
