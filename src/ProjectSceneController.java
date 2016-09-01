import java.util.ArrayList;
import java.util.Optional;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProjectSceneController {
	@FXML
	private Button newProjectButton;
	@FXML
	private Button deleteProjectButton;
	@FXML
	private Button renameProjectButton;
	@FXML
	private Button startProjectButton;
	@FXML
	private Pane graphPane;
	@FXML
	private ListView<VBox> projectListView;
	private ArrayList<VBox> projectVBoxList;
	
	//private Project selectedProject;
	private TimerApp timerApp;
	private TextInputDialog newProjDialog;
	
	public ProjectSceneController() {
		timerApp = new TimerApp();
		projectVBoxList = new ArrayList<VBox>();
	}
	
	@FXML
	private void initialize() {
		initListView();
	}
	
	private void initListView() {
		timerApp.setProjectData(LoadSaveData.loadProjectData());
		updateVBoxList();
		
		projectListView.getSelectionModel().selectedItemProperty()
	        .addListener(new ChangeListener<VBox>() {
	          public void changed(ObservableValue<? extends VBox> observable,
	              VBox oldValue, VBox newValue) {
	        	  	Project currentProject = getSelectedProject();
	        	  	LineGraph currentProjectGraph = new LineGraph(currentProject);
	        	  	graphPane.getChildren().clear();
	        	  	graphPane.getChildren().add(currentProjectGraph.getChart());
	          }
	        });
	}
	
	@FXML
	private void newProject() {
		createNewProjDialog();
		Optional<String> name = newProjDialog.showAndWait();
		if (name.isPresent()){
			Project newProject = new Project(name.get());
			timerApp.getProjectData().add(newProject);
			projectVBoxList.add(newProject.getProjectView());
			projectListView.setItems(FXCollections.observableList(projectVBoxList));
		}
	}
	
	private void createNewProjDialog() {
		newProjDialog = new TextInputDialog();
		newProjDialog.setTitle("Enter Project Name");
		newProjDialog.setHeaderText("Enter Project Name");
		newProjDialog.setContentText("Name: ");
	}
	
	@FXML
	private void renameProject() {
		Project selectedProject = getSelectedProject();
		if (selectedProject != null) {
			createNewProjDialog();
			Optional<String> name = newProjDialog.showAndWait();
			if (name.isPresent()){
				selectedProject.rename(name.get());
				updateVBoxList();
			}
		}
	}
	
	private void updateVBoxList() {
		projectVBoxList.clear();
		for(Project p : timerApp.getProjectData()) {
			projectVBoxList.add(p.getProjectView());
		}
		projectListView.setItems(FXCollections.observableList(projectVBoxList));
	}
	
	private Project getSelectedProject() {
		int selectedIndex = projectListView.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			return timerApp.getProjectData().get(selectedIndex);
		} else {
			return null;
		}
	}

	@FXML
	private void deleteProject() {
		Project selectedProject = getSelectedProject();
		if (selectedProject != null) {
			timerApp.getProjectData().remove(selectedProject);
			updateVBoxList();
		}
	}
	
	@FXML
	private void startProject() throws Exception{
		saveProjectList();
		Stage stage = (Stage) startProjectButton.getScene().getWindow();
		FXMLLoader timerSceneLoader = new FXMLLoader(getClass().getResource("timerScene.fxml"));
		Project selectedProject = getSelectedProject();
		if (selectedProject != null) {
	        timerSceneController controller = new timerSceneController(selectedProject, timerApp);
	        timerSceneLoader.setController(controller);
	        BorderPane timerSceneLayout = (BorderPane) timerSceneLoader.load();
	        Scene timerScene = new Scene(timerSceneLayout);
	        stage.setScene(timerScene);
	        stage.show();
		} else {
			new ErrorDialog("Please select a project");
		}
	}
	
	private void saveProjectList() {
		LoadSaveData.saveProjectData(timerApp.getProjectData());
	}
	
	
}
