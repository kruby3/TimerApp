import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TimerApp extends Application{
	
	private ArrayList<Project> projectData;
	
	public TimerApp() {
		projectData = new ArrayList<>();
	}
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
            FXMLLoader projectSceneloader = new FXMLLoader(TimerApp.class.getResource("projectSelectScene.fxml"));
            BorderPane projectSceneLayout = (BorderPane) projectSceneloader.load();
            Scene projectScene = new Scene(projectSceneLayout);
            primaryStage.setScene(projectScene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

	public ArrayList<Project> getProjectData() {
		return projectData;
	}

	public void setProjectData(ArrayList<Project> projectData) {
		this.projectData = projectData;
	}
	


}
