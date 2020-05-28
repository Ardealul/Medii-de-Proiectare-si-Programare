using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Services
{
    public class MyException: Exception
    {
        public MyException() : base() { }

        public MyException(String msg) : base(msg) { }

        public MyException(String msg, Exception ex) : base(msg,ex) { }
    }
}
