package controller;
import domain.Cursa;
import domain.Participant;
import domain.ParticipantCursa;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.util.converter.IntegerStringConverter;
import repo.RepositoryException;
import service.Service;

public class MainController {
    private Service service = null;
    Stage stage;
    private ObservableList<Cursa> cursaObservableList = FXCollections.observableArrayList();
    private ObservableList<Participant> participantObservableList = FXCollections.observableArrayList();

    public void setService(Service service, Stage stage) {
        this.service = service;
        this.stage = stage;
        initialize();
    }

    private void initialize(){
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
        categorieComboBox.getItems().setAll(125,250,500,1000);
        categorieComboBox.getSelectionModel().selectFirst();
        categorieComboBox.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                cursaObservableList.setAll(service.findAllCursa(newValue));
            }
        });

    }

    private void setData(){
        cursaObservableList.setAll(service.findAllCursa(categorieComboBox.getSelectionModel().getSelectedItem()));
        participantObservableList.setAll(service.findAllParticipant());
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

    private void setEchipe(){
        echipeTooltip.setText(service.findAllEchipe());
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
    ComboBox<Integer> categorieComboBox;

    @FXML
    Label echipaLabel;

    public void handleLogoutButton(){
        stage.close();
    }

    public void handleCautaButton(){
        String echipa = cautaEchipaTextField.getText();
        participantObservableList.setAll(service.findAllParticipant(echipa));
    }

    public void handleInscrieButton(){
        try {
            String idCursa = idTextField.getText();
            String nume = numeTextField.getText();
            String echipa = echipaTextField.getText();
            Integer capMotor = capMotorComboBox.getSelectionModel().getSelectedItem();

            String idParticipant = service.findParticipant(nume, echipa, capMotor).getId();
            Cursa cursa = service.findCursa(idCursa);

            ParticipantCursa participantCursa = new ParticipantCursa(idParticipant, idCursa);
            participantCursa.setId(new Pair<>(idParticipant, idCursa));

            if (capMotor.toString().equals(cursa.getCapacitateMotor().toString())) {
                service.saveParticipantCursa(participantCursa);

                cursa.setNrPersoane(cursa.getNrPersoane() + 1);
                service.updateCursa(idCursa, cursa);

                cursaObservableList.setAll(service.findAllCursa());
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
        catch (RepositoryException e){
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

}
