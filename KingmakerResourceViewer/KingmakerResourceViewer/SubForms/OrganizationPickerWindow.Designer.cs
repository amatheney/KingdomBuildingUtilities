namespace KingmakerResourceViewer
{
    partial class OrganizationPickerWindow
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
            this.SelectOrganizationButton = new System.Windows.Forms.Button();
            this.OrganizationListBox = new System.Windows.Forms.ListBox();
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
            // SelectOrganizationButton
            // 
            this.SelectOrganizationButton.Location = new System.Drawing.Point(12, 607);
            this.SelectOrganizationButton.Name = "SelectOrganizationButton";
            this.SelectOrganizationButton.Size = new System.Drawing.Size(172, 23);
            this.SelectOrganizationButton.TabIndex = 4;
            this.SelectOrganizationButton.Text = "Select Organization";
            this.SelectOrganizationButton.UseVisualStyleBackColor = true;
            this.SelectOrganizationButton.Click += new System.EventHandler(this.SelectOrganizationButton_Click);
            // 
            // OrganizationListBox
            // 
            this.OrganizationListBox.FormattingEnabled = true;
            this.OrganizationListBox.Location = new System.Drawing.Point(12, 12);
            this.OrganizationListBox.Name = "OrganizationListBox";
            this.OrganizationListBox.Size = new System.Drawing.Size(172, 589);
            this.OrganizationListBox.TabIndex = 3;
            this.OrganizationListBox.SelectedIndexChanged += new System.EventHandler(this.OrganizationListBox_SelectedIndexChanged);
            // 
            // OrganizationPickerWindow
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(195, 663);
            this.Controls.Add(this.CancelButton);
            this.Controls.Add(this.SelectOrganizationButton);
            this.Controls.Add(this.OrganizationListBox);
            this.Name = "OrganizationPickerWindow";
            this.Text = "Organization Picker";
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Button CancelButton;
        private System.Windows.Forms.Button SelectOrganizationButton;
        private System.Windows.Forms.ListBox OrganizationListBox;
    }
}