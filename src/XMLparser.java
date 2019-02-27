import java.io.File;
import java.util.*;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLparser {
	public static ArrayList<Event> tEvents = new ArrayList<Event>();
	public static ArrayList<Event> score = new ArrayList<Event>();
	public static HashMap<String, Integer> keys = new HashMap<String, Integer>();

	public static ArrayList<Event> parse(String s) {
		makeMap();
		try {
			File inputFile = new File(s);
			//File inputFile = new File("CStF.xml");
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			UserHandler userhandler = new UserHandler();
			saxParser.parse(inputFile, userhandler);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return score;
	}

	public static void makeMap() {
		//keys = new HashMap<String, Integer>();
		keys.put("C", 0);
		keys.put("D", 2);
		keys.put("E", 4);
		keys.put("F", 5);
		keys.put("G", 7);
		keys.put("A", 9);
		keys.put("B", 11);
	}
}

class UserHandler extends DefaultHandler {

	final int OFFSET = 12;
	boolean divisions, beats, step, octave, alter, duration, rst, chord;
	int time = 0;
	int tempo = 120;
	int rTempo;
	int s, d, n, art = 0;
	int prevT=0;
	boolean p;
	boolean slurCont;
	Event temp;
	HashMap<String, Integer> keys = XMLparser.keys;
	

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		//System.out.println(keys);
		if (qName.equalsIgnoreCase("measure")) {
		} else if (qName.equalsIgnoreCase("divisions")) {
			divisions = true;
		} else if (qName.equalsIgnoreCase("beats")) {
			beats = true;
		} else if (qName.equalsIgnoreCase("step")) {
			step = true;
		} else if (qName.equalsIgnoreCase("octave")) {
			octave = true;
		} else if (qName.equalsIgnoreCase("alter")) {
			alter = true;
		} else if (qName.equalsIgnoreCase("duration")) {
			duration = true;
		} else if (qName.equalsIgnoreCase("rest")) {
			rst = true;
		} else if (qName.equalsIgnoreCase("staccato")){
			art = 1;
		} else if (qName.equalsIgnoreCase("slur")){
			art = 2;
			slurCont = attributes.getValue("type").equalsIgnoreCase("start");
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		// Slurs:https://usermanuals.musicxml.com/MusicXML/Content/EL-MusicXML-slur.htm
		// Staccato:http://usermanuals.musicxml.com/MusicXML/MusicXML.htm#EL-MusicXML-staccato.htm%3FTocPath%3DMusicXML%2520Reference%7CScore%2520Schema%2520(XSD)%7CElements%7Cnote%7C_____98
		
		if (qName.equalsIgnoreCase("chord")) {
			chord=true;
		}
		if (qName.equalsIgnoreCase("note")) {
			if(rst){          			//What to do for rests
				rst=false;
			} else{           			//What to do for notes
				if(chord){    			//Chord exception
					time=prevT;
					chord=false;
				}
				temp = new Event(time, n+OFFSET, true, 0, art);
				XMLparser.tEvents.add(temp);
				temp = new Event(time+d*rTempo, n+OFFSET, false, 0, art);
				XMLparser.tEvents.add(temp);
				if(!slurCont)
					art = 0;
			}
			prevT=time;	
			time=time+d*rTempo;

		} else if (qName.equalsIgnoreCase("backup")) { 
			time=time-d*rTempo;
			
		} else if (qName.equalsIgnoreCase("measure")) {
			Collections.sort(XMLparser.tEvents, new Sortbystart());
			for (Event e : XMLparser.tEvents)
				XMLparser.score.add(e);
			XMLparser.tEvents.clear();
		} else if (qName.equalsIgnoreCase("score-partwise")){
		}
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {

		if (divisions) {
			/*
			rTempo = (int) (60.0 * 1000 / tempo / Double.parseDouble(new String(ch, start, length)));
			*/
			divisions = false;
			rTempo = 45;  //CHECK BACK ON THIS LATER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		} else if (beats) {
			beats = false;
		} else if (step) {
			// Temporary storage of note base pitch
			n = keys.get(new String(ch, start, length).toUpperCase());
			step = false;
		} else if (octave) {
			// Adds octave offset to base
			n = n + 12 * Integer.parseInt(new String(ch, start, length));
			octave = false;
		} else if (alter) {
			// Changes n to adjust for flats and sharps
			n = n + Integer.parseInt(new String(ch, start, length));
			alter = false;
		} else if (duration) {
				d = Integer.parseInt(new String(ch, start, length));
				duration = false;
		}
	}
}
