package org.imt.drr.simulators;

import java.io.*;
import java.util.Vector;

import org.imt.drr.model.Constants;

public final class StatsWriter {

  public static void writeHeader(String fileName) {
    try{
      FileWriter fstream = new FileWriter(fileName, false);
      BufferedWriter out = new BufferedWriter(fstream);
      out.write("#######################################################################\r\n\r\n");
      out.write(Constants.getConstantsString());
      out.write("#######################################################################\r\n\r\n");
      out.write("\r\n");
      //Close the output stream
      out.close();
    }catch (Exception e){//Catch exception if any
      System.err.println("Error: " + e.getMessage());
    }
    
  }

  public static void writeResults(String fileName, double[] throughput, int experimentNumber){
    try{
      // Create file 
      FileWriter fstream = new FileWriter(fileName, true);
      BufferedWriter out = new BufferedWriter(fstream);
      out.write("######################## EXPERIMENT RUN N" + experimentNumber + " #######################\r\n\r\n");
//      out.write(Constants.getConstantsString());
      for(int i = 0; i < throughput.length; i++) {
        out.write(i + " " + throughput[i] + "\r\n");
      }
      out.write("\r\n");
      //Close the output stream
      out.close();
    }catch (Exception e){//Catch exception if any
      System.err.println("Error: " + e.getMessage());
    }
  }
}
