/**
 * 
 */
package org.imt.drr.model.sfq;

import java.util.Vector;
import org.apache.log4j.Logger;
import org.imt.drr.model.Constants;
import org.imt.drr.model.Node;
import org.imt.drr.model.Packet;
import org.imt.drr.model.Router;
import org.imt.drr.model.drr.ActiveListElement;
import org.imt.drr.model.statistics.Statistics;

import event.Event;

/**
 * @author Andrea Vandin, Dmytro Karamshuk, Iffat Ahmed PhD students at IMTLucca
 *         http://imtlucca.it
 * 
 */
public class SFQRouter extends Router {

  /**
   * Logger
   */
  static Logger logger = Logger.getLogger(Router.class);

  private int numberOfFlows;
  private Vector<Vector<Packet>> incomingFlows;
  private Vector<ActiveListElement> activeList;
  
  private int lastScheduledDepartureTime;

  public SFQRouter(Vector<Node> sources, int bandwidth, int numberOfFlows) {
    super(sources, bandwidth);
    this.numberOfFlows = numberOfFlows;
  }

  public SFQRouter(Vector<Node> sources, int bandwidth, int numberOfFlows, Statistics stats, String name, boolean cleanOutgoing) {
    super(sources, bandwidth, stats, name, cleanOutgoing);
    this.numberOfFlows = numberOfFlows;
  }

  @Override
  public void initialize() {
    super.initialize();
    incomingFlows = new Vector<Vector<Packet>>(numberOfFlows);
    activeList = new Vector<ActiveListElement>();
    lastScheduledDepartureTime=0;

    for (int i = 0; i < numberOfFlows; i++) {
      incomingFlows.add(new Vector<Packet>());
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
    scheduleOneRound();
    super.proceedNextEvent();
  }
  
  @Override
  protected void arrivalEventHandler(Event evt) {
    logger.debug("!!!!!!!!!!!!!!!SFQRouter BEGIN handling arrival event. "+evt);

    int idFlow = evt.getPacket().getIdFlow();
    Vector<Packet> flowQueue = incomingFlows.elementAt(idFlow);

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
//    deficitCounters[idFlow] = 0;
    }
    // *** END check if the flow is in the active list *** //

    if(isIncomingBufferFull()){
      freeBuffer();
    }
    flowQueue.add(evt.getPacket());
    /*if(lastScheduledDepartureTime < getCurrentSimulationTime()){
      scheduleOneRound();
    }*/

    logger.debug("!!!!!!!!!!!!!!!SFQRouter END handling arrival event. "+evt);
  }
  
  
  @Override
  protected void departureEventHandler(Event evt) {
    logger.debug("!!!!!!!!!!!!!!!SFQRouter BEGIN handling departure event. "+evt);
    
    Packet sentPacket = evt.getPacket();
    addOutgoingPacket(sentPacket);
    
    //scheduleOneRound();

    logger.debug("!!!!!!!!!!!!!!!SFQRouter END handling departure event. "+evt);
  }
  
  /**
   * This method implement the scheduling of the packets of a round.
   * It works on all the flows in activeList
   */
  private void scheduleOneRound() {
    if(existsIncomingPackets()){
      int initialActiveListSize = activeList.size();
      // During a round are scheduled packets from all the flows in activeList
      for (int i = 0; i < initialActiveListSize; i++) {
        ActiveListElement ale = activeList.remove(0);
        Vector<Packet> flowQueue = ale.getFlowQueue();
        lastScheduledDepartureTime = Math.max(lastScheduledDepartureTime, getCurrentSimulationTime());
        lastScheduledDepartureTime = createDepartureEvent(flowQueue.remove(0), lastScheduledDepartureTime);
        if (!flowQueue.isEmpty()) {
          activeList.add(ale);
        }
      }
    }
  }
  
}