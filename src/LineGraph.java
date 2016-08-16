import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

public class LineGraph {

	final CategoryAxis xAxis = new CategoryAxis();
	final NumberAxis yAxis = new NumberAxis();
	final LineChart<String,Number> lineChart = new LineChart<String,Number>(xAxis, yAxis);
	private Project currentProject;

	
	public LineGraph(Project currentProject) {
		xAxis.setLabel("Date");
		yAxis.setLabel("Time worked");
		lineChart.setTitle(currentProject.getName());
		this.currentProject = currentProject;
		XYChart.Series series = new XYChart.Series();
		
		for(DayLog dayLog: currentProject.getDayLogs()) {
			String date = dayLog.getDate().toString();
			Number totalMins = dayLog.getTotalMins();
			series.getData().add(new XYChart.Data(date, totalMins));
			System.out.println("Added: " + date + " | " + totalMins);
		}
		lineChart.getData().add(series);
		lineChart.setPrefSize(367, 375);
		lineChart.setLegendVisible(false);
	}
	
	public LineChart<String,Number> getChart() {
		return lineChart;
	}
	
	
}
