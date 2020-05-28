using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ClientServer
{
    public enum UserEvent
    {
        NewParticipantCursa
    };
    public class UserEventArgs: EventArgs
    {
        private readonly UserEvent userEvent;
        private readonly Object data;

        public UserEventArgs(UserEvent userEvent, Object data)
        {
            this.userEvent = userEvent;
            this.data = data;
        }
        public UserEvent UserEvent { get { return userEvent; } }
        public object Data { get { return data; } }
    }
}
