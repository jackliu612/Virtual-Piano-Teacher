import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.*;

public class Comp {
	// static final String MXML_INPUT = "Plsss.xml";
	// static final String MIDI_INPUT = "Clannad 1-8 Slow 60 Errors.txt";
	static String Mxml_input, Midi_input;
	public static ArrayList<Event> testing = new ArrayList<Event>();
	public static ArrayList<Event> xmlScore;
	public static ArrayList<Event> midiScore;

	public static int tempo;
	public static ArrayList<Integer> dLevels = new ArrayList<Integer>();

	public static void main(String args[]) throws FileNotFoundException {
		// Scanner keybd = new Scanner(System.in);
		// System.out.println("MIDI?");
		// Midi_input = keybd.nextLine();
		// System.out.println("MXL?");
		// Mxml_input = keybd.nextLine();

		xmlScore = XMLparser.parse("../" + args[0]/* Mxml_input */ + ".xml");
		midiScore = MIDIparser.parse("../" + args[0]/* Midi_input */ + ".txt");

		importCal("../" + args[1]/* calibration file */ + ".txt");
		System.out.println(tempo);
		System.out.println(dLevels);
		/*
		 * Collections.sort(midiScore, new Sortbystart());
		 * System.out.println(xmlScore); System.out.println(midiScore);
		 * compare(xmlScore, midiScore);
		 */
		compare(xmlScore, midiScore, null);
	}

	public static void importCal(String s) throws FileNotFoundException {
		Scanner infile = new Scanner(new File(s));
		try {
			tempo = Integer.parseInt(infile.nextLine());
			while (infile.hasNext()) {
				String next = infile.next();
				//System.out.println("Processing: " + next + "|");
				dLevels.add(Integer.parseInt(next));
			}
			dLevels.add(0);
			dLevels.add(128);
			Collections.sort(dLevels);

		} catch (Exception e) {
			if (e instanceof FileNotFoundException) {
				System.out.println("File not Found");
			} else if (e instanceof NoSuchElementException) {
				System.out.println("Calibration file improperly formatted");
			} else {
				System.out.println(e);
			}
		}
		infile.close();
	}

	public static void compare(ArrayList<Event> xml, ArrayList<Event> midi, TreeMap<Integer, Integer> dMeasures) {
		//int currentDynmLevel = dMeasures.get(0);
		int DynmIndex = 1;
		int note = 0;
		
		String s = "";
		double time = 0;
		for (int x = 0; x < xml.size(); x++) {
			time = midi.get(x).getStart() / tempo;
			/*
			 * if(xml.get(x).getPlay()){ numNote++; }
			 */
			s = "";
			if(midi.get(x).getPlay()){
				note++;
				if(note>4)
					DynmIndex = 2;
				if(note>7)
					DynmIndex = 3;
			}
			/*if (!xml.get(x).equals(midi.get(x))) {*/
				Event a = xml.get(x);
				Event b = midi.get(x);
				if (b.getPlay()) {
					if (b.getNote() != a.getNote()) {
						//s += ", Pitch off";
					}
					if (b.getStart() > a.getStart()) {
						//s += ", Started too slow";
					}
					if (b.getStart() < a.getStart()) {
						//s += ", Started too fast";
					}
					if (midi.get(x).getVel() > dLevels.get(DynmIndex+1)) {
						s += ", Too loud";
					}
					if (midi.get(x).getVel() < dLevels.get(DynmIndex-1)) {
						s += ",Too soft";
					}
				} else {
					if (b.getStart() > a.getStart()) {
						//s += ", Ended too slow";
					}
					if (b.getStart() < a.getStart() && b.getArt() != 1) {
						//s += ", Ended too fast";
					}
				//}
			}

			if (!s.equals("")) {
				System.out.println("Note " + note + "" + s);
				System.out.println(note +", "+ midi.get(x).getVel() +", "+ dLevels.get(DynmIndex+1) + ", " + dLevels.get(DynmIndex-1)) ;
			}
			//System.out.println(note +", "+ dLevels.get(DynmIndex) + ", " + dLevels.get(DynmIndex)-1) ;
		}
	}

	public void calibrate(ArrayList<Event> midi) throws FileNotFoundException {
		int sum = 0;
		for (int i = 0; i < 24; i += 2) {
			sum += midi.get(i).getStart() - midi.get(i + 1).getStart();
		}
		int tempo = sum / 12 - (sum / 12) % 10;
		System.setOut(new PrintStream(new File("d.txt")));
		System.out.println(tempo);
	}

	public static void makeTesting() {
		//See comments below
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
