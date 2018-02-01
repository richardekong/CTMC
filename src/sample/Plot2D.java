package sample;

import javafx.geometry.Side;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import smile.math.Math;

import java.util.Collections;
import java.util.List;

public class Plot2D {
    //declaration of instance variable
    private NumberAxis x;
    private NumberAxis y;
    private XYChart.Series series;
    private LineChart lineChart;
    private String Title;
    private String id;

    //create constructor
    public Plot2D(List<Double> data, BorderPane borderPane, String title,String id) {
        //initialize x and y axis

        this.x = new NumberAxis();
        this.y = new NumberAxis(Collections.min(data),Collections.max(data),0.001);
        //initialize line chart object
        lineChart = new LineChart<Number,Number>(this.x, this.y);
        //set up the chart
        this.id=id;
        this.Title = title;
        lineChart.setTitle(this.Title);
        lineChart.setId(this.id);
        lineChart.setCreateSymbols(false);
        lineChart.setLegendSide(Side.RIGHT);
        lineChart.setPrefSize(borderPane.getPrefWidth() - 1, borderPane.getPrefHeight() - 1);
        series = new XYChart.Series();
        //add data to series
        for (int i = 0; i < data.size(); i++) {
            series.getData().addAll(new XYChart.Data((Number) i, (Number) data.get(i)));
        }
        //plot data by adding the series to the line chart object
        lineChart.getData().add(series);
        //set line chart on the borderPane
        borderPane.setCenter(lineChart);
    }

    public NumberAxis getX() {
        return this.x;
    }

    public void setX(NumberAxis x) {
        this.x = x;
    }

    public NumberAxis getY() {
        return this.y;
    }

    public void setY(NumberAxis y) {
        this.y = y;
    }

    public XYChart.Series getSeries() {
        return series;
    }

    public void setSeries(XYChart.Series series) {
        this.series = series;
    }

    public LineChart getLineChart() {
        return this.lineChart;
    }

    public void setLineChart(LineChart lineChart) {
        this.lineChart = lineChart;
    }

    public String getTitle() {
        return this.Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }
    public String getId(){
        return this.id;
    }
    public void setId(String value){
        try{
            this.id=value;
        }catch (NullPointerException e){}
         catch(IllegalArgumentException e){}
    }

}
