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
    public partial class TeamPickerWindow : Form
    {
        private CentralCommand central = new CentralCommand(Properties.Resources.OwnersCSV, Properties.Resources.settlementExample);

        public Team selectedTeam;

        public TeamPickerWindow()
        {
            InitializeComponent();

            ListBox.ObjectCollection items = TeamListBox.Items;
            items.AddRange(GetTeamNames().ToArray());
            TeamListBox.SelectedIndex = 0;
        }

        //Gets a String list of all the names in CompleteBuildingList within central command.
        private List<String> GetTeamNames()
        {
            List<String> names = new List<String>(0);

            foreach (Team element in central.getCompleteTeamList())
            {
                names.Add(element.Name);
            }

            return names;
        }

        private void TeamListBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (TeamListBox.SelectedIndex >= 0)
            {
                selectedTeam = central.getCompleteTeamList()[TeamListBox.SelectedIndex];
            }
        }

        private void SelectTeamButton_Click(object sender, EventArgs e)
        {
            if (TeamListBox.SelectedIndex >= 0)
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
    }
}
