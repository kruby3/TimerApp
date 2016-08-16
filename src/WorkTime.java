
public class WorkTime{
	private Time workTime;
	
	public WorkTime() {
		workTime = new Time();
	}
	
	public void setWorkTime(int minutes) {
		workTime.setTime(minutes);
	}
	
	public String getWorkTime() {
		return workTime.toString();
	}
}
