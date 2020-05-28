package server;

import model.Cursa;
import model.Oficiu;
import model.Participant;
import model.ParticipantCursa;
import repository.CursaRepository;
import repository.OficiuRepository;
import repository.ParticipantCursaRepository;
import repository.ParticipantRepository;
import services.IObserver;
import services.IServices;
import services.MyException;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class ServiceImpl implements IServices {

    private OficiuRepository oficiuRepository;
    private ParticipantRepository participantRepository;
    private CursaRepository cursaRepository;
    private ParticipantCursaRepository participantCursaRepository;
    private Map<String, IObserver> loggedClients;

    public ServiceImpl(OficiuRepository oficiuRepository, ParticipantRepository participantRepository, CursaRepository cursaRepository, ParticipantCursaRepository participantCursaRepository) {
        this.oficiuRepository = oficiuRepository;
        this.participantRepository = participantRepository;
        this.cursaRepository = cursaRepository;
        this.participantCursaRepository = participantCursaRepository;
        this.loggedClients = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized Oficiu findOficiuByUserPass(String username, String password, IObserver client) throws MyException {
        Oficiu oficiu = oficiuRepository.findByUserPass(username, password);
        System.out.println("Oficiul la login in ServiceImpl" + oficiu);
        if (oficiu != null){
            if (loggedClients.get(oficiu.getId()) != null)
                throw new MyException("User already logged in.");
            loggedClients.put(oficiu.getId(), client);
        } else
            throw new MyException("Authentication failed.");
        return oficiu;
    }

    @Override
    public synchronized void logout(Oficiu oficiu, IObserver client) throws MyException {
        System.out.println("Oficiul la logout in ServiceImpl este" + oficiu);
        IObserver localClient = loggedClients.remove(oficiu.getId());
        System.out.println("Oficiu de care ma deloghez este: " + oficiu);
        if (localClient == null)
            throw new MyException("User " + oficiu.getId() + " is not logged in.");
    }


    private final int defaultThreadsNo = 5;
    public synchronized void newParticipantCursa() {
        Iterable<Oficiu> oficiu = oficiuRepository.findAll();

        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        for(Oficiu of : oficiu){
            IObserver client = loggedClients.get(of.getId());
            if (client != null)
                executor.execute(() -> {
                    try {
                        client.participantCursaAdded();
                    } catch (MyException | RemoteException e) {
                        System.err.println("Error notifying friend " + e);
                    }
                });
        }
        executor.shutdown();
    }

    @Override
    public synchronized Cursa[] findAllCursa() throws MyException {
        List<Cursa> listaCursa = new ArrayList<>();
        cursaRepository.findAll().forEach(listaCursa::add);
        return listaCursa.toArray(new Cursa[listaCursa.size()]);
    }

    @Override
    public synchronized Cursa findCursa(String idCursa) throws MyException{
        return cursaRepository.findOne(idCursa);
    }

    @Override
    public synchronized void updateCursa(String idCursa, Cursa cursa) throws MyException {
        cursaRepository.update(idCursa, cursa);
        newParticipantCursa();
    }

    @Override
    public synchronized Participant[] findAllParticipant(String echipa) throws MyException {
        List<Participant> listaParticipanti = new ArrayList<>();
        for(Participant participant : participantRepository.findAll()){
            if(participant.getEchipa().equals(echipa))
                listaParticipanti.add(participant);
        }
        return listaParticipanti.toArray(new Participant[listaParticipanti.size()]);
    }

    @Override
    public synchronized Participant[] findAllParticipant() throws MyException {
        List<Participant> listaParticipanti = new ArrayList<>();
        participantRepository.findAll().forEach(listaParticipanti::add);
        return listaParticipanti.toArray(new Participant[listaParticipanti.size()]);
    }

    @Override
    public synchronized Participant findParticipant(String nume, String echipa, Integer capMotor) throws MyException {
        return participantRepository.findOneByNumeEchipaCapMotor(nume, echipa, capMotor);
    }

    @Override
    public synchronized String findAllEchipe() throws MyException{
        String echipe = "Echipele participantilor:\n";
        List<String> list = new ArrayList<>();
        for(Participant participant : findAllParticipant()) list.add(participant.getEchipa());
        List<String> result = list.stream().distinct().collect(Collectors.toList());
        for(String el : result){
            echipe = echipe + el + "\n";
        }
        return echipe;
    }

    @Override
    public synchronized void saveParticipantCursa(ParticipantCursa participantCursa) throws MyException {
        try{
            participantCursaRepository.save(participantCursa);
        }
        catch (Exception ex){
            throw new MyException();
        }
    }

    @Override
    public Oficiu findOficiuByUserPass(String username, String password) throws MyException {
        return oficiuRepository.findByUserPass(username, password);
    }

}
