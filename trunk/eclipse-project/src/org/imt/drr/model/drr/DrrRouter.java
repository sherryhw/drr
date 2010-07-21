/**
 * 
 */
package org.imt.drr.model.drr;

import java.nio.BufferOverflowException;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.imt.drr.model.Node;
import org.imt.drr.model.Packet;
import org.imt.drr.model.Router;
import org.imt.drr.model.statistics.Statistics;

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
  private final int DEFAULTQUANTUMOFSERVICE = 500;

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

  @Override
  protected void arrivalEventHandler(Event evt) {
    logger.info("!!!!!!!!!!!!!!!DRRRouter BEGIN handling arrival event. "+evt);

    int idFlow = evt.getPacket().getIdFlow();
    Vector<Packet> flowQueue = incomingFlows.elementAt(idFlow);


    if(isServing()){
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

      /* if no free buffers left FreeBuffer... TODOTODOTODOTODOTODOTODOTODO... */
      // I enqueue the packet
      if (flowQueue.size() == MAXQUEUESIZE) {
        throw new BufferOverflowException();
      } else {
        flowQueue.add(evt.getPacket());
      }
    } else {
      //The router is idle, so I can directly transmit this packet
      createDepartureEvent(evt.getPacket());
      setServing(true);
    }

    logger.info("!!!!!!!!!!!!!!!DRRRouter END handling arrival event. "+evt);
  }

  
  @Override
  protected void departureEventHandler(Event evt) {
    logger.info("!!!!!!!!!!!!!!!DRRRouter BEGIN handling departure event. "+evt);
    
    // First put the sent packet in the list of the outgoing packets
    Packet sentPacket = evt.getPacket();
    addOutgoingPacket(sentPacket);
    
    if(existsIncomingPackets()){
      //Then create the departure events relative to a round
      scheduleOneRound();
    }
    else{
      //otherwise make the router idles
      setServing(false);
    }

    logger.info("!!!!!!!!!!!!!!!DRRRouter END handling departure event. "+evt);
  }

  
  
  /**
   * This method simulate the implement the scheduling of the packets of a round.
   * It works on all the flows in activeList
   */
  private void scheduleOneRound() {
    if (!activeList.isEmpty()) {
      int initialActiveListSize = activeList.size();
      int lastScheduledDepartureTime = this.getCurrentSimulationTime();
      
      // During a round are scheduled packets from all the flows in activeList
      for (int i = 0; i < initialActiveListSize; i++) {
        ActiveListElement ale = activeList.remove(0);
        int flowId = ale.getFlowId();
        Vector<Packet> flowQueue = ale.getFlowQueue();
        deficitCounters[flowId] += quantumOfServices[flowId];
        while ((deficitCounters[flowId] > 0) && (!flowQueue.isEmpty())) {
          int packetSize = flowQueue.elementAt(0).getSize();
          if (packetSize <= deficitCounters[flowId]) {
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

}
