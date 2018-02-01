package sample;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class CTMCDownTimeTableModel {
        //instance variable declaration
        private IntegerProperty t=new SimpleIntegerProperty();
        private DoubleProperty
                Dt=new SimpleDoubleProperty(),
                p1=new SimpleDoubleProperty(),
                p2=new SimpleDoubleProperty(),
                p3=new SimpleDoubleProperty(),
                p4=new SimpleDoubleProperty(),
                p7=new SimpleDoubleProperty(),
                p8=new SimpleDoubleProperty(),
                p9=new SimpleDoubleProperty();
        private SimpleDoubleProperty [] cols;
        //constructor for the rpde downtime level table
        public CTMCDownTimeTableModel(int t, double Dt, double p1) {
            this.t.set(t);
            this.Dt.set(Dt);
            this.p1.set(p1);
        }

    //constructor for hwcpool  downtime level
    public CTMCDownTimeTableModel(int t, double Dt, double p2, double p3, double p4, double p7, double p8, double p9){
        this.t.set(t);
        this.Dt.set(Dt);
        this.p2.set(p2);
        this.p3.set(p3);
        this.p4.set(p4);
        this.p7.set(p7);
        this.p8.set(p8);
        this.p9.set(p9);
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

        //create mutators and accessors
        public double getDt(){
            return this.Dt.get();
        }
        public DoubleProperty Dt(){
            return Dt;
        }
        public void setDt(double value){
            this.Dt.set(value);
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
        //create mutators and accessors for p2
        public double getP2(){
            return this.p2.get();
        }
        public DoubleProperty p2(){
            return p2;
        }
        public void setP2(double value){
            this.p2.set(value);
        }
        //create mutators and accessors for p3
        public double getP3(){
            return this.p3.get();
        }
        public DoubleProperty p3(){
            return p3;
        }
        public void setP3(double value){
            this.p3.set(value);
        }
        //create mutators and accessors for p4
        public double getP4(){
            return this.p4.get();
        }
        public DoubleProperty p4(){
            return p4;
        }
        public void setP4(double value){
            this.p4.set(value);
        }

        //create mutators and accessors for p7
        public double getP7(){
            return this.p7.get();
        }
        public DoubleProperty p7(){
            return p7;
        }
        public void setP7(double value){
            this.p7.set(value);
        }
        //create mutators and accessors for p8
        public double getP8(){
            return this.p8.get();
        }
        public DoubleProperty p8(){
            return p8;
        }
        public void setP8(double value){
            this.p8.set(value);
        }
        //create mutators and accessors for p9
        public double getP9(){
            return this.p9.get();
        }
        public DoubleProperty p9(){
            return p9;
        }
        public void setP9(double value){
            this.p9.set(value);
        }
    }


