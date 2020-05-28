using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Networking.dto
{
    [Serializable]
    public class CursaDTO
    {
        private string id;
        private int nrPersoane;
        private int capMotor;

        public CursaDTO(string id, int nrPersoane, int capMotor)
        {
            this.id = id;
            this.nrPersoane = nrPersoane;
            this.capMotor = capMotor;
        }

        public virtual string Id { get { return id; } }
        public virtual int NrPersoane { get { return nrPersoane; } }
        public virtual int CapMotor { get { return capMotor; } }
    }
}
