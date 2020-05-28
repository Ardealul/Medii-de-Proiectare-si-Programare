using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace lab3.domain
{
    public class ParticipantCursa: HasId<Tuple<string, string>>
    {
        public Tuple<string, string> Id { get; set; }
        public string IdParticipant { get; set; }
        public string IdCursa { get; set; }

        public ParticipantCursa(string idParticipant, string idCursa)
        {
            IdParticipant = idParticipant;
            IdCursa = idCursa;
        }

        public override bool Equals(object obj)
        {
            var cursa = obj as ParticipantCursa;
            return cursa != null &&
                   IdParticipant == cursa.IdParticipant &&
                   IdCursa == cursa.IdCursa;
        }

        public override int GetHashCode()
        {
            return base.GetHashCode();
        }

        public override string ToString()
        {
            return string.Format("[ParticipantCursa-> idParticipant:{0}, idCursa:{1}]", IdParticipant, IdCursa);
        }
    }
}
