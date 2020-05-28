using lab3.domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Networking.dto
{
    public class DTOUtils
    {
        public static Oficiu getFromDTO(OficiuDTO oficiuDTO)
        {
            string id = oficiuDTO.Id;
            string username = oficiuDTO.Username;
            string password = oficiuDTO.Password;
            return new Oficiu(username, password) { Id = id };
        }

        public static OficiuDTO getDTO(Oficiu oficiu)
        {
            string id = oficiu.Id;
            string username = oficiu.Username;
            string password = oficiu.Password;
            return new OficiuDTO(id, username, password);
        }

        public static Participant getFromDTO(ParticipantDTO participantDTO)
        {
            string id = participantDTO.Id;
            string nume = participantDTO.Nume;
            string echipa = participantDTO.Echipa;
            int capMotor = participantDTO.CapMotor;
            return new Participant(nume, echipa, capMotor) { Id = id };
        }

        public static ParticipantDTO getDTO(Participant participant)
        {
            string id = participant.Id;
            string nume = participant.Nume;
            string echipa = participant.Echipa;
            int capMotor = participant.CapacitateMotor;
            return new ParticipantDTO(id, nume, echipa, capMotor);
        }

        public static Cursa getFromDTO(CursaDTO cursaDTO)
        {
            string id = cursaDTO.Id;
            int nrPersoane = cursaDTO.NrPersoane;
            int capMotor = cursaDTO.CapMotor;
            return new Cursa(nrPersoane, capMotor) { Id = id };
        }

        public static CursaDTO getDTO(Cursa cursa)
        {
            string id = cursa.Id;
            int nrPersoane = cursa.NrPersoane;
            int capMotor = cursa.CapacitateMotor;
            return new CursaDTO(id, nrPersoane, capMotor);
        }

        public static ParticipantCursa getFromDTO(ParticipantCursaDTO participantCursaDTO)
        {
            Tuple<string, string> id = participantCursaDTO.Id;
            string idP = participantCursaDTO.IdParticipant;
            string idC = participantCursaDTO.IdCursa;
            return new ParticipantCursa(idP, idC) { Id = id };
        }

        public static ParticipantCursaDTO getDTO(ParticipantCursa participantCursa)
        {
            Tuple<string, string> id = participantCursa.Id;
            string idP = participantCursa.IdParticipant;
            string idC = participantCursa.IdCursa;
            return new ParticipantCursaDTO(id, idP, idC);
        }

        public static CursaDTO[] getDTO(Cursa[] curse)
        {
            CursaDTO[] curseDTO = new CursaDTO[curse.Length];
            for (int i = 0; i < curse.Length; i++)
            {
                curseDTO[i] = getDTO(curse[i]);
            }
            return curseDTO;
        }

        public static Cursa[] getFromDTO(CursaDTO[] curseDTO)
        {
            Cursa[] curse = new Cursa[curseDTO.Length];
            for (int i = 0; i < curseDTO.Length; i++)
            {
                curse[i] = getFromDTO(curseDTO[i]);
            }
            return curse;
        }

        public static ParticipantDTO[] getDTO(Participant[] participanti)
        {
            ParticipantDTO[] participantiDTO = new ParticipantDTO[participanti.Length];
            for(int i = 0; i < participanti.Length; i++)
            {
                participantiDTO[i] = getDTO(participanti[i]);
            }
            return participantiDTO;
        }

        public static Participant[] getFromDTO(ParticipantDTO[] participantiDTO)
        {
            Participant[] participanti = new Participant[participantiDTO.Length];
            for(int i = 0; i <participantiDTO.Length; i++)
            {
                participanti[i] = getFromDTO(participantiDTO[i]);
            }
            return participanti;
        }
    }
}
