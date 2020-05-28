using Networking.dto;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Networking
{
    public interface Request
    {
    }

    [Serializable]
    public class LoginRequest : Request
    {
        private OficiuDTO oficiuDTO;

        public LoginRequest(OficiuDTO oficiuDTO)
        {
            this.oficiuDTO = oficiuDTO;
        }
        public virtual OficiuDTO OficiuDTO { get { return oficiuDTO; } }
    }

    [Serializable]
    public class LogoutRequest : Request
    {
        private OficiuDTO oficiuDTO;

        public LogoutRequest(OficiuDTO oficiuDTO)
        {
            this.oficiuDTO = oficiuDTO;
        }
        public virtual OficiuDTO OficiuDTO { get { return oficiuDTO; } }
    }

    [Serializable]
    public class FindOficiuByUserPassRequest : Request
    {
        private string username;
        private string password;

        public FindOficiuByUserPassRequest(string username, string password)
        {
            this.username = username;
            this.password = password;
        }
        public virtual string Username { get { return username; } }
        public virtual string Password { get { return password; } }
    }

    [Serializable]
    public class FindAllParticipantRequest: Request { }

    [Serializable]
    public class FindAllCursaRequest : Request { }

    [Serializable]
    public class FindAllParticipantEchipaRequest: Request
    {
        private string echipa;

        public FindAllParticipantEchipaRequest(string echipa)
        {
            this.echipa = echipa;
        }
        public virtual string Echipa { get { return echipa; } }
    }

    [Serializable]
    public class FindParticipantRequest: Request
    {
        private string nume;
        private string echipa;
        private int capMotor;

        public FindParticipantRequest(string nume, string echipa, int capMotor)
        {
            this.nume = nume;
            this.echipa = echipa;
            this.capMotor = capMotor;
        }
        public virtual string Nume { get { return nume; } }
        public virtual string Echipa { get { return echipa; } }
        public virtual int CapMotor { get { return capMotor; } }
    }

    [Serializable]
    public class FindCursaRequest: Request
    {
        private string id;

        public FindCursaRequest(string id)
        {
            this.id = id;
        }
        public virtual string Id { get { return id; } }
    }

    [Serializable]
    public class SaveParticipantCursaRequest: Request
    {
        private ParticipantCursaDTO participantCursaDTO;

        public SaveParticipantCursaRequest(ParticipantCursaDTO participantCursaDTO)
        {
            this.participantCursaDTO = participantCursaDTO;
        }
        public virtual ParticipantCursaDTO ParticipantCursaDTO { get { return participantCursaDTO; } }
    }

    [Serializable]
    public class DeleteCursaRequest: Request
    {
        private string id;

        public DeleteCursaRequest(string id)
        {
            this.id = id;
        }
        public virtual string Id { get { return id; } }
    }

    [Serializable]
    public class SaveCursaRequest: Request
    {
        private CursaDTO cursaDTO;

        public SaveCursaRequest(CursaDTO cursaDTO)
        {
            this.cursaDTO = cursaDTO;
        }
        public virtual CursaDTO CursaDTO { get { return cursaDTO; } }
    }

    [Serializable]
    public class NotifyRequest: Request
    {
        private CursaDTO[] cursaDTOs;

        public NotifyRequest(CursaDTO[] cursaDTOs)
        {
            this.cursaDTOs = cursaDTOs;
        }
        public virtual CursaDTO[] CursaDTOs { get { return cursaDTOs; } }
    }
}
