/**
 * 
 */
package org.imt.drr.model.fifo;

import java.nio.BufferOverflowException;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.imt.drr.model.Node;
import org.imt.drr.model.Packet;
import org.imt.drr.model.Router;
import org.imt.drr.model.statistics.Statistics;

import event.Event;

/**
 * @author Andrea Vandin, Dmytro Karamshuk, Iffat Ahmed 
 *         PhD students at IMTLucca http://imtlucca.it
 *
 */
public class FifoRouter extends Router {
  private boolean serving=false;
  private Vector<Packet> incomingPackets;

  /**
   * Logger
   */
  static Logger logger = Logger.getLogger(Router.class);

  public FifoRouter(Vector<Node> sources, int bandwidth){
    this(sources, bandwidth, null, "FifoRouter", false);
  }

  public FifoRouter(Vector<Node> sources, int bandwidth, Statistics stats, String name, boolean cleanOutgoing){
    super(sources, bandwidth, stats, name, cleanOutgoing);
  }
  
  @Override
  public void initialize() {
    super.initialize();
    serving=false;
    incomingPackets = new Vector<Packet>(MAXQUEUESIZE);
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
  
  @Override
  protected void arrivalEventHandler(Event evt){
    logger.debug("!!!!!!!!!!!!!!!start handling arrival event. incomingPackets.size="+incomingPackets.size()+ ". isServing= "+isServing()+". " + evt);
    if(isServing()){
      //The router is busy, so I have to enqueue the packet, if there is still space in the queue.
      if(incomingPackets.size() == MAXQUEUESIZE){
        throw new BufferOverflowException();
      }
      else{
        incomingPackets.add(evt.getPacket());
      }
    }
    else
    {
      //the router is currently idle. I can directly transmit the packet.
      createDepartureEvent(evt.getPacket());
      setServing(true);
    }
    logger.debug("END handling arrival event. incomingPackets.size()="+incomingPackets.size() +". isServing= "+isServing());
  }
  
  @Override
  protected void departureEventHandler(Event evt){
    logger.debug("BEGIN handling departure event. incomingPackets.size="+incomingPackets.size()+ ". isServing= "+isServing()+". " + evt);
    //First put the sent packet in the list of the outgoing packets 
    Packet sentPacket = evt.getPacket();
    addOutgoingPacket(sentPacket);  
    //Add some statistic gathering here
    if (stats != null) {
      stats.countPacket(sentPacket);
      stats.setTime(getCurrentSimulationTime());
    }
    //Then create the next departure event if there are any packets in the incomingList
    if(incomingPackets.isEmpty()){
      setServing(false);
    }
    else{
      Packet nextPacket = incomingPackets.remove(0);
      createDepartureEvent(nextPacket);
    }
    logger.debug("END handling departure event. incomingPackets.size()="+incomingPackets.size() +". isServing= "+isServing());
  }

}
