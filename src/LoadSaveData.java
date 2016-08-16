import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class LoadSaveData {

	private static final File file = new File("projectData.dat");
	
	public static void saveProjectData(ArrayList<Project> projectList) {
		ObjectOutputStream output;
		try {
			loadFile();
			output = new ObjectOutputStream(new FileOutputStream(file, false));
			output.writeObject(projectList);
			output.close();
			System.out.println("-----saving");
			for(Project p : projectList) {
				System.out.println(p);
			}
			System.out.println("-----end");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void loadFile() throws IOException {
		if (!file.exists()) {
			file.createNewFile();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Project> loadProjectData() {
		ObjectInputStream input;
		ArrayList<Project> projectList = new ArrayList<Project>();
		
		try {
			loadFile();
			input = new ObjectInputStream(new FileInputStream(file));
			projectList = (ArrayList<Project>)(input.readObject());
			for(Project p : projectList) {
				System.out.println(p);
			}
			input.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		return projectList;
	}

}
