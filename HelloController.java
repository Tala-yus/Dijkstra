package edu.birzeit.algo.dijkstra.dijkstraalgorithm;

import edu.birzeit.algo.dijkstra.dijkstraalgorithm.djkstraUtils.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Pattern;

public class HelloController implements Initializable {

    private double x = 0;
    private double y = 0;


    public static HashMap<String, Building> buildingMap = new HashMap<>();

    private final List<Circle> buildingPin = new ArrayList<>();
    @FXML
    private ComboBox<Building> sourceComboBox;

    @FXML
    private ComboBox<Building> destinationComboBox;

    @FXML
    private Group mapAreaGroup;

    @FXML
    private ImageView mapImg;

    @FXML
    private AnchorPane mapPane;

    @FXML
    private TextArea pathTextArea;
    @FXML
    private TextField distanceTF;

    private final DialogBox alertBox = new DialogBox();

    ArrayList<Circle> pinList = new ArrayList<>();

    Graph buildingGraph = new Graph();




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void importCountiesBtnClicked(ActionEvent event) {
        readDataFile();
    }

    private void readDataFile() {

        try {

            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Files", "*.csv", "*.txt"));
            File fileToRead = fc.showOpenDialog(null);
            DecimalFormat df = new DecimalFormat("#.##");
            if (fileToRead != null) {

                BufferedReader sc = new BufferedReader(new FileReader(fileToRead));
                String line = sc.readLine();
                int numBuilding = Integer.parseInt(line.split(" ")[0].trim());
                int numPaths = Integer.parseInt(line.split(" ")[1].trim());

                for (int i = 0; i < numBuilding; i++) {
                    line = sc.readLine();
                    String[] buildingInfo = line.split(" ");
                   // System.out.println("this is a coordinates " + Arrays.toString(buildingInfo));
                    Building c = new Building(buildingInfo[0], Double.parseDouble(buildingInfo[1].trim()), Double.parseDouble(buildingInfo[2].trim()));
                    buildingMap.put(buildingInfo[0], c);
                    double x = Double.parseDouble(df.format(c.getMapped_x_coordinate()));
                    double y = Double.parseDouble(df.format(c.getMapped_y_coordinate()));
                    makeMapPoint(x, y, c.getBuildingName());
                    sourceComboBox.getItems().add(c);
                    destinationComboBox.getItems().add(c);

                    buildingGraph.addBuilding(c);
                }
                for (int i = 0; i < numPaths; i++) {
                    line = sc.readLine();
                    String[] buildingPaths = line.split(" ");
                    System.out.println(line.charAt(0));
                    System.out.println(buildingPaths);
                    System.out.println("building path " + Arrays.toString(buildingPaths));

                    double distance = Double.parseDouble(df.format(Double.parseDouble(buildingPaths[2].trim())));
                    Building c1 = buildingMap.get(buildingPaths[0].trim());
                    Building c2 = buildingMap.get(buildingPaths[1].trim());
                    buildingGraph.addEdge(c1, c2, distance);
                    buildingGraph.addEdge(c2, c1, distance);
                }
                sc.close();
            }

            printXY();
        } catch (Exception e) {
            alertBox.displayPopUp("Please Check Your File Format And Try Again !", "Warning!", 2);
        }
    }

    @FXML
    void findPathBtnClicked(ActionEvent event) {

        DecimalFormat df = new DecimalFormat("#.##");

        if (sourceComboBox.getSelectionModel().getSelectedItem() != null && destinationComboBox.getSelectionModel().getSelectedItem() != null) {
            clearUI();
            DijkstraAlgo dijkstraAlgo = new DijkstraAlgo();
            List<Building> visited = dijkstraAlgo.getShortestPath(sourceComboBox.getSelectionModel().getSelectedItem(),
                    destinationComboBox.getSelectionModel().getSelectedItem(), buildingGraph);
            if (visited != null) {

                pathTextArea.appendText("Source : " + visited.get(0) + '\n');
                pathTextArea.appendText("Destination : " + visited.get(visited.size() - 1) + '\n');
                pathTextArea.appendText("Path from source to destination:" + '\n');
                for (int i = 0; i < visited.size() - 1; i++) {
                    Building current = visited.get(i);
                    Building next = visited.get(i + 1);
                    double x = buildingGraph.getDistances().get(next) - buildingGraph.getDistances().get(current);
                    pathTextArea.appendText("==> " + current.getBuildingName() + "==> " + next.getBuildingName() + " " + "( " +df.format(x) + " )"+'\n') ;
                    paintImage(current, next);

                }
                distanceTF.setText(buildingGraph.getDistances().get(destinationComboBox.getValue()) + " M");
                sourceComboBox.setValue(null);
                destinationComboBox.setValue(null);
            } else {
                alertBox.displayPopUp("No path ", "warning ", 2);
            }

        } else
            alertBox.displayPopUp("Please Choose Buildings", "Warning !", 2);
    }

    private void paintImage(Building c1, Building c2) {

        Line line = new Line();
        line.setStartX(c1.getMapped_x_coordinate());
        line.setStartY(c1.getMapped_y_coordinate());
        line.setFill(Color.BLACK);
        line.setSmooth(true);
        line.setStroke(Color.BLACK);
        line.setStrokeWidth(2);
        line.setEndX(c2.getMapped_x_coordinate());
        line.setEndY(c2.getMapped_y_coordinate());

        mapPane.getChildren().add(line);

    }

    private void makeMapPoint(double x, double y, String name) {


        Circle c = new Circle(5);
        pinList.add(c);
        c.setRadius(5);
        c.setFill(Color.web("#ff0000"));
        c.setStroke(Color.BLACK);
        c.setSmooth(true);
        Group g = new Group();
        if(!name.startsWith("Path")) {
            Label l = new Label(name);
            l.setFont(Font.font("System",FontWeight.BOLD,9));
            l.setTextFill(Color.BLACK);
             g = new Group(c,l);
        }
        else {
        g = new Group(c);

        }
        g.setLayoutX(x);
        g.setLayoutY(y);
        mapPane.getChildren().add(g);
        for (Circle circle : pinList) {
            circle.setFill(Color.RED);
        }
        c.setOnMouseClicked(e -> {

            if (sourceComboBox.getValue() == null) {
                sourceComboBox.setValue(buildingMap.get(name));
                c.setFill(Color.LIMEGREEN);

            } else if (destinationComboBox.getValue() == null) {
                c.setFill(Color.LIMEGREEN);
                destinationComboBox.setValue(buildingMap.get(name));
            }

        });

        buildingPin.add(c);
    }


    private void clearUI() {

        for (Map.Entry<Building, Double> entry : buildingGraph.getDistances().entrySet()) {
            entry.setValue(Double.MAX_VALUE);
        }
        for (int i = 0; i < mapPane.getChildren().size(); i++) {
            System.out.println(mapPane.getChildren().get(i).getClass());
            if (mapPane.getChildren().get(i) instanceof javafx.scene.shape.Line) {
                mapPane.getChildren().get(i).setVisible(false);
            }
        }

        for (Circle c : buildingPin) {
            c.setFill(Color.RED);
        }

        pathTextArea.clear();
        distanceTF.clear();
    }

    @FXML
    void clearUIBtnClicked(ActionEvent event) {
        clearUI();
    }


    @FXML
    void draggedScene(MouseEvent event) {

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);

    }

    @FXML
    void pressedOnScene(MouseEvent event) {

        x = event.getSceneX();
        y = event.getSceneY();
    }

    @FXML
    void exitBtnClicked(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }


    public void printXY() {
        System.out.println(buildingMap.toString());
    }


}