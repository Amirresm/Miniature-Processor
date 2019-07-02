package control;

import control.models.GuiDataHolder;
import control.models.MemTableCell;
import control.models.cpu.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class UiController {
    private int stageNumber = 0;

    private String codeString = "";

    private ObservableList<MemTableCell> imList;
    private ObservableList<MemTableCell> rfList;
    private ObservableList<MemTableCell> mmList;

    private DataPathDriver dataPathDriver;

    private GuiDataHolder uiHolder;

    //================================================

    @FXML
    private TableColumn<String, MemTableCell> imBinCol;

    @FXML
    private TableView<MemTableCell> mmTableView;

    @FXML
    private TableColumn<String, MemTableCell> imDecCol;

    @FXML
    private TableColumn<String, MemTableCell> mmValCol;

    @FXML
    private TableColumn<String, MemTableCell> mmDecValCol;

    @FXML
    private TableColumn<String, MemTableCell> imValCol;

    @FXML
    private TableColumn<String, MemTableCell> mmDecCol;

    @FXML
    private TableView<MemTableCell> imTableView;

    @FXML
    private TableColumn<String, MemTableCell> rfDecCol;

    @FXML
    private TableColumn<String, MemTableCell> rfBinCol;

    @FXML
    private TableView<MemTableCell> rfTableView;

    @FXML
    private TableColumn<String, MemTableCell> rfValCol;

    @FXML
    private TableColumn<String, MemTableCell> rfDecValCol;

    @FXML
    private TableColumn<String, MemTableCell> mmBinCol;

    @FXML
    private Label mReadData1Lb;

    @FXML
    private Label mBranchMuxZero;

    @FXML
    private Label cMemToRegLb;

    @FXML
    private Label cRegWriteLb;

    @FXML
    private Circle iRegDest;

    @FXML
    private Label mPCLb;

    @FXML
    private Label mRDLb;

    @FXML
    private Label cALUOpLb;

    @FXML
    private Circle iALUSrc;

    @FXML
    private TextField regSizeTf;

    @FXML
    private Label mMemReadLb;

    @FXML
    private Label mBranchMuxOne;

    @FXML
    private Label mOffsetLb;

    @FXML
    private Label mALUSrcMuxZero;

    @FXML
    private Label mJumpMuxOne;

    @FXML
    private Label decodeBanner;

    @FXML
    private Label mRSLb;

    @FXML
    private Label mOppCodeLb;

    @FXML
    private Label iALUOp;

    @FXML
    private Label mNextPCLb;

    @FXML
    private TextField memSizeTf;

    @FXML
    private Label wbBanner;

    @FXML
    private Circle iMemWrite;

    @FXML
    private Label mMemRegMuxZero;

    @FXML
    private Circle iMemToReg;

    @FXML
    private Label cMemReadLb;

    @FXML
    private Label cALUSrcLb;

    @FXML
    private TextField delayTf;

    @FXML
    private Circle iZero;

    @FXML
    private Label mReadData2Lb;

    @FXML
    private Label mALUSrcMuxOne;

    @FXML
    private Label mALUresultLb;

    @FXML
    private Label mInstructionLb;

    @FXML
    private Label memBanner;

    @FXML
    private Label mMemRegMuxOne;

    @FXML
    private Button loadBt;

    @FXML
    private Label cMemWriteLb;

    @FXML
    private Button setBt;

    @FXML
    private Label mWriteRegLb;

    @FXML
    private Label exeBanner;

    @FXML
    private Slider delaySlider;

    @FXML
    private Label mMemoryWriteLb;

    @FXML
    private Button nextBt;

    @FXML
    private Label mReadRegLb;

    @FXML
    private Circle iJump;

    @FXML
    private Label mJumpLb;

    @FXML
    private Label cZeroLb;

    @FXML
    private Label mPCpOneLb;

    @FXML
    private Label mRegDestMuxZero;

    @FXML
    private Label mRTLb;

    @FXML
    private Label mReadReg2Lb;

    @FXML
    private Label fetchBanner;

    @FXML
    private Circle iBranchAndZero;

    @FXML
    private Label mBranchPCLb;

    @FXML
    private TextField changeAddrTf;

    @FXML
    private Button updateMemBt;

    @FXML
    private Label cJumpLb;

    @FXML
    private Label mRegDestMuxOne;

    @FXML
    private Label cRegDestLb;

    @FXML
    private Label cBranchLb;

    @FXML
    private TextField changeValTf;

    @FXML
    private Circle iMemRead;

    @FXML
    private Circle iRegWrite;

    @FXML
    private Circle iBranch;

    @FXML
    private Label mJumpMuxZero;

    @FXML
    private Label mWriteDataLb;

//=======================================================

    private Timer timer;

    private boolean isWorking = false;


    @FXML
    public void initialize() {
        dataPathDriver = new DataPathDriver();
        uiHolder = new GuiDataHolder();
        dataPathDriver.setUiHolder(uiHolder);

        InstructionMem instructionMem = new InstructionMem();
        ControlUnit control = new ControlUnit();
        ALU alu = new ALU();

        initTableViews();

        dataPathDriver.setInstructionMem(instructionMem);
        dataPathDriver.setAlu(alu);
        dataPathDriver.setControlUnit(control);
        dataPathDriver.setRegisterFile(new RegisterFile(rfList));
        dataPathDriver.setMainMemory(new MainMemory(mmList));

        delaySlider.valueProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                delayTf.setText((int) delaySlider.getValue() + "");
            }
        });
    }

    @FXML
    void nextBtAction(ActionEvent event) {
        if (isWorking) {
            timer.cancel();
            timer.purge();
            nextBt.setText("Run");
            isWorking = false;
        } else {
            int delay = 500;
            if (!delayTf.getText().trim().isEmpty())
                delay = Integer.parseInt(delayTf.getText());
            startDriverTimer(delay);
            nextBt.setText("Stop");
            isWorking = true;

        }
    }

    private void startDriverTimer(int delay) {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                drive();
            }
        };
        timer.schedule(task, 0, delay);
    }

    private void drive() {
        stageNumber = dataPathDriver.getStageIndicator();
        if (stageNumber == 0)
            dataPathDriver.getUiHolder().reset();
        System.out.println("timer iterate: STAGE = " + stageNumber);
        System.out.println("PC = " + dataPathDriver.getPc());
        if (dataPathDriver.getHALT().data != 1 || stageNumber != 0) {
//                    Platform.runLater(() -> {
//                    });
            dataPathDriver.executeStage();
            Platform.runLater(() -> {
                enableBanner(stageNumber);
                updateGuiMap();
                rfTableView.refresh();
                mmTableView.refresh();
                if (stageNumber == 0) {
                    imTableView.getSelectionModel().select(Integer.parseInt(dataPathDriver.getPc(), 2));
                }
            });
        } else {
            timer.cancel();
            timer.purge();
            Platform.runLater(() -> {nextBt.setText("Run");});
            enableBanner(-1);
            isWorking = false;
        }
    }

    @FXML
    void resetAction(ActionEvent event) {
        dataPathDriver.resetDriver();
        dataPathDriver.getUiHolder().reset();
        enableBanner(-1);
        updateGuiMap();
    }

    @FXML
    void setMemAction(ActionEvent event) {
        if (regSizeTf.getText().trim().length() > 0) {//TODO: try-catch
            int regSize = Integer.parseInt(regSizeTf.getText());
            dataPathDriver.getRegisterFile().resizeMemory(regSize);
        }
        if (memSizeTf.getText().trim().length() > 0) {//TODO: try-catch
            int memSize = Integer.parseInt(memSizeTf.getText());
            dataPathDriver.getMainMemory().resizeMemory(memSize);
        }
    }

    @FXML
    void updateMemAction(ActionEvent event) {
        if ((changeValTf.getText().trim().length() > 0) && (changeAddrTf.getText().trim().length() > 0)) {
            dataPathDriver.getMainMemory().predefineData(Long.parseLong(changeValTf.getText()), Integer.parseInt(changeAddrTf.getText()));
            mmTableView.refresh();
        }
        System.out.println("data:");
        for (MemTableCell cell : rfList) {
            System.out.println(cell.getData());
        }
    }

    @FXML
    void loadButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open project");
        File file = fileChooser.showOpenDialog(loadBt.getScene().getWindow());
        if(file != null) {
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
            } catch (Exception ignored) {
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
            dataPathDriver.getInstructionMem().load(codeString);
        }
    }


    private void enableBanner(int id) {
        System.out.println("banner number " + (id + 1));
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

    private void updateGuiMap() {
        mPCLb.setText(uiHolder.pc);
        mPCpOneLb.setText(uiHolder.pcPOne);
        mInstructionLb.setText(uiHolder.instruction);
        mJumpLb.setText(uiHolder.jump);
        mOppCodeLb.setText(uiHolder.oppCode);
        mRSLb.setText(uiHolder.rs);
        mRTLb.setText(uiHolder.rt);
        mRDLb.setText(uiHolder.rd);
        mOffsetLb.setText(uiHolder.offset);
        mReadRegLb.setText(uiHolder.regRead1);
        mReadReg2Lb.setText(uiHolder.regRead2);
        mWriteRegLb.setText(uiHolder.writeReg);
        mReadData1Lb.setText(uiHolder.readData1);
        mReadData2Lb.setText(uiHolder.readData2);
        mBranchPCLb.setText(uiHolder.branchPc);
        mNextPCLb.setText(uiHolder.nextPc);
        mALUresultLb.setText(uiHolder.aluResult);
        mMemReadLb.setText(uiHolder.memory);
        mMemoryWriteLb.setText(uiHolder.memoryWrite);
        mWriteDataLb.setText(uiHolder.writeData);
        iALUOp.setText(uiHolder.aluOp);

        setEnabled(cRegDestLb, "signal", uiHolder.regDest);
        setEnabled(cJumpLb, "signal", uiHolder.jumpSignal);
        setEnabled(cMemToRegLb, "signal", uiHolder.memToReg);
        setEnabled(cBranchLb, "signal", uiHolder.branch);
        setEnabled(cMemReadLb, "signal", uiHolder.memRead);
        setEnabled(cMemWriteLb, "signal", uiHolder.memWrite);
        setEnabled(cALUSrcLb, "signal", uiHolder.aluSrc);
        setEnabled(cRegWriteLb, "signal", uiHolder.regWrite);
        setEnabled(cZeroLb, "signal", uiHolder.aluZero);

        setEnabled(iRegDest, "circle", uiHolder.regDest);
        setEnabled(iJump, "circle", uiHolder.jumpSignal);
        setEnabled(iMemToReg, "circle", uiHolder.memToReg);
        setEnabled(iBranch, "circle", uiHolder.branch);
        setEnabled(iMemRead, "circle", uiHolder.memRead);
        setEnabled(iMemWrite, "circle", uiHolder.memWrite);
        setEnabled(iALUSrc, "circle", uiHolder.aluSrc);
        setEnabled(iRegWrite, "circle", uiHolder.regWrite);
        setEnabled(iZero, "circle", uiHolder.aluZero);
        setEnabled(iBranchAndZero, "circle", uiHolder.branchANDZero);
    }

    private void setEnabled(Node node, String type, boolean enable) {
        String enableStyle = "";
        String disableStyle = "";
        switch (type) {
            case "signal":
                enableStyle = "    -fx-background-color: rgb(255, 96, 0);\n" +
                        "    -fx-background-radius: 5px;\n" +
                        "    -fx-background-insets: 1px;\n" +
                        "    -fx-text-fill: #8e3e00;\n" +
                        "    -fx-font-size: 9px;\n" +
                        "    -fx-border-radius: 5px;\n" +
                        "    -fx-border-insets: 1px;";
                disableStyle = "    -fx-background-color: #927865;\n" +
                        "    -fx-background-radius: 3px;\n" +
                        "    -fx-background-insets: 1px;\n" +
                        "    -fx-text-fill: #49382a;\n" +
                        "    -fx-font-size: 9px;\n" +
                        "    -fx-font-weight: normal;\n" +
                        "    -fx-border-color: transparent;";
                break;
            case "circle":
                enableStyle = "    -fx-fill: #e53607;\n" +
                        "    -fx-stroke: #2C0303;";
                disableStyle = "    -fx-fill: #66544e;\n" +
                        "    -fx-stroke: transparent;";
                break;
        }
        if (enable) {
//            node.getStyleClass().remove(disableStyle);
//            node.getStyleClass().add(enableStyle);
            node.setStyle(enableStyle);
            if (type.equals("circle")) {
                ((Circle) node).setRadius(5);
            }
        } else {
            node.setStyle(disableStyle);
//            node.getStyleClass().remove(enableStyle);
//            node.getStyleClass().add(disableStyle);
            if (type.equals("circle")) {
                ((Circle) node).setRadius(3);
            }
        }
    }


    private void initTableViews() {
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
        rfDecValCol.setCellValueFactory(new PropertyValueFactory<>("decData"));
        rfTableView.setItems(rfList);

        mmDecCol.setCellValueFactory(new PropertyValueFactory<>("row"));
        mmBinCol.setCellValueFactory(new PropertyValueFactory<>("index"));
        mmValCol.setCellValueFactory(new PropertyValueFactory<>("data"));
        mmDecValCol.setCellValueFactory(new PropertyValueFactory<>("decData"));
        mmTableView.setItems(mmList);
    }
}
