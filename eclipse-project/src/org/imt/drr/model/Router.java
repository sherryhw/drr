/**
 * 
 */
package org.imt.drr.model;


import java.util.Comparator;
import java.util.TreeSet;
import java.util.Vector;

import event.Event;
import event.EventComparator;

/**
 * @author Andrea Vandin, Dmytro Karamshuk, Iffat Ahmed 
 *         PhD students at IMTLucca http://imtlucca.it
 *
 */
public abstract class Router implements Node {

  private Vector<Packet> incomingPackets, outgoingPackets;
  private TreeSet<Event> eventList;
  
  /*
   * Initialize the rouer.
   */
  public void initialize(){
    eventList = new TreeSet<Event>(new EventComparator());
    incomingPackets=new Vector<Packet>();
    outgoingPackets=new Vector<Packet>();
  }
  
  /**
   * 
   * @return next packet 
   */
  public Packet getNextPacket(){
    
    return null;
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
