package services;

import model.Cursa;
import model.Oficiu;
import model.Participant;
import model.ParticipantCursa;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServices extends Remote {

    Oficiu findOficiuByUserPass(String username, String password, IObserver client) throws MyException, RemoteException;
    void logout(Oficiu oficiu, IObserver client) throws MyException, RemoteException;
    Cursa[] findAllCursa() throws MyException, RemoteException;
    Cursa findCursa(String idCursa) throws MyException, RemoteException;
    void updateCursa(String idCursa, Cursa cursa) throws MyException, RemoteException;
    Participant[] findAllParticipant(String echipa) throws MyException, RemoteException;
    Participant[] findAllParticipant() throws MyException, RemoteException;
    Participant findParticipant(String nume, String echipa, Integer capMotor) throws MyException, RemoteException;
    String findAllEchipe() throws MyException, RemoteException;
    void saveParticipantCursa(ParticipantCursa participantCursa) throws MyException, RemoteException;

    Oficiu findOficiuByUserPass(String username, String password) throws MyException, RemoteException;

    void setClient(IObserver client) throws MyException, RemoteException;
}
