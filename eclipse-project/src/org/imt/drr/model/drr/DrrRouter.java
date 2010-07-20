/**
 * 
 */
package org.imt.drr.model.drr;



import java.nio.BufferOverflowException;
import java.util.Collection;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.imt.drr.model.Node;
import org.imt.drr.model.Packet;
import org.imt.drr.model.Router;

import event.Event;

/**
 * @author Andrea Vandin, Dmytro Karamshuk, Iffat Ahmed 
 *         PhD students at IMTLucca http://imtlucca.it
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
  private final int DEFAULTQUANTUMOFSERVICE=500;
  
  public DrrRouter(Collection<Node> sources, int bandwidth, int numberOfFlows){
    super(sources,bandwidth);
    this.numberOfFlows=numberOfFlows;
  }

  @Override
  public void initialize() {
    super.initialize();
    deficitCounters=new int[numberOfFlows];
    quantumOfServices=new int[numberOfFlows];
    incomingFlows=new Vector<Vector<Packet>>(numberOfFlows);
    activeList=new Vector<ActiveListElement>();
    
    for(int i=0;i<numberOfFlows;i++){
      incomingFlows.add(new Vector<Packet>());
      deficitCounters[i]=0;
      quantumOfServices[i]=DEFAULTQUANTUMOFSERVICE;
    }
    
  }

  @Override
  protected void arrivalEventHandler(Event evt) {
    //logger.info("!!!!!!!!!!!!!!!DRRRouter start handling arrival event. incomingPackets.size="+incomingPackets.size()+ ". isServing= "+isServing()+". " + evt);
    if(isServing()){
    //The router is busy
      
      //BEGIN check if the flow is in the active list
      int idFlow=evt.getPacket().getIdFlow();
      Vector<Packet> flowQueue = incomingFlows.elementAt(idFlow);
      boolean flowIsInActiveList=false;
      for (ActiveListElement ale : activeList) {
        if(ale.getFlowId()==idFlow){
          flowIsInActiveList=true;
          break;
        }
      }
      if(!flowIsInActiveList){
        ActiveListElement element = new ActiveListElement(flowQueue, idFlow);
        activeList.add(element);
        deficitCounters[idFlow]=0;
      }
     //END check if the flow is in the active list
      
      /*
       * if no free buffers left FreeBuffer... TODOTODOTODOTODOTODOTODOTODO...
       */
      //The router is busy, so I have to enqueue the packet, if there is still space in the queue.
      if(flowQueue.size()==MAXQUEUESIZE){
        throw new BufferOverflowException();
      }
      else{
        //incomingPackets.add(evt.getPacket());
        flowQueue.add(evt.getPacket());
      }
    }
    
    
    
    else //CONTROLLARE, NON SO SERVE.....
    {
      //the router is currently idle. I can directly transmit the packet.
      createDepartureEvent(evt.getPacket());
      setServing(true);///////////////////////
    }
    //logger.info("!!!!!!!!!!!!!!DRRRouter end handling arrival event. incomingPackets.size()="+incomingPackets.size() +". isServing= "+isServing());
    
  }

  
  private void dequeueModule(){
    while(true){
      if(!activeList.isEmpty()){
        //Vector<Packet> flowQueue = activeList.remove(0);
      }
    }
  }
  
  @Override
  protected void departureEventHandler(Event evt) {
    /*
    //logger.info("!!!!!!!!!!!!!!!start handling departure event. incomingPackets.size="+incomingPackets.size()+ ". isServing= "+isServing()+". " + evt);
    //First put the sent packet in the list of the outgoing packets
    Packet sentPacket = evt.getPacket();
    addOutgoingPacket(sentPacket);  

    int idFlow=sentPacket.getIdFlow();
    Vector<Packet> flowQueue = incomingFlows.elementAt(idFlow);
    //Then create the next departure event if there are any packets in the .....
    if(incomingPackets.isEmpty()){//NO dirò: se activeList è vuota...
      setServing(false);
    }
    else{
      //Qui ora devo segliere quali e quanti pacchetti fare partire, aggiornare DC_i ... 
      Packet nextPacket = incomingPackets.remove(0);
      createDepartureEvent(nextPacket);
      
      
      
      
      
    }
    //logger.info("!!!!!!!!!!!!!!!end handling departure event. incomingPackets.size()="+incomingPackets.size() +". isServing= "+isServing());
    */
  }

}
