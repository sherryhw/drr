/**
 * 
 */
package org.imt.drr;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.imt.drr.model.fifo.FifoSimulator;

/**
 * The main application launch class.
 * 
 * @author Andrea Vandin, Dmytro Karamshuk, Iffat Ahmed 
 *         PhD students at IMTLucca http://imtlucca.it
 *
 */
public class Runner {

  /**
   * Logger
   */
  static Logger logger = Logger.getLogger(Runner.class);
  
  /**
   * The main method of the app.
   * 
   * @param args
   */
  public static void main(String[] args) {
    
    //Configure logger
    PropertyConfigurator.configure("log/log4j.properties");
    
    //We just start with creating simple Fifo Simulator
    FifoSimulator simulator = new FifoSimulator();
    simulator.initialize(100);
    simulator.execute();
    logger.debug("fuck off");
    
  }

}
