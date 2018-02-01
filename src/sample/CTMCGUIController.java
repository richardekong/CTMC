package sample;

import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.sun.deploy.util.SyncAccess;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import javax.xml.crypto.Data;

public class CTMCGUIController implements Initializable{

    @FXML
    private AnchorPane anchorpane;

    @FXML
    private JFXHamburger ham;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private Tab rpdetab;

    @FXML
    private Tab pooltab;

    private rpdeController rpdeController;
    private hwcPoolsController hwcPoolsController;
    private DataModel dataModel=new DataModel();

    @Override
    public void initialize(URL url, ResourceBundle rb){

        try{
            //load sidepane for navigation drawer
            VBox sidePane= FXMLLoader.load(getClass().getResource("NavigationDrawer.fxml"));
            drawer.setSidePane(sidePane);
            //load rpde layout to the corresponding tab
            FXMLLoader rpdeLoader=new FXMLLoader(getClass().getResource("rpde.fxml"));
            Parent rpdeContent=(Parent)rpdeLoader.load();
            rpdetab.setContent(rpdeContent);
            rpdeController=rpdeLoader.getController();

            //load components for the hwcpools layout
            FXMLLoader hwcpoolLoader=new FXMLLoader(getClass().getResource("hwcpools.fxml"));
            Parent hwcpoolContents=(Parent)hwcpoolLoader.load();
            pooltab.setContent(hwcpoolContents);
            hwcPoolsController=hwcpoolLoader.getController();
            //add mouse click event the buttons in the navigation drawer
            for(Node node:sidePane.getChildren() ) {
                if (node.getAccessibleText() != null) {
                    node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                        switch (node.getAccessibleText()) {
                            case "Save":
                                save();
                                break;
                            case "Load":
                                Load();
                                break;
                            case "Help":
                                 Help();
                                break;
                            case "Quit":
                                exit();
                                break;
                        }
                    });
                }
            }
        }catch(IOException e){
            Logger.getLogger(CTMCGUIController.class.getName()).log(Level.SEVERE,null,e);
        }
        //animate the hamburger componenet
        HamburgerBackArrowBasicTransition burgerTask=new HamburgerBackArrowBasicTransition(ham);
        burgerTask.setRate(-1);
        ham.addEventHandler(MouseEvent.MOUSE_PRESSED,(e)->{
            burgerTask.setRate(burgerTask.getRate()*-1);
            burgerTask.play();

            if(drawer.isShown())
            {
                SwingUtilities.invokeLater(()->{
                    drawer.close();
                });
                //set navigation drawer z-horizon to back
                drawer.toBack();
            }else{
                //set navigation drawer z-horizon
                drawer.toFront();
                SwingUtilities.invokeLater(()->{
                    drawer.open();
                });
            }
        });
    }
    private static void exit(){
        //prompt user for permission to exit
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"Do you wish to exit?",
                ButtonType.YES, ButtonType.NO);
        //make alert dialog visible
        alert.showAndWait().ifPresent(response->{
            //determine if the yes button is clicked
            if  (response==ButtonType.YES)
                //close the system
                System.exit(0);
        });
    }
    private void save(){
        //create a file chooser object
        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Save File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel","*.xlsx"));
        try {
            File file=fileChooser.showSaveDialog(Main.getMainStage());
            String filename=file.toString();
            if (file != null) {
                //determine the current tab
                if (rpdetab.isSelected()) {
                     dataModel=rpdeController.getDataModel();
                    if (rpdeController.getAvTab().isSelected()) {
                        String [] header={"t","Av","P0"};//specify the header
                        //set up the data to save
                        dataModel.Data(dataModel.getRateRpde(),dataModel.getRpdeTData(),dataModel.getRpdeAvData(), dataModel.getRpdeP0Data());
                        dataModel.save(filename,header,dataModel.Data() );
                    } else {
                        String [] header={"t","Dt","P1"};//specify the header
                        //set up the data to save
                        dataModel.Data(dataModel.getRateRpde(),dataModel.getRpdeTData(),dataModel.getRpdeDtData(), dataModel.getRpdeP1Data());
                        dataModel.save(filename,header,dataModel.Data());
                    }
                } else if (pooltab.isSelected()) {
                        dataModel=hwcPoolsController.getDataModel();
                        if(hwcPoolsController.getAvTab().isSelected()){
                            String header []={"t","Av","P0","P1","P5","P6"};//specify the data header
                            //set up the data to save
                            dataModel.Data(dataModel.getRateHwc(),dataModel.gettData(),dataModel.getAvData(),dataModel.getP0Data(),
                                    dataModel.getP1Data(),dataModel.getP5Data(),dataModel.getP6Data());
                            dataModel.save(filename,header,dataModel.Data());//save the data
                        }else{
                            String header []={"t","Dt","P2","P3","P4","P7","P8","P9"};//specify the data header
                            //set up the data
                            dataModel.Data(dataModel.getRateHwc(),dataModel.gettData(),dataModel.getDtData(),dataModel.getP2Data(),dataModel.getP3Data(),
                                    dataModel.getP4Data(),dataModel.getP7Data(),dataModel.getP8Data(),dataModel.getP9Data());
                            //save the data
                            dataModel.save(filename,header,dataModel.Data());
                        }
                }
            }
            // handle file object with null
        }catch (NullPointerException e){
            new Alert(Alert.AlertType.INFORMATION,"Cancelled!");

        }
    }
    private void Load(){
        //create a file chooser object
        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Load file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel","*.xlsx"));
        try{

            File file=fileChooser.showOpenDialog(Main.getMainStage());
            String filename=file.toString();
            if(file!=null){
                if(rpdetab.isSelected()){
                    rpdeController.setDataModel(dataModel);//initialize data model
                    dataModel=rpdeController.getDataModel();
                    if(rpdeController.getAvTab().isSelected()){
                        String [] header={"t","Av","P0"};//specify the header
                        dataModel.load(filename,header,dataModel.getRpdeTData(),dataModel.getRpdeAvData(), dataModel.getRpdeP0Data());
                        dataModel.setRpdeAvTableData();
                        rpdeController.getRpdeAvTableView().setItems(dataModel.getRpdeAvTableData());

                    }else{
                        String [] header={"t","Dt","P1"};//specify the header
                        dataModel.load(filename,header,dataModel.getRpdeTData(),dataModel.getRpdeDtData(),dataModel.getRpdeP1Data());
                        dataModel.setRpdeDtTableData();
                        rpdeController.getRpdeDtTableView().setItems(dataModel.getRpdeDtTableData());
                    }
                    rpdeController.getRpdelambdavalue().setText(String.format("%.3f",dataModel.getRateRpde().getL()));
                    rpdeController.getRpdeMuvalue().setText(String.format("%.3f",dataModel.getRateRpde().getM()));
                    rpdeController.getRpdeTvalue().setText(String.valueOf(dataModel.getRpdeTData().get(dataModel.getRpdeTData().size()-1)));

                }else{
                    hwcPoolsController.setDataModel(dataModel);
                    dataModel=hwcPoolsController.getDataModel();
                    if(hwcPoolsController.getAvTab().isSelected()){
                        String header []={"t","Av","P0","P1","P5","P6"};//specify the data header
                        dataModel.load(filename,header,dataModel.gettData(),dataModel.getAvData(),dataModel.getP0Data(),
                                dataModel.getP1Data(),dataModel.getP5Data(),dataModel.getP6Data());
                        dataModel.setHwcPoolAvTableData();
                        hwcPoolsController.getAvTableView().setItems(dataModel.getAvTableData());

                    }else {
                        String header []={"t","Dt","P2","P3","P4","P7","P8","P9"};//specify the data header
                        dataModel.load(filename,header,dataModel.gettData(),dataModel.getDtData(),dataModel.getP2Data(),dataModel.getP3Data(),
                                dataModel.getP4Data(),dataModel.getP7Data(),dataModel.getP8Data(),dataModel.getP9Data());
                        dataModel.setHwcPoolDtTableData();
                        hwcPoolsController.getDtTableView().setItems(dataModel.getDtTableData());

                    }
                    hwcPoolsController.getLambdavalue().setText(String.format("%.3f",dataModel.getRateHwc().getL()));
                    hwcPoolsController.getMuvalue().setText(String.format("%.3f",dataModel.getRateHwc().getM()));
                    hwcPoolsController.getDeltavalue().setText(String.format("%.3f",dataModel.getRateHwc().getD()));
                    hwcPoolsController.getGamavalue().setText(String.format("%.3f",dataModel.getRateHwc().getG()));
                    hwcPoolsController.getTimeValue().setText(String.valueOf(dataModel.gettData().get(dataModel.gettData().size()-1)));
                }
            }
            System.out.print(filename);
        }catch (NullPointerException e){
            new Alert(Alert.AlertType.INFORMATION,"Cancelled!");
        }
        catch (ArrayIndexOutOfBoundsException e){}
    }

    public  void Help() {
        //start a new Thread
        new Thread(()-> {
            try {
                //specify the path of the pdf help manual
                String path=getClass().getClassLoader().getResource("sample/CTMCManual.pdf").getPath();
                //create file object with the path above
                File file=new File(path);
                //open file
                Desktop.getDesktop().open(file);
            }   catch(NullPointerException e){new Alert(Alert.AlertType.ERROR,"File is empty!").show();}
                catch (IOException e) {new Alert(Alert.AlertType.ERROR,"Failed to open help file!").show();}
                catch(UnsupportedOperationException e){new Alert(Alert.AlertType.ERROR,"Platform does not support this operation!").show();}
                catch(SecurityException e){new Alert(Alert.AlertType.ERROR,"Access denied!").show();}
                catch (IllegalArgumentException e){new Alert(Alert.AlertType.ERROR,"File does not exist!").show();}
        }).start();
    }
    }
