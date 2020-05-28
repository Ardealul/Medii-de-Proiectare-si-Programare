using lab3.domain;
using lab3.repo;
using Networking.dto;
using Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Server
{
    public class ServerImpl: IServices
    {
        private OficiuRepository oficiuRepository;
        private ParticipantRepository participantRepository;
        private CursaRepository cursaRepository;
        private ParticipantCursaRepository participantCursaRepository;
        private readonly IDictionary<String, IObserver> loggedClients;

        public ServerImpl(OficiuRepository oficiuRepository, ParticipantRepository participantRepository, CursaRepository cursaRepository, ParticipantCursaRepository participantCursaRepository)
        {
            this.oficiuRepository = oficiuRepository;
            this.participantRepository = participantRepository;
            this.cursaRepository = cursaRepository;
            this.participantCursaRepository = participantCursaRepository;
            loggedClients = new Dictionary<String, IObserver>();
        }

        public Oficiu findOficiuByUserPass(string username, string password, IObserver client)
        {
            return oficiuRepository.findOne(username, password);
        }

        public Oficiu findOficiuByUserPass(string username, string password)
        {
            return oficiuRepository.findOne(username, password);
        }

        public void login(Oficiu oficiu, IObserver client)
        {
            Oficiu of = oficiuRepository.findOne(oficiu.Id);
            if (of != null)
            {
                if (loggedClients.ContainsKey(of.Id))
                    throw new MyException("User already logged in.");
                loggedClients[of.Id] = client;
            }
            else
                throw new MyException("Authentication failed.");
        }

        public void logout(Oficiu oficiu, IObserver client)
        {
            IObserver localClient = loggedClients[oficiu.Id];
            if (localClient == null)
                throw new MyException("User " + oficiu.Id + " is not logged in.");
            loggedClients.Remove(oficiu.Id);
        }

        public lab3.domain.Cursa findCursa(string id)
        {
            return cursaRepository.findOne(id);
        }

        public lab3.domain.Cursa[] findAllCursa()
        {
            IEnumerable<lab3.domain.Cursa> curse = cursaRepository.findAll();
            IList<lab3.domain.Cursa> result = new List<lab3.domain.Cursa>();
            foreach (lab3.domain.Cursa cursa in curse)
            {
                result.Add(cursa);
            }
            return result.ToArray();
        }

        public void submitted(lab3.domain.Cursa[] curse)
        {
            IEnumerable<Oficiu> oficiu = oficiuRepository.findAll();
            foreach (Oficiu of in oficiu)
            {
                if (loggedClients.ContainsKey(of.Id))
                {
                    IObserver client = loggedClients[of.Id];
                    Task.Run(() => client.participantCursaAdded(curse)); //trimit cursele
                }
            }
        }

        public lab3.domain.Cursa[] findAllCursa(int capMotor)
        {
            IEnumerable<lab3.domain.Cursa> curse = cursaRepository.findAll(capMotor);
            IList<lab3.domain.Cursa> result = new List<lab3.domain.Cursa>();
            foreach (lab3.domain.Cursa cursa in curse)
            {
                result.Add(cursa);
            }
            return result.ToArray();
        }

        public lab3.domain.Participant[] findAllParticipant(string echipa)
        {
            IEnumerable<lab3.domain.Participant> participanti = participantRepository.findAll(echipa);
            IList<lab3.domain.Participant> result = new List<lab3.domain.Participant>();
            foreach (lab3.domain.Participant participant in participanti)
            {
                result.Add(participant);
            }
            return result.ToArray();
        }

        public lab3.domain.Participant[] findAllParticipant()
        {
            IEnumerable<lab3.domain.Participant> participanti = participantRepository.findAll();
            IList<lab3.domain.Participant> result = new List<lab3.domain.Participant>();
            foreach (lab3.domain.Participant participant in participanti)
            {
                result.Add(participant);
            }
            return result.ToArray();
        }

        public lab3.domain.Participant findParticipant(string nume, string echipa, int capMotor)
        {
            return participantRepository.findOne(nume, echipa, capMotor);
        }

        public void saveParticipantCursa(lab3.domain.ParticipantCursa participantCursa)
        {
            participantCursaRepository.save(participantCursa);
        }

        public void saveCursa(lab3.domain.Cursa cursa)
        {
            cursaRepository.save(cursa);
        }

        public void deleteCursa(string idCursa)
        {
            cursaRepository.delete(idCursa);
        }

    }
}
