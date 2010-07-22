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
import org.imt.drr.simulators.StatsWriter;

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
    
    StatsWriter.writeHeader(Constants.OUTPUT_FILE_NAME);
    for (int i = 0; i < Constants.NUMBER_OF_RUNS; i++){
      OneRouterSimulator simulator = new OneRouterSimulator();
      simulator.initialize(Constants.SIMULATION_TIME, RouterType.NEWDRR, i);
      simulator.execute();
    }

//    Four fifos
//    StatsWriter.writeHeader(Constants.OUTPUT_FILE_NAME);
//    for (int i = 0; i < Constants.NUMBER_OF_RUNS; i++){
//      FourRoutersSimulator simulator = new FourRoutersSimulator();
//      simulator.initialize(Constants.SIMULATION_TIME, RouterType.FIFO, 4, i);
//      simulator.execute();
//    }
  }

}
