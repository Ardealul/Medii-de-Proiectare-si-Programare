package service;

import domain.Cursa;
import domain.Oficiu;
import domain.Participant;
import domain.ParticipantCursa;
import domain.validator.ParticipantCursaValidator;
import domain.validator.Validator;
import domain.validator.ValidatorException;
import javafx.util.Pair;
import repo.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Service {
    private OficiuRepository oficiuRepository;
    private ParticipantRepository participantRepository;
    private CursaRepository cursaRepository;
    private ParticipantCursaRepository participantCursaRepository;

    public Service(OficiuRepository oficiuRepository, ParticipantRepository participantRepository, CursaRepository cursaRepository, ParticipantCursaRepository participantCursaRepository) {
        this.oficiuRepository = oficiuRepository;
        this.participantRepository = participantRepository;
        this.cursaRepository = cursaRepository;
        this.participantCursaRepository = participantCursaRepository;
    }

    //Oficiu
    public void saveOficiu(Oficiu oficiu){
        oficiuRepository.save(oficiu);
    }

    public void deleteOficiu(String id){
        oficiuRepository.delete(id);
    }

    public void updateOficiu(String id, Oficiu oficiu){
        oficiuRepository.update(id, oficiu);
    }

    public Oficiu findOficiu(String id){
        return oficiuRepository.findOne(id);
    }

    public Oficiu findOficiuByUserPass(String username, String password){
        return oficiuRepository.findByUserPass(username, password);
    }

    public List<Oficiu> findAllOficiu(){
        List<Oficiu> all = new ArrayList<>();
        oficiuRepository.findAll().forEach(all::add);
        return all;
    }

    //Cursa
    public void saveCursa(Cursa cursa){
        cursaRepository.save(cursa);
    }

    public void deleteCursa(String id){
        cursaRepository.delete(id);
    }

    public void updateCursa(String id, Cursa cursa){
        cursaRepository.update(id, cursa);
    }

    public Cursa findCursa(String id){
        return cursaRepository.findOne(id);
    }

    public List<Cursa> findAllCursa(){
        List<Cursa> all = new ArrayList<>();
        cursaRepository.findAll().forEach(all::add);
        return all;
    }

    public List<Cursa> findAllCursa(Integer capMotor){
        List<Cursa> all = new ArrayList<>();
        cursaRepository.findAll(capMotor).forEach(all::add);
        return all;
    }

    //Participant
    public void saveParticipant(Participant participant){
        participantRepository.save(participant);
    }

    public void deleteParticipant(String id){
        participantRepository.delete(id);
    }

    public void updateParticipant(String id, Participant participant){
        participantRepository.update(id, participant);
    }

    public Participant findParticipant(String id){
        return participantRepository.findOne(id);
    }

    public Participant findParticipant(String nume, String echipa, Integer capMotor){
        return participantRepository.findOneByNumeEchipaCapMotor(nume, echipa, capMotor);
    }

    public List<Participant> findAllParticipant(){
        List<Participant> all = new ArrayList<>();
        participantRepository.findAll().forEach(all::add);
        return all;
    }

    public List<Participant> findAllParticipant(String echipa){
        List<Participant> all = new ArrayList<>();
        for(Participant participant : participantRepository.findAll()){
            if(participant.getEchipa().equals(echipa))
                all.add(participant);
        }
        return all;
    }

    public String findAllEchipe(){
        String echipe = "Echipele participantilor:\n";
        List<String> list = new ArrayList<>();
        for(Participant participant : findAllParticipant()) list.add(participant.getEchipa());
        List<String> result = list.stream().distinct().collect(Collectors.toList());
        for(String el : result){
            echipe = echipe + el + "\n";
        }
        return echipe;
    }

    //ParticipantCursa
    public void saveParticipantCursa(ParticipantCursa participantCursa){
        participantCursaRepository.save(participantCursa);
    }

    public void deleteParticipantCursa(String idParticipant, String idCursa){
        participantCursaRepository.delete(new Pair<>(idParticipant, idCursa));
    }

    public void updateParticipantCursa(String idParticipant, String idCursa, ParticipantCursa participantCursa){
        participantCursaRepository.update(new Pair<>(idParticipant, idCursa), participantCursa);
    }

    public ParticipantCursa findParticipantCursa(String idParticipant, String idCursa){
        return participantCursaRepository.findOne(new Pair<>(idParticipant, idCursa));
    }

    public List<ParticipantCursa> findAllParticipantCursa(){
        List<ParticipantCursa> all = new ArrayList<>();
        participantCursaRepository.findAll().forEach(all::add);
        return all;
    }


}
