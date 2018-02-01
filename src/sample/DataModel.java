package sample;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import com.richy.Rate;
import com.richy.RungeKutta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataModel{
    //instance variable declaration
    private List<Integer>
            tData=new ArrayList<>();
    private List<Integer> rpdeTData=new ArrayList<>();
    private List<Double> AvData=new ArrayList<>();
    private List<Double> DtData=new ArrayList<>();
    private List<Double> p0Data=new ArrayList<>();
    private List<Double> p1Data=new ArrayList<>();
    private List<Double> p2Data=new ArrayList<>();
    private List<Double> p3Data=new ArrayList<>();
    private List<Double> p4Data=new ArrayList<>();
    private List<Double> p5Data=new ArrayList<>();
    private List<Double> p6Data=new ArrayList<>();
    private List<Double> p7Data=new ArrayList<>();
    private List<Double> p8Data=new ArrayList<>();
    private List<Double> p9Data=new ArrayList<>();
    private List<Double> rpdeAvData=new ArrayList<>();
    private List<Double> rpdeDtData=new ArrayList<>();
    private List<Double> rpdeP0Data=new ArrayList<>();
    private List<Double> rpdeP1Data=new ArrayList<>();

    private ObservableList<CTMCAvailabilityTableModel> AvTableData;
    private ObservableList<CTMCDownTimeTableModel> DtTableData;
    private ObservableList<CTMCAvailabilityTableModel>rpdeAvTableData;
    private ObservableList<CTMCDownTimeTableModel>rpdeDtTableData;
    private double [][]rungekuttaValues;
    private double l, m, t;
    private Rate rateHwc;
    private Rate rateRpde;
    private Object [][] data;
    private Rate rate;
    //default constructor
    public DataModel(){}
    //class constructor for hwcpool
    public DataModel(RungeKutta rk, Rate rate){
        //obtain array of rungekutta values
        this.rungekuttaValues=rk.RungekuttaTable();
        //load the corresponding lists with contents of rungekutta Values
        loadList(this.rungekuttaValues,this.p0Data,this.p1Data,this.p2Data,this.p3Data,this.p4Data,
                this. p5Data,this.p6Data,this.p7Data,this.p8Data,this.p9Data);
        //calculate availaility and downtime
        this.AvData=(solveAvailability(AvData,p0Data,p1Data,p5Data, p6Data));
        this.DtData=(solveDownTime(AvData,DtData));
        //set up rate
        this.rateHwc=rate;
        //set up table data
        setHwcPoolAvTableData();
        setHwcPoolDtTableData();
    }
    //constructor for the rpde pool
    public DataModel(Rate rate, double t){
        this.rateRpde=rate;
        this.l=rate.getL();
        this.m=rate.getM();
        //load rpdeP0 and rpdeP1 lists
        for (int i=0;i<= t;i++){
            this.rpdeTData.add(i,i);
            this.rpdeP0Data.add(i,rpdeP0((double)i,l,m));
            this.rpdeP1Data.add(i,rpdeP1((double)i,l,m));
        }
        //set rpdeAvData and rpdeDtData to rpdeP0Data and rpdeP1 respectively
        this.rpdeAvData=this.rpdeP0Data;
        this.rpdeDtData=this.rpdeP1Data;
        //set data for table
        setRpdeAvTableData();
        setRpdeDtTableData();
    }
    //create getters and setters
    public List<Double> getAvData(){
        return this.AvData;
    }

    public List<Integer> gettData() {
        return this.tData;
    }

    public void settData(List<Integer> tData) {
        this.tData = tData;
    }

    public void setAvData(List<Double> avData) {
        this.AvData = avData;
    }

    public List<Double> getDtData() {
        return this.DtData;
    }

    public void setDtData(List<Double> dtData) {
        this.DtData = dtData;
    }

    public List<Double> getP0Data() {
        return this.p0Data;
    }

    public void setP0Data(List<Double> p0Data) {
        this.p0Data = p0Data;
    }

    public List<Double> getP1Data() {
        return this.p1Data;
    }

    public void setP1Data(List<Double> p1Data) {
        this.p1Data = p1Data;
    }

    public List<Double> getP2Data() {
        return this.p2Data;
    }

    public void setP2Data(List<Double> p2Data) {
        this.p2Data = p2Data;
    }

    public List<Double> getP3Data() {
        return this.p3Data;
    }

    public void setP3Data(List<Double> p3Data) {
        this.p3Data = p3Data;
    }

    public List<Double> getP4Data() {
        return this.p4Data;
    }

    public void setP4Data(List<Double> p4Data) {
        this.p4Data = p4Data;
    }

    public List<Double> getP5Data() {
        return this.p5Data;
    }

    public void setP5Data(List<Double> p5Data) {
        this.p5Data = p5Data;
    }

    public List<Double> getP6Data() {
        return this.p6Data;
    }

    public void setP6Data(List<Double> p6Data) {
        this.p6Data = p6Data;
    }

    public List<Double> getP7Data() {
        return this.p7Data;
    }

    public void setP7Data(List<Double> p7Data) {
        this.p7Data = p7Data;
    }

    public List<Double> getP8Data() {
        return this.p8Data;
    }

    public void setP8Data(List<Double> p8Data) {
        this.p8Data = p8Data;
    }

    public List<Double> getP9Data() {
        return this.p9Data;
    }

    public void setP9Data(List<Double> p9Data) {
        this.p9Data = p9Data;
    }

    public ObservableList<CTMCAvailabilityTableModel> getAvTableData() {
        return this.AvTableData;
    }

    public void setTableData(ObservableList<CTMCAvailabilityTableModel> tableData) {
        this.AvTableData = tableData;
    }

    public ObservableList<CTMCDownTimeTableModel> getDtTableData() {
        return this.DtTableData;
    }

    public void setDtTableData(ObservableList<CTMCDownTimeTableModel> dtTableData) {
        this.DtTableData = dtTableData;
    }
    public double[][] getRungekuttaValues() {
        return this.rungekuttaValues;
    }
    public void setRungekuttaValues(double[][] rungekuttaValues) {
        this.rungekuttaValues = rungekuttaValues;
    }
    //load data into the cells of each column (Array of lists)
    public void loadList(double data[][],List<Double>...list) {

        for(int i=0;i<data.length;i++) {
            for (int j = 0; j < data[i].length; j++) {
                list[j].add(i, data[i][j]);
            }
        }
    }
    public List<Double> solveAvailability(List<Double>Av,List<Double>...lists){
        Av=new ArrayList<Double>();
        int rows=lists[0].size();
        for(int i=0;i<rows;i++){//loop through the lists vertically
            double rowSum=0.0;
            for(int j=0;j<lists.length;j++){//loop through the lists horizontally
                rowSum+=lists[j].get(i);
            }
            Av.add(i,rowSum);
        }
        return Av;
    }

    public  List<Integer> getRpdeTData() {
        return this.rpdeTData;
    }

    public void setRpdeTData(List<Integer> rpdeTData) {
        this.rpdeTData = rpdeTData;
    }

    public   List<Double> getRpdeAvData() {
        return this.rpdeAvData;
    }

    public void setRpdeAvData(List<Double> rpdeAvData) {
        this.rpdeAvData = rpdeAvData;
    }

    public  List<Double> getRpdeDtData() {
        return this.rpdeDtData;
    }

    public void setRpdeDtData(List<Double> rpdeDtData) {
        this.rpdeDtData = rpdeDtData;
    }

    public  List<Double> getRpdeP0Data() {
        return rpdeP0Data;
    }

    public void setRpdeP0Data(List<Double> rpdeP0Data) {
        this.rpdeP0Data = rpdeP0Data;
    }

    public  List<Double> getRpdeP1Data() {
        return this.rpdeP1Data;
    }

    public void setRpdeP1Data(List<Double> rpdeP1Data) {
        this.rpdeP1Data = rpdeP1Data;
    }

    public void setAvTableData(ObservableList<CTMCAvailabilityTableModel> avTableData) {
        AvTableData = avTableData;
    }

    public ObservableList<CTMCAvailabilityTableModel> getRpdeAvTableData() {
        return rpdeAvTableData;
    }

    public void setRpdeAvTableData(ObservableList<CTMCAvailabilityTableModel> rpdeAvTableData) {
        this.rpdeAvTableData = rpdeAvTableData;
    }

    public ObservableList<CTMCDownTimeTableModel> getRpdeDtTableData() {
        return rpdeDtTableData;
    }

    public void setRpdeDtTableData(ObservableList<CTMCDownTimeTableModel> rpdeDtTableData) {
        this.rpdeDtTableData = rpdeDtTableData;
    }

    public List<Double>solveDownTime(List<Double>Av, List<Double>Dt){
        //initialize Dt
        try {
            Dt = new ArrayList<Double>();
            for (int i = 0; i < Av.size(); i++) {
                Dt.add(i, 1 - Av.get(i));
            }
        }catch (NullPointerException e){}
        return Dt;
    }

    public double getL() {
        return l;
    }

    public void setL(double l) {
        this.l = l;
    }

    public double getM() {
        return m;
    }

    public void setM(double m) {
        this.m = m;
    }

    public double getT() {
        return t;
    }

    public void setT(double t) {
        this.t = t;
    }

    public Rate getRateHwc() {
        return rateHwc;
    }

    public void setRateHwc(Rate rate) {
        this.rateHwc = rate;
    }

    public Rate getRateRpde() {
        return rateRpde;
    }

    public void setRateRpde(Rate rateRpde) {
        this.rateRpde = rateRpde;
    }

    public void clearLists(List<?>...lists){
        for(int i=0;i<lists.length;i++){
            lists[i].clear();
        }
    }
    public void setRpdeAvTableData(){
        this.rpdeAvTableData=FXCollections.observableArrayList();
        try{
            for (int i=0;i<this.rpdeP0Data.size();i++){
                this.rpdeAvTableData.add(new CTMCAvailabilityTableModel(this.rpdeTData.get(i),this.rpdeAvData.get(i),this.rpdeP0Data.get(i)));
            }
        }catch (NullPointerException e){}
        catch (IllegalArgumentException e){}
        catch (IndexOutOfBoundsException e){}
    }

    public void setRpdeDtTableData(){
        this.rpdeDtTableData=FXCollections.observableArrayList();
        try{
            for (int i=0;i<this.rpdeP0Data.size();i++){
                this.rpdeDtTableData.add(new CTMCDownTimeTableModel(this.rpdeTData.get(i),this.rpdeDtData.get(i),this.rpdeP1Data.get(i)));
            }
        }catch (NullPointerException e){}
        catch (IllegalArgumentException e){}
        catch (IndexOutOfBoundsException e){}
    }

    public void setHwcPoolAvTableData(){

        this.AvTableData= FXCollections.observableArrayList();
        try {
            for (int i = 0; i < p0Data.size(); i++) {
                this.tData.add(i);
                this.AvTableData.addAll(new CTMCAvailabilityTableModel(this.tData.get(i), this.AvData.get(i), this.p0Data.get(i),
                        this.p1Data.get(i), this.p5Data.get(i), this.p6Data.get(i)));
            }
        }catch (NullPointerException e){}
        catch (IllegalArgumentException e){}
        catch(IndexOutOfBoundsException e){}
    }

    public void setHwcPoolDtTableData(){

        this.DtTableData=FXCollections.observableArrayList();
        try {
            for (int i = 0; i < p0Data.size(); i++) {
                this.tData.add(i);
                this.DtTableData.addAll(new CTMCDownTimeTableModel(this.tData.get(i), this.DtData.get(i), this.p2Data.get(i), this.p3Data.get(i), this.p4Data.get(i), this.p7Data.get(i),
                        this.p8Data.get(i), this.p9Data.get(i)));
            }
        }catch (NullPointerException e){}
        catch (IllegalArgumentException e){}
        catch(IndexOutOfBoundsException e){}
    }
    public void clearData(){
        //clear all the private member list
        try{
            this.tData.clear();
            clearLists(this.AvTableData,this.DtTableData,this.AvData,this.DtData,this.p0Data,this.p1Data,this.p2Data,this.p3Data,this.p4Data,
                    this.p5Data,this.p6Data,this.p7Data,this.p8Data,this.p9Data);
        }catch (NullPointerException e){}

    }
    public double rpdeP0(double t, double l, double m){
        double a=m/(m+l);
        double b=l/(m+l);
        double c=(m+l)*t;
        double d=Math.exp(-c);

        return a+(b*d);
    }
    //obtain overall data
    public Object [][] Data(){
        if (this.data==null)
            throw new NullPointerException();
        return this.data;
    }
    public void Data(Rate rate, List...list){
        try{

            //initialize the data object
                this.data = new Object[list[1].size()+3][list.length];
            //populate the data
            for (int i=0;i<this.data.length;i++) {// preserve the first row for data header
                if (i==this.data.length-3){ //append rate details from n-3rd row to n-1st row
                    this.data[i]=new Object[]{""};
                    continue;
                }
                else if (i==this.data.length-2) { //set header for rates
                    this.data[i]=new Object[]{"λ","µ","δ","γ"};
                    continue;
                } else if(i==this.data.length-1) { //insert rate values at the last row
                    if(rate.getD()==0 && rate.getG()==0){
                        this.data[i] = new Object[]{rate.getL(),rate.getM()};

                    }else {
                        this.data[i] = new Object[]{rate.getL(), rate.getM(), rate.getD(), rate.getG()};
                    }
                    continue;
                }else
                    for (int j = 0; j < this.data[i].length; j++) {
                        this.data[i][j] = list[j].get(i);
                    }
            }
        }catch(NullPointerException e){}

    }
    public double rpdeP1(double t, double l, double m){
        return 1-rpdeP0(t,l,m);
    }
    //save the availability level data as an excel file
    public  void save(String filename,String [] header, Object [][] data) throws NullPointerException{
        //create workbook object
        XSSFWorkbook workbook=new XSSFWorkbook();
        //create a sheet from the workbook
        XSSFSheet sheet=workbook.createSheet();
        //create row object for the
        Row topRow=sheet.createRow(0);
        for(int i=0;i<1;i++){
            //keep track of the cells in the header
            int cellNum=0;
            for(Object content:header) {
                //create cell object within the first row and set its values to the contents of the header
                Cell cell = topRow.createCell(cellNum++);
                cell.setCellValue((String) content);
            }
        }//finish setting header
        //continue to write to the sheet from second row
        int rowNum=1;
        //loop through data
        for(Object [] records:data){
            //create row object
            Row row=sheet.createRow(rowNum++);
            //counter to keep track of the cell in each row
            int colCount=0;
            //loop through the row
            for(Object field:records) {
                //create cell object from the row
                Cell cell=row.createCell(colCount++);
                //check the data type of the field, then set the cell value to the corresponding data type of the current field
                if (field instanceof String)
                    cell.setCellValue((String)field);
                else if (field instanceof Integer)
                    cell.setCellValue((Integer)field);
                else if (field instanceof Double)
                    cell.setCellValue((Double)field);
                //set the cell value to the corresponding data type of the current field
            }
        }
        try {
            //create an instance of file output stream
            FileOutputStream fout=new FileOutputStream(filename);
            //write the contents of the data to the excel file
            workbook.write(fout);
            //release allocated resources
            workbook.close();
            new Alert(Alert.AlertType.INFORMATION,"Saved Successfully to"+"\n"+filename).show();

        }catch (FileNotFoundException e){
            new Alert(AlertType.ERROR,"File does not exist!").show();
        }catch  (IOException e){
            new Alert(AlertType.ERROR,"Failed save File!");
        }
    }
    //load excel file
    public void load(String filename, String header [],List ... lists){
        try{
            //Determine the correctness of the file through the header, the header length must be equal to the length of the sheet's header and,
            //the header contents must match the contents of the first row in the sheet. cases may be ignored.
            if(header.length==lists.length){ //criterion 1
                //create an instance of file input stream to read the excel sheet, given the provided name of the file
                FileInputStream fin=new FileInputStream(filename);
                //get the first sheet from the excel workbook using the file input stream object
                XSSFWorkbook workbook=new XSSFWorkbook(fin);
                Sheet sheet=workbook.getSheetAt(0);

                Row firstRow=sheet.getRow(0);
                if(firstRow.getPhysicalNumberOfCells()==lists.length){ //criterion 2
                    String [] RowTop=new String [header.length];
                    Iterator<Cell>cellIterator=firstRow.iterator();
                    //populate the first row content into the RowTop
                    int cellCounter=0;
                    while (cellIterator.hasNext()){
                        Cell currentCell=cellIterator.next();
                        if(currentCell.getCellTypeEnum()== CellType.STRING){
                            RowTop[cellCounter]=currentCell.getStringCellValue();
                            cellCounter++;
                        }
                    }
                    if (Arrays.deepEquals(RowTop,header)) {//criterion 3
                        //load each column of the sheet into the corresponding data structure (list)
                        int sheetLength = sheet.getLastRowNum() + 1;
                        for (int i = 1; i < sheetLength; i++) { //skip the first row of the sheet
                            //skip the n-3rd to the n-2nd rows of the sheet
                            if (i >= sheetLength - 3 && i <= sheetLength - 2)
                                continue;
                            else if (i == sheetLength - 1) {
                                //at the last row of the sheet set the rate values to the contents of the last row in the sheet
                                Row LastRow = sheet.getRow(i);

                                if(LastRow.getPhysicalNumberOfCells()==2 ) {
                                    setRateRpde(new Rate(LastRow.getCell(0).getNumericCellValue(), LastRow.getCell(1).getNumericCellValue()));
                                }
                                else if(LastRow.getPhysicalNumberOfCells()>2){
                                    setRateHwc(new Rate(LastRow.getCell(0).getNumericCellValue(), LastRow.getCell(1).getNumericCellValue(),
                                            LastRow.getCell(2).getNumericCellValue(), LastRow.getCell(3).getNumericCellValue()));
                                }
                            } else {
                                Row currentRow = sheet.getRow(i);//create an instance of the current row
                                //set the corresponding text fields of the list's contents to the corresponding table and its contents
                                for (int j = 0; j < lists.length; j++) {
                                    if(j==0){
                                        lists[j].add((int)currentRow.getCell(j).getNumericCellValue());
                                    }else{
                                        lists[j].add(currentRow.getCell(j).getNumericCellValue());
                                    }
                                }
                            }
                        }
                        new Alert(Alert.AlertType.INFORMATION,"Loaded Successfully from"+"\n"+filename).show();

                    }else {
                        //alert the user of trying to access the wrong file with a warning message
                        new Alert(AlertType.WARNING,"Trying to Access the wrong File!").show();
                    }
            }else {
                    //alert the user of trying to access the wrong file with a warning message
                    new Alert(AlertType.WARNING,"Trying to Access the wrong File!").show();
                }
            }else {
                //alert the user of trying to access the wrong file with a warning message
                new Alert(AlertType.WARNING,"Trying to Access the wrong File!").show();
            }
        } catch(FileNotFoundException e){
            new Alert(AlertType.ERROR,"File does not exist!").show();
        }catch (IOException e){
            new Alert(AlertType.ERROR,"Failed to load File!");
        }catch (ArrayIndexOutOfBoundsException e){}
        catch (NullPointerException e){}
    }
}
