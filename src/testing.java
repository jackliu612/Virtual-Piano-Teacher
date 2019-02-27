import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.*;

public class testing {
	// static final String MXML_INPUT = "Plsss.xml";
	// static final String MIDI_INPUT = "Clannad 1-8 Slow 60 Errors.txt";
	static String Mxml_input, Midi_input;
	public static ArrayList<Event> testing = new ArrayList<Event>();
	public static ArrayList<Event> xmlScore;
	public static ArrayList<Event> midiScore;

	public static int tempo;
	public static ArrayList<Integer> dLevels = new ArrayList<Integer>();

	public static void main(String args[]) throws FileNotFoundException {

		xmlScore = XMLparser.parse("../" + args[0]/* Mxml_input */ + ".xml");
		System.out.println(xmlScore);
		//midiScore = MIDIparser.parse("../" + args[0]/* Midi_input */ + ".txt");

		//importCal("../" + args[1]/* calibration file */ + ".txt");
	}
}
/**********************************************
 * 
 * 
 * testing.add(new Event(0, 49, true)); testing.add(new Event(0, 82, true));
 * testing.add(new Event(250, 49, false)); testing.add(new Event(250, 56,
 * true)); testing.add(new Event(500, 56, false)); testing.add(new Event(500,
 * 82, false)); testing.add(new Event(500, 61, true)); testing.add(new
 * Event(500, 80, true)); testing.add(new Event(750, 61, false));
 * testing.add(new Event(750, 80, false)); testing.add(new Event(750, 56,
 * true)); testing.add(new Event(750, 77, true)); testing.add(new Event(1000,
 * 56, false)); testing.add(new Event(1000, 77, false)); testing.add(new
 * Event(1000, 65, true)); testing.add(new Event(1000, 68, true));
 * testing.add(new Event(1000, 77, true)); testing.add(new Event(1250, 65,
 * false)); testing.add(new Event(1250, 68, false)); testing.add(new Event(1250,
 * 77, false)); testing.add(new Event(1250, 56, true)); testing.add(new
 * Event(1250, 82, true)); testing.add(new Event(1500, 56, false));
 * testing.add(new Event(1500, 82, false)); testing.add(new Event(1500, 61,
 * true)); testing.add(new Event(1500, 80, true)); testing.add(new Event(1750,
 * 61, false)); testing.add(new Event(1750, 80, false)); testing.add(new
 * Event(1750, 56, true)); testing.add(new Event(1750, 87, true));
 * testing.add(new Event(2000, 56, false)); testing.add(new Event(2000, 87,
 * false));
 * 
 * 
 ***********************************************/
