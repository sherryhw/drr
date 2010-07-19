/**
 * 
 */
package org.imt.drr.model;


import java.util.Collection;
import java.util.TreeSet;
import java.util.Vector;

import event.Event;
import event.EventComparator;
import event.EventType;

/**
 * @author Andrea Vandin, Dmytro Karamshuk, Iffat Ahmed 
 *         PhD students at IMTLucca http://imtlucca.it
 *
 */
public abstract class Router implements Node {

  private int clockTime;
  
  private Vector<Packet> incomingPackets, outgoingPackets;
  private TreeSet<Event> eventList;
  
  private Collection<Node> sources;
  
  public Router(Collection<Node> sources){
    initialize();
    this.sources=sources;
  }
  
  /*
   * Initialize the rouer.
   */
  public void initialize(){
    clockTime=0;
    eventList = new TreeSet<Event>(new EventComparator());
    incomingPackets = new Vector<Packet>();
    outgoingPackets = new Vector<Packet>();
  }
  
  /**
   * Return the next event from eventList 
   * @return
   */
  protected Event getNextEvent(){
    Event evt = null;
    if(eventList.size()>0){
      evt=eventList.first();
      eventList.remove(evt);
    }
    return evt;
  }
  
  /**
   * 
   * @return next packet 
   */
  public Packet getNextPacket(){
    
    return null;
  }
  
  protected void askNewPackets(){
    for (Node node : sources) {
      Packet p = node.getNextPacket();
      if(p!=null){
        Event evt=new Event(p, clockTime, EventType.ARRIVAL);
        eventList.add(evt);
      }
    }
  }
  
  /**
   * The main process of router. 
   */
  abstract public void proceedNextEvent();
  
  /**
   * Choose the next packet to be outputted
   */
  public abstract void scheduleNextPacket();

}
