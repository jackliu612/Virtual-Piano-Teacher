import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.*;

public class MIDIparser {
	
	static final int TIME = 1;
	static final int NOTE = 4;
	static final int DYNM = 5;
	
	public static int offset;
	public static final boolean both = true;
	public static ArrayList<Event> score = new ArrayList<Event>();
	public static ArrayList<Event> parse(String s) throws FileNotFoundException {
		Scanner infile = new Scanner(new File(s)); 
		String fLine = infile.nextLine();
		offset = Integer.parseInt(fLine.split("[\t ,]{1,}")[TIME]);
		score.add(makeEvent(fLine));
		while (infile.hasNext()) {
			score.add(makeEvent(infile.nextLine()));
			/*String s = infile.nextLine();
			if (s.split("[\t ]{1,}")[1].equals("Note_on_c")) {
				System.out.println(s);
			}*/
		}
		infile.close();
		return score;
	}
	
	public static Event makeEvent(String s){
		boolean b = !(Integer.parseInt(s.split("[\t ,]{1,}")[DYNM])==0 || s.split("[\t ,]{1,}")[2].equalsIgnoreCase("Note_off_c"));
		int time = Integer.parseInt(s.split("[\t ,]{1,}")[TIME])-offset;
		int note = Integer.parseInt(s.split("[\t ,]{1,}")[NOTE]);
		int vel = Integer.parseInt(s.split("[\t ,]{1,}")[DYNM]);
		if(b || both)
			time=quantize(time);
		return new Event(time,note, b, vel);
	}
	
	public static int quantize(int t){
		//Method 1: simple rounding
		//return Math.round(t/(1000*60/TEMPO))*(1000*60/TEMPO);
		//return t;
		return (int)(Math.round(t/(45.0))*(45));
		//Method 2: percentage rounding
		//return t+Math.round(Math.round(t/(1000*60/TEMPO))*(1000*60/TEMPO))*.9;
		
	}

}
