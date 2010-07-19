package event;

import org.imt.drr.model.Packet;

/**
 * 
 * * @author Andrea Vandin, Dmytro Karamshuk, Iffat Ahmed 
 *         PhD students at IMTLucca http://imtlucca.it
 */
public class Event {
  private int time;
  private EventType type;
  private Packet packet;
  private final int id;

  private static int idCounter=0;
  
  public int getTime() {
    return time;
  }

  public EventType getType() {
    return type;
  }
  
  public int getId(){
    return id;
  }
  
  public Packet getPacket(){
    return packet;
  }

  public Event(Packet packet, int time, EventType type) {
    super();
    this.packet = packet;
    this.time = time;
    this.type = type;
    this.id=idCounter++;
  }
  
}


