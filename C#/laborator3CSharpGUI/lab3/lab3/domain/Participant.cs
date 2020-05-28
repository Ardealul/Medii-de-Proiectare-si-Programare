using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace lab3.domain
{
    public class Participant:HasId<string>
    {
        public string Nume { get; set; }
        public string Echipa { get; set; }
        public int CapacitateMotor { get; set; }
        public string Id { get; set; }

        public Participant(string nume, string echipa, int capactitateMotor)
        {
            Nume = nume;
            Echipa = echipa;
            CapacitateMotor = capactitateMotor;
        }

        public override bool Equals(object obj)
        {
            var participant = obj as Participant;
            return participant != null &&
                   Id == participant.Id;
        }

        public override int GetHashCode()
        {
            return base.GetHashCode();
        }

        public override string ToString()
        {
            return string.Format("[Participant-> id:{0}, nume:{1}, echipa:{2}, capMotor:{3}]",
                Id, Nume, Echipa, CapacitateMotor);
        }
    }
}
