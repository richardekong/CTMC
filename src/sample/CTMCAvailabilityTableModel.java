package sample;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class CTMCAvailabilityTableModel {
    //instance variable declaration
    private IntegerProperty t=new SimpleIntegerProperty();
    private DoubleProperty
            Av=new SimpleDoubleProperty(),
            p0=new SimpleDoubleProperty(),
            p1=new SimpleDoubleProperty(),
            p5=new SimpleDoubleProperty(),
            p6=new SimpleDoubleProperty();
    private SimpleDoubleProperty [] cols;
    //constructor for the rpde availability level table
    public CTMCAvailabilityTableModel(int t, double Av, double p0) {
        this.t.set(t);
        this.Av.set(Av);
        this.p0.set(p0);
    }

   // constructor for hwcpool availability level
    public CTMCAvailabilityTableModel(int t, double Av, double p0, double p1, double p5, double p6){
        this.t.set(t);
        this.Av.set(Av);
        this.p0.set(p0);
        this.p1.set(p1);
        this.p5.set(p5);
        this.p6.set(p6);
    }

    //create mutators and accessors for t
    public int getT(){
        return this.t.get();
    }
    public IntegerProperty t(){
        return t;
    }
    public void setT(int value){
        this.t.set(value);
    }

    //create mutators and accessors for Av
    public double getAv(){
        return this.Av.get();
    }
    public DoubleProperty Av(){
        return Av;
    }
    public void setAV(double value){
        this.Av.set(value);
    }

    //create mutators and accessors for p0
    public double getP0(){
        return this.p0.get();
    }
    public DoubleProperty p0(){
        return p0;
    }
    public void setP0(double value){
        this.p0.set(value);
    }
    //create mutators and accessors for p1
    public double getP1(){
        return this.p1.get();
    }
    public DoubleProperty p1(){
        return p1;
    }
    public void setP1(double value){
        this.p1.set(value);
    }

    //create mutators and accessors for p5
    public double getP5(){
        return this.p5.get();
    }
    public DoubleProperty p5(){
        return p5;
    }
    public void setP5(double value){
        this.p5.set(value);
    }
    //create mutators and accessors for p6
    public double getP6(){
        return this.p6.get();
    }
    public DoubleProperty p6(){
        return p6;
    }
    public void setP6(double value){
        this.p6.set(value);
    }

}
