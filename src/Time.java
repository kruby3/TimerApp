import java.io.Serializable;

public class Time implements Serializable{
	private int hours;
	private int minutes;
	
	public Time() {
		this(0,0);
	}
	
	public Time(int minutes) {
		this(0, minutes);
	}
	
	public Time(int hours, int minutes) {
		setTime(hours,minutes);
	}
	
	public void setTime(int hours, int minutes){
		this.hours = hours;
		this.minutes = minutes;
		roundMinutes();
	}
	
	public void setTime(int minutes){
		setTime(0 , minutes);
	}
	
	private void roundMinutes() {
		if (minutes >= 60) {
			hours = minutes / 60;
			minutes = minutes % 60;
		}
	}
	
	public void decreaseMinute() throws OutOfTimeException{
		int newMinutes;
		int newHours;
		
		newMinutes = minutes - 1;
		if (newMinutes < 0) {
			newMinutes = 59;
			newHours = hours - 1;
			if (newHours <= -1) {
				throw new OutOfTimeException();
			}
			hours = newHours;
		}
		
		minutes = newMinutes;
	}
	
	public void setEqualTo(Time otherTime) {
		this.hours = otherTime.hours;
		this.minutes = otherTime.minutes;
		
	}
	
	public int getTotalMinutes() {
		return (hours * 60) + minutes;
	}
	
	public Time difference(Time otherTime) {
		int difference = otherTime.getTotalMinutes() - this.getTotalMinutes();
		return new Time(Math.abs(difference));
	}
	 
	@Override
	public String toString() {
		String strHours = padZeros(Integer.toString(hours));
		String strMins = padZeros(Integer.toString(minutes));
		return strHours + ":" + strMins;
	}
	
	private String padZeros(String s) {
		if (s.length() == 1) {
			return "0" + s;
		} 
		return s;
	}
	
	public Time add(Time otherTime) {
		int totalMins = this.getTotalMinutes() + otherTime.getTotalMinutes();
		Time totalTime = new Time(totalMins);
		return totalTime;
	}
}
