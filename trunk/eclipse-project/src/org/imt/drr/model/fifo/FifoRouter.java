/**
 * 
 */
package org.imt.drr.model.fifo;




import org.imt.drr.model.Packet;
import org.imt.drr.model.Router;

/**
 * @author Andrea Vandin, Dmytro Karamshuk, Iffat Ahmed 
 *         PhD students at IMTLucca http://imtlucca.it
 *
 */
public class FifoRouter extends Router {

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
    // TODO Auto-generated method stub
    
  }

  @Override
  public void scheduleNextPacket() {
    // TODO Auto-generated method stub
    
  }

}
