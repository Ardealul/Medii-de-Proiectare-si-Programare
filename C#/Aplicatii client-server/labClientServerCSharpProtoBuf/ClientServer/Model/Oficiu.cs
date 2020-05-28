using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace lab3.domain
{
    public class Oficiu:HasId<string>
    {
        public string Id { get; set; }
        public string Username { get; set; }
        public string Password { get; set; }

        public Oficiu(string username, string password)
        {
            Username = username;
            Password = password;
        }

        public override string ToString()
        {
            return string.Format("[Oficiu-> id:{0}, username:{1}, password:{2}]", Id, Username, Password);
        }

        public override int GetHashCode()
        {
            return base.GetHashCode();
        }

        public override bool Equals(object obj)
        {
            var oficiu = obj as Oficiu;
            return oficiu != null &&
                   Id == oficiu.Id;
        }

        
    }
}
