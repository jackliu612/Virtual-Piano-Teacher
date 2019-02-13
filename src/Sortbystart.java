import java.util.*;

public class Sortbystart implements Comparator<Event> {
	public int compare(Event a, Event b) {
		if(a.getStart() == b.getStart()){
			if(!a.getPlay()&&!b.getPlay())			//If both are note_off events, lowest pitch takes priority
				return a.getNote()-b.getNote();   
			else if(a.getPlay()&&b.getPlay())		//If both are note_on events, lowest pitch takes priority
				return a.getNote()-b.getNote();
			else if(a.getPlay())					//If only a is a note_on event, b takes priority
				return 1;
			else 									//Only a is a note_off event so it takes priority
				return -1;
		}
		return a.getStart() - b.getStart();
	}
}
