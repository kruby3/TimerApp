import java.util.ArrayList;

import javafx.scene.layout.HBox;

public class TimeLogList {
		private ArrayList<HBox> timeLogList;
		private Time totalWorkTime;
		private Time totalBreakTime;
		
		public TimeLogList() {
			timeLogList = new ArrayList<HBox>();
			totalWorkTime = new Time();
			totalBreakTime = new Time();
		}
		
		public void add(TimeLog timeLog) {
			timeLogList.add(timeLog.getTimeLogView());
			if (timeLog.isWorkMode()) {
				totalWorkTime = totalWorkTime.add(timeLog.getTimePassed());
			} else {
				totalBreakTime = totalBreakTime.add(timeLog.getTimePassed());
			}
		}
		
		public ArrayList<HBox> getArrayList() {
			return timeLogList;
		}
		
		public Time getTotalWorkTime() {
			return totalWorkTime;
		}
		
		public Time getTotalBreakTime() {
			return totalBreakTime;
		}
}
