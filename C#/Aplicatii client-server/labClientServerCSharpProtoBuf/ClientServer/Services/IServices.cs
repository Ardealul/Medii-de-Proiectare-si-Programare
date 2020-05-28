using lab3.domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Services
{
    public interface IServices
    {
        Oficiu findOficiuByUserPass(string username, string password, IObserver client);
        Oficiu findOficiuByUserPass(string username, string password);
        void login(Oficiu oficiu, IObserver client);
        void logout(Oficiu oficiu, IObserver client);
        Cursa findCursa(string id);
        Cursa[] findAllCursa();
        Cursa[] findAllCursa(int capMotor);
        Participant[] findAllParticipant(string echipa);
        Participant[] findAllParticipant();
        Participant findParticipant(string nume, string echipa, int capMotor);
        void saveParticipantCursa(ParticipantCursa participantCursa);
        void saveCursa(Cursa cursa);
        void deleteCursa(string idCursa);
        void submitted(Cursa[] cursa);
    }
}
