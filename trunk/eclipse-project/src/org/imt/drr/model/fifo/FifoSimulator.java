/**
 * 
 */
package org.imt.drr.model.fifo;

import org.imt.drr.model.Simulator;

/**
 * @author Andrea Vandin, Dmytro Karamshuk, Iffat Ahmed 
 *         PhD students at IMTLucca http://imtlucca.it
 *
 */
public class FifoSimulator implements Simulator {

  /**
   * An instance of fifo router.
   */
  public FifoRouter router;

  /**
   * Number of loops to proceed. 
   */
  public int duration;
  
  /* (non-Javadoc)
   * @see org.imt.drr.model.Simulator#execute()
   */
  @Override
  public void execute() {
    for (int i = 0; i < duration; i++ ) {
      router.proceedNextEvent();
    }
  }

  /* (non-Javadoc)
   * @see org.imt.drr.model.Simulator#initialize()
   */
  @Override
  public void initialize() {
    router = new FifoRouter();
    router.initialize();
    duration = 100;
  }

}
