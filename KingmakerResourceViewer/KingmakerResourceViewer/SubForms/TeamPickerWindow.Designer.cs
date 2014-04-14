namespace KingmakerResourceViewer.SubForms
{
    partial class TeamPickerWindow
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
            this.CancelButton = new System.Windows.Forms.Button();
            this.SelectTeamButton = new System.Windows.Forms.Button();
            this.TeamListBox = new System.Windows.Forms.ListBox();
            this.SuspendLayout();
            // 
            // CancelButton
            // 
            this.CancelButton.Location = new System.Drawing.Point(12, 636);
            this.CancelButton.Name = "CancelButton";
            this.CancelButton.Size = new System.Drawing.Size(172, 23);
            this.CancelButton.TabIndex = 5;
            this.CancelButton.Text = "Cancel";
            this.CancelButton.UseVisualStyleBackColor = true;
            this.CancelButton.Click += new System.EventHandler(this.CancelButton_Click);
            // 
            // SelectTeamButton
            // 
            this.SelectTeamButton.Location = new System.Drawing.Point(12, 607);
            this.SelectTeamButton.Name = "SelectTeamButton";
            this.SelectTeamButton.Size = new System.Drawing.Size(172, 23);
            this.SelectTeamButton.TabIndex = 4;
            this.SelectTeamButton.Text = "Select Team";
            this.SelectTeamButton.UseVisualStyleBackColor = true;
            this.SelectTeamButton.Click += new System.EventHandler(this.SelectTeamButton_Click);
            // 
            // TeamListBox
            // 
            this.TeamListBox.FormattingEnabled = true;
            this.TeamListBox.Location = new System.Drawing.Point(12, 12);
            this.TeamListBox.Name = "TeamListBox";
            this.TeamListBox.Size = new System.Drawing.Size(172, 589);
            this.TeamListBox.TabIndex = 3;
            this.TeamListBox.SelectedIndexChanged += new System.EventHandler(this.TeamListBox_SelectedIndexChanged);
            // 
            // TeamPickerWindow
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(197, 666);
            this.Controls.Add(this.CancelButton);
            this.Controls.Add(this.SelectTeamButton);
            this.Controls.Add(this.TeamListBox);
            this.Name = "TeamPickerWindow";
            this.Text = "TeamPickerWindow";
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Button CancelButton;
        private System.Windows.Forms.Button SelectTeamButton;
        private System.Windows.Forms.ListBox TeamListBox;
    }
}