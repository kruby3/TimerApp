import java.awt.Toolkit;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class timerSceneController {
	@FXML
	private Slider workSlider;
	@FXML
	private Slider breakSlider;
	@FXML 
	private Label timeLabel;
	@FXML
	private Label modeStatusLabel;
	@FXML
	private Label breakSliderLabel;
	@FXML
	private Label workSliderLabel;
	@FXML
	private Label totalWorkTimeLabel;
	@FXML
	private Label totalBreakTimeLabel;
	@FXML 
	private ToggleButton startStopButton;
	@FXML 
	private ListView<HBox> timeLogListView;
	@FXML
	private Button backButton;
	@FXML
	private Label currentProjectLabel;
	
	private Time time;
	private Time workTime;
	private Time breakTime;
	private Timeline timer;
	private boolean isWorking;
	private TimeLogList timeLogList;
	private Project selectedProject;
	private TimerApp timerApp;
	//private Project oldProject;
	
	public timerSceneController(Project selectedProject, TimerApp timerApp) {
		this.selectedProject = selectedProject;
		this.timerApp = timerApp;
		time = new Time();
		workTime = new Time();
		breakTime = new Time();
		timeLogList = new TimeLogList();
	}
	
	@FXML
	private void initialize() {
		initCurrentProjectLabel();
		initWorkSlider();
		initBreakSlider();
		initTimer();
		initStartStopButton();
	}
	
	private void initCurrentProjectLabel() {
		currentProjectLabel.setText(selectedProject.getName());
	}
	private void initStartStopButton() {
		startStopButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
	        if (newValue) {
	        	startWorking();
	        } else {
	        	startBreak();
	        }
	    });
	}
	private void initWorkSlider() {
		workSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
			workTime.setTime(newValue.intValue());
			workSliderLabel.setText(workTime.toString());
		});
	}
	
	private void initBreakSlider() {
		breakSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
			breakTime.setTime(newValue.intValue());
			breakSliderLabel.setText(breakTime.toString());
		});
	}

	private void initTimer() {
		timer = new Timeline(new KeyFrame( Duration.seconds(1),
				new EventHandler<ActionEvent>(){
				     @Override
				     public void handle(ActionEvent event) {
				          try {
							time.decreaseMinute();
						} catch (OutOfTimeException e) {
							timesUp();
						}
				          timeLabel.setText(time.toString());
				     }
				}));
		timer.setCycleCount(Animation.INDEFINITE);
	}

	private void timesUp() {
		timer.pause();
		final Runnable runnable = (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.default");
        if (runnable != null) {
            runnable.run();
        }
	}
	
	@FXML
	private void startWorking() {
	    if (timeLogList.getArrayList().size() != 0) {
            updateTimeLogList();
        }
		startStopButton.setText("Start Break");
		modeStatusLabel.setText("working");
		isWorking = true;
		updateTimesToSliderVals();
		time.setEqualTo(workTime);
		timer.play();
	}
	
	@FXML
	private void startBreak() {
		updateTimeLogList();
		startStopButton.setText("Start Working!");
		modeStatusLabel.setText("taking a break");
		isWorking = false;
		updateTimesToSliderVals();
		time.setEqualTo(breakTime);
		timer.play();
	}
	
	private void updateTimesToSliderVals() {
		workTime.setTime((int)workSlider.getValue());
        breakTime.setTime((int)breakSlider.getValue());
        
	}
	private Time getCurrentTime() {
		Calendar currentTime = Calendar.getInstance();
		int hour = currentTime.get(Calendar.HOUR_OF_DAY);
		int mins = currentTime.get(Calendar.MINUTE);
		return new Time(hour,mins);
	}
	
	private void updateTimeLogList() {
		timeLogList.add(new TimeLog(getCurrentTime(), workTime, time, isWorking));
		timeLogListView.setItems(FXCollections.observableList(timeLogList.getArrayList()));
		totalWorkTimeLabel.setText(timeLogList.getTotalWorkTime().toString());
		totalBreakTimeLabel.setText(timeLogList.getTotalBreakTime().toString());
	}
	
	@FXML
	private void goBack() {
		timesUp();
		save();
		Stage stage = (Stage) backButton.getScene().getWindow();
		FXMLLoader projectSceneLoader = new FXMLLoader(getClass().getResource("projectSelectScene.fxml"));
        BorderPane projectSceneLayout;
		try {
			projectSceneLayout = (BorderPane) projectSceneLoader.load();
			Scene projectScene = new Scene(projectSceneLayout);
		    stage.setScene(projectScene);
		    stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
       
	}
	private void save(){
		selectedProject.add(timeLogList.getTotalWorkTime(), LocalDate.now());
		ArrayList<Project> projectData = timerApp.getProjectData();
		timerApp.setProjectData(projectData);
		LoadSaveData.saveProjectData(projectData);
	}
	
}
