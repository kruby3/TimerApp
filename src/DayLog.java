import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class DayLog implements Serializable{
	private int totalMins;
	private LocalDate date;
	private DateFormat dateFormat;
	
	public DayLog() {
		this(0);
	}
	
	public DayLog(Time totalTime) {
		this(totalTime.getTotalMinutes());
	}
	
	public DayLog(int totalMins){
		this.totalMins = totalMins;
		date = LocalDate.now() ;
		dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	}
	
	public void add(int mins) {
		totalMins += mins;	
	}
	
	public void add(Time time){
		totalMins += time.getTotalMinutes();
	}
	
	public int getTotalMins() {
		return totalMins;
	}
	
	public Time getTotalTime() {
		return new Time(totalMins);
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof DayLog)) {
			return false;
		} else if (date.equals(((DayLog) other).getDate())) {
			return true;
		} else {
			return false;
		}
	}
}
