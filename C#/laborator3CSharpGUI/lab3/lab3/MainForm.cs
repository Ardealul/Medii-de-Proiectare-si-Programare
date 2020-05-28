using lab3.domain;
using lab3.repo;
using lab3.service;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace lab3
{
    public partial class MainForm : Form
    {
        OficiuRepository oficiuRepository = new OficiuRepository();
        ParticipantRepository participantRepository = new ParticipantRepository();
        CursaRepository cursaRepository = new CursaRepository();
        ParticipantCursaRepository participantCursaRepository = new ParticipantCursaRepository();
        Service service;

        public MainForm()
        {
            InitializeComponent();
            service =
                new Service(oficiuRepository, participantRepository, cursaRepository, participantCursaRepository);

            initialize();
        }

        private void initialize()
        {
            curseGridView.DataSource = service.findAllCursa();
            participantiGridView.DataSource = service.findAllParticipant();
            setComboBox();
        }

        private void setComboBox()
        {
            List<int> lista = new List<int>();
            lista.Add(125); lista.Add(250); lista.Add(500); lista.Add(1000);
            categorieComboBox.DataSource = lista;

            capMotorComboBox.DataSource = lista;
        }

        private void logoutButton_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void curseGridView_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            int index = e.RowIndex;
            DataGridViewRow selectedRow = curseGridView.Rows[index];
            idTextBox.Text = selectedRow.Cells[2].Value.ToString();
        }

        private void participantiGridView_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            int index = e.RowIndex;
            DataGridViewRow selectedRow = participantiGridView.Rows[index];
            numeTextBox.Text = selectedRow.Cells[0].Value.ToString();
            echipaTextBox.Text = selectedRow.Cells[1].Value.ToString();
            capMotorComboBox.SelectedIndex = capMotorComboBox.FindString(selectedRow.Cells[2].Value.ToString());
        }

        private void cautaButton_Click(object sender, EventArgs e)
        {
            string echipa = cautaEchipaTextBox.Text;
            participantiGridView.DataSource = service.findAllParticipant(echipa);
        }

        private void categorieComboBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            int capMotor = Int32.Parse(categorieComboBox.SelectedItem.ToString());
            curseGridView.DataSource = service.findAllCursa(capMotor);
        }

        private void inscrieButton_Click(object sender, EventArgs e)
        {
            try
            {
                string idCursa = idTextBox.Text;
                string nume = numeTextBox.Text;
                string echipa = echipaTextBox.Text;
                int capMotor = Int32.Parse(categorieComboBox.SelectedItem.ToString());

                string idParticipant = service.findParticipant(nume, echipa, capMotor).Id;
                Cursa cursa = service.findCursa(idCursa);

                ParticipantCursa participantCursa = new ParticipantCursa(idParticipant, idCursa)
                {
                    Id = new Tuple<string, string>(idParticipant, idCursa)
                };

                if (cursa.CapacitateMotor.ToString().Equals(capMotor.ToString()))
                {
                    participantCursaRepository.save(participantCursa);
                    cursaRepository.delete(idCursa);

                    cursa.NrPersoane = cursa.NrPersoane + 1;
                    cursaRepository.save(cursa);

                    curseGridView.DataSource = service.findAllCursa(Int32.Parse(categorieComboBox.SelectedItem.ToString()));
                }
                else
                    MessageBox.Show("Capacitatea motorului participantului nu se potriveste cu categoria cursei!");
            }
            catch (NullReferenceException ex)
            {
                MessageBox.Show("Selectati participantul si cursa la care doriti sa-l inscrieti!");
            }
            catch (Exception ex)
            {
                MessageBox.Show("Participantul a fost adaugat deja la cursa respectiva!");
            }
        }
    }
}
