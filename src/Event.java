public class Event implements Comparable<Event>{
	private int start;
	//private int duration ;
	private int note;
	private int vel;
	private int art;
	private boolean play;
	
	public Event(){
		start = 0; 
		note = 0;
		play = false;
		vel = 0;
		art = 0;
	}
	
	public Event (int s, int n, boolean p, int v){
		start = s;
		note = n;
		play = p;
		vel = v;
	}
	
	public Event (int s, int n, boolean p, int v, int a){
		start = s; 
		note = n;
		play = p;
		vel = v;
		art = a;
	}
	
	@Override
	public int compareTo(Event e) {
		if(this.getStart() == e.getStart()){
			if(!this.getPlay()&&!e.getPlay())			//If both are note_off events, lowest pitch takes priority
				return this.getNote()-e.getNote();   
			else if(this.getPlay()&&e.getPlay())		//If both are note_on events, lowest pitch takes priority
				return this.getNote()-e.getNote();
			else if(this.getPlay())					//If only a is a note_on event, b takes priority
				return 1;
			else 									//Only a is a note_off event so it takes priority
				return -1;
		}
		return this.getStart() - e.getStart();
	}
	
	public boolean equals(Object o){
		Event e = (Event) o;
		if(this.getStart() == e.getStart() && this.getNote() == e.getNote() && this.getPlay() == e.getPlay()){
			return true;
		}
		else{
			return false;
		}
	}
	
	public int getStart(){
		return this.start;
	}
	
	public int getNote(){
		return this.note;
	}
	
	public boolean getPlay(){
		return this.play;
	}
	
	public int getVel(){
		return this.vel;
	}
	
	public int getArt(){
		return this.art;
	}
	
	public void setStart(int s){
		this.start = s;
	}
	
	public void setNote(int n){
		this.note = n;
	}
	
	public void setPlay(boolean p){
		this.play = p;
	}
	
	public void setArticulation(int a){
		this.art = a;
	}
	
	public String toString(){
		return "["+start+", "+note+", "+play+", "+vel+"]\n";
	}
}
