using lab3;
using Networking;
using Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace ClientServer
{
    static class StartClient
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
       
            IServices server = new ServerProxy("127.0.0.1", 55555);
            ClientCtrl ctrl = new ClientCtrl(server);
            LoginForm win = new LoginForm(ctrl);
            Application.Run(win);
        }
    }
}
