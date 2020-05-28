using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using lab3.repo;

namespace lab3
{
    public partial class LoginForm : Form
    {
        public LoginForm()
        {
            InitializeComponent();
            passwordTextBox.PasswordChar = '*';
            passwordTextBox.MaxLength = 20;
            usernameTextBox.MaxLength = 20;
        }

        private void loginButton_Click(object sender, EventArgs e)
        {
            string username = usernameTextBox.Text;
            string password = passwordTextBox.Text;

            OficiuRepository oficiuRepository = new OficiuRepository();
            if(oficiuRepository.findOne(username, password) != null)
            {
                MainForm mainForm = new MainForm();
                mainForm.Text = username;
                mainForm.Show();
            }
            else
                MessageBox.Show("Username sau parola incorecta!");
            
        }
    }
}
