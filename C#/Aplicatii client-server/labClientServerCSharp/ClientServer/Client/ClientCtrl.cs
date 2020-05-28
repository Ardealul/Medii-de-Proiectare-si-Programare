using lab3.domain;
using Networking.dto;
using Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ClientServer
{
    public class ClientCtrl: IObserver
    {
        public event EventHandler<UserEventArgs> updateEvent;
        private readonly IServices server;
        private Oficiu currentUser;

        public ClientCtrl(IServices server)
        {
            this.server = server;
            currentUser = null;
        }

        //----------------------------------------
        public void participantCursaAdded(Cursa[] curse)
        {
            Console.WriteLine("participantCursaAdded in Ctrl");
            UserEventArgs userEventArgs = new UserEventArgs(UserEvent.NewParticipantCursa, curse);
            OnUserEvent(userEventArgs);
        }

        protected virtual void OnUserEvent(UserEventArgs e)
        {
            Console.WriteLine("onUserEvent in Ctrl");
            if (updateEvent == null) return;
            updateEvent(this, e);
            Console.WriteLine("Update Event called");
        }
        //----------------------------------------

        public void submitted()
        {
            Cursa[] curse = server.findAllCursa();
            server.submitted(curse);
        }

        public Oficiu login(string username, string password)
        {
            Oficiu oficiu = server.findOficiuByUserPass(username, password);
            server.login(oficiu, this);
            Console.WriteLine("Login succeded...");
            currentUser = oficiu;
            Console.WriteLine("Current user is {0}", oficiu);
            return oficiu;
        }

        public void logout()
        {
            Console.WriteLine("Logout...");
            server.logout(currentUser, this);
            currentUser = null;
        }

        public Cursa[] findAllCursa()
        {
            return server.findAllCursa();
        }

        public Participant[] findAllParticipant()
        {
            return server.findAllParticipant();
        }

        public Participant[] findAllParticipant(string echipa)
        {
            return server.findAllParticipant(echipa);
        }

        public Participant findParticipant(string nume, string echipa, int capMotor)
        {
            return server.findParticipant(nume, echipa, capMotor);
        }

        public Cursa findCursa(string id)
        {
            return server.findCursa(id);
        }

        public void saveParticipantCursa(ParticipantCursa participantCursa)
        {
            server.saveParticipantCursa(participantCursa);
        }

        public void deleteCursa(string id)
        {
            server.deleteCursa(id);
        }

        public void saveCursa(Cursa cursa)
        {
            server.saveCursa(cursa);
        }
    }
}
