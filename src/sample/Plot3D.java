package sample;

import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import java.awt.Color;
import smile.math.Math;
import smile.plot.PlotCanvas;
import smile.plot.Surface;
import javafx.concurrent.Task;
import javax.swing.*;
import java.util.List;

public class Plot3D {
    //instance variable declaration
    private double x[],y[], lowerBounds[],upperBounds[];
    private double z[][];
    private PlotCanvas plotCanvas;
    private Surface surface;
    private SwingNode swingNode;

    //constructor
    public Plot3D(List<Double> data, BorderPane borderPane, String title,Color...plotColor){
        //initialize x, y and z
        this.x=new double[data.size()];
        this.y=new double[data.size()];
        this.z=new double[x.length][x.length];
        //load data point from data into x and y
        loadPoints(this.x,this.y,data);
        //set a z to a function of x and y
        this.z=f(this.x,this.y);
        //determine lower and upper bounds
        this.lowerBounds=new double[]{Math.min(this.x),Math.min(this.y),Math.min(this.z)};
        this.upperBounds=new double[]{Math.max(this.x),Math.max(this.y),Math.max(this.z)};
        //initialize plotcanvas and surface objects
        this.surface=new Surface(this.x,this.y,this.z,plotColor);
        this.plotCanvas=new PlotCanvas(lowerBounds,upperBounds);
        //set title for the plot
        this.plotCanvas.setTitle(title);

        SwingUtilities.invokeLater(()->{
            //add the surface to plotCanvas
            this.plotCanvas.add(this.surface);
        });
        //embed the canvass in swingNode
        this.swingNode=new SwingNode();
        embedPlotCanvas(this.swingNode,this.plotCanvas);
        //set swingNode on the pane
        Platform.runLater(()->{
            borderPane.setCenter(this.swingNode);
        });

    }
    //mutators and accessors
    public double[] getX() {
        return this.x;
    }

    public void setX(double[] x) {
        this.x = x;
    }

    public double[] getY() {
        return this.y;
    }

    public void setY(double[] y) {
        this.y = y;
    }

    public double[][] getZ() {
        return this.z;
    }

    public void setZ(double[][] z) {
        this.z = z;
    }

    public double[] getLowerBounds() {
        return this.lowerBounds;
    }

    public void setLowerBounds(double[] lowerBounds) {
        this.lowerBounds = lowerBounds;
    }

    public double[] getUpperBounds() {
        return this.upperBounds;
    }

    public void setUpperBounds(double[] upperBounds) {
        this.upperBounds = upperBounds;
    }

    public PlotCanvas getPlotCanvas() {
        return this.plotCanvas;
    }

    public void setPlotCanvas(PlotCanvas plotCanvas) {
        this.plotCanvas = plotCanvas;
    }

    public Surface getSurface() {
        return this.surface;
    }

    public void setSurface(Surface surface) {
        this.surface = surface;
    }

    public SwingNode getSwingNode() {
        return this.swingNode;
    }

    public void setSwingNode(SwingNode swingNode) {
        this.swingNode = swingNode;
    }
    private void loadPoints(double[]x,double [] y,List<Double>data){
        if(!data.isEmpty()){
            for (int i=0;i<data.size();i++){
                x[i]=i;
                y[i]=data.get(i);
            }
        }
    }
    private double[][] f(double[]x,double [] y){
        //declare a 2d array to hold fucntion of x and y
        double z[][]=new double[x.length][y.length];
                for (int i=0;i<x.length;i++){
                    for (int j=0;j<y.length;j++){
                        z[j][i]=x[i]*y[i];
                    }
                }
        return z;
    }
    private void embedPlotCanvas(SwingNode sw,PlotCanvas plt){
        SwingUtilities.invokeLater(()->{
            sw.setContent(plt);
        });
    }
}
