package sample;
import com.richy.Rate;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.*;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

public class rpdeController implements Initializable {
    //instance variable declaration
    @FXML
    private VBox rpderoot;
    @FXML
    private HBox rpdeHbox1;
    @FXML
    private TitledPane rpderatePane;
    @FXML
    private VBox vboxRate;
    @FXML
    private HBox rpdehbox1a;
    @FXML
    private Label rpdelambdalabel;
    @FXML
    private Slider rpdelambdaslider;
    @FXML
    private TextField rpdelambdavalue;
    @FXML
    private HBox rpdeHbox1b;
    @FXML
    private Label rpdemulabel;
    @FXML
    private Slider rpdeMuslider;
    @FXML
    private TextField rpdeMuvalue;
    @FXML
    private HBox rpdeHbox1b1;
    @FXML
    private Label rpdeTlabel;
    @FXML
    private Slider rpdeTslider;
    @FXML
    private TextField rpdeTvalue;
    @FXML
    private TitledPane rpdeFinitegraph;
    @FXML
    private BorderPane rpdeFiniteGraphPane;
    @FXML
    private HBox rpdeHbox2;
    @FXML
    private TabPane rpdeTabpane;
    @FXML
    private  Tab rpdeAvalilability;
    @FXML
    private HBox rpdeHbox2a;
    @FXML
    private TitledPane rpdeAvTablePane;
    @FXML
    private TableView<CTMCAvailabilityTableModel> rpdeAvTableView;
    @FXML
    private TableColumn<CTMCAvailabilityTableModel, Integer> rpdeAvTCol;
    @FXML
    private TableColumn<CTMCAvailabilityTableModel, Double> rpdeColAv;
    @FXML
    private TableColumn<CTMCAvailabilityTableModel, Double> rpdeColP0;
    @FXML
    private TitledPane rpdeAvPlotTitle;
    @FXML
    private BorderPane rpdeAvPlotPane;
    @FXML
    public  Tab rpdeDowntime;
    @FXML
    private HBox rpdeHbox2b;
    @FXML
    private TitledPane rpdeDtTablePane;
    @FXML
    private TableView<CTMCDownTimeTableModel> rpdeDtTableView;
    @FXML
    private TableColumn<CTMCDownTimeTableModel, Integer> rpdeDownTCol;
    @FXML
    private TableColumn<CTMCDownTimeTableModel, Double> rpdeColDt;
    @FXML
    private TableColumn<CTMCDownTimeTableModel, Double> rpdeColP1;
    @FXML
    private TitledPane rpdeDtPlotTitle;
    @FXML
    private BorderPane rpdeDtplotPane;
    @FXML
    private HBox rpdeHbox3;
    @FXML
    private TitledPane rpdeControlTitlePane;
    @FXML
    private HBox rpdeHbox3a;
    @FXML
    private Button rpdeBtn2d;
    @FXML
    private ImageView rpdeImg2d;
    @FXML
    private Button rpdeBtn3d;
    @FXML
    private ImageView rpdeImg3d;
    @FXML
    private Button rpdeClearbtn;
    @FXML
    private ImageView rpdeImgClear;
    @FXML
    private HBox rpdeHbox4;

    @FXML
    private ProgressBar rpdeProgBar;
    private String [] tableHeader=new String[]{"t","Av","p0","t","Dt","p1"};
    private Plot3D rpdeAvplot3D;
    private Plot3D rpdeDtPlot3D;
    private Plot2D rpdeAvplot2D;
    private Plot2D rpdeDtplot2D;
    private DataModel dataModel;
    @Override
    public void initialize(URL url, ResourceBundle rb){
     //add listener to sliders

        rpdelambdaslider.valueProperty().addListener((obs,oldValue,newValue)-> {
                rpdelambdavalue.setText(String.format("%.3f",newValue.doubleValue()));
        });

        rpdeMuslider.valueProperty().addListener((obs,oldValue,newValue)->{
            rpdeMuvalue.setText(String.format("%.3f",newValue.doubleValue()));
        });

        rpdeTslider.valueProperty().addListener((obs,oldValue,newValue)->{
            rpdeTvalue.setText(String.format("%.3f",newValue.doubleValue()));
        });
        //set up cell for each table column
        setHeaders(tableHeader, rpdeAvTCol, rpdeColAv, rpdeColP0, rpdeDownTCol, rpdeColDt,rpdeColP1);
        //set up finite graph
        Group finiteGraph=drawFiniteGraph();
        rpdeFiniteGraphPane.setCenter(finiteGraph);

    }
    public void setHeaders(String header[],TableColumn...cols){
        for (int i=0;i<cols.length;i++){
            cols[i].setCellValueFactory(new PropertyValueFactory<>(header[i]));
        }
    }
    //load availability and downtime data into the corresponding table views
    private void loadTable(){
        try{
            //get values from the input
            double
                    l=Double.parseDouble(rpdelambdavalue.getText()),
                    m=Double.parseDouble(rpdeMuvalue.getText()),
                    t=Double.parseDouble(rpdeTvalue.getText());
            //create an instance of Rate
            Rate rate=new Rate(l,m);
            //create a data model object
            dataModel=new DataModel(rate,t);
            //set data for table
            rpdeAvTableView.setItems(dataModel.getRpdeAvTableData());
            rpdeDtTableView.setItems(dataModel.getRpdeDtTableData());
        }catch(NullPointerException e){ }
        catch (IllegalArgumentException e1){ }
    }
    @FXML
    public void plotData3D(ActionEvent event){
        //clear previously existing data
        clear(event);
        //create an array of 3d plots
        Plot3D [] plot3Ds=new Plot3D[]{rpdeAvplot3D,rpdeDtPlot3D};
        Platform.runLater(()->{
            //load table data
            loadTable();
            //declare the title for the graph
            String Avtitle = "Availability of RPDE";
            String DtTitle = "Downtime of RPDE";
            //create a task to handle the 3d plots
            Task task=new Task<Void>(){
                protected Void call(){
                    //show progress bar
                    rpdeProgBar.setVisible(true);
                    int plot,  maxPlots=plot3Ds.length;
                    for (plot=0;plot<=maxPlots;plot++){
                        if (plot==0){
                            //setup the availability level plot
                            rpdeAvplot3D=new Plot3D(getDataModel().getRpdeAvData(),rpdeAvPlotPane,Avtitle, new Color(66, 66, 244));
                            rpdeAvplot3D.getPlotCanvas().setAxisLabels("t", "Av", "f(Av,t)");

                        }else{
                            //setup the downtime level plot
                            rpdeDtPlot3D=new Plot3D(getDataModel().getRpdeDtData(),rpdeDtplotPane,DtTitle, new Color(244, 66, 66));
                            rpdeDtPlot3D.getPlotCanvas().setAxisLabels("t","Dt","f(Dt,t)");
                        }
                        //update the progress property of the task
                        updateProgress(plot,maxPlots);
                    }
                    rpdeProgBar.setVisible(false);
                    return null;
                }
            };
            //update the progress bar
            rpdeProgBar.progressProperty().bind(task.progressProperty());
            new Thread(task).start();
        });
    }
    @FXML
    public void plotData2D(ActionEvent event) {
        //clear previously existing data
        clear(event);
        //create javafx application thread
        Platform.runLater(() -> {
                //specify the plot titles for both Availability and downtime plots
                String AvTitle = "Availability level of RPDE";
                String DtTitle = "Downtime level of RPDE";
                //load tables
                loadTable();
                //create an array of plot2D object
                //create a task object
                //plot the availability graph using the plot2D class
                try{
                rpdeAvplot2D = new Plot2D(dataModel.getRpdeAvData(), rpdeAvPlotPane, AvTitle, "rpdeAvplot2D");
                //set x axis label
                rpdeAvplot2D.getX().setLabel("Time");
                //set y axis label
                rpdeAvplot2D.getY().setLabel("Availability");
                //plot downtime graph using the plot2D class
                rpdeDtplot2D = new Plot2D(dataModel.getRpdeDtData(), rpdeDtplotPane, DtTitle, "rpdeDtplot2D");
                //set x axis label
                rpdeDtplot2D.getX().setLabel("Time");
                //set y axis label
                rpdeDtplot2D.getY().setLabel("Downtime");
            }catch(NoSuchElementException|NullPointerException e){
                    Alert alert=new Alert(Alert.AlertType.ERROR,"Missing information!");
                    alert.show();
                }
        });
    }
        @FXML
    public void clear(ActionEvent event){
        Platform.runLater(()->{
            //clear all data in datamodel object
            try{
                if (dataModel.getRpdeTData()!=null&&dataModel.getAvData()!=null&&
                        dataModel.getRpdeP0Data()!=null&& dataModel.getRpdeDtData()!=null&& dataModel.getRpdeP1Data()!=null){

                    dataModel.getRpdeTData().clear();
                    dataModel.getAvData().clear();
                    dataModel.getRpdeP0Data().clear();
                    dataModel.getRpdeDtData().clear();
                    dataModel.getRpdeP1Data().clear();
                    rpdeAvTableView.getItems().clear();
                    rpdeDtTableView.getItems().clear();
                    rpdeAvPlotPane.getChildren().clear();
                    rpdeDtplotPane.getChildren().clear();
                }
            }catch(NullPointerException e){}
        });
    }
    public Group drawFiniteGraph(){
        //create the root node
        Group root=new Group();
        double w=300.0f, h=300.0f, r=20.0f, s=5*r, of=s/20, fs=r/1.5;
        javafx.scene.paint.Color CircleColor= javafx.scene.paint.Color.color(0.05f,0.80f,0.05f);
        root.prefWidth(300.0f);
        root.prefHeight(300.0f);
        //create finiteGraph1 objects
        //create circular objects
        Circle c=new Circle(w/2,w/2,r, CircleColor);
        c.setStroke(CircleColor);
        Circle c1=new Circle(w/2+s,w/2,r, CircleColor);
        c.setStroke(CircleColor);
        c1.setStroke(CircleColor);
        //create text objects to insert in the circle
        Text t1=new Text(c.getCenterX()-of,c.getCenterY()+of,"1");
        Text t2=new Text(c1.getCenterX()-of,c1.getCenterY()+of,"2");
        Text t3=new Text(((h/2)+(s/2)),((w/2)+(s/2)),"µ=");
        Text t4=new Text(((h/2)+(s/2)),((w/2)-(s/2)),"λ=");
        Text t5=new Text(t3.getX()+(3*of),t3.getY(),"");
        Text t6=new Text(t4.getX()+(3*of),t4.getY(),"");
        t5.textProperty().bind(rpdeMuvalue.textProperty());
        t6.textProperty().bind(rpdelambdavalue.textProperty());
        t1.setFont(javafx.scene.text.Font.font(fs));
        t2.setFont(javafx.scene.text.Font.font(fs));
        t3.setFont(javafx.scene.text.Font.font(fs));
        t4.setFont(Font.font(fs));
        //create a path
        Path path=new Path();
        Path path1=new Path();
        //create a moveto object
        MoveTo moveto=new MoveTo(w/2+of,w/2+r);
        MoveTo moveto1=new MoveTo(w/2+of,w/2-r);
        //create arrows to indicate direction
        Polygon forward=new Polygon(new double[]{w/2+r/4,h/2+r,w/2+r/2,h/2+r-r/4,w/2+r/2,h/2+r+r/4});
        Polygon backward=new Polygon(new double[]{w/2+s-r/4,h/2-r,w/2+s-r/4-r/4,h/2-r-r/4,w/2+s-r/4-r/4,h/2-r+r/4});
        forward.setFill(javafx.scene.paint.Color.color(0.8,0.2,0.2));
        backward.setFill(javafx.scene.paint.Color.color(0.8,0.2,0.2));
        //create an upper arc
        CubicCurveTo curveToDown=new CubicCurveTo(w/2+of,(w/2+r),((h/2)+(s/2)),((w/2)+(s/2)),((w/2+s)-of),((h/2+r)));
        CubicCurveTo curveUp=new CubicCurveTo(w/2+of,w/2-r,((h/2)+(s/2)),((w/2)-(s/2)),((w/2+s)-of),((h/2)-r));
        //add curves and moveto object to the path
        path.getElements().addAll(moveto,curveToDown);
        path1.getElements().addAll(moveto1,curveUp);
        //add the finiteGraph to root node
        root.getChildren().addAll(c,c1,t1,t2,t3,t4,t5,t6,path,path1,forward,backward);
        return root;
    }
    public DataModel getDataModel(){
        return this.dataModel;
    }
    public void setDataModel(DataModel value){
        this.dataModel=value;}
    public   Tab getAvTab(){

        return this.rpdeAvalilability;
    }
    public  Tab getDtTab(){
        return this.rpdeDowntime;
    }
    public TableView getRpdeAvTableView(){
        return this.rpdeAvTableView;
    }
    public TableView getRpdeDtTableView(){
        return this.rpdeDtTableView;
    }

    public TextField getRpdelambdavalue() {
        return rpdelambdavalue;
    }

    public void setRpdelambdavalue(TextField rpdelambdavalue) {
        this.rpdelambdavalue = rpdelambdavalue;
    }

    public TextField getRpdeMuvalue() {
        return rpdeMuvalue;
    }

    public void setRpdeMuvalue(TextField rpdeMuvalue) {
        this.rpdeMuvalue = rpdeMuvalue;
    }

    public TextField getRpdeTvalue() {
        return rpdeTvalue;
    }

    public void setRpdeTvalue(TextField rpdeTvalue) {
        this.rpdeTvalue = rpdeTvalue;
    }
    public TextField getRpdeTValue(){
        return rpdeTvalue;
    }
    public void setRpdeTValue(TextField rpdeTValue){
        this.rpdeTvalue=rpdeTValue;
    }
}
