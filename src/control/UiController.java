package control;

import control.models.MemTableCell;
import control.models.cpu.DataPathDriver;
import control.models.cpu.Utility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class UiController {
    private String colorActive = "#66CC33";
    private String colorDeactive = "#295214";

    private int stageNumber = 0;

    private String codeString = "";

    private ObservableList<MemTableCell> imList;
    private ObservableList<MemTableCell> rfList;
    private ObservableList<MemTableCell> mmList;

    //================================================

    private DataPathDriver dataPathDriver;

    @FXML
    private TableColumn<String, MemTableCell> imBinCol;

    @FXML
    private TableView<MemTableCell> mmTableView;

    @FXML
    private Label fetchBanner;

    @FXML
    private Label memBanner;

    @FXML
    private TableColumn<String, MemTableCell> imDecCol;

    @FXML
    private TextField memSizeTf;

    @FXML
    private TableColumn<String, MemTableCell> mmValCol;

    @FXML
    private Label wbBanner;

    @FXML
    private TableColumn<String, MemTableCell> imValCol;

    @FXML
    private Button setBt;

    @FXML
    private TableColumn<String, MemTableCell> mmDecCol;

    @FXML
    private TableView<MemTableCell> imTableView;

    @FXML
    private TableColumn<String, MemTableCell> rfBinCol;

    @FXML
    private Label exeBanner;

    @FXML
    private TextField regSizeTf;

    @FXML
    private TableView<MemTableCell> rfTableView;

    @FXML
    private TableColumn<String, MemTableCell> rfValCol;

    @FXML
    private TableColumn<String, MemTableCell> mmBinCol;

    @FXML
    private Button nextBt;

    @FXML
    private Button loadBt;

    @FXML
    private TableColumn<String, MemTableCell> rfDecCol;

    @FXML
    private TextField delayTf;

    @FXML
    private Label decodeBanner;

//=======================================================

    Timer timer = new Timer();
    boolean isWorking = false;


    @FXML
    public void initialize() {
        dataPathDriver = new DataPathDriver();

        initTableViews();
    }

    @FXML
    void nextBtAction(ActionEvent event) {
        if (isWorking) {
            timer.cancel();
            nextBt.setText("run");
            isWorking = false;
        } else {
            timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    incrementBanner();
                }
            };
            timer.schedule(task, Integer.parseInt(delayTf.getText()), Integer.parseInt(delayTf.getText()));
            nextBt.setText("stop");
            isWorking = true;
        }
    }

    @FXML
    void goNext(ActionEvent event) {

        Timer t = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                for (int i = 0; i < 50; i++) {
                    incrementBanner();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t.schedule(task, 0);
    }

    @FXML
    void setMemAction(ActionEvent event) {
        if (regSizeTf.getText().trim().length() > 0) {//TODO: try-catch
            int regSize = Integer.parseInt(regSizeTf.getText());
            int radix = (int) Math.sqrt(regSize) + 1;
            rfList.clear();
            for (int i = 0; i < regSize; i++) {
                String key = Utility.decimalToString(i, radix);
                String value = "00000000000000000000000000000000";
                MemTableCell cell = new MemTableCell(i, key, value);
                rfList.add(cell);
            }
        }
        if (memSizeTf.getText().trim().length() > 0) {//TODO: try-catch
            int memSize = Integer.parseInt(memSizeTf.getText());
            int radix = (int) Math.sqrt(memSize) + 1;
            mmList.clear();
            for (int i = 0; i < memSize; i++) {
                String key = Utility.decimalToString(i, radix);
                String value = "00000000000000000000000000000000";
                MemTableCell cell = new MemTableCell(i, key, value);
                mmList.add(cell);
            }
        }
    }


    @FXML
    void loadButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open project");
        File file = fileChooser.showOpenDialog(loadBt.getScene().getWindow());
        List<String> instructions = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                int value = Integer.parseInt(line);//TODO: check for errors
                instructions.add(String.format("%" + 32 + "s", Integer.toBinaryString(value)).replace(' ', '0'));
                line = br.readLine();
            }
            codeString = sb.toString();
            br.close();
        } catch (Exception e) {
        }

        int imSize = instructions.size();
        int radix = (int) Math.sqrt(imSize) + 1;
        imList.clear();
        for (int i = 0; i < imSize; i++) {
            String key = Utility.decimalToString(i, radix);
            String value = instructions.get(i);
            MemTableCell cell = new MemTableCell(i, key, value);
            imList.add(cell);
        }
    }

    void enableBanner(int id) {
        fetchBanner.getStyleClass().remove("banner-active");
        fetchBanner.getStyleClass().add("banner-deactive");
        decodeBanner.getStyleClass().remove("banner-active");
        decodeBanner.getStyleClass().add("banner-deactive");
        exeBanner.getStyleClass().remove("banner-active");
        exeBanner.getStyleClass().add("banner-deactive");
        memBanner.getStyleClass().remove("banner-active");
        memBanner.getStyleClass().add("banner-deactive");
        wbBanner.getStyleClass().remove("banner-active");
        wbBanner.getStyleClass().add("banner-deactive");

        switch (id) {
            case -1:
                break;
            case 0:
                fetchBanner.getStyleClass().remove("banner-deactive");
                fetchBanner.getStyleClass().add("banner-active");
                break;
            case 1:
                decodeBanner.getStyleClass().remove("banner-deactive");
                decodeBanner.getStyleClass().add("banner-active");
                break;
            case 2:
                exeBanner.getStyleClass().remove("banner-deactive");
                exeBanner.getStyleClass().add("banner-active");
                break;
            case 3:
                memBanner.getStyleClass().remove("banner-deactive");
                memBanner.getStyleClass().add("banner-active");
                break;
            case 4:
                wbBanner.getStyleClass().remove("banner-deactive");
                wbBanner.getStyleClass().add("banner-active");
                break;
        }
    }

    void incrementBanner() {
        enableBanner(stageNumber);
        stageNumber++;
        if (stageNumber > 4)
            stageNumber = 0;
    }


    void initTableViews() {
        imList = FXCollections.observableArrayList();
        rfList = FXCollections.observableArrayList();
        mmList = FXCollections.observableArrayList();

        imDecCol.setCellValueFactory(new PropertyValueFactory<>("row"));
        imBinCol.setCellValueFactory(new PropertyValueFactory<>("index"));
        imValCol.setCellValueFactory(new PropertyValueFactory<>("data"));
        imTableView.setItems(imList);

        rfDecCol.setCellValueFactory(new PropertyValueFactory<>("row"));
        rfBinCol.setCellValueFactory(new PropertyValueFactory<>("index"));
        rfValCol.setCellValueFactory(new PropertyValueFactory<>("data"));
        rfTableView.setItems(rfList);

        mmDecCol.setCellValueFactory(new PropertyValueFactory<>("row"));
        mmBinCol.setCellValueFactory(new PropertyValueFactory<>("index"));
        mmValCol.setCellValueFactory(new PropertyValueFactory<>("data"));
        mmTableView.setItems(mmList);
    }
}
