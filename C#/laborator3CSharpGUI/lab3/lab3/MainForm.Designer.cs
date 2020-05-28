namespace lab3
{
    partial class MainForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(MainForm));
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.curseGridView = new System.Windows.Forms.DataGridView();
            this.participantiGridView = new System.Windows.Forms.DataGridView();
            this.label3 = new System.Windows.Forms.Label();
            this.cautaEchipaTextBox = new System.Windows.Forms.TextBox();
            this.cautaButton = new System.Windows.Forms.Button();
            this.logoutButton = new System.Windows.Forms.Button();
            this.label4 = new System.Windows.Forms.Label();
            this.categorieComboBox = new System.Windows.Forms.ComboBox();
            this.label5 = new System.Windows.Forms.Label();
            this.label6 = new System.Windows.Forms.Label();
            this.label7 = new System.Windows.Forms.Label();
            this.label8 = new System.Windows.Forms.Label();
            this.idTextBox = new System.Windows.Forms.TextBox();
            this.numeTextBox = new System.Windows.Forms.TextBox();
            this.echipaTextBox = new System.Windows.Forms.TextBox();
            this.inscrieButton = new System.Windows.Forms.Button();
            this.label9 = new System.Windows.Forms.Label();
            this.capMotorComboBox = new System.Windows.Forms.ComboBox();
            this.pictureBox1 = new System.Windows.Forms.PictureBox();
            ((System.ComponentModel.ISupportInitialize)(this.curseGridView)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.participantiGridView)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).BeginInit();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(229, 39);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(72, 20);
            this.label1.TabIndex = 0;
            this.label1.Text = "CURSE";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label2.Location = new System.Drawing.Point(870, 39);
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
            this.curseGridView.Location = new System.Drawing.Point(32, 75);
            this.curseGridView.Name = "curseGridView";
            this.curseGridView.ReadOnly = true;
            this.curseGridView.RowTemplate.Height = 24;
            this.curseGridView.Size = new System.Drawing.Size(480, 247);
            this.curseGridView.TabIndex = 2;
            this.curseGridView.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.curseGridView_CellClick);
            // 
            // participantiGridView
            // 
            this.participantiGridView.AllowUserToAddRows = false;
            this.participantiGridView.AllowUserToDeleteRows = false;
            this.participantiGridView.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.participantiGridView.Location = new System.Drawing.Point(602, 75);
            this.participantiGridView.Name = "participantiGridView";
            this.participantiGridView.ReadOnly = true;
            this.participantiGridView.RowTemplate.Height = 24;
            this.participantiGridView.Size = new System.Drawing.Size(620, 247);
            this.participantiGridView.TabIndex = 3;
            this.participantiGridView.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.participantiGridView_CellClick);
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label3.Location = new System.Drawing.Point(771, 341);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(72, 20);
            this.label3.TabIndex = 4;
            this.label3.Text = "Echipa:";
            // 
            // cautaEchipaTextBox
            // 
            this.cautaEchipaTextBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.cautaEchipaTextBox.Location = new System.Drawing.Point(849, 338);
            this.cautaEchipaTextBox.Name = "cautaEchipaTextBox";
            this.cautaEchipaTextBox.Size = new System.Drawing.Size(129, 27);
            this.cautaEchipaTextBox.TabIndex = 5;
            // 
            // cautaButton
            // 
            this.cautaButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.cautaButton.ForeColor = System.Drawing.SystemColors.GrayText;
            this.cautaButton.Location = new System.Drawing.Point(1013, 335);
            this.cautaButton.Name = "cautaButton";
            this.cautaButton.Size = new System.Drawing.Size(89, 35);
            this.cautaButton.TabIndex = 6;
            this.cautaButton.Text = "CAUTA";
            this.cautaButton.UseVisualStyleBackColor = true;
            this.cautaButton.Click += new System.EventHandler(this.cautaButton_Click);
            // 
            // logoutButton
            // 
            this.logoutButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.logoutButton.ForeColor = System.Drawing.SystemColors.GrayText;
            this.logoutButton.Location = new System.Drawing.Point(1093, 588);
            this.logoutButton.Name = "logoutButton";
            this.logoutButton.Size = new System.Drawing.Size(121, 54);
            this.logoutButton.TabIndex = 7;
            this.logoutButton.Text = "LOGOUT";
            this.logoutButton.UseVisualStyleBackColor = true;
            this.logoutButton.Click += new System.EventHandler(this.logoutButton_Click);
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label4.Location = new System.Drawing.Point(102, 459);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(90, 20);
            this.label4.TabIndex = 8;
            this.label4.Text = "ID Cursa:";
            // 
            // categorieComboBox
            // 
            this.categorieComboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.categorieComboBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.categorieComboBox.FormattingEnabled = true;
            this.categorieComboBox.Location = new System.Drawing.Point(254, 338);
            this.categorieComboBox.Name = "categorieComboBox";
            this.categorieComboBox.Size = new System.Drawing.Size(121, 28);
            this.categorieComboBox.TabIndex = 9;
            this.categorieComboBox.SelectedIndexChanged += new System.EventHandler(this.categorieComboBox_SelectedIndexChanged);
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label5.Location = new System.Drawing.Point(141, 341);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(96, 20);
            this.label5.TabIndex = 10;
            this.label5.Text = "Categorie:";
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label6.Location = new System.Drawing.Point(129, 496);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(63, 20);
            this.label6.TabIndex = 11;
            this.label6.Text = "Nume:";
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label7.Location = new System.Drawing.Point(120, 528);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(72, 20);
            this.label7.TabIndex = 12;
            this.label7.Text = "Echipa:";
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label8.Location = new System.Drawing.Point(90, 564);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(102, 20);
            this.label8.TabIndex = 13;
            this.label8.Text = "Cap motor:";
            // 
            // idTextBox
            // 
            this.idTextBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.idTextBox.Location = new System.Drawing.Point(221, 456);
            this.idTextBox.Name = "idTextBox";
            this.idTextBox.Size = new System.Drawing.Size(154, 27);
            this.idTextBox.TabIndex = 14;
            // 
            // numeTextBox
            // 
            this.numeTextBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.numeTextBox.Location = new System.Drawing.Point(221, 491);
            this.numeTextBox.Name = "numeTextBox";
            this.numeTextBox.Size = new System.Drawing.Size(154, 27);
            this.numeTextBox.TabIndex = 15;
            // 
            // echipaTextBox
            // 
            this.echipaTextBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.echipaTextBox.Location = new System.Drawing.Point(221, 527);
            this.echipaTextBox.Name = "echipaTextBox";
            this.echipaTextBox.Size = new System.Drawing.Size(154, 27);
            this.echipaTextBox.TabIndex = 16;
            // 
            // inscrieButton
            // 
            this.inscrieButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.inscrieButton.ForeColor = System.Drawing.SystemColors.GrayText;
            this.inscrieButton.Location = new System.Drawing.Point(289, 611);
            this.inscrieButton.Name = "inscrieButton";
            this.inscrieButton.Size = new System.Drawing.Size(127, 42);
            this.inscrieButton.TabIndex = 18;
            this.inscrieButton.Text = "INSCRIE";
            this.inscrieButton.UseVisualStyleBackColor = true;
            this.inscrieButton.Click += new System.EventHandler(this.inscrieButton_Click);
            // 
            // label9
            // 
            this.label9.AutoSize = true;
            this.label9.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label9.Location = new System.Drawing.Point(158, 419);
            this.label9.Name = "label9";
            this.label9.Size = new System.Drawing.Size(232, 20);
            this.label9.TabIndex = 19;
            this.label9.Text = "INSCRIERE PARTICIPANT";
            // 
            // capMotorComboBox
            // 
            this.capMotorComboBox.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.capMotorComboBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.capMotorComboBox.FormattingEnabled = true;
            this.capMotorComboBox.Location = new System.Drawing.Point(221, 561);
            this.capMotorComboBox.Name = "capMotorComboBox";
            this.capMotorComboBox.Size = new System.Drawing.Size(154, 28);
            this.capMotorComboBox.TabIndex = 20;
            // 
            // pictureBox1
            // 
            this.pictureBox1.Image = ((System.Drawing.Image)(resources.GetObject("pictureBox1.Image")));
            this.pictureBox1.Location = new System.Drawing.Point(602, 419);
            this.pictureBox1.Name = "pictureBox1";
            this.pictureBox1.Size = new System.Drawing.Size(346, 223);
            this.pictureBox1.SizeMode = System.Windows.Forms.PictureBoxSizeMode.Zoom;
            this.pictureBox1.TabIndex = 21;
            this.pictureBox1.TabStop = false;
            // 
            // MainForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1260, 691);
            this.Controls.Add(this.pictureBox1);
            this.Controls.Add(this.capMotorComboBox);
            this.Controls.Add(this.label9);
            this.Controls.Add(this.inscrieButton);
            this.Controls.Add(this.echipaTextBox);
            this.Controls.Add(this.numeTextBox);
            this.Controls.Add(this.idTextBox);
            this.Controls.Add(this.label8);
            this.Controls.Add(this.label7);
            this.Controls.Add(this.label6);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.categorieComboBox);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.logoutButton);
            this.Controls.Add(this.cautaButton);
            this.Controls.Add(this.cautaEchipaTextBox);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.participantiGridView);
            this.Controls.Add(this.curseGridView);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Name = "MainForm";
            this.Text = "MainForm";
            ((System.ComponentModel.ISupportInitialize)(this.curseGridView)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.participantiGridView)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.DataGridView curseGridView;
        private System.Windows.Forms.DataGridView participantiGridView;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.TextBox cautaEchipaTextBox;
        private System.Windows.Forms.Button cautaButton;
        private System.Windows.Forms.Button logoutButton;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.ComboBox categorieComboBox;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.TextBox idTextBox;
        private System.Windows.Forms.TextBox numeTextBox;
        private System.Windows.Forms.TextBox echipaTextBox;
        private System.Windows.Forms.Button inscrieButton;
        private System.Windows.Forms.Label label9;
        private System.Windows.Forms.ComboBox capMotorComboBox;
        private System.Windows.Forms.PictureBox pictureBox1;
    }
}