import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


public class Project implements Serializable{
	private String name;
	private Time totalTimeWorked;
	private ArrayList<DayLog> dayLogs;
	
	public Project(String name) {
		this.name = name;
		totalTimeWorked = new Time();
		dayLogs = new ArrayList<>();
	}
	
	public void add(Time addedTime, LocalDate date) {
		LocalDate today = LocalDate.now();
		if (today.equals(date) && dayLogs.size() != 0) {
			DayLog todaysLog = dayLogs.get(dayLogs.size() - 1);
			todaysLog.add(addedTime);
		} else {
			DayLog newLog = new DayLog(addedTime);
			dayLogs.add(newLog);
		}
		totalTimeWorked = totalTimeWorked.add(addedTime);
	}
	
	public void rename(String newName) {
		name = newName;
	}
	
	@Override
	public String toString() {
		return name + "\n" + totalTimeWorked;
	}
	
	public String getName() {
		return name;
	}
	public VBox getProjectView() {
		VBox projectBox = new VBox();
		Label nameLabel = new Label(name);
		Label timeLabel = new Label("Time worked: " + totalTimeWorked);
		projectBox.getChildren().addAll(nameLabel, timeLabel);
		return projectBox;
	}
	
	public ArrayList<DayLog> getDayLogs() {
		return dayLogs;
	}
	
}
