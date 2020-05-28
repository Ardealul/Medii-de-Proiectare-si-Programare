using Networking.dto;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Networking
{
    public interface Response
    {
    }

    [Serializable]
    public class OkResponse : Response { }

    [Serializable]
    public class ErrorResponse : Response
    {
        private string message;
        public ErrorResponse(string message)
        {
            this.message = message;
        }
        public virtual string Message
        {
            get { return message; }
        }
    }

    [Serializable]
    public class FindOficiuByUserPassResponse : Response
    {
        private OficiuDTO oficiuDTO;

        public FindOficiuByUserPassResponse(OficiuDTO oficiuDTO)
        {
            this.oficiuDTO = oficiuDTO;
        }
        public virtual OficiuDTO OficiuDTO { get { return oficiuDTO; } }
    }

    [Serializable]
    public class FindAllParticipantResponse: Response
    {
        private ParticipantDTO[] participantDTOs;

        public FindAllParticipantResponse(ParticipantDTO[] participantDTOs)
        {
            this.participantDTOs = participantDTOs;
        }
        public virtual ParticipantDTO[] ParticipantDTOs { get { return participantDTOs; } }
    }

    [Serializable]
    public class FindAllCursaResponse: Response
    {
        private CursaDTO[] cursaDTOs;

        public FindAllCursaResponse(CursaDTO[] cursaDTOs)
        {
            this.cursaDTOs = cursaDTOs;
        }
        public virtual CursaDTO[] CursaDTOs { get { return cursaDTOs; } }
    }

    [Serializable]
    public class FindAllParticipantEchipaResponse: Response
    {
        private ParticipantDTO[] participantDTOs;

        public FindAllParticipantEchipaResponse(ParticipantDTO[] participantDTOs)
        {
            this.participantDTOs = participantDTOs;
        }
        public virtual ParticipantDTO[] ParticipantDTOs { get { return participantDTOs; } }
    }

    [Serializable]
    public class FindParticipantResponse: Response
    {
        private ParticipantDTO participantDTO;

        public FindParticipantResponse(ParticipantDTO participantDTO)
        {
            this.participantDTO = participantDTO;
        }
        public virtual ParticipantDTO ParticipantDTO { get { return participantDTO; } }
    }

    [Serializable]
    public class FindCursaResponse: Response
    {
        private CursaDTO cursaDTO;

        public FindCursaResponse(CursaDTO cursaDTO)
        {
            this.cursaDTO = cursaDTO;
        }
        public virtual CursaDTO CursaDTO { get { return cursaDTO; } }
    }

    public interface UpdateResponse : Response
    {
    }

    [Serializable]
    public class NewParticipantCursaResponse : UpdateResponse
    {
        private CursaDTO[] cursaDTOs;

        public NewParticipantCursaResponse(CursaDTO[] cursaDTOs)
        {
            this.cursaDTOs = cursaDTOs;
        }
        public virtual CursaDTO[] CursaDTOs { get { return cursaDTOs; } }
    }


}
