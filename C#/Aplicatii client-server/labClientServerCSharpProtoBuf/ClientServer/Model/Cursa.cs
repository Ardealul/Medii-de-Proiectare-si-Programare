using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace lab3.domain
{
    public class Cursa : HasId<string>
    {
        public int NrPersoane { get; set; }

        public int CapacitateMotor { get; set; }

        public string Id { get; set; }

        public Cursa(int nrPersoane, int capacitateMotor)
        {
            NrPersoane = nrPersoane;
            CapacitateMotor = capacitateMotor;
        }

        public override bool Equals(object obj)
        {
            var cursa = obj as Cursa;
            return cursa != null &&
                   Id == cursa.Id;
        }

        public override int GetHashCode()
        {
            return base.GetHashCode();
        }

        public override string ToString()
        {
            return string.Format("[Cursa-> id:{0}, nrPersoane:{1}, capMotor:{2}]", Id, NrPersoane, CapacitateMotor);
        }

    }
}
