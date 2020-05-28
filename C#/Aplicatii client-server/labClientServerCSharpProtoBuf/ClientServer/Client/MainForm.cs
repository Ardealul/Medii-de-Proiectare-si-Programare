using ClientServer;
using lab3.domain;
using lab3.repo;
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
        private readonly ClientCtrl ctrl;
        private Label label1;
        private Label label2;
        private DataGridView curseGridView;
        private DataGridView participantiGridView;
        private Label label3;
        private TextBox cautaEchipaTextBox;
        private Button cautaButton;
        private Label label4;
        private Label label5;
        private Label label6;
        private Label label7;
        private Label label8;
        private TextBox idTextBox;
        private TextBox numeTextBox;
        private TextBox echipaTextBox;
        private ComboBox capMotorComboBox;
        private Button logoutButton;
        private Button inscrieButton;

        public MainForm(ClientCtrl ctrl)
        {
            InitializeComponent();
            this.ctrl = ctrl;
            ctrl.updateEvent += Update;

            initialize();
        }

        //----------------------------------------
        private void updateTabelList(DataGridView dataGridView, List<Cursa> newdata)
        {
            dataGridView.DataSource = null;
            dataGridView.DataSource = newdata;
        }

        public delegate void UpdateTabelListCallback(DataGridView dataGridView, List<Cursa> data);

        public void Update(object sender, UserEventArgs e)
        {
            if (e.UserEvent == UserEvent.NewParticipantCursa)
            {
                Cursa[] curse = (Cursa[])e.Data;
                IList<Cursa> listaCurse = new List<Cursa>();
                foreach(Cursa c in curse)
                {
                    listaCurse.Add(c);
                }
                curseGridView.BeginInvoke(new UpdateTabelListCallback(updateTabelList), new Object[] { curseGridView, listaCurse });
                //curseGridView.Invoke(new UpdateTabelListCallback(updateTabelList), new Object[] { curseGridView, curse });
            }
        }
        //----------------------------------------

        private void initialize()
        {
            curseGridView.DataSource = ctrl.findAllCursa();
            participantiGridView.DataSource = ctrl.findAllParticipant();
            setComboBox();
        }

        private void setComboBox()
        {
            List<int> lista = new List<int>();
            lista.Add(125); lista.Add(250); lista.Add(500); lista.Add(1000);
            capMotorComboBox.DataSource = lista;
        }

        private void logoutButton_Click(object sender, EventArgs e)
        {
            ctrl.logout();
            ctrl.updateEvent -= Update;
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
            participantiGridView.DataSource = ctrl.findAllParticipant(echipa);
        }

        private void inscrieButton_Click(object sender, EventArgs e)
        {
            try
            {
                string idCursa = idTextBox.Text;
                string nume = numeTextBox.Text;
                string echipa = echipaTextBox.Text;
                int capMotor = Int32.Parse(capMotorComboBox.SelectedItem.ToString());

                string idParticipant = ctrl.findParticipant(nume, echipa, capMotor).Id;
                Cursa cursa = ctrl.findCursa(idCursa);

                ParticipantCursa participantCursa = new ParticipantCursa(idParticipant, idCursa)
                {
                    Id = new Tuple<string, string>(idParticipant, idCursa)
                };

                if (cursa.CapacitateMotor.ToString().Equals(capMotor.ToString()))
                {
                    ctrl.saveParticipantCursa(participantCursa);
                    ctrl.deleteCursa(idCursa);

                    cursa.NrPersoane = cursa.NrPersoane + 1;
                    ctrl.saveCursa(cursa);

                    curseGridView.DataSource = ctrl.findAllCursa();

                    //
                    ctrl.submitted();

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

        private void InitializeComponent()
        {
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.curseGridView = new System.Windows.Forms.DataGridView();
            this.participantiGridView = new System.Windows.Forms.DataGridView();
            this.label3 = new System.Windows.Forms.Label();
            this.cautaEchipaTextBox = new System.Windows.Forms.TextBox();
            this.cautaButton = new System.Windows.Forms.Button();
            this.label4 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.label6 = new System.Windows.Forms.Label();
            this.label7 = new System.Windows.Forms.Label();
            this.label8 = new System.Windows.Forms.Label();
            this.idTextBox = new System.Windows.Forms.TextBox();
            this.numeTextBox = new System.Windows.Forms.TextBox();
            this.echipaTextBox = new System.Windows.Forms.TextBox();
            this.capMotorComboBox = new System.Windows.Forms.ComboBox();
            this.logoutButton = new System.Windows.Forms.Button();
            this.inscrieButton = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.curseGridView)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.participantiGridView)).BeginInit();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(164, 37);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(72, 20);
            this.label1.TabIndex = 0;
            this.label1.Text = "CURSE";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label2.Location = new System.Drawing.Point(619, 37);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(133, 20);
            this.label2.TabIndex = 1;
            this.label2.Text = "PARTICIPANTI";
            // 
            // curseGridView
            // 
            this.curseGridView.AllowUserToAddRows = false;
            this.curseGridView.AllowUserToDeleteRows = false;
            this.curseGridView.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.curseGridView.Location = new System.Drawing.Point(27, 72);
            this.curseGridView.Name = "curseGridView";
            this.curseGridView.ReadOnly = true;
            this.curseGridView.RowTemplate.Height = 24;
            this.curseGridView.Size = new System.Drawing.Size(352, 140);
            this.curseGridView.TabIndex = 2;
            this.curseGridView.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.curseGridView_CellClick);
            // 
            // participantiGridView
            // 
            this.participantiGridView.AllowUserToAddRows = false;
            this.participantiGridView.AllowUserToDeleteRows = false;
            this.participantiGridView.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.participantiGridView.Location = new System.Drawing.Point(440, 74);
            this.participantiGridView.Name = "participantiGridView";
            this.participantiGridView.ReadOnly = true;
            this.participantiGridView.RowTemplate.Height = 24;
            this.participantiGridView.Size = new System.Drawing.Size(463, 226);
            this.participantiGridView.TabIndex = 3;
            this.participantiGridView.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.participantiGridView_CellClick);
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label3.Location = new System.Drawing.Point(534, 329);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(72, 20);
            this.label3.TabIndex = 4;
            this.label3.Text = "Echipa:";
            // 
            // cautaEchipaTextBox
            // 
            this.cautaEchipaTextBox.Location = new System.Drawing.Point(624, 329);
            this.cautaEchipaTextBox.Name = "cautaEchipaTextBox";
            this.cautaEchipaTextBox.Size = new System.Drawing.Size(128, 22);
            this.cautaEchipaTextBox.TabIndex = 5;
            // 
            // cautaButton
            // 
            this.cautaButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.cautaButton.Location = new System.Drawing.Point(783, 323);
            this.cautaButton.Name = "cautaButton";
            this.cautaButton.Size = new System.Drawing.Size(107, 32);
            this.cautaButton.TabIndex = 6;
            this.cautaButton.Text = "CAUTA";
            this.cautaButton.UseVisualStyleBackColor = true;
            this.cautaButton.Click += new System.EventHandler(this.cautaButton_Click);
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label4.Location = new System.Drawing.Point(123, 304);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(237, 20);
            this.label4.TabIndex = 7;
            this.label4.Text = "INSCRIERE PARTICIPANTI";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label5.Location = new System.Drawing.Point(29, 372);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(90, 20);
            this.label5.TabIndex = 8;
            this.label5.Text = "ID Cursa:";
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label6.Location = new System.Drawing.Point(29, 410);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(63, 20);
            this.label6.TabIndex = 9;
            this.label6.Text = "Nume:";
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label7.Location = new System.Drawing.Point(29, 451);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(72, 20);
            this.label7.TabIndex = 10;
            this.label7.Text = "Echipa:";
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label8.Location = new System.Drawing.Point(29, 496);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(102, 20);
            this.label8.TabIndex = 11;
            this.label8.Text = "Cap motor:";
            // 
            // idTextBox
            // 
            this.idTextBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.idTextBox.Location = new System.Drawing.Point(187, 370);
            this.idTextBox.Name = "idTextBox";
            this.idTextBox.Size = new System.Drawing.Size(157, 24);
            this.idTextBox.TabIndex = 12;
            // 
            // numeTextBox
            // 
            this.numeTextBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.numeTextBox.Location = new System.Drawing.Point(187, 410);
            this.numeTextBox.Name = "numeTextBox";
            this.numeTextBox.Size = new System.Drawing.Size(157, 24);
            this.numeTextBox.TabIndex = 13;
            // 
            // echipaTextBox
            // 
            this.echipaTextBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.echipaTextBox.Location = new System.Drawing.Point(187, 451);
            this.echipaTextBox.Name = "echipaTextBox";
            this.echipaTextBox.Size = new System.Drawing.Size(157, 24);
            this.echipaTextBox.TabIndex = 14;
            // 
            // capMotorComboBox
            // 
            this.capMotorComboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.capMotorComboBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.capMotorComboBox.FormattingEnabled = true;
            this.capMotorComboBox.Location = new System.Drawing.Point(187, 496);
            this.capMotorComboBox.Name = "capMotorComboBox";
            this.capMotorComboBox.Size = new System.Drawing.Size(157, 26);
            this.capMotorComboBox.TabIndex = 15;
            // 
            // logoutButton
            // 
            this.logoutButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.logoutButton.Location = new System.Drawing.Point(669, 525);
            this.logoutButton.Name = "logoutButton";
            this.logoutButton.Size = new System.Drawing.Size(157, 54);
            this.logoutButton.TabIndex = 16;
            this.logoutButton.Text = "LOGOUT";
            this.logoutButton.UseVisualStyleBackColor = true;
            this.logoutButton.Click += new System.EventHandler(this.logoutButton_Click);
            // 
            // inscrieButton
            // 
            this.inscrieButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.inscrieButton.Location = new System.Drawing.Point(223, 551);
            this.inscrieButton.Name = "inscrieButton";
            this.inscrieButton.Size = new System.Drawing.Size(137, 47);
            this.inscrieButton.TabIndex = 17;
            this.inscrieButton.Text = "INSCRIE";
            this.inscrieButton.UseVisualStyleBackColor = true;
            this.inscrieButton.Click += new System.EventHandler(this.inscrieButton_Click);
            // 
            // MainForm
            // 
            this.ClientSize = new System.Drawing.Size(963, 609);
            this.Controls.Add(this.inscrieButton);
            this.Controls.Add(this.logoutButton);
            this.Controls.Add(this.capMotorComboBox);
            this.Controls.Add(this.echipaTextBox);
            this.Controls.Add(this.numeTextBox);
            this.Controls.Add(this.idTextBox);
            this.Controls.Add(this.label8);
            this.Controls.Add(this.label7);
            this.Controls.Add(this.label6);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.cautaButton);
            this.Controls.Add(this.cautaEchipaTextBox);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.participantiGridView);
            this.Controls.Add(this.curseGridView);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Name = "MainForm";
            this.Load += new System.EventHandler(this.MainForm_Load);
            ((System.ComponentModel.ISupportInitialize)(this.curseGridView)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.participantiGridView)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        private void MainForm_Load(object sender, EventArgs e)
        {

        }
    }
}
