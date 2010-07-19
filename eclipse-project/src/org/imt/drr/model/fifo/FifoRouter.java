/**
 * 
 */
package org.imt.drr.model.fifo;




import java.util.Collection;

import org.imt.drr.model.Node;
import org.imt.drr.model.Packet;
import org.imt.drr.model.Router;

import event.Event;
import event.EventType;

/**
 * @author Andrea Vandin, Dmytro Karamshuk, Iffat Ahmed 
 *         PhD students at IMTLucca http://imtlucca.it
 *
 */
public class FifoRouter extends Router {

  public FifoRouter(Collection<Node> sources){
    super(sources);
  }
  
  /* (non-Javadoc)
   * @see org.imt.drr.model.Router#proceedNextEvent()
   */
  @Override
  public void proceedNextEvent() {
    Event evt = getNextEvent();
    if(evt != null){
      switch(evt.getType()){
      case ARRIVAL:
        //arrival event handler
        break;
      case DEPARTURE:
        //departure event handler
        break;
      }
      //here I have to ask a new packet to every source node
      askNewPackets();
    }
    

  }
  
  
  
  
  
  

  /* (non-Javadoc)
   * @see org.imt.drr.model.Node#getNextPacket()
   */
  @Override
  public Packet getNextPacket() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void initialize() {
    
  }

  @Override
  public void scheduleNextPacket() {
    // TODO Auto-generated method stub
    
  }

}
