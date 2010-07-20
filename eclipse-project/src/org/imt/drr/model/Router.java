/**
 * 
 */
package org.imt.drr.model;


import java.util.Collection;
import java.util.TreeSet;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.imt.drr.model.statistics.Statistics;

import event.Event;
import event.EventComparator;
import event.EventType;

/**
 * @author Andrea Vandin, Dmytro Karamshuk, Iffat Ahmed 
 *         PhD students at IMTLucca http://imtlucca.it
 *
 */
public abstract class Router implements ActiveNode {
  
  protected String name;
  
  protected final int MAXQUEUESIZE=500;
  
  private Vector<Packet> outgoingPackets;
  private TreeSet<Event> eventList;
  private Collection<Node> sources;

  private final int bandwidth; 
  private int simulationTime;
  
  /** Object for gathering statistics. */
  protected Statistics stats;
  
  /**
   * Logger
   */
  static Logger logger = Logger.getLogger(Router.class);

  public Router(Collection<Node> sources, int bandwidth){
    this(sources, bandwidth, null, "Router");
  }
  
  public Router(Collection<Node> sources, int bandwidth, Statistics stats, String name){
    this.stats = stats;
    logger.info("Router constructor");
    this.bandwidth=bandwidth;
    //initialize();
    this.sources=sources;
    this.name = name;
  }

  
  /*
   * Initialize the router.
   */
  @Override
  public void initialize(){
    simulationTime=0;
    eventList = new TreeSet<Event>(new EventComparator());
    outgoingPackets = new Vector<Packet>();
  }
  
  public int getCurrentSimulationTime(){
    return simulationTime;
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
    logger.debug("ASK NEW PACKETS (size of sources = " + sources.size() + ")");
    for (Node node : sources) {
      Packet p = node.getNextPacket();
      logger.debug("+++++node " + node + " returned packet " + p);
      if(p != null){
        createArrivalEvent(p);
      }
    }
  }
  
  /**
   * Create the arrivalEvent associated to the packet in the argument
   */
  private void createArrivalEvent(Packet p){
    int arrivalTime = simulationTime + p.getInterarrivalTime();
    p.setArrivalTimeInRouter(arrivalTime);
    Event evt = new Event(p, arrivalTime , EventType.ARRIVAL);
    eventList.add(evt);
  }
  
  /**
   * Evaluate the transmission time of the packet, basing on its size and on the bandwidth
   */
  private int evaluateTransimissionTime(Packet p){
    if(bandwidth == Integer.MAX_VALUE){
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
   * Create the departureEvent associated to the packet in the argument, but evaluate its departure time starting from the time given as argument
   * Return the departure time 
   */
  protected int createDepartureEvent(Packet p, int time){
    int departureTime=time+evaluateTransimissionTime(p);
    Event departureEvent=new Event(p, departureTime, EventType.DEPARTURE);
    eventList.add(departureEvent);
    return departureTime;
  }
  
  /**
   * The main process of router. 
   */
  @Override
  public void proceedNextEvent() {
    logger.debug("router.ProceedNextEvent");
    //As first step I have to ask a new packet to every source node
    askNewPackets();
    logger.debug("after ask new packets eventList.size = " + eventList.size());
    //then I really proceed the next event
    Event evt = getNextEventUpdateSimulationTime();
    logger.debug("after getNextEventUpdateSimulationTime, eventList.size="+eventList.size()+", simulationTime: " + simulationTime);
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
    //Some logging here 
    String log = "";
    for (int i = 0; i < outgoingPackets.size(); i++) {
      log += "pack#" + i + " = " + outgoingPackets.get(i) + "\n";
    }
    if (log.length() > 0) {
      logger.info(name + " " + log);
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
