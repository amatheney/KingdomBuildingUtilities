namespace KingmakerResourceViewer.SubForms
{
    partial class RoomPickerWindow
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
            this.SelectRoomButton = new System.Windows.Forms.Button();
            this.RoomListBox = new System.Windows.Forms.ListBox();
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
            // SelectRoomButton
            // 
            this.SelectRoomButton.Location = new System.Drawing.Point(12, 607);
            this.SelectRoomButton.Name = "SelectRoomButton";
            this.SelectRoomButton.Size = new System.Drawing.Size(172, 23);
            this.SelectRoomButton.TabIndex = 4;
            this.SelectRoomButton.Text = "Select Room";
            this.SelectRoomButton.UseVisualStyleBackColor = true;
            this.SelectRoomButton.Click += new System.EventHandler(this.SelectRoomButton_Click);
            // 
            // RoomListBox
            // 
            this.RoomListBox.FormattingEnabled = true;
            this.RoomListBox.Location = new System.Drawing.Point(12, 12);
            this.RoomListBox.Name = "RoomListBox";
            this.RoomListBox.Size = new System.Drawing.Size(172, 589);
            this.RoomListBox.TabIndex = 3;
            this.RoomListBox.SelectedIndexChanged += new System.EventHandler(this.RoomListBox_SelectedIndexChanged);
            // 
            // RoomPickerWindow
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(196, 671);
            this.Controls.Add(this.CancelButton);
            this.Controls.Add(this.SelectRoomButton);
            this.Controls.Add(this.RoomListBox);
            this.Name = "RoomPickerWindow";
            this.Text = "RoomPickerWindow";
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Button CancelButton;
        private System.Windows.Forms.Button SelectRoomButton;
        private System.Windows.Forms.ListBox RoomListBox;
    }
}