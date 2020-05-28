using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Networking.dto
{
    [Serializable]
    public class ParticipantCursaDTO
    {
        private Tuple<string, string> id;
        private string idParticipant;
        private string idCursa;

        public ParticipantCursaDTO(Tuple<string, string> id, string idParticipant, string idCursa)
        {
            this.id = id;
            this.idParticipant = idParticipant;
            this.idCursa = idCursa;
        }

        public virtual Tuple<string, string> Id { get { return id; } }
        public virtual string IdParticipant { get { return idParticipant; } }
        public virtual string IdCursa { get { return idCursa; } }
    }
}
