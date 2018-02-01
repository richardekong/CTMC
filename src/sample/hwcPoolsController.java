package sample;

import com.richy.Rate;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.awt.*;
import java.awt.Polygon;
import java.awt.ScrollPane;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
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
import com.richy.RungeKutta;
import com.richy.Derivatives;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class hwcPoolsController implements Initializable {
    // instance variable declaration
    @FXML
    private VBox root;

    @FXML
    private HBox Hbox1;

    @FXML
    private TitledPane ratePane;

    @FXML
    private VBox vboxRate;

    @FXML
    private HBox hbox1a;

    @FXML
    private Label lambdalabel;

    @FXML
    private Slider lambdaslider;

    @FXML
    private TextField lambdavalue;

    @FXML
    private HBox Hbox1b;

    @FXML
    private Label mulabel;

    @FXML
    private Slider muslider;

    @FXML
    private TextField muvalue;

    @FXML
    private HBox Hbox1c;

    @FXML
    private Label gamalabel;

    @FXML
    private Slider gamaslider;

    @FXML
    private TextField gamavalue;

    @FXML
    private HBox hbox1d;

    @FXML
    private Label deltalabel1;

    @FXML
    private Slider deltaslider;

    @FXML
    private TextField deltavalue;

    @FXML
    private HBox hbox1d1;

    @FXML
    private Label timelabel11;

    @FXML
    private Slider timeslider;

    @FXML
    private TextField timeValue;

    @FXML
    private TitledPane finitegraph;

    @FXML
    private ScrollPane fintegraphscrollPane;

    @FXML
    private BorderPane finiteGraphPane;

    @FXML
    private HBox Hbox2;

    @FXML
    private Tab avalilability;

    @FXML
    private HBox hbox2a;

    @FXML
    private TitledPane AvTablePane;

    @FXML
    private TableView<CTMCAvailabilityTableModel> AvTableView;

    @FXML
    private TableColumn<CTMCAvailabilityTableModel, Integer> tAvCol;

    @FXML
    private TableColumn<CTMCAvailabilityTableModel, Double> Av;

    @FXML
    private TableColumn<CTMCAvailabilityTableModel, Double> colP0;

    @FXML
    private TableColumn<CTMCAvailabilityTableModel, Double> colP1;

    @FXML
    private TableColumn<CTMCAvailabilityTableModel, Double> colP5;

    @FXML
    private TableColumn<CTMCAvailabilityTableModel, Double> colP6;

    @FXML
    private TitledPane AvPlotTitle;

    @FXML
    private BorderPane AvPlotPane;

    @FXML
    private Tab downtime;

    @FXML
    private HBox hbox2b;

    @FXML
    private TitledPane DtTablePane;

    @FXML
    private TableView<CTMCDownTimeTableModel> DtTableView;

    @FXML
    private TableColumn<CTMCDownTimeTableModel, Integer> DtTCol;

    @FXML
    private TableColumn<CTMCDownTimeTableModel, Double> Dt1;

    @FXML
    private TableColumn<CTMCDownTimeTableModel, Double> colP2;

    @FXML
    private TableColumn<CTMCDownTimeTableModel, Double> colP3;

    @FXML
    private TableColumn<CTMCDownTimeTableModel, Double> colP4;

    @FXML
    private TableColumn<CTMCDownTimeTableModel, Double> colP7;

    @FXML
    private TableColumn<CTMCDownTimeTableModel, Double> colP8;

    @FXML
    private TableColumn<CTMCDownTimeTableModel, Double> colP9;

    @FXML
    private TitledPane DtPlotTitle;

    @FXML
    private BorderPane DtplotPane1;

    @FXML
    private HBox hbox3;

    @FXML
    private HBox Hbox3a;

    @FXML
    private Button btn2d;

    @FXML
    private ImageView img2d;

    @FXML
    private Button btn3d;

    @FXML
    private ImageView img3d;

    @FXML
    private Button clearbtn;

    @FXML
    private ImageView imgClear;

    @FXML
    private HBox hbox4;

    @FXML
    private ProgressBar progressBar;

    private DataModel dataModel;
    private Plot3D Av3dplot;
    private Plot3D Dt3dPlot;
    private Plot2D Av2dplot;
    private Plot2D Dt2dplot;
    private String[] tableHeader = new String[]{"t", "t", "Av", "Dt", "p0", "p1", "p2", "p3", "p4", "p5", "p6", "p7", "p8", "p9"};
    private Text rateLabels[];
    private Text gamaValuex2;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //add listeners to sliders
        lambdaslider.valueProperty().addListener((obs, oldValue, newValue) -> {
            lambdavalue.setText(String.format("%.3f", newValue.doubleValue()));
        });
        muslider.valueProperty().addListener((obs, oldValue, newValue) -> {
            muvalue.setText(String.format("%.3f", newValue.doubleValue()));
        });
        deltaslider.valueProperty().addListener((obs, oldValue, newValue) -> {
            deltavalue.setText(String.format("%.3f", newValue.doubleValue()));
        });
        gamaslider.valueProperty().addListener((obs, oldValue, newValue) -> {
            gamavalue.setText(String.format("%.3f", newValue.doubleValue()));
            });
        //set gamaValuex2 to the string representation of gama value multiplied by 2
        gamaValuex2=new Text("");
        gamavalue.textProperty().addListener((obs,oldValue,newValue)->{
            try {
                gamaValuex2.setText(String.format("%.3f", Double.parseDouble(newValue) * 2));
            }catch (NumberFormatException e){}
        });
        timeslider.valueProperty().addListener((obs, oldValue, newValue) -> {
            timeValue.setText(String.format("%.3f", newValue.doubleValue()));
        });

        //set up header for each table column
        setHeaders(tableHeader, tAvCol, DtTCol, Av, Dt1, colP0, colP1, colP2, colP3, colP4, colP5, colP6, colP7, colP8, colP9);
        //draw the finite graph
        Group finiteGraph = drawFiniteGraph();
        finiteGraphPane.setCenter(finiteGraph);


    }

    public void setHeaders(String header[], TableColumn... cols) {
        for (int i = 0; i < cols.length; i++) {
            cols[i].setCellValueFactory(new PropertyValueFactory<>(header[i]));
        }
    }

    public void loadTable()  {
        try {
            //get values from the input
            double
                    l = Double.parseDouble(lambdavalue.getText()),
                    m = Double.parseDouble(muvalue.getText()),
                    d = Double.parseDouble(deltavalue.getText()),
                    g = Double.parseDouble(gamavalue.getText()),
                    t = Double.parseDouble(timeValue.getText());
            //set rates for differential equations
            Rate rates = new Rate(l, m, d, g);
            double p[] = new double[]{1.0, .0, .0, .0, .0, .0, .0, .0, .0, .0};
            //create slope values for the derivatives
            Derivatives derivatives = new Derivatives(l, m, d, g);
            double steps = 0.01;
            //Create an object of Runge-kutta 4th order
            RungeKutta rk = new RungeKutta(p, t, steps, derivatives);
            //load rungekutta data into data model
            this.dataModel = new DataModel(rk,rates);
            //set table data to corresponding table view
            AvTableView.setItems(this.dataModel.getAvTableData());
            DtTableView.setItems(this.dataModel.getDtTableData());

        } catch (NullPointerException e) {
        } catch (NumberFormatException e) {
        }catch (NoSuchElementException e){}
    }

    @FXML
    public void plotData3d(ActionEvent event) {
        //clear previously existing data
        clear(event);
        //create an array of 3d plot objects to be plotted
        Plot3D[] plot3Ds = new Plot3D[]{Av3dplot, Dt3dPlot};
        Platform.runLater(() -> {
            //load table with required data
            loadTable();
            //declare the title for the graph
            String Avtitle = "Availability of IaaS";
            String DtTitle = "Downtime of IaaS";
            //create a task to plot 3d graphs
            Task plot3DGraphs = new Task<Void>() {
                @Override
                protected Void call() {
                        progressBar.setVisible(true);//show the progress bar
                        int plots, maxTasks = plot3Ds.length; //calculate work and total work as the current plot and plots to be created
                        for (plots = 0; plots <= maxTasks; plots++) {
                            if (plots == 0) { //if it's the first plot object
                                //plot availability graph
                                Av3dplot = new Plot3D(getDataModel().getAvData(), AvPlotPane, Avtitle, new Color[]{new Color(66, 66, 244)});
                                Av3dplot.getPlotCanvas().setAxisLabels("t", "Av", "f(Av,t)");
                            } else { //if it's the second plot object
                                //plot downtime graph
                                Dt3dPlot = new Plot3D(getDataModel().getDtData(), DtplotPane1, DtTitle, new Color[]{new Color(244, 66, 66)});
                                Dt3dPlot.getPlotCanvas().setAxisLabels("t", "Dt", "f(Dt,t)");
                            }
                            updateProgress(plots, maxTasks); //update the task progress so far
                        }
                        progressBar.setVisible(false);//hide the progress bar
                    return null;
                }
            };
            progressBar.progressProperty().bind(plot3DGraphs.progressProperty());//bind progress bar's property to task's progrss property
            new Thread(plot3DGraphs).start();// start executing the task in a new thread
        });
    }

    @FXML
    public void plotData2D(ActionEvent event) {
        //clear prevously existing data
        clear(event);

            Platform.runLater(() -> {
                //specify title for the plot
                String AvTitle = "Availability of IaaS";
                String DtTitle = "Downtime of IaaS";
                //load data
                loadTable();
                try {
                    //plot availability graph
                    Av2dplot = new Plot2D(dataModel.getAvData(), AvPlotPane, AvTitle, "Av2dplot");
                    Av2dplot.getX().setLabel("time");
                    Av2dplot.getY().setLabel("Availability");
                    //plot downtime graph
                    Dt2dplot = new Plot2D(dataModel.getDtData(), DtplotPane1, DtTitle, "Dt2dplot");
                    Dt2dplot.getX().setLabel("time");
                    Dt2dplot.getY().setLabel("Downtime");
                } catch (NullPointerException |NoSuchElementException e) {
                    Alert alert=new Alert(Alert.AlertType.ERROR, "Missing Information!");
                    alert.show();
                }
            });
    }

    @FXML
    public void clear(ActionEvent event) {
        Platform.runLater(() -> {
            //clear all data in datamodel object
            if (dataModel != null) {
                dataModel.clearLists(AvTableView.getItems(), DtTableView.getItems());
                dataModel.clearData();
                AvPlotPane.getChildren().clear();
                DtplotPane1.getChildren().clear();

            }
        });
    }

    //draw finte graph of the hot, warm and cold pool
    public Group drawFiniteGraph() {
        //declare properties
        double w = 300.0, h = 300.0, r = 15.0, sp = r * 5, oft = sp / 20, fs = r / 1.5;
        //create a group node
        Group g = new Group();
        g.prefWidth(w);
        g.prefHeight(h);
        javafx.scene.paint.Color stateColor = javafx.scene.paint.Color.color(0.05, 0.8, 0.05);
        //create an array of 10 circle
        Circle states[] = {
                new Circle(w / 2, h / 2, r, stateColor), new Circle(w / 2 + sp, h / 2, r, stateColor),
                new Circle(w / 2 + (2 * sp), h / 2, r, stateColor), new Circle(w / 2, h / 2 + sp, r, stateColor),
                new Circle(w / 2 + sp, h / 2 + sp, r, stateColor), new Circle(w / 2 + (3 * sp), h / 2, r, stateColor),
                new Circle(w / 2 + (4 * sp), h / 2, r, stateColor), new Circle(w / 2 + (5 * sp), h / 2, r, stateColor),
                new Circle(w / 2 + (3 * sp), h / 2 + sp, r, stateColor), new Circle(w / 2 + (4 * sp), h / 2 + sp, r, stateColor)
        };

        //create an array of state labels
        Text stateLabels[] = new Text[10];
        int vm0 = 2, vm1 = 2, vm2 = 2, vm3 = 2;
        for (int i = 0; i < states.length; i++) {
            //set up labels
            if (i >= 0 && i <= 2) {
                stateLabels[i] = new Text(states[i].getCenterX() - 2 * oft, states[i].getCenterY() + oft, vm0 + "," + 1);
                vm0--;
            } else if (i > 2 && i <= 4) {
                stateLabels[i] = new Text(states[i].getCenterX() - 2 * oft, states[i].getCenterY() + oft, vm1 + "," + 0);
                vm1--;
            } else if (i >= 5 && i <= 7) {
                stateLabels[i] = new Text(states[i].getCenterX() - 2 * oft, states[i].getCenterY() + oft, vm2 + "," + 1);
                vm2--;
            } else {
                stateLabels[i] = new Text(states[i].getCenterX() - 2 * oft, states[i].getCenterY() + oft, vm3 + "," + 0);
                vm3--;
            }
            stateLabels[i].setFont(javafx.scene.text.Font.font(fs));
            states[i].setStroke(stateColor);
            //add the states and labels
            g.getChildren().addAll(stateLabels[i], states[i]);
            stateLabels[i].toFront();
        }
        //create an array of texts for rate labels
        rateLabels = new Text[]{
                new Text(w / 2 + sp / 2, h / 2 - r / 4, "2γ"), new Text(w / 2 + (3 * sp / 2), h / 2 - r / 4, "γ"), new Text(w / 2 + (7 * sp / 2), h / 2 - r / 4, "2γ"),
                new Text(w / 2 + (9 * sp / 2), h / 2 - r / 4, "γ"), new Text(w / 2 + sp / 2, h / 2 + (r * 0.75) + r / 2, "δ"), new Text(w / 2 + (3 * sp / 2), h / 2 + (r * 0.75) + r / 2, "δ"),
                new Text(w / 2 + (7 * sp / 2), h / 2 + (r * 0.75) + r / 2, "δ"), new Text(w / 2 + (9 * sp / 2), h / 2 + (r * 0.75) + r / 2, "δ"), new Text(w / 2 - (r * 0.75), h / 2 + sp / 2, "λ"),
                new Text(w / 2 + (r * 0.75), h / 2 + sp / 2, "µ"), new Text(w / 2 + sp - (r * 0.75), h / 2 + sp / 2, "λ"), new Text(w / 2 + sp + (r * 0.75), h / 2 + sp / 2, "µ"),
                new Text(w / 2 + (3 * sp) - (r * 0.75), h / 2 + sp / 2, "λ"), new Text(w / 2 + (3 * sp) + (r * 0.75), h / 2 + sp / 2, "µ"), new Text(w / 2 + (4 * sp) - (r * 0.75), h / 2 + sp / 2, "λ"),
                new Text(w / 2 + (4 * sp) + (r * 0.75), h / 2 + sp / 2, "µ"),
                //rate labels for curved path
                new Text(w / 2 + sp / 2, h / 2 + (5 * sp / 2), "γ"), new Text(w / 2 + sp / 2, h / 2 + (2 * sp) + r / 4, "γ"), new Text(w / 2 + (5 * sp / 2), h / 2 - sp - r / 4, "γ"),
                new Text(w / 2 + (2 * sp), h / 2 - sp + r, "γ"), new Text(h / 2 + (2 * sp), h / 2 - sp / 2, "γ"), new Text(w / 2 + (5 * sp / 2), h / 2 - sp / 4, "γ"),
                new Text(w / 2 + (2 * sp), h / 2 + sp, "γ"), new Text(w / 2 + (3 * sp / 2), h / 2 + (2 * sp) - r, "γ")

        };
        //add the rate text to the group layout
        for (int i = 0; i < rateLabels.length; i++) {

            if (rateLabels[i].getText().equals("λ")) {
                rateLabels[i].textProperty().bind(lambdavalue.textProperty());
            } else if (rateLabels[i].getText().equals("µ")) {
                rateLabels[i].textProperty().bind(muvalue.textProperty());
            } else if (rateLabels[i].getText().equals("γ")) {
                rateLabels[i].textProperty().bind(gamavalue.textProperty());
            } else if (rateLabels[i].getText().equals("δ")) {
                rateLabels[i].textProperty().bind(deltavalue.textProperty());
            } else if(rateLabels[i].getText().equals("2γ")){
                rateLabels[i].textProperty().bind(gamaValuex2.textProperty());
            }

        rateLabels[i].setFont(Font.font(fs / 1.5));
        g.getChildren().add(rateLabels[i]);
    }

        //create an array of horizontal straight line
        MoveTo mh2 []={
                new MoveTo(states[0].getCenterX()+(r*0.75),states[0].getCenterY()-(r*0.75)), new MoveTo(states[1].getCenterX()+(r*0.75),states[1].getCenterY()-(r*0.75)),
                new MoveTo(states[5].getCenterX()+(r*0.75),states[5].getCenterY()-(r*0.75)), new MoveTo(states[6].getCenterX()+(r*0.75),states[6].getCenterY()-(r*0.75)),
                new MoveTo(states[7].getCenterX()-(r*0.75),states[7].getCenterY()+(r*0.75)), new MoveTo(states[6].getCenterX()-(r*0.75),states[6].getCenterY()+(r*0.75)),
                new MoveTo(states[2].getCenterX()-(r*0.75),states[2].getCenterY()+(r*0.75)), new MoveTo(states[1].getCenterX()-(r*0.75),states[1].getCenterY()+(r*0.75))
        };
        //create an array of horizontal lines
        HLineTo Lines []={
                new HLineTo(states[1].getCenterX()-(r*0.75)),new HLineTo(states[2].getCenterX()-(r*0.75)),
                new HLineTo(states[6].getCenterX()-(r*0.75)),new HLineTo(states[7].getCenterX()-(r*0.75)),
                new HLineTo(states[6].getCenterX()+(r*0.75)),new HLineTo(states[5].getCenterX()+(r*0.75)),
                new HLineTo(states[1].getCenterX()+(r*0.75)),new HLineTo(states[0].getCenterX()+(r*0.75))
        };
        //set up a horizontal straight path
        Path hStraightPath [] =new Path[8];
        for (int i=0; i<hStraightPath.length;i++){
            hStraightPath[i]=new Path();
            hStraightPath[i].getElements().addAll(mh2[i],Lines[i]);
            g.getChildren().add(hStraightPath[i]);
        }
        //create an array of vertical
        MoveTo mv2 []={
                new MoveTo(states[0].getCenterX()-(r*0.75),states[0].getCenterY()+(r*0.75)),new MoveTo(states[3].getCenterX()+(r*0.75),states[3].getCenterY()-(r*0.75)),
                new MoveTo(states[1].getCenterX()-(r*0.75),states[1].getCenterY()+(r*0.75)),new MoveTo(states[4].getCenterX()+(r*0.75),states[4].getCenterY()-(r*0.75)),
                new MoveTo(states[5].getCenterX()-(r*0.75),states[5].getCenterY()+(r*0.75)),new MoveTo(states[8].getCenterX()+(r*0.75),states[8].getCenterY()-(r*0.75)),
                new MoveTo(states[6].getCenterX()-(r*0.75),states[6].getCenterY()+(r*0.75)),new MoveTo(states[9].getCenterX()+(r*0.75),states[9].getCenterY()-(r*0.75)),
        };
        VLineTo vLines[]={
                new VLineTo(states[3].getCenterY()-(r*0.75)),new VLineTo(states[0].getCenterY()+(r*0.75)),new VLineTo(states[4].getCenterY()-(r*0.75)),
                new VLineTo(states[1].getCenterY()+(r*0.75)),new VLineTo(states[8].getCenterY()-(r*0.75)),new VLineTo(states[5].getCenterY()+(r*0.75)),
                new VLineTo(states[9].getCenterY()-(r*0.75)),new VLineTo(states[6].getCenterY()+(r*0.75))
        };

        //set up a vertical straight path
        Path vStgraightPath[]=new Path[8];
        for(int i=0;i<vStgraightPath.length;i++){
            vStgraightPath[i]=new Path();
            vStgraightPath[i].getElements().addAll(mv2[i],vLines[i]);
            vStgraightPath[i].setStroke(javafx.scene.paint.Color.color(0,0,0));
            g.getChildren().addAll(vStgraightPath[i]);
        }

        //create an array of curved transition
        Path curvedPath[]=new Path[8];
        MoveTo m1[]={new MoveTo(states[0].getCenterX()-r,states[0].getCenterY()),new MoveTo(states[0].getCenterX()-r,states[0].getCenterY()),
                new MoveTo(states[0].getCenterX()-r*0.25,states[0].getCenterY()-r), new MoveTo(states[0].getCenterX()-r*0.25,states[0].getCenterY()-r),
                new MoveTo(states[5].getCenterX()-r*0.25,states[5].getCenterY()-r), new MoveTo(states[5].getCenterX()-r*0.25,states[5].getCenterY()-r),
                new MoveTo(states[5].getCenterX()-r,states[5].getCenterY()),new MoveTo(states[5].getCenterX()-r,states[5].getCenterY())};

        CubicCurveTo curveTo[]={
                new CubicCurveTo(m1[0].getX(),m1[0].getY(),states[1].getCenterX()-(sp*2.5),sp*6,states[9].getCenterX(),states[9].getCenterY()+r),
                new CubicCurveTo(m1[1].getX(),m1[1].getY(),states[1].getCenterX()-(sp*2),sp*5,states[8].getCenterX(),states[8].getCenterY()+r),
                new CubicCurveTo(m1[2].getX(),m1[2].getY(),states[2].getCenterX()-r+sp/2,w/2-sp*2,states[7].getCenterX(),states[7].getCenterY()-r),
                new CubicCurveTo(m1[3].getX(),m1[3].getY(),states[2].getCenterX()+r+sp/2,w/2-sp*1.5,states[6].getCenterX(),states[6].getCenterY()-r),
                new CubicCurveTo(m1[4].getX(),m1[4].getY(),states[2].getCenterX()+r,states[2].getCenterY()-sp,states[1].getCenterX(),states[1].getCenterY()-r),
                new CubicCurveTo(m1[5].getX(),m1[5].getY(),states[2].getCenterX()+sp/2,states[2].getCenterY()-sp/2,states[2].getCenterX()+r*0.25,states[2].getCenterY()-r),
                new CubicCurveTo(m1[6].getX(),m1[6].getY(),m1[6].getX()-sp,m1[6].getY()+(2*sp),states[4].getCenterX(),states[4].getCenterY()+r),
                new CubicCurveTo(m1[7].getX(),m1[7].getY(),m1[7].getX()-sp,m1[7].getY()+(3*sp),states[3].getCenterX(),states[3].getCenterY()+r)};
        //add curves and moveto to curved path
        //create an array of arrows
        javafx.scene.shape.Polygon arrowCurvedPaths []={
                new javafx.scene.shape.Polygon(new double[]{m1[0].getX(),m1[0].getY(),m1[0].getX()-r/4,m1[0].getY()+r/4,m1[0].getX()+r/4,m1[0].getY()+r/4}),
                new javafx.scene.shape.Polygon(new double[]{m1[2].getX(),m1[2].getY(),m1[2].getX()+r/4,m1[2].getY()+r/4,m1[2].getX()+r/4,m1[2].getY()-r/4}),
                new javafx.scene.shape.Polygon(new double[]{m1[4].getX(),m1[4].getY(),m1[4].getX()-r/4,m1[4].getY()-r/4,m1[4].getX()-r/4,m1[4].getY()+r/4}),
                new javafx.scene.shape.Polygon(new double[]{m1[7].getX(),m1[7].getY(),m1[7].getX()-r/4,m1[7].getY()+r/4,m1[7].getX()+r/4,m1[7].getY()+r/4})};
        for(int i=0; i<arrowCurvedPaths.length;i++){
            arrowCurvedPaths[i].setFill(javafx.scene.paint.Color.color(0.8,0.2,0.2));
            g.getChildren().add(arrowCurvedPaths[i]);
        }
        //front arrows
        double arrowHieght=states[1].getCenterY()-(r*0.75), arrowHieght2=arrowHieght-r/4, arrowHieght3=arrowHieght+r/4;
        javafx.scene.shape.Polygon frontArrows[]={
                new javafx.scene.shape.Polygon(new double[]{Lines[0].getX(),arrowHieght,Lines[0].getX()-(r/4),arrowHieght2,Lines[0].getX()-(r/4),arrowHieght3}),
                new javafx.scene.shape.Polygon(new double[]{Lines[1].getX(),arrowHieght,Lines[1].getX()-(r/4),arrowHieght2,Lines[1].getX()-(r/4),arrowHieght3}),
                new javafx.scene.shape.Polygon(new double[]{Lines[2].getX(),arrowHieght,Lines[2].getX()-(r/4),arrowHieght2,Lines[2].getX()-(r/4),arrowHieght3}),
                new javafx.scene.shape.Polygon(new double[]{Lines[3].getX(),arrowHieght,Lines[3].getX()-(r/4),arrowHieght2,Lines[3].getX()-(r/4),arrowHieght3})
        };

        //back arrows
        double bArrowHeight=states[0].getCenterY()+(r*0.75), bArrowHeight2=bArrowHeight-r/4, bArrowHieght3=bArrowHeight+r/4;
        javafx.scene.shape.Polygon backArrows[]={
                new javafx.scene.shape.Polygon(new double[]{Lines[7].getX(),bArrowHeight,Lines[7].getX()+(r/4),bArrowHeight2,Lines[7].getX()+(r/4),bArrowHieght3}),
                new javafx.scene.shape.Polygon(new double[]{Lines[6].getX(),bArrowHeight,Lines[6].getX()+(r/4),bArrowHeight2,Lines[6].getX()+(r/4),bArrowHieght3}),
                new javafx.scene.shape.Polygon(new double[]{Lines[5].getX(),bArrowHeight,Lines[5].getX()+(r/4),bArrowHeight2,Lines[5].getX()+(r/4),bArrowHieght3}),
                new javafx.scene.shape.Polygon(new double[]{Lines[4].getX(),bArrowHeight,Lines[4].getX()+(r/4),bArrowHeight2,Lines[4].getX()+(r/4),bArrowHieght3}),
        };

        //down Arrow
        double xShift1=r*0.75, xShift3=xShift1+r/4, xShift2=xShift1-r/4, yShift=r/4;
        javafx.scene.shape.Polygon downArrows[]={
                new javafx.scene.shape.Polygon(new double[]{states[3].getCenterX()-xShift1,vLines[0].getY(),states[3].getCenterX()-xShift2,vLines[0].getY()-yShift,states[3].getCenterX()-xShift3,vLines[0].getY()-yShift}),
                new javafx.scene.shape.Polygon(new double[]{states[4].getCenterX()-xShift1,vLines[0].getY(),states[4].getCenterX()-xShift2,vLines[0].getY()-yShift,states[4].getCenterX()-xShift3,vLines[0].getY()-yShift}),
                new javafx.scene.shape.Polygon(new double[]{states[8].getCenterX()-xShift1,vLines[0].getY(),states[8].getCenterX()-xShift2,vLines[0].getY()-yShift,states[8].getCenterX()-xShift3,vLines[0].getY()-yShift}),
                new javafx.scene.shape.Polygon(new double[]{states[9].getCenterX()-xShift1,vLines[0].getY(),states[9].getCenterX()-xShift2,vLines[0].getY()-yShift,states[9].getCenterX()-xShift3,vLines[0].getY()-yShift})

        };
        //up Arrow
        javafx.scene.shape.Polygon upArrows[]={
                new javafx.scene.shape.Polygon(new double []{states[0].getCenterX()+xShift1,vLines[1].getY(),states[0].getCenterX()+(r*0.75)-r/4,vLines[1].getY()+yShift,states[0].getCenterX()+(r*0.75)+r/4,vLines[1].getY()+yShift}),
                new javafx.scene.shape.Polygon(new double []{states[1].getCenterX()+xShift1,vLines[1].getY(),states[1].getCenterX()+(r*0.75)-r/4,vLines[1].getY()+yShift,states[1].getCenterX()+(r*0.75)+r/4,vLines[1].getY()+yShift}),
                new javafx.scene.shape.Polygon(new double []{states[5].getCenterX()+xShift1,vLines[1].getY(),states[5].getCenterX()+(r*0.75)-r/4,vLines[1].getY()+yShift,states[5].getCenterX()+(r*0.75)+r/4,vLines[1].getY()+yShift}),
                new javafx.scene.shape.Polygon(new double []{states[6].getCenterX()+xShift1,vLines[1].getY(),states[6].getCenterX()+(r*0.75)-r/4,vLines[1].getY()+yShift,states[6].getCenterX()+(r*0.75)+r/4,vLines[1].getY()+yShift})
        };
        //add the arrows to the group
        for (int i=0; i<upArrows.length;i++){
            frontArrows[i].setFill(javafx.scene.paint.Color.color(0.8,0.2,0.2));
            backArrows[i].setFill(javafx.scene.paint.Color.color(0.8,0.2,0.2));
            downArrows[i].setFill(javafx.scene.paint.Color.color(0.8,0.2,0.2));
            upArrows[i].setFill(javafx.scene.paint.Color.color(0.8,0.2,0.2));
            g.getChildren().addAll(frontArrows[i],backArrows[i],upArrows[i],downArrows[i]);
        }
        //add curved path
        for (int i=0;i<curvedPath.length;i++){
            curvedPath[i]=new Path();
            curvedPath[i].setStroke(javafx.scene.paint.Color.color(0,0,0));
            curvedPath[i].getElements().addAll(m1[i],curveTo[i]);
            g.getChildren().add(curvedPath[i]);

        }
        return g;
    }
    public DataModel getDataModel(){
        return this.dataModel;
    }
    public void  setDataModel(DataModel value){this.dataModel=value;}
    public Tab getAvTab(){
        return this.avalilability;
    }
    public Tab getDtTab(){
        return this.downtime;
    }
    public TableView getAvTableView(){ return this.AvTableView; }
    public TableView getDtTableView(){ return this.DtTableView; }

    public TextField getLambdavalue() {
        return lambdavalue;
    }

    public void setLambdavalue(TextField lambdavalue) {
        this.lambdavalue = lambdavalue;
    }

    public TextField getMuvalue() {
        return muvalue;
    }

    public void setMuvalue(TextField muvalue) {
        this.muvalue = muvalue;
    }

    public TextField getGamavalue() {
        return gamavalue;
    }

    public void setGamavalue(TextField gamavalue) {
        this.gamavalue = gamavalue;
    }

    public TextField getDeltavalue() {
        return deltavalue;
    }

    public void setDeltavalue(TextField deltavalue) {
        this.deltavalue = deltavalue;
    }

    public TextField getTimeValue() {
        return timeValue;
    }

    public void setTimeValue(TextField timeValue) {
        this.timeValue = timeValue;
    }
}
