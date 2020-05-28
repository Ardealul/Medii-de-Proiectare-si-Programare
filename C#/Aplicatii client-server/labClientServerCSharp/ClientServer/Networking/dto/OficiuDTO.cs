using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Networking.dto
{
    [Serializable]
    public class OficiuDTO
    {
        private string id;
        private string username;
        private string password;

        public OficiuDTO(string id, string username, string password)
        {
            this.id = id;
            this.username = username;
            this.password = password;
        }

        public virtual string Id { get { return id; } }
        public virtual string Username {get { return username; } }
        public virtual string Password { get { return password; } }
    }
}
