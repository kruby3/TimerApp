import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class TimeLog {

	private Time currentTime;
	private Time startTime;
	private Time endTime;
	private Time differenceTime;
	private boolean inWorkMode;
	private Label currentTimeLabel;
	private Label differenceTimeLabel;
	private Label modeLabel;
	private HBox hBox;
	
	public TimeLog(Time currentTime, Time startTime, Time endTime, boolean inWorkMode) {
		this.currentTime = currentTime;
		this.startTime = startTime;
		this.endTime = endTime;
		this.differenceTime = endTime.difference(startTime);
		this.inWorkMode = inWorkMode;
		createTimeLogView();
	}
	
	private void createTimeLogView() {
		hBox = new HBox();
		modeLabel = new Label(createModeLabelText());
		currentTimeLabel = new Label(currentTime.toString());
		differenceTimeLabel = new Label(differenceTime.toString());
		hBox.getChildren().addAll(modeLabel, currentTimeLabel, differenceTimeLabel);
		hBox.setSpacing(20);
	}
	
	private String createModeLabelText() {
		String s;
		if (inWorkMode) {
			s = "Working";
		} else {
			s = "Break";
		}
		return s;
	}
	
	public HBox getTimeLogView() {
		return hBox;
	}
	
	public boolean isWorkMode() {
		return inWorkMode;
	}
	
	public Time getTimePassed() {
		return differenceTime;
	}
}
