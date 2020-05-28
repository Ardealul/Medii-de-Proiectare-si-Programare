using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Networking.dto
{
    [Serializable]
    public class ParticipantDTO
    {
        private string id;
        private string nume;
        private string echipa;
        private int capMotor;

        public ParticipantDTO(string id, string nume, string echipa, int capMotor)
        {
            this.id = id;
            this.nume = nume;
            this.echipa = echipa;
            this.capMotor = capMotor;
        }

        public virtual string Id { get { return id; } }
        public virtual string Nume { get { return nume; } }
        public virtual string Echipa { get { return echipa; } }
        public virtual int CapMotor { get { return capMotor; } }
    }
}
