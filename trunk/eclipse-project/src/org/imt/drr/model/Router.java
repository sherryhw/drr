/**
 * 
 */
package org.imt.drr.model;


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
  
  private boolean serving=false;
  
  protected String name;
  
  protected static final int MAXQUEUESIZE = Constants.MAXQUEUESIZE;
  
  public static final int DEFAULT_BANDWIDTH = Constants.DEFAULT_BANDWIDTH;
  
  private Vector<Packet> outgoingPackets;
  private TreeSet<Event> eventList;
  private Vector<Node> sources;
  
  private Vector<Integer> sourceTimeCounters;

  private final int bandwidth; 
  private int simulationTime;
  private boolean cleanOutgoing;
  
  /**
   * @return the cleanOutgoing
   */
  public boolean isCleanOutgoing() {
    return cleanOutgoing;
  }

  /** Object for gathering statistics. */
  protected Statistics stats=null;
  
  /**
   * Logger
   */
  static Logger logger = Logger.getLogger(Router.class);

  public Router(Vector<Node> sources, int bandwidth){
    this(sources, bandwidth, null, "Router", false);
  }
  
  public Router(Vector<Node> sources, int bandwidth, Statistics stats, String name, boolean cleanOutgoing){
    this.stats = stats;
    this.cleanOutgoing = cleanOutgoing;
    logger.info("Router constructor");
    this.bandwidth = bandwidth;
    //initialize();
    this.sources=sources;
    this.name = name;
  }

  
  /*
   * Initialize the router.
   */
  @Override
  public void initialize(){
    serving=false;
    simulationTime = 0;
    eventList = new TreeSet<Event>(new EventComparator());
    sourceTimeCounters = new Vector<Integer>();
    outgoingPackets = new Vector<Packet>();
    for (int i = 0; i < sources.size(); i++) {
      Event nopeEvent = new Event(null, simulationTime, EventType.NOPE, i);
      eventList.add(nopeEvent);
      sourceTimeCounters.add(new Integer(Integer.MIN_VALUE));
    }
    String log = "\n";
    //Some logging here 
    for (Event event : eventList) {
      log += "event#" + event.getId() + " = " + event + "\n";
      
    }
    logger.info("\n" + name + "\n" + log);
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
    //if (!(isCleanOutgoing() && outgoingPackets.size() > 10)) { 
      outgoingPackets.add(p);
    //}
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
    for (int i = 0; i < sources.size(); i++) {
      if (sourceTimeCounters.get(i) <= simulationTime) {
        askNewPackets(i);
      }
    }
  }

  /**
   * Ask to the sources to send a packet
   */
  protected void askNewPackets(int sourceId){
    Node node = sources.get(sourceId);
    Packet p = node.getNextPacket();
    logger.debug("+++++node " + node + " returned packet " + p);
    if (p != null){
      createArrivalEvent(p);
      sourceTimeCounters.set(sourceId, simulationTime + p.getInterarrivalTime());
    } else { 
      createNopeEvent(sourceId);
    }
  }

  /**
   * Create the arrivalEvent associated to the packet in the argument
   */
  private void createArrivalEvent(Packet p){
    /** BEGIN EVALUATION ARRIVAL TIME **/
    int arrivalTime;
    if(p.getDepartureTime()==Integer.MIN_VALUE){
      //the packet came out from 
      arrivalTime = simulationTime + p.getInterarrivalTime();
    } else {
      arrivalTime = p.getDepartureTime();
    }
    /** END EVALUATIO ARRIVAL TIME **/
    
    p.setArrivalTimeInRouter(arrivalTime);
    Event evt = new Event(p, arrivalTime , EventType.ARRIVAL, Integer.MIN_VALUE);
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
   * Create the nope event associated to the source
   */
  protected void createNopeEvent(int sourceNumber){
    Event nopeEvent = new Event(null, simulationTime, EventType.NOPE, sourceNumber);
    eventList.add(nopeEvent);
  }

  /**
   * Create the departureEvent associated to the packet in the argument
   */
  protected void createDepartureEvent(Packet p){
    int departureTime = simulationTime+evaluateTransimissionTime(p);
    p.setDepartureTime(departureTime);
    int delayInQueue = simulationTime - p.getArrivalTimeInRouter();
    p.setDelayInQueue(delayInQueue);
    Event departureEvent=new Event(p, departureTime, EventType.DEPARTURE, Integer.MIN_VALUE);
    eventList.add(departureEvent);
  }
  
  /**
   * Create the departureEvent associated to the packet in the argument, but evaluate its departure time starting from the time given as argument
   * Return the departure time 
   */
  protected int createDepartureEvent(Packet p, int time){
    int departureTime = time + evaluateTransimissionTime(p);
    p.setDepartureTime(departureTime);
    int delayInQueue = time - p.getArrivalTimeInRouter();
    p.setDelayInQueue(delayInQueue);
    Event departureEvent=new Event(p, departureTime, EventType.DEPARTURE, Integer.MIN_VALUE);
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
    //askNewPackets();
    logger.debug("after ask new packets eventList.size = " + eventList.size());
    //then I really proceed the next event
    Event evt = getNextEventUpdateSimulationTime();
    logger.debug("after getNextEventUpdateSimulationTime, eventList.size="+eventList.size()+", simulationTime: " + simulationTime);
    if(evt != null){
      switch(evt.getType()){
      case ARRIVAL:
        askNewPackets();
        arrivalEventHandler(evt);
        break;
      case DEPARTURE:
        //Add some statistic gathering here
        if (stats != null) {
          stats.countPacket(evt.getPacket());
          stats.setTime(getCurrentSimulationTime());
        }
        departureEventHandler(evt);
        break;
      case NOPE:
        nopeEventHandler(evt);
        break;
      }
    }
    //Some logging here 
    String log = "";
    for (int i = 0; i < outgoingPackets.size(); i++) {
      log += "pack#" + i + " = " + outgoingPackets.get(i) + "\n";
    }
    log += "\n";
    //Some logging here 
    for (Event event : eventList) {
      log += "event#" + event.getId() + " = " + event + "\n";
      
    }
    logger.info("\n__________" + name + "__________\n\n" + log);
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
  
  /**
   * The event handler of the departure event
   * @param evt
   */
  protected void nopeEventHandler(Event evt){
    askNewPackets(evt.getSourceId());
  }
 

}
