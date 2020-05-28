package services;

import model.Cursa;
import model.Oficiu;
import model.Participant;
import model.ParticipantCursa;

public interface IServices {

    Oficiu findOficiuByUserPass(String username, String password, IObserver client) throws MyException;
    void logout(Oficiu oficiu, IObserver client) throws MyException;
    Cursa[] findAllCursa() throws MyException;
    Cursa findCursa(String idCursa) throws MyException;
    void updateCursa(String idCursa, Cursa cursa) throws MyException;
    Participant[] findAllParticipant(String echipa) throws MyException;
    Participant[] findAllParticipant() throws MyException;
    Participant findParticipant(String nume, String echipa, Integer capMotor) throws MyException;
    String findAllEchipe() throws MyException;
    void saveParticipantCursa(ParticipantCursa participantCursa) throws MyException;

    Oficiu findOficiuByUserPass(String username, String password) throws MyException;

    void setClient(IObserver client);
}
