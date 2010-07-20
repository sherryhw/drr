package event;


import java.util.*;


/**
 * 
 * * @author Andrea Vandin, Dmytro Karamshuk, Iffat Ahmed 
 *         PhD students at IMTLucca http://imtlucca.it
 */
public class EventComparator implements Comparator<Event>
{
  
  /**
   * This method is utilized to compare to events. Thanks to this method, the event will be sorted based on their time-type of event - id
   * 
   */
  public int compare(Event event1, Event event2)
  {
    int order = event1.getTime() - event2.getTime();
    
    //If they have the same time, and only one has type nope, then it is the smaller.
    if(order==0){
      if((event1.getType()==EventType.NOPE)&&(event2.getType()!=EventType.NOPE))
        order=-1;
      else
        if((event1.getType()!=EventType.NOPE)&&(event2.getType()==EventType.NOPE))
          order=1;        
    }
    
    //If they are still even, then the order is defined by their ID. If they have same id, then they are the same event.
    if(order==0){
      if(event1.getId() < event2.getId())
        order=-1;
      else{ 
        if(event1.getId() == event2.getId())
          order=0;
        else
          order=1;
      }
    }
    return order;
  }
  
}
