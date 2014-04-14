namespace KingmakerResourceViewer
{
    partial class BuildingPickerWindow
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
            this.BuildingListBox = new System.Windows.Forms.ListBox();
            this.SelectBuildingButton = new System.Windows.Forms.Button();
            this.CancelButton = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // BuildingListBox
            // 
            this.BuildingListBox.FormattingEnabled = true;
            this.BuildingListBox.Location = new System.Drawing.Point(12, 6);
            this.BuildingListBox.Name = "BuildingListBox";
            this.BuildingListBox.Size = new System.Drawing.Size(172, 589);
            this.BuildingListBox.TabIndex = 0;
            this.BuildingListBox.SelectedIndexChanged += new System.EventHandler(this.BuildingListBox_SelectedIndexChanged);
            // 
            // SelectBuildingButton
            // 
            this.SelectBuildingButton.Location = new System.Drawing.Point(12, 601);
            this.SelectBuildingButton.Name = "SelectBuildingButton";
            this.SelectBuildingButton.Size = new System.Drawing.Size(172, 23);
            this.SelectBuildingButton.TabIndex = 1;
            this.SelectBuildingButton.Text = "Select Building";
            this.SelectBuildingButton.UseVisualStyleBackColor = true;
            this.SelectBuildingButton.Click += new System.EventHandler(this.SelectBuildingButton_Click);
            // 
            // CancelButton
            // 
            this.CancelButton.Location = new System.Drawing.Point(12, 630);
            this.CancelButton.Name = "CancelButton";
            this.CancelButton.Size = new System.Drawing.Size(172, 23);
            this.CancelButton.TabIndex = 2;
            this.CancelButton.Text = "Cancel";
            this.CancelButton.UseVisualStyleBackColor = true;
            this.CancelButton.Click += new System.EventHandler(this.CancelButton_Click);
            // 
            // BuildingPickerWindow
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(192, 659);
            this.Controls.Add(this.CancelButton);
            this.Controls.Add(this.SelectBuildingButton);
            this.Controls.Add(this.BuildingListBox);
            this.Name = "BuildingPickerWindow";
            this.Text = "BuildingPickerWindow";
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.ListBox BuildingListBox;
        private System.Windows.Forms.Button SelectBuildingButton;
        private System.Windows.Forms.Button CancelButton;
    }
}