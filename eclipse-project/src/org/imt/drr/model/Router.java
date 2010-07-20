/**
 * 
 */
package org.imt.drr.model;


import java.util.Collection;
import java.util.TreeSet;
import java.util.Vector;

import org.apache.log4j.Logger;

import event.Event;
import event.EventComparator;
import event.EventType;

/**
 * @author Andrea Vandin, Dmytro Karamshuk, Iffat Ahmed 
 *         PhD students at IMTLucca http://imtlucca.it
 *
 */
public abstract class Router implements Node {
  protected final int MAXQUEUESIZE=500;

  private boolean serving=false;
  private final int bandwidth; 
  private final boolean zeroTransmissionTime;
  private int simulationTime;
  private Vector<Packet> outgoingPackets;
  private TreeSet<Event> eventList;
  private Collection<Node> sources;
  
  /**
   * Logger
   */
  static Logger logger = Logger.getLogger(Router.class);

  public Router(Collection<Node> sources, int bandwidth){
    this(sources,bandwidth,false);
  }
  
  public Router(Collection<Node> sources, int bandwidth, boolean zeroTransimissionTime){
    this.zeroTransmissionTime=false;
    logger.info("Router constructor");
    this.bandwidth=bandwidth;
    //initialize();
    this.sources=sources;
  }

  
  /*
   * Initialize the router.
   */
  @Override
  public void initialize(){
    serving=false;
    simulationTime=0;
    eventList = new TreeSet<Event>(new EventComparator());
    outgoingPackets = new Vector<Packet>();
  }
  
  protected int getCurrentSimulationTime(){
    return simulationTime;
  }
  
  /**
   * return true if the router is currently serving (transmitting) a packet
   */
  protected boolean isServing() {
    return serving;
  }

  protected void setServing(boolean serving) {
    this.serving = serving;
  }
  
  /**
   * Return the next event from eventList, and update accordingly  the simulation time 
   * @return
   */
  protected Event getNextEventUpdateSimulationTime(){
    Event evt = null;
    if(eventList.size()>0){
      evt=eventList.first();
      eventList.remove(evt);
      simulationTime=evt.getTime();
    }
    return evt;
  }
  
  /**
   * Add a sent packet to the list of the outgoing packets
   */
  protected void addOutgoingPacket(Packet p){
    outgoingPackets.add(p);
  }
  
  /**
   * This method is called by the next router to ask an outgoing packet
   */
  public Packet getNextPacket(){
    Packet nextPacket=null;
    if(!outgoingPackets.isEmpty()){
      nextPacket=outgoingPackets.remove(0);
    }
    return nextPacket;
  }
  
  /**
   * Ask to the sources to send a packet
   */
  protected void askNewPackets(){
    for (Node node : sources) {
      Packet p = node.getNextPacket();
      if(p!=null){
        createArrivalEvent(p);
      }
    }
  }
  
  /**
   * Create the arrivalEvent associated to the packet in the argument
   */
  private void createArrivalEvent(Packet p){
    int arrivalTime=simulationTime+p.getInterarrivalTime();
    p.setArrivalTimeInRouter(arrivalTime);
    Event evt = new Event(p, arrivalTime , EventType.ARRIVAL);
    eventList.add(evt);
  }
  
  /**
   * Evaluate the transmission time of the packet, basing on its size and on the bandwidth
   */
  private int evaluateTransimissionTime(Packet p){
    if(this.zeroTransmissionTime){
      return 0;
    }
    else{
      return p.getSize()/bandwidth; 
    }
  }
  
  /**
   * Create the departureEvent associated to the packet in the argument
   */
  protected void createDepartureEvent(Packet p){
    Event departureEvent=new Event(p, simulationTime+evaluateTransimissionTime(p), EventType.DEPARTURE);
    eventList.add(departureEvent);
  }
  
  /**
   * The main process of router. 
   */
  public void proceedNextEvent() {
    logger.info("router.ProceedNextEvent");
    //As first step I have to ask a new packet to every source node
    askNewPackets();
    logger.info("after ask new packets eventList.size = " + eventList.size());
    //then I really proceed the next event
    Event evt = getNextEventUpdateSimulationTime();
    logger.info("after getNextEventUpdateSimulationTime, eventList.size="+eventList.size()+", simulationTime: " + simulationTime);
    if(evt != null){
      switch(evt.getType()){
      case ARRIVAL:
        arrivalEventHandler(evt);
        break;
      case DEPARTURE:
        departureEventHandler(evt);
        break;
      }
    }
  }
  
  /**
   * The event handler of the arrival event
   * @param evt
   */
  abstract protected void arrivalEventHandler(Event evt);
  
  /**
   * The event handler of the departure event
   * @param evt
   */
  abstract protected void departureEventHandler(Event evt);
  
 

}
