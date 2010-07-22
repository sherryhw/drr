/**
 * 
 */
package org.imt.drr;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.imt.drr.model.Constants;
import org.imt.drr.simulators.FourRoutersSimulator;
import org.imt.drr.simulators.OneRouterSimulator;
import org.imt.drr.simulators.RouterType;

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
//    OneRouterSimulator simulator = new OneRouterSimulator();
//    simulator.initialize(10000, RouterType.DRR);
//    simulator.execute();
//    OneRouterSimulator simulator = new OneRouterSimulator();
//    simulator.initialize(1000000, RouterType.DRR);
//    simulator.execute();

    //Four fifos
    FourRoutersSimulator simulator = new FourRoutersSimulator();
    simulator.initialize(Constants.SIMULATION_TIME, RouterType.FIFO, 4);
    simulator.execute();
  }

}
