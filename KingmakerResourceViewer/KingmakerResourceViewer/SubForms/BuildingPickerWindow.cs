using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace KingmakerResourceViewer
{
    public partial class BuildingPickerWindow : Form
    {
        private CentralCommand central = new CentralCommand(Properties.Resources.OwnersCSV, Properties.Resources.settlementExample);

        public Building selectedBuilding;

        public BuildingPickerWindow()
        {
            InitializeComponent();
            
            ListBox.ObjectCollection items = BuildingListBox.Items;
            items.AddRange(GetBuildingNames().ToArray());
            BuildingListBox.SelectedIndex = 0;
        }

        //Gets a String list of all the names in CompleteBuildingList within central command.
        private List<String> GetBuildingNames()
        {
            List<String> names = new List<String>(0);

            foreach (Building element in central.getCompleteBuildingList())
            {
                names.Add(element.name);
            }

            return names;
        }

        private void CancelButton_Click(object sender, EventArgs e)
        {
            this.DialogResult = DialogResult.Cancel;
            this.Close();
        }

        private void SelectBuildingButton_Click(object sender, EventArgs e)
        {
            if (BuildingListBox.SelectedIndex >= 0)
            {
                this.DialogResult = DialogResult.OK;
                this.Close();
            }
        }

        private void BuildingListBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (BuildingListBox.SelectedIndex >= 0)
            {
                selectedBuilding = central.getCompleteBuildingList()[BuildingListBox.SelectedIndex];
            }
        }

    }
}
