using lab3.domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Services
{
    public interface IObserver
    {
        void participantCursaAdded(Cursa[] cursa);
    }
}
