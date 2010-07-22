/**
 * 
 */
package org.imt.drr.model;

/**
 * @author Andrea Vandin, Dmytro Karamshuk, Iffat Ahmed 
 *         PhD students at IMTLucca http://imtlucca.it
 *
 */
public final class Constants {

  public static final boolean USE_BASTARD_FLOW = true;
  
  public static final int DEFAULT_PACKET_SIZE_MAX = 4500;
  public static final int DEFAULT_NUMBER_OF_FLOWS = 20;
  public static final int DEFAULT_ARRIVAL_TIME_MEAN = 45;
  
  public static final int MAXQUEUESIZE = 10000; //20 * 500
  public static final int DEFAULT_BANDWIDTH = 1000; //250;
  
  public static final int DEFAULTQUANTUMOFSERVICE = 50;
  
  public static final boolean USE_LOG = false;
  
  public static final int SETUP_TIME = 5000; //50000;
  
  public static final int PART_AVERAGE = 100;
  
  public static final int SIMULATION_TIME = 2000000;
  
  public static final int NUMBER_OF_RUNS = 5;

  public static final String OUTPUT_FILE_NAME = "log/results.txt";

  /**
   * returns string representation
   * @return
   */
  public static final String getConstantsString() {
    String str = "";
    str += "USE_BASTARD_FLOW = " + USE_BASTARD_FLOW + "\r\r\n";
    str += "DEFAULT_PACKET_SIZE_MAX = " + DEFAULT_PACKET_SIZE_MAX + "\r\n";
    str += "DEFAULT_NUMBER_OF_FLOWS = " + DEFAULT_NUMBER_OF_FLOWS + "\r\n";
    str += "DEFAULT_ARRIVAL_TIME_MEAN = " + DEFAULT_ARRIVAL_TIME_MEAN + "\r\n";
    str += "MAXQUEUESIZE = " + MAXQUEUESIZE + "\r\n";
    str += "DEFAULT_BANDWIDTH = " + DEFAULT_BANDWIDTH + "\r\n";
    str += "SETUP_TIME = " + SETUP_TIME + "\r\n";
    str += "PART_AVERAGE = " + PART_AVERAGE + "\r\n";
    str += "SIMULATION_TIME = " + SIMULATION_TIME + "\r\n\r\n";
    str += "NUMBER_OF_RUNS = " + NUMBER_OF_RUNS + "\r\n\r\n";
    return str;
  }
}
