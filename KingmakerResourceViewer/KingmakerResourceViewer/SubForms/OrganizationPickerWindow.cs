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
    public partial class OrganizationPickerWindow : Form
    {
        private CentralCommand central = new CentralCommand(Properties.Resources.OwnersCSV, Properties.Resources.settlementExample);

        public Organization selectedOrganization;

        public OrganizationPickerWindow()
        {
            InitializeComponent();

            ListBox.ObjectCollection items = OrganizationListBox.Items;
            items.AddRange(GetOrganizationNames().ToArray());
            OrganizationListBox.SelectedIndex = 0;
        }

        private void SelectOrganizationButton_Click(object sender, EventArgs e)
        {
            if (OrganizationListBox.SelectedIndex >= 0)
            {
                selectedOrganization = central.getCompleteOrganizationList()[OrganizationListBox.SelectedIndex];
                this.DialogResult = DialogResult.OK;
                Console.WriteLine("Selected organization: " + selectedOrganization.name);
                this.Close();
            }
        }

        //Gets a String list of all the names in CompleteBuildingList within central command.
        private List<String> GetOrganizationNames()
        {
            List<String> names = new List<String>(0);

            foreach (Organization element in central.getCompleteOrganizationList())
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

        private void OrganizationListBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (OrganizationListBox.SelectedIndex >= 0)
            {
                selectedOrganization = central.getCompleteOrganizationList()[OrganizationListBox.SelectedIndex];
            }
        }
    }
}
