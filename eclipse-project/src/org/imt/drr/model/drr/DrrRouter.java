/**
 * 
 */
package org.imt.drr.model.drr;



import java.util.Collection;

import org.imt.drr.model.Node;
import org.imt.drr.model.Packet;
import org.imt.drr.model.Router;

/**
 * @author Andrea Vandin, Dmytro Karamshuk, Iffat Ahmed 
 *         PhD students at IMTLucca http://imtlucca.it
 *
 */
public class DrrRouter extends Router {
  
  public DrrRouter(Collection<Node> sources){
    super(sources);
  }

  /* (non-Javadoc)
   * @see org.imt.drr.model.Router#proceedNextEvent()
   */
  @Override
  public void proceedNextEvent() {
    // TODO Auto-generated method stub

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
    super.initialize();
    
  }

  @Override
  public void scheduleNextPacket() {
    // TODO Auto-generated method stub
    
  }

}
