using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace KingmakerResourceViewer.SubForms
{
    public partial class RoomPickerWindow : Form
    {
        private CentralCommand central = new CentralCommand(Properties.Resources.OwnersCSV, Properties.Resources.settlementExample);

        public Room selectedRoom;

        public RoomPickerWindow()
        {
            InitializeComponent();

            ListBox.ObjectCollection items = RoomListBox.Items;
            items.AddRange(GetRoomNames().ToArray());
            RoomListBox.SelectedIndex = 0;
        }

        //Gets a String list of all the names in CompleteBuildingList within central command.
        private List<String> GetRoomNames()
        {
            List<String> names = new List<String>(0);

            foreach (Room element in central.getCompleteRoomList())
            {
                names.Add(element.Name);
            }

            return names;
        }

        private void SelectRoomButton_Click(object sender, EventArgs e)
        {
            if (RoomListBox.SelectedIndex >= 0)
            {
                this.DialogResult = DialogResult.OK;
                this.Close();
            }
        }

        private void CancelButton_Click(object sender, EventArgs e)
        {
            this.DialogResult = DialogResult.Cancel;
            this.Close();
        }

        private void RoomListBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (RoomListBox.SelectedIndex >= 0)
            {
                selectedRoom = central.getCompleteRoomList()[RoomListBox.SelectedIndex];
            }
        }
    }
}
