using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using lab3.domain;
using lab3.repo;

namespace lab3.service
{
    class Service
    {
        private OficiuRepository OficiuRepository;
        private ParticipantRepository ParticipantRepository;
        private CursaRepository CursaRepository;
        private ParticipantCursaRepository ParticipantCursaRepository;

        public Service(OficiuRepository oficiuRepository, ParticipantRepository participantRepository, CursaRepository cursaRepository, ParticipantCursaRepository participantCursaRepository)
        {
            OficiuRepository = oficiuRepository;
            ParticipantRepository = participantRepository;
            CursaRepository = cursaRepository;
            ParticipantCursaRepository = participantCursaRepository;
        }

        //Oficiu
        public Oficiu findOficiu(string id)
        {
            return OficiuRepository.findOne(id);
        }

        public Oficiu findOficiu(string username, string password)
        {
            return OficiuRepository.findOne(username, password);
        }

        public IList<Oficiu> findAllOficiu()
        {
            IList<Oficiu> oficiuList = new List<Oficiu>();
            foreach (Oficiu oficiu in OficiuRepository.findAll())
            {
                oficiuList.Add(oficiu);
            }
            return oficiuList;
        }

        //Participant
        public Participant findParticipant(string id)
        {
            return ParticipantRepository.findOne(id);
        }

        public Participant findParticipant(string nume, string echipa, int capMotor)
        {
            return ParticipantRepository.findOne(nume, echipa, capMotor);
        }

        public IList<Participant> findAllParticipant()
        {
            IList<Participant> participantList = new List<Participant>();
            foreach (Participant participant in ParticipantRepository.findAll())
            {
                participantList.Add(participant);
            }
            return participantList;
        }

        public IList<Participant> findAllParticipant(string echipa)
        {
            IList<Participant> participantList = new List<Participant>();
            foreach (Participant participant in ParticipantRepository.findAll(echipa))
            {
                participantList.Add(participant);
            }
            return participantList;
        }

        //Cursa
        public Cursa findCursa(string id)
        {
            return CursaRepository.findOne(id);
        }

        public IList<Cursa> findAllCursa()
        {
            IList<Cursa> cursaList = new List<Cursa>();
            foreach (Cursa cursa in CursaRepository.findAll())
            {
                cursaList.Add(cursa);
            }
            return cursaList;
        }

        public IList<Cursa> findAllCursa(int capMotor)
        {
            IList<Cursa> cursaList = new List<Cursa>();
            foreach (Cursa cursa in CursaRepository.findAll(capMotor))
            {
                cursaList.Add(cursa);
            }
            return cursaList;
        }

        //ParticipantCursa
        public void saveParticipantCursa(ParticipantCursa participantCursa)
        {
            ParticipantCursaRepository.save(participantCursa);
        }
    }
}
