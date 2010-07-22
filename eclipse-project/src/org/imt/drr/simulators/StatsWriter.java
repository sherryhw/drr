package org.imt.drr.simulators;

import java.io.*;
import java.util.Collection;
import java.util.Vector;

public class StatsWriter {

  public static void writeResults(String fileName, Vector<Integer> flowIds, Vector<Double> throughput){
    try{
      // Create file 
      FileWriter fstream = new FileWriter(fileName);
      BufferedWriter out = new BufferedWriter(fstream);
      for(int i=0; i < flowIds.size();i++){
        out.write(flowIds.get(i) +" "+throughput+"\n");
      }
      //Close the output stream
      out.close();
    }catch (Exception e){//Catch exception if any
      System.err.println("Error: " + e.getMessage());
    }
  }
}
