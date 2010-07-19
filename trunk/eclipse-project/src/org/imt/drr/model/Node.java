package org.imt.drr.model;

/**
 * Interface of node. 
 * 
 * @author Andrea Vandin, Dmytro Karamshuk, Iffat Ahmed 
 *         PhD students at IMTLucca http://imtlucca.it
 *
 */
public interface Node {
	
	/**
	 * 
	 * @return next packet 
	 */
  public Packet getNextPacket();
  
  /*
   * Initialize the node.
   */
  public void initialize();

}
