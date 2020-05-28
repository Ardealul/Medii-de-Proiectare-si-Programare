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
            System.out.println("client in loggedClients uiteeee: " + client);
            if (client != null)
                executor.execute(() -> {
                    try {
                        client.participantCursaAdded();
                    } catch (MyException e) {
                        System.err.println("Error notifying friend " + e);
                    }
                });
        }
        executor.shutdown();
    }

    @Override
    public synchronized Cursa[] findAllCursa() {
        List<Cursa> listaCursa = new ArrayList<>();
        cursaRepository.findAll().forEach(listaCursa::add);
        return listaCursa.toArray(new Cursa[listaCursa.size()]);
    }

    @Override
    public synchronized Cursa findCursa(String idCursa) {
        return cursaRepository.findOne(idCursa);
    }

    @Override
    public synchronized void updateCursa(String idCursa, Cursa cursa) throws MyException {
        cursaRepository.update(idCursa, cursa);

        //
        newParticipantCursa();
    }

    @Override
    public synchronized Participant[] findAllParticipant(String echipa) {
        List<Participant> listaParticipanti = new ArrayList<>();
        for(Participant participant : participantRepository.findAll()){
            if(participant.getEchipa().equals(echipa))
                listaParticipanti.add(participant);
        }
        return listaParticipanti.toArray(new Participant[listaParticipanti.size()]);
    }

    @Override
    public synchronized Participant[] findAllParticipant() {
        List<Participant> listaParticipanti = new ArrayList<>();
        participantRepository.findAll().forEach(listaParticipanti::add);
        return listaParticipanti.toArray(new Participant[listaParticipanti.size()]);
    }

    @Override
    public synchronized Participant findParticipant(String nume, String echipa, Integer capMotor) {
        return participantRepository.findOneByNumeEchipaCapMotor(nume, echipa, capMotor);
    }

    @Override
    public synchronized String findAllEchipe() {
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
    public synchronized void saveParticipantCursa(ParticipantCursa participantCursa) {
        participantCursaRepository.save(participantCursa);
    }

    @Override
    public Oficiu findOficiuByUserPass(String username, String password) {
        return oficiuRepository.findByUserPass(username, password);
    }

    @Override
    public void setClient(IObserver client) {
    }
}
