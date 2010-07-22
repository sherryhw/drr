/**
 * 
 */
package org.imt.drr.model.drr;

import java.util.Vector;
import org.apache.log4j.Logger;
import org.imt.drr.model.Constants;
import org.imt.drr.model.Node;
import org.imt.drr.model.Packet;
import org.imt.drr.model.Router;
import org.imt.drr.model.statistics.Statistics;

import com.sun.org.apache.bcel.internal.generic.GETSTATIC;

import event.Event;

/**
 * @author Andrea Vandin, Dmytro Karamshuk, Iffat Ahmed PhD students at IMTLucca
 *         http://imtlucca.it
 * 
 */
public class DrrRouter extends Router {

  /**
   * Logger
   */
  static Logger logger = Logger.getLogger(Router.class);

  private int numberOfFlows;
  private Vector<Vector<Packet>> incomingFlows;
  private Vector<ActiveListElement> activeList;
  private int[] deficitCounters;
  private int[] quantumOfServices;
  private final int DEFAULTQUANTUMOFSERVICE = Constants.DEFAULTQUANTUMOFSERVICE;
  
  private int lastScheduledDepartureTime;

  public DrrRouter(Vector<Node> sources, int bandwidth, int numberOfFlows) {
    super(sources, bandwidth);
    this.numberOfFlows = numberOfFlows;
  }

  public DrrRouter(Vector<Node> sources, int bandwidth, int numberOfFlows, Statistics stats, String name, boolean cleanOutgoing) {
    super(sources, bandwidth, stats, name, cleanOutgoing);
    this.numberOfFlows = numberOfFlows;
  }

  @Override
  public void initialize() {
    super.initialize();
    deficitCounters = new int[numberOfFlows];
    quantumOfServices = new int[numberOfFlows];
    incomingFlows = new Vector<Vector<Packet>>(numberOfFlows);
    activeList = new Vector<ActiveListElement>();
    lastScheduledDepartureTime=0;

    for (int i = 0; i < numberOfFlows; i++) {
      incomingFlows.add(new Vector<Packet>());
      deficitCounters[i] = 0;
      quantumOfServices[i] = DEFAULTQUANTUMOFSERVICE;
    }

  }
  
  private boolean existsIncomingPackets(){
    for (ActiveListElement ale : activeList) {
      if(!ale.getFlowQueue().isEmpty())
        return true;
    }
    return false;
  }
  
  private boolean isIncomingBufferFull(){
    int cumulativeSize=0;
    for (ActiveListElement ale : activeList) {
      cumulativeSize+= ale.getFlowQueue().size();
    }
    return cumulativeSize >= MAXQUEUESIZE;//equal should be enough
  }
  
  private void freeBuffer() {
    ActiveListElement longestQueue = getLongestQueue();
    Vector<Packet> flowQueue = longestQueue.getFlowQueue();
    flowQueue.remove(flowQueue.size()-1);
    
    if (flowQueue.isEmpty()) {
      activeList.remove(longestQueue);
      deficitCounters[longestQueue.getFlowId()] = 0;
    }
  }

  private ActiveListElement getLongestQueue() {
    if(activeList.isEmpty()){
      return null;
    }
    ActiveListElement current = null;
    ActiveListElement longest = activeList.get(0);
    for (int i=1; i < activeList.size();i++) {
      current = activeList.get(i);
      if(current.getFlowQueue().size()>longest.getFlowQueue().size()){
        longest = current;
      }
    }
    return longest;
  }

  @Override
  public void proceedNextEvent() {
    super.proceedNextEvent();
    
    if(!aaa){
    if(needToSchedule()){
      resetSchedulationFlags();
      if (existsIncomingPackets()) {
        // Generate the departure events of this round
        scheduleOneRound();
      }
      /*
       * SERVING else{ //If there are no incoming packets, and I am not
       * actually sending a packet, then the router is idle
       * if(lastScheduledDepartureTime<=getCurrentSimulationTime()){
       * setServing(false); } }
       */
    } else {
      resetSchedulationFlags();
    }
    /*if (nopeEvent) {//this way I don't schedule after a nope event
      nopeEvent = false;
    } else {
      
    }*/
  }
    
    aaa=false;
  }
  
  private boolean aaa=false;
  
  @Override
  protected void arrivalEventHandler(Event evt) {
    logger.debug("!!!!!!!!!!!!!!!DRRRouter BEGIN handling arrival event. "+evt);

    int idFlow = evt.getPacket().getIdFlow();
    Vector<Packet> flowQueue = incomingFlows.elementAt(idFlow);

    //SERVINGif(isServing()){
    // *** BEGIN check if the flow is in the active list *** //
    boolean flowIsInActiveList = false;
    for (ActiveListElement ale : activeList) {
      if (ale.getFlowId() == idFlow) {
        flowIsInActiveList = true;
        break;
      }
    }
    if (!flowIsInActiveList) {    
      //I add the flow to activeList.
      ActiveListElement element = new ActiveListElement(flowQueue, idFlow);
      activeList.add(element);
      deficitCounters[idFlow] = 0;
    }
    // *** END check if the flow is in the active list *** //

    if(isIncomingBufferFull()){
      // if no free buffers left, then FreeBuffer
      freeBuffer();
    }
    // enqueue the packet
    flowQueue.add(evt.getPacket());
    /* SERVING
     * } else {
      //The router is idle, so I can directly transmit this packet
      lastScheduledDepartureTime=createDepartureEvent(evt.getPacket());
      setServing(true);
    }*/


    if(lastScheduledDepartureTime < getCurrentSimulationTime()){
      if(existsIncomingPackets()){
        //Then create the departure events relative to a round
        scheduleOneRound();
        if(needToSchedule())
          aaa=true;
      }
    }

    logger.debug("!!!!!!!!!!!!!!!DRRRouter END handling arrival event. "+evt);
  }
  
  
  @Override
  protected void departureEventHandler(Event evt) {
    logger.debug("!!!!!!!!!!!!!!!DRRRouter BEGIN handling departure event. "+evt);
    
    // First put the sent packet in the list of the outgoing packets
    Packet sentPacket = evt.getPacket();
    addOutgoingPacket(sentPacket);
    
    
    if(existsIncomingPackets()){
      //Then create the departure events relative to a round
      scheduleOneRound();
    }
    /*else{
      //otherwise make the router idle
      setServing(false);
    }*/

    logger.debug("!!!!!!!!!!!!!!!DRRRouter END handling departure event. "+evt);
  }

  
  
  /**
   * This method simulate the implement the scheduling of the packets of a round.
   * It works on all the flows in activeList
   */
  private void scheduleOneRound() {
    if (!activeList.isEmpty()) {
      schedulerCalled = true;
      
      int initialActiveListSize = activeList.size();
      lastScheduledDepartureTime = Math.max(lastScheduledDepartureTime, getCurrentSimulationTime());
      
      // During a round are scheduled packets from all the flows in activeList
      for (int i = 0; i < initialActiveListSize; i++) {
        ActiveListElement ale = activeList.remove(0);
        int flowId = ale.getFlowId();
        Vector<Packet> flowQueue = ale.getFlowQueue();
        deficitCounters[flowId] += quantumOfServices[flowId];
        while ((deficitCounters[flowId] > 0) && (!flowQueue.isEmpty())) {
          int packetSize = flowQueue.elementAt(0).getSize();
          if (packetSize <= deficitCounters[flowId]) {
            scheduledSomething = true;
            lastScheduledDepartureTime = createDepartureEvent(flowQueue.remove(0), lastScheduledDepartureTime);
            deficitCounters[flowId] -= packetSize;
          } else {
            // if the size of the packet is greater then the remained deficit counter, then exit from this while loop.
            break;
          }
        }
        if (flowQueue.isEmpty()) {
          deficitCounters[flowId] = 0;
        } else {
          activeList.add(ale);
        }
      }
    }
  }
  
  
  
  
  private boolean scheduledSomething = false;
  private boolean schedulerCalled = false;
  private boolean needToSchedule(){
    return schedulerCalled && (!scheduledSomething);
  }
  private void resetSchedulationFlags(){
    schedulerCalled = false;
    scheduledSomething = false;
  }
}