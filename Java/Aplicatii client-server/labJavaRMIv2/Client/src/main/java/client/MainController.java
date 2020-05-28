package client;
import dto.DTOUtils;
import dto.OficiuDTO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.util.converter.IntegerStringConverter;
import model.Cursa;
import model.Oficiu;
import model.Participant;
import model.ParticipantCursa;
import services.IObserver;
import services.IServices;
import services.MyException;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MainController implements IObserver, Serializable {
    private IServices server = null;
    private OficiuDTO oficiuDTO;
    Stage stage;
    private ObservableList<Cursa> cursaObservableList = FXCollections.observableArrayList();
    private ObservableList<Participant> participantObservableList = FXCollections.observableArrayList();

    public MainController() {
    }

    public Oficiu login(String username, String password) throws RemoteException, MyException {
        return server.findOficiuByUserPass(username, password, this);
    }

    public void setServer(IServices server, Stage stage) throws MyException, RemoteException {
        try {
            UnicastRemoteObject.exportObject(this,0); //stub
        } catch (RemoteException e) {
            System.out.println("Error exporting object " + e);
        }
        this.server = server;
        this.stage = stage;
        initialize();
    }

    public void setOficiuDTO(OficiuDTO oficiuDTO){
        this.oficiuDTO = oficiuDTO;
        System.out.println("OficiulDTO in MainController este: " + this.oficiuDTO);
    }

    private void initialize() throws MyException, RemoteException {
        setComboBox();
        setData();
        setCurseTable(cursaObservableList);
        setParticipantiTable(participantObservableList);
        setSelectionTable();
        setEchipe();
    }

    private void setComboBox(){
        capMotorComboBox.getItems().setAll(125,250,500,1000);
        capMotorComboBox.getSelectionModel().selectFirst();
    }

    private void setData() throws MyException, RemoteException {
        cursaObservableList.setAll(server.findAllCursa());
        participantObservableList.setAll(server.findAllParticipant());
    }

    private void setCurseTable(ObservableList<Cursa> cursaObservableList) {
        idCursaColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        idCursaColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nrPersCursaColumn.setCellValueFactory(new PropertyValueFactory<>("NrPersoane"));
        nrPersCursaColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        capMotorCursaColumn.setCellValueFactory(new PropertyValueFactory<>("CapacitateMotor"));
        capMotorCursaColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        curseTable.getColumns().set(0, idCursaColumn);
        curseTable.getColumns().set(1, nrPersCursaColumn);
        curseTable.getColumns().set(2, capMotorCursaColumn);
        curseTable.setItems(cursaObservableList);
    }

    private void setParticipantiTable(ObservableList<Participant> participantObservableList) {
        numePartColumn.setCellValueFactory(new PropertyValueFactory<>("Nume"));
        numePartColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        capMotorPartColumn.setCellValueFactory(new PropertyValueFactory<>("CapacitateMotor"));
        capMotorPartColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        participantiTable.getColumns().set(0, numePartColumn);
        participantiTable.getColumns().set(1, capMotorPartColumn);
        participantiTable.setItems(participantObservableList);
    }

    private void setSelectionTable(){
        curseTable.getSelectionModel().selectedItemProperty().addListener((x, y, z) -> {
            Cursa cursa = (Cursa) z;
            if(cursa != null){
                idTextField.setText(cursa.getId());
            }
        });

        participantiTable.getSelectionModel().selectedItemProperty().addListener((x, y, z) -> {
            Participant participant = (Participant) z;
            if(participant != null){
                numeTextField.setText(participant.getNume());
                echipaTextField.setText(participant.getEchipa());
                capMotorComboBox.getSelectionModel().select(participant.getCapacitateMotor());
            }
        });
    }

    private void setEchipe() throws MyException, RemoteException {
        echipeTooltip.setText(server.findAllEchipe());
    }

    @FXML
    Tooltip echipeTooltip;

    @FXML
    TableView curseTable;
    @FXML
    TableColumn<Cursa, String> idCursaColumn;
    @FXML
    TableColumn<Cursa, Integer> nrPersCursaColumn;
    @FXML
    TableColumn<Cursa, Integer> capMotorCursaColumn;

    @FXML
    TableView participantiTable;
    @FXML
    TableColumn<Participant, String> numePartColumn;
    @FXML
    TableColumn<Participant, Integer> capMotorPartColumn;

    @FXML
    Button cautaButton;
    @FXML
    Button inscrieButton;
    @FXML
    Button logoutButton;

    @FXML
    TextField cautaEchipaTextField;
    @FXML
    TextField idTextField;
    @FXML
    TextField numeTextField;
    @FXML
    TextField echipaTextField;
    @FXML
    ComboBox<Integer> capMotorComboBox;

    @FXML
    Label echipaLabel;

    public void handleLogoutButton() throws MyException, RemoteException {
        System.out.println("OficiulDTO in handleLogout din MC este: " + this.oficiuDTO);
        Oficiu oficiu = DTOUtils.getfromDTO(this.oficiuDTO);
        server.logout(oficiu, this);
        stage.close();
    }

    public void handleCautaButton() throws MyException, RemoteException {
        String echipa = cautaEchipaTextField.getText();
        participantObservableList.setAll(server.findAllParticipant(echipa));
    }

    public void handleInscrieButton(){
        try {
            String idCursa = idTextField.getText();
            String nume = numeTextField.getText();
            String echipa = echipaTextField.getText();
            Integer capMotor = capMotorComboBox.getSelectionModel().getSelectedItem();

            Participant participant = server.findParticipant(nume, echipa, capMotor);
            String idParticipant = participant.getId();
            Cursa cursa = server.findCursa(idCursa);

            ParticipantCursa participantCursa = new ParticipantCursa(idParticipant, idCursa);
            participantCursa.setId(new Pair<>(idParticipant, idCursa));

            if (capMotor.toString().equals(cursa.getCapacitateMotor().toString())) {
                server.saveParticipantCursa(participantCursa);
                cursa.setNrPersoane(cursa.getNrPersoane() + 1);
                server.updateCursa(idCursa, cursa);

                cursaObservableList.setAll(server.findAllCursa());
            } else {
                String error = "Participantul nu se poate inscrie la cursa deoarece: \n -categoria cursei este: " +
                        cursa.getCapacitateMotor() +
                        "\n -capacitatea motorului sau este: " +
                        capMotor;
                Alert alert = new Alert(Alert.AlertType.ERROR, error);
                alert.setHeaderText("Incompatibilitate categorii!");
                alert.setTitle("Eroare");
                alert.show();
            }
        }
        catch (MyException | RemoteException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Participant deja inscris la cursa!");
            alert.setHeaderText("Violare reguli cursa!");
            alert.setTitle("Eroare");
            alert.show();
        }
        catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Selectati participantul si cursa la care doriti sa-l inscrieti!");
            alert.setHeaderText("Selectare invalida!");
            alert.setTitle("Eroare");
            alert.show();
        }
    }

    @Override
    public void participantCursaAdded() throws MyException {
        Platform.runLater(()->{
            try {
                setData();
            } catch (MyException | RemoteException e) {
                e.printStackTrace();
            }
        });

    }

}
