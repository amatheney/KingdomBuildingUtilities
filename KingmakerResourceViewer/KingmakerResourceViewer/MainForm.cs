using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Globalization;
using System.Threading;
using System.Runtime.Serialization.Formatters.Binary;
using System.Xml.Serialization;
using System.IO;
using System.Runtime.Serialization;

namespace KingmakerResourceViewer
{
    public partial class MainViewerForm : Form
    {
        Building selectedBuilding;
        Room selectedRoom;
        Organization selectedOrganization;
        Team selectedTeam;
        String savableFilename;

        //Conditional forms
        public BusinessCreationForm NewBusinessForm;

        CentralCommand central = new CentralCommand(Properties.Resources.OwnersCSV, Properties.Resources.settlementExample);

        public MainViewerForm()
        {
            InitializeComponent();
            PreferredIncomeComboBox.SelectedIndex = 0;
            OrganizationPreferredIncomeComboBox.SelectedIndex = 0;

            //Building tab
            ListBox.ObjectCollection items = BuildingPickerListbox.Items;
            items.AddRange(GetBuildingNames().ToArray());
            BuildingPickerListbox.SelectedIndex = 0;

            //Room tab
            ListBox.ObjectCollection roomItems = RoomListBox.Items;
            roomItems.AddRange(GetRoomNames().ToArray());
            RoomListBox.SelectedIndex = 0;

            //Organization tab
            ListBox.ObjectCollection organizationItems = OrganizationListBox.Items;
            organizationItems.AddRange(GetOrganizationNames().ToArray());
            OrganizationListBox.SelectedIndex = 0;
            
            //Team tab
            ListBox.ObjectCollection teamItems = TeamListBox.Items;
            teamItems.AddRange(GetTeamNames().ToArray());
            TeamListBox.SelectedIndex = 0;

            //Unit testing
            //selectedBuilding = central.getCompleteBuildingList()[0];

            //Console.Out.WriteLine("Selected building: " + selectedBuilding.toString());
            setBuildingFields();
            setRoomFields();

            //BusinessDescriptionTextBox.Text = "(Load a business from a file, or create one from scratch using the buttons on the right)";
        }

        //Simple exit command from toolStrip
        private void exitToolStripMenuItem_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }

        //Sets all visible fields on the form
        private void setBuildingFields()
        {
            if (selectedBuilding != null)
            {
                //First tab
                BuildingGroupBox.Text = selectedBuilding.name;

                CorruptionUpDown.Value = selectedBuilding.SettlementModifiers.settlementCorruptionModifier;
                CrimeUpDown.Value = selectedBuilding.SettlementModifiers.settlementCrimeModifier;
                ProductivityUpDown.Value = selectedBuilding.SettlementModifiers.settlementLoreModifier;
                LawUpDown.Value = selectedBuilding.SettlementModifiers.settlementLawModifier;
                LoreUpDown.Value = selectedBuilding.SettlementModifiers.settlementLoreModifier;
                SocietyUpDown.Value = selectedBuilding.SettlementModifiers.settlementSocietyModifier;

                BalanceSheet selectedIncomes = getPreferredIncomeBlock();
                GoldIncomeUpDown.Value = (decimal)selectedIncomes.GP;
                GoodsIncomeUpDown.Value = (decimal)selectedIncomes.Goods;
                InfluenceIncomeUpDown.Value = (decimal)selectedIncomes.Influence;
                LaborIncomeUpDown.Value = (decimal)selectedIncomes.Labor;
                MagicIncomeUpDown.Value = (decimal)selectedIncomes.Magic;
                CapitalIncomeUpDown.Value = (decimal)selectedBuilding.getCapitalAvailable();

                BuildingDescriptionTextBox.Text = selectedBuilding.getDescriptionTestBoxString();

                //Second Tab
                ListBox.ObjectCollection items = ComponentRoomListBox.Items;
                items.Clear();
                items.AddRange(GetInteriorRoomNames().ToArray());

                //Third Tab
                FameUpDown.Value = selectedBuilding.kingdomFameModifier;
                LoyaltyUpDown.Value = selectedBuilding.kingdomLoyaltyModifier;
                EconomyUpDown.Value = selectedBuilding.kingdomEconomyModifier;
                StabilityUpDown.Value = selectedBuilding.kingdomStabilityModifier;
                UnrestUpDown.Value = selectedBuilding.kingdomUnrestModifier;
                DefenseUpDown.Value = selectedBuilding.kingdomgDefenseModifier;

                BPCostLabel.Text = "Build Point Cost: " + selectedBuilding.BPCost + " BP or " + ((selectedBuilding.BPCost * 4000) * 1.1) + " GP";

                //Fourth tab

            }
        }

        private void setRoomFields()
        {
            if (selectedRoom != null)
            {
                //First tab
                RoomGroupBox.Text = selectedRoom.Name;

                GoldRoomCostUpDown.Value = (decimal)selectedRoom.GPCreate;
                InfluenceRoomCostUpDown.Value = (decimal)selectedRoom.InfluenceCreate;
                GoodsRoomCostUpDown.Value = (decimal)selectedRoom.GoodsCreate;
                LaborRoomCostUpDown.Value = (decimal)selectedRoom.LaborCreate;
                MagicRoomCreationCostUpDown.Value = (decimal)selectedRoom.MagicCreate;

                GoldRoomIncomeUpDown.Value = (decimal)selectedRoom.GPEarnings;
                InfluenceRoomIncomeUpDown.Value = (decimal)selectedRoom.InfluenceEarnings;
                GoodsRoomIncomeUpDown.Value = (decimal)selectedRoom.GoodsEarnings;
                LaborRoomIncomeUpDown.Value = (decimal)selectedRoom.LaborEarnings;
                CapitalRoomIncomeUpDown.Value = (decimal)selectedRoom.CapitalEarnings;
                MagicRoomIncomeUpDown.Value = (decimal)selectedRoom.MagicEarnings;

                RoomDescriptionTextBox.Text = selectedRoom.toStringLimitedDescription();

                DaysCreateLabel.Text = "Days to create: " + selectedRoom.DaysCreate + " days";

                //Second tab
                RoomRushProductionCostUpDown.Value = 100;
                RoomCostModUpDown.Value = 0;

                FlagsGoldUpDown.Value = (decimal)selectedRoom.GPCreate;
                FlagsInfluenceCostUpDown.Value = (decimal)selectedRoom.InfluenceCreate;
                FlagsGoodsUpDown.Value = (decimal)selectedRoom.GoodsCreate;
                FlagsLaborUpDown.Value = (decimal)selectedRoom.LaborCreate;
                FlagsMagicUpDown.Value = (decimal)selectedRoom.MagicCreate;

                updateRoomCost();

            }
            
        }

        private void setOrganizationFields()
        {
            if (selectedOrganization != null)
            {

            }
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

        //Gets a String list of all the names in CompleteOrganizationList within central command.
        private List<String> GetOrganizationNames()
        {
            List<String> names = new List<String>(0);

            foreach (Organization elements in central.getCompleteOrganizationList())
            {
                names.Add(elements.name);
            }

            return names;
        }

        private List<String> GetRoomNames()
        {
            List<String> names = new List<String>(0);

            foreach (Room element in central.getCompleteRoomList())
            {
                names.Add(element.Name);
            }

            return names;
        }

        private List<String> GetTeamNames()
        {
            List<String> names = new List<String>(0);

            foreach (Team element in central.getCompleteTeamList())
            {
                names.Add(element.Name);
            }

            return names;
        }

        //Gets a String list of all the names of the rooms within SelectedBuilding
        private List<String> GetInteriorRoomNames()
        {
            List<String> names = new List<String>(0);

            foreach (Room element in selectedBuilding.rooms)
            {
                names.Add(element.Name);
            }

            return names;
        }
        
        //Gets the preferred income based off the selected value in PreferredIncomeComboBox
        private BalanceSheet getPreferredIncomeBlock()
        {
            int index = PreferredIncomeComboBox.SelectedIndex;
            if (index >= 0)
            {
                if (index == 0)
                    return selectedBuilding.GPEarnable;
                if (index == 1)
                    return selectedBuilding.GoodsEarnable;
                if (index == 2)
                    return selectedBuilding.InfluenceEarnable;
                if (index == 3)
                    return selectedBuilding.LaborEarnable;
                if (index == 4)
                    return selectedBuilding.MagicEarnable;
            }
            return null;
        }

        private void EditBuildingCheckbox_CheckedChanged(object sender, EventArgs e)
        {
            
        }

        //Refresh the incomes visible based off the selected preferred income
        private void PreferredIncomeComboBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (selectedBuilding != null)
                setBuildingFields();
        }

        private void BuildingPickerListbox_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (BuildingPickerListbox.SelectedIndex >= 0)
            {
                selectedBuilding = central.getCompleteBuildingList()[BuildingPickerListbox.SelectedIndex];
                setBuildingFields();
            }
        }

        private void DeleteBuildingButton_Click(object sender, EventArgs e)
        {

        }

        private void ComponentRoomListBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (ComponentRoomListBox.SelectedIndex >= 0 && ComponentRoomListBox.SelectedIndex < ComponentRoomListBox.Items.Count)
            {
                AdditionalStatisticsTextBox.Text = selectedBuilding.rooms[ComponentRoomListBox.SelectedIndex].toStringNormalizedOutput();
            }
        }

        private void RoomListBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (RoomListBox.SelectedIndex >= 0 && RoomListBox.SelectedIndex < RoomListBox.Items.Count)
            {
                selectedRoom = central.getCompleteRoomList()[RoomListBox.SelectedIndex];
                setRoomFields();
            }
        }

        private void RoomBrokenCheckbox_CheckedChanged(object sender, EventArgs e)
        {
            if (RoomBrokenCheckbox.Checked)
                RoomCostModUpDown.Value = -50;
            else
                RoomCostModUpDown.Value = 0;
            //updateRoomCost();
        }

        private void RoomRushProductionCostUpDown_ValueChanged(object sender, EventArgs e)
        {
            updateRoomCost();
        }

        //Apply general cost modifiers from the flags section - Rush Production modifier is applied before the general cost modifier.
        //Rush production can only increase cost - can't work slower to lower cost...
        //General Cost Mod goes from -100% (free) to +200% (triple)
        private void updateRoomCost()
        {
            float rushCostMod = 0;
            float generalCostMod = 0;

            //Reset values from array
            FlagsGoldUpDown.Value = (decimal)selectedRoom.GPCreate;
            FlagsInfluenceCostUpDown.Value = (decimal)selectedRoom.InfluenceCreate;
            FlagsGoodsUpDown.Value = (decimal)selectedRoom.GoodsCreate;
            FlagsLaborUpDown.Value = (decimal)selectedRoom.LaborCreate;
            FlagsMagicUpDown.Value = (decimal)selectedRoom.MagicCreate;
            FlagsDaysToCreateLabel.Text = "Days to create: " + selectedRoom.DaysCreate + " days";

            rushCostMod = (float)RoomRushProductionCostUpDown.Value / 100;
            if (RoomCostModUpDown.Value < 0)
                generalCostMod = (float)(1 - (Math.Abs(RoomCostModUpDown.Value) / 100));
            if (RoomCostModUpDown.Value > 0)
                generalCostMod = (float)((RoomCostModUpDown.Value / 100) + 1);
            if (RoomCostModUpDown.Value == 0)
                generalCostMod = 1;
            if (RoomCostModUpDown.Value == -100)
                generalCostMod = 0;
            

            RushCostModLabel.Text = "Rush Cost Mod: " + rushCostMod.ToString();
            GeneralCostModLabel.Text = "General Cost Mod: " + generalCostMod.ToString();
            DaysCreateMod.Text = "Days Create Mod: " + (1-(((RoomRushProductionCostUpDown.Value-100)/100)/2)).ToString();

            if (rushCostMod != 0)
            {
                FlagsGoldUpDown.Value = FlagsGoldUpDown.Value * (decimal)rushCostMod;
                FlagsInfluenceCostUpDown.Value = FlagsInfluenceCostUpDown.Value * (decimal)rushCostMod;
                FlagsGoodsUpDown.Value = FlagsGoodsUpDown.Value * (decimal)rushCostMod;
                FlagsLaborUpDown.Value = FlagsLaborUpDown.Value * (decimal)rushCostMod;
                FlagsMagicUpDown.Value = FlagsMagicUpDown.Value * (decimal)rushCostMod;
            }

            FlagsGoldUpDown.Value = FlagsGoldUpDown.Value * (decimal)generalCostMod;
            FlagsInfluenceCostUpDown.Value = FlagsInfluenceCostUpDown.Value * (decimal)generalCostMod;
            FlagsGoodsUpDown.Value = FlagsGoodsUpDown.Value * (decimal)generalCostMod;
            FlagsLaborUpDown.Value = FlagsLaborUpDown.Value * (decimal)generalCostMod;
            FlagsMagicUpDown.Value = FlagsMagicUpDown.Value * (decimal)generalCostMod;

            //Adjust creation time
            double daysToCreate = selectedRoom.DaysCreate;
            if (rushCostMod != 0)
                daysToCreate = Math.Ceiling(selectedRoom.DaysCreate * (float)(1 - (((RoomRushProductionCostUpDown.Value - 100) / 100)) / 2));
            if (RoomBrokenCheckbox.Checked)
                daysToCreate *= .5;

            FlagsDaysToCreateLabel.Text = "Days to create: " + daysToCreate + " days";
        }

        private void RoomCostModUpDown_ValueChanged(object sender, EventArgs e)
        {
            updateRoomCost();
        }

        private void DaysCreateMod_Click(object sender, EventArgs e)
        {

        }

        private void GeneralCostModLabel_Click(object sender, EventArgs e)
        {

        }

        private void RushCostModLabel_Click(object sender, EventArgs e)
        {

        }

        private void aboutToolStripMenuItem_Click(object sender, EventArgs e)
        {
            central = new CentralCommand(Properties.Resources.OwnersCSV, Properties.Resources.settlementExample);

            PreferredIncomeComboBox.SelectedIndex = 0;
            ListBox.ObjectCollection items = BuildingPickerListbox.Items;
            items.AddRange(GetBuildingNames().ToArray());
            BuildingPickerListbox.SelectedIndex = 0;

            ListBox.ObjectCollection roomItems = RoomListBox.Items;
            roomItems.AddRange(GetRoomNames().ToArray());
            RoomListBox.SelectedIndex = 0;
            
            //Unit testing
            //selectedBuilding = central.getCompleteBuildingList()[0];

            //Console.Out.WriteLine("Selected building: " + selectedBuilding.toString());
            setBuildingFields();
            setRoomFields();
        }

        private void aboutToolStripMenuItem1_Click(object sender, EventArgs e)
        {
            MessageBox.Show("Kingmaker Resource Viewer: By Judd Hunter\r\nContact: bitwize01@gmail.com", "About KRV", MessageBoxButtons.OK, MessageBoxIcon.Information);
        }

        private void newBusinessButton_Click(object sender, EventArgs e)
        {
            //newBusinessButton.Enabled = false;
            NewBusinessForm = new BusinessCreationForm();
            NewBusinessForm.ShowDialog();

            central.addBusiness(NewBusinessForm.selectedBusiness);
            FillBusinessListBox();
            PopulateBusinessData();

            central.compileLandowners();
            Console.WriteLine(central.command.derivedSettlement.toString());
        }

        //Gets a String list of all the names in CompleteBuildingList within central command.
        private List<String> GetBusinessNames()
        {
            List<String> names = new List<String>(0);

            foreach (Business element in central.businesses)
            {
                names.Add(element.getName());
            }

            return names;
        }

        private List<String> GetLocalBuildingNames()
        {
            List<String> names = new List<String>(0);

            foreach (Building element in central.businesses[BusinessComboBox.SelectedIndex].getBuildings())
            {
                names.Add(element.name);
            }

            return names;
        }

        private List<String> GetLocalOrganizationNames()
        {
            List<String> names = new List<String>(0);

            foreach (Organization element in central.businesses[BusinessComboBox.SelectedIndex].getOrganizations())
            {
                names.Add(element.name);
            }

            return names;
        }

        private void FillBusinessListBox()
        {
            //Pull items array, clear it, add our rebuilt structure
            ComboBox.ObjectCollection items = BusinessComboBox.Items;
            items.Clear();
            items.AddRange(GetBusinessNames().ToArray());
        }

        private void PopulateBusinessData()
        {
            Console.WriteLine("Index: " + BusinessComboBox.SelectedIndex);
            if (central.businesses.Length > 0 && BusinessComboBox.SelectedIndex >= 0)
            {
                Business selected = central.businesses[BusinessComboBox.SelectedIndex];

                Account sample = selected.getOwner().balance;
                //Now do holdings
                GoldHoldingsUpDown.Value = (decimal)sample.GP;
                GoodsHoldingsUpDown.Value = sample.Goods;
                InfluenceHoldingsUpDown.Value = sample.Influence;
                MagicHoldingsUpDown.Value = sample.Magic;
                LaborHoldingsUpDown.Value = sample.Labor;

                BusinessGroupBox.Text = selected.getName();
                //BusinessListBox.SelectedIndex = 0;
                BusinessSynopsisTextBox.Text = selected.getSynopsis();

                //Populate Buildings tab
                ListBox.ObjectCollection items = BusinessBuildingListBox.Items;
                items.Clear();
                items.AddRange(GetLocalBuildingNames().ToArray());
                if (selected.getBuildings().Length > 0)
                {
                    BusinessBuildingListBox.SelectedIndex = 0;
                    PopulateBusinessBuildingTextBox();
                }

                //Populate Organization tab
                ListBox.ObjectCollection itemsOrg = BusinessOrganizationListBox.Items;
                itemsOrg.Clear();
                itemsOrg.AddRange(GetLocalOrganizationNames().ToArray());
                if (selected.getOrganizations().Length > 0)
                {
                    BusinessOrganizationListBox.SelectedIndex = 0;
                    PopulateBusinessOrganizationTextBox();
                }

            }
            /*else
            {
                Console.WriteLine("Well shoot. How did I get here?");
                ListBox.ObjectCollection items = BusinessListBox.Items;
                items.Clear();
            }*/
        }

        private void PopulateBusinessBuildingTextBox()
        {
            Console.WriteLine("Populating business building text box");
            if (central.businesses.Length > 0 && BusinessComboBox.SelectedIndex >= 0)
            {
                Business selected = central.businesses[BusinessComboBox.SelectedIndex];

                if (selected.getBuildings().Length > 0 && BusinessBuildingListBox.SelectedIndex >= 0)
                {
                    BusinessBuildingDescriptionTextBox.Text = selected.getBuildings()[BusinessBuildingListBox.SelectedIndex].toStringForTextBox();
                }
                else
                    Console.WriteLine("No buildings/no selected business building text box");
            }
            else
                Console.WriteLine("No business selected for business building text box");
        }

        private void PopulateBusinessOrganizationTextBox()
        {
            Console.WriteLine("Populating business organization text box");
            if (central.businesses.Length > 0 && BusinessComboBox.SelectedIndex >= 0)
            {
                Business selected = central.businesses[BusinessComboBox.SelectedIndex];

                if (selected.getOrganizations().Length > 0 && BusinessOrganizationListBox.SelectedIndex >= 0)
                {
                    BusinessOrganizationTextBox.Text = selected.getOrganizations()[BusinessBuildingListBox.SelectedIndex].toTextBoxOutput();
                }
                else
                    Console.WriteLine("No organization/no selected business organization text box");
            }
            else
                Console.WriteLine("No business selected for business organization text box");
        }

        private void BusinessListBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            PopulateBusinessData();
        }

        private void EditHoldingsCheckbox_CheckedChanged(object sender, EventArgs e)
        {
            GoldHoldingsUpDown.ReadOnly = !EditHoldingsCheckbox.Checked;
            GoodsHoldingsUpDown.ReadOnly = !EditHoldingsCheckbox.Checked;
            InfluenceHoldingsUpDown.ReadOnly = !EditHoldingsCheckbox.Checked;
            MagicHoldingsUpDown.ReadOnly = !EditHoldingsCheckbox.Checked;
            LaborHoldingsUpDown.ReadOnly = !EditHoldingsCheckbox.Checked;
        }

        private void BusinessMainTab_Click(object sender, EventArgs e)
        {

        }

        private void BusinessComboBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            PopulateBusinessData();
        }

        private void BusinessPreferredComboBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (central.businesses.Length > 0 && BusinessComboBox.SelectedIndex >= 0)
            {
                //Get selected incomes
                string preferredIncome = BusinessPreferredComboBox.Items[BusinessPreferredComboBox.SelectedIndex].ToString();
                if (preferredIncome.Equals("Gold"))
                    preferredIncome = "GP";
             
                Business selected = central.businesses[BusinessComboBox.SelectedIndex];

                /*Account sample = selected.getOwner().balance;
                //Now do holdings
                GoldHoldingsUpDown.Value = (decimal)sample.GP;
                GoodsHoldingsUpDown.Value = sample.Goods;
                InfluenceHoldingsUpDown.Value = sample.Influence;
                MagicHoldingsUpDown.Value = sample.Magic;
                LaborHoldingsUpDown.Value = sample.Labor;

                BusinessGroupBox.Text = selected.getName();
                //BusinessListBox.SelectedIndex = 0;
                 * */

                BalanceSheet values = selected.consolidateBusinessBalanceSheets(preferredIncome);

                GoldDailyIncomeUpDown.Value = (decimal)values.GP;
                GoodsDailyIncomeUpDown.Value = (decimal)values.Goods;
                LaborDailyIncomeUpDown.Value = (decimal)values.Labor;
                InfluenceDailyIncomeUpDown.Value = (decimal)values.Influence;
                MagicDailyIncomeUpDown.Value = (decimal)values.Magic;

                //Now set their daily income values
            }
        }

        private void GoldHoldingsUpDown_ValueChanged(object sender, EventArgs e)
        {
            if (central.businesses.Length > 0 && BusinessComboBox.SelectedIndex >= 0)
            {
                Business selected = central.businesses[BusinessComboBox.SelectedIndex];

                selected.setGPAccount((float)GoldHoldingsUpDown.Value);
            }
            else
            {
                MessageBox.Show("No business selected - use the drop-down above to select a business", "Error setting holdings", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private void GoodsHoldingsUpDown_ValueChanged(object sender, EventArgs e)
        {
            if (ExchangeGoldCheckbox.Checked)
            {
                if (central.businesses.Length > 0 && BusinessComboBox.SelectedIndex >= 0)
                {
                    Business selected = central.businesses[BusinessComboBox.SelectedIndex];

                    //Need to get the delta!
                    int currentValue = decimal.ToInt32(GoodsHoldingsUpDown.Value);
                    int previousValue = selected.getOwner().balance.Goods;

                    int delta = previousValue - currentValue;
                    float gpModifier = (float)(GoldHoldingsUpDown.Value + (20 * delta));

                    if (gpModifier > 0)
                    {
                        //Set fields
                        selected.setGPAccount(gpModifier);
                        selected.setGoodsAccount(currentValue);

                        PopulateBusinessData();
                    }
                    else
                    {
                        //Set fields
                        selected.setGoodsAccount(previousValue);

                        PopulateBusinessData();
                    }


                }
                else
                {
                    MessageBox.Show("No business selected - use the drop-down above to select a business", "Error setting holdings", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
            }
        }

        private void InfluenceHoldingsUpDown_ValueChanged(object sender, EventArgs e)
        {
            if (ExchangeGoldCheckbox.Checked)
            {
                if (central.businesses.Length > 0 && BusinessComboBox.SelectedIndex >= 0)
                {
                    Business selected = central.businesses[BusinessComboBox.SelectedIndex];

                    //Need to get the delta!
                    int currentValue = decimal.ToInt32(InfluenceHoldingsUpDown.Value);
                    int previousValue = selected.getOwner().balance.Influence;

                    int delta = previousValue - currentValue;
                    float gpModifier = (float)(GoldHoldingsUpDown.Value + (30 * delta));

                    if (gpModifier > 0)
                    {
                        //Set fields
                        selected.setGPAccount(gpModifier);
                        selected.setInfluenceAccount(currentValue);

                        PopulateBusinessData();
                    }
                    else
                    {
                        //Set fields
                        selected.setInfluenceAccount(previousValue);

                        PopulateBusinessData();
                    }


                }
                else
                {
                    MessageBox.Show("No business selected - use the drop-down above to select a business", "Error setting holdings", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
            }
        }

        private void LaborHoldingsUpDown_ValueChanged(object sender, EventArgs e)
        {
            if (ExchangeGoldCheckbox.Checked)
            {
                if (central.businesses.Length > 0 && BusinessComboBox.SelectedIndex >= 0)
                {
                    Business selected = central.businesses[BusinessComboBox.SelectedIndex];

                    //Need to get the delta!
                    int currentValue = decimal.ToInt32(LaborHoldingsUpDown.Value);
                    int previousValue = selected.getOwner().balance.Labor;

                    int delta = previousValue - currentValue;
                    float gpModifier = (float)(GoldHoldingsUpDown.Value + (20 * delta));

                    if (gpModifier > 0)
                    {
                        //Set fields
                        selected.setGPAccount(gpModifier);
                        selected.setLaborAccount(currentValue);

                        PopulateBusinessData();
                    }
                    else
                    {
                        //Set fields
                        selected.setLaborAccount(previousValue);

                        PopulateBusinessData();
                    }


                }
                else
                {
                    MessageBox.Show("No business selected - use the drop-down above to select a business", "Error setting holdings", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
            }
        }

        private void MagicHoldingsUpDown_ValueChanged(object sender, EventArgs e)
        {
            if (ExchangeGoldCheckbox.Checked)
            {
                if (central.businesses.Length > 0 && BusinessComboBox.SelectedIndex >= 0)
                {
                    Business selected = central.businesses[BusinessComboBox.SelectedIndex];

                    //Need to get the delta!
                    int currentValue = decimal.ToInt32(MagicHoldingsUpDown.Value);
                    int previousValue = selected.getOwner().balance.Magic;

                    int delta = previousValue - currentValue;
                    float gpModifier = (float)(GoldHoldingsUpDown.Value + (100 * delta));

                    if (gpModifier > 0)
                    {
                        //Set fields
                        selected.setGPAccount(gpModifier);
                        selected.setMagicAccount(currentValue);

                        PopulateBusinessData();
                    }
                    else
                    {
                        //Set fields
                        selected.setMagicAccount(previousValue);

                        PopulateBusinessData();
                    }


                }
                else
                {
                    MessageBox.Show("No business selected - use the drop-down above to select a business", "Error setting holdings", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
            }
        }

        private void BusinessBuildingListBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            PopulateBusinessBuildingTextBox();
        }

        private void BusinessOrganizationListBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            PopulateBusinessOrganizationTextBox();
        }

        private void OrganizationListBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            PopulateCompleteOrganizationTab();
        }

        public void PopulateCompleteOrganizationTab()
        {
            if (OrganizationListBox.SelectedIndex >= 0)
            {
                Organization sample = new Organization();
                ListBox.ObjectCollection itemsOrg = OrganizationListBox.Items;
                sample.name = itemsOrg[OrganizationListBox.SelectedIndex].ToString();

                selectedOrganization = central.getCompleteOrganizationList()[central.indexOf(sample)];
                selectedOrganization.regenerateBalanceSheets();

                OrganizationGroupBox.Text = selectedOrganization.name;

                //Basic statistics
                //First, we need to calculate the incomes based off the preferred income selector
                BalanceSheet preferredIncome = GetOrganizationPreferredIncome();
               
                OrganizationGoldIncomeUpDown.Value = (decimal)preferredIncome.GP;
                OrganizationGoodsIncomeUpDown.Value = (decimal)preferredIncome.Goods;
                OrganizationLaborIncomeUpDown.Value = (decimal)preferredIncome.Labor;
                OrganizationInfluenceIncomeUpDown.Value = (decimal)preferredIncome.Influence;
                OrganizationMagicIncomeUpDown.Value = (decimal)preferredIncome.Magic;
                OrganizationCapitalIncomeUpDown.Value = (decimal)selectedOrganization.getTotalCapitalEarnings();

                //Now, set the text box
                OrganizationDescriptionTextBox.Text = selectedOrganization.toTextBoxOutput();

                //Set 2nd tab (rooms)
                ListBox.ObjectCollection roomsOrg = OrganizationRoomListBox.Items;
                roomsOrg.Clear();
                roomsOrg.AddRange(selectedOrganization.getTeamNames().ToArray());


            }
        }

        public BalanceSheet GetOrganizationPreferredIncome()
        {
            int index = OrganizationPreferredIncomeComboBox.SelectedIndex;
            if (index >= 0)
            {
                if (index == 0)
                    return selectedOrganization.GPEarnable;
                if (index == 1)
                    return selectedOrganization.GoodsEarnable;
                if (index == 2)
                    return selectedOrganization.InfluenceEarnable;
                if (index == 3)
                    return selectedOrganization.LaborEarnable;
                if (index == 4)
                    return selectedOrganization.MagicEarnable;
            }
            return null;
        }

        private void OrganizationPreferredIncomeComboBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            PopulateCompleteOrganizationTab();
        }

        private void OrganizationRoomListBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (OrganizationRoomListBox.SelectedIndex >= 0)
            {
                Team selected = selectedOrganization.teams[OrganizationRoomListBox.SelectedIndex];

                OrganizationRoomDescriptionTextBox.Text = selected.toStringForTextBox();
            }
        }

        private void TeamListBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (TeamListBox.SelectedIndex >= 0)
            {
                selectedTeam = central.getCompleteTeamList()[TeamListBox.SelectedIndex];

                PopulateCompleteTeamTab();
            }
        }

        private void PopulateCompleteTeamTab()
        {
            if (TeamListBox.SelectedIndex >= 0)
            {
                selectedTeam = central.getCompleteTeamList()[TeamListBox.SelectedIndex];

                TeamGroupBox.Text = selectedTeam.Name;

                //Earnable incomes
                TeamEarnableGoldIncomeUpDown.Value = (decimal)selectedTeam.GPEarnings;
                TeamEarnableGoodsIncomeUpDown.Value = (decimal)selectedTeam.GoodsEarnings;
                TeamEarnableLaborIncomeUpDown.Value = (decimal)selectedTeam.LaborEarnings;
                TeamEarnableMagicIncomeUpDown.Value = (decimal)selectedTeam.MagicEarnings;
                TeamEarnableCapitalIncomeUpDown.Value = (decimal)selectedTeam.CapitalEarnings;

                //Creation costs
                TeamCostGoldUpDown.Value = (decimal)selectedTeam.GPCreate;
                TeamCostGoodsUpDown.Value = (decimal)selectedTeam.GoodsCreate;
                TeamCostLaborUpDown.Value = (decimal)selectedTeam.LaborCreate;
                TeamCostMagicUpDown.Value = (decimal)selectedTeam.MagicCreate;

                TeamDaysCreateLabel.Text = "Days to create: " + selectedTeam.DaysCreate + " days";

                TeamDescriptionTextBox.Text = selectedTeam.LimtedDescriptionForTextBox();
            }
        }

        private void saveAsToolStripMenuItem_Click(object sender, EventArgs e)
        {
            SaveFileDialog savefile1 = new SaveFileDialog();

            savefile1.Filter = "Kingmaker files |*.king";
            savefile1.FilterIndex = 2;
            savefile1.RestoreDirectory = true;

            if (savefile1.ShowDialog() == DialogResult.OK)
            {
                savableFilename = savefile1.FileName;
                SaveValues();
            }
        }

        public void SaveValues()
        {
            /*XmlSerializer serializer = new XmlSerializer();
            using(TextWriter textWriter = new StreamWriter(filename)
            {
                serializer.Serialize(textWriter, v);
            }*/
            Stream myStream;

            Console.WriteLine("Writing file: " + savableFilename);
            IFormatter formatter = new BinaryFormatter();
            myStream = new FileStream(savableFilename, FileMode.Create, FileAccess.Write, FileShare.None);
            formatter.Serialize(myStream, central);
            myStream.Close();
        }

        private void saveToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (savableFilename != null)
            {
                SaveValues();
            }
            else
            {
                SaveFileDialog savefile1 = new SaveFileDialog();

                savefile1.Filter = "Kingmaker files |*.king";
                savefile1.FilterIndex = 2;
                savefile1.RestoreDirectory = true;

                if (savefile1.ShowDialog() == DialogResult.OK)
                {
                    savableFilename = savefile1.FileName;
                    SaveValues();
                }
            }
        }

        private void openToolStripMenuItem_Click(object sender, EventArgs e)
        {
            OpenFileDialog openfile1 = new OpenFileDialog();

            openfile1.Filter = "Kingmaker files |*.king";
            openfile1.FilterIndex = 0;
            openfile1.RestoreDirectory = true;

            if (openfile1.ShowDialog() == DialogResult.OK)
            {
                savableFilename = openfile1.FileName;

                IFormatter formatter = new BinaryFormatter();
                Stream stream = new FileStream(savableFilename, FileMode.Open, FileAccess.Read, FileShare.Read);
                central = (CentralCommand)formatter.Deserialize(stream);
                stream.Close();

                generalRefresh();
            }
        }

        private void generalRefresh()
        {
            PreferredIncomeComboBox.SelectedIndex = 0;
            OrganizationPreferredIncomeComboBox.SelectedIndex = 0;

            //Building tab
            ListBox.ObjectCollection items = BuildingPickerListbox.Items;
            items.AddRange(GetBuildingNames().ToArray());
            BuildingPickerListbox.SelectedIndex = 0;

            //Room tab
            ListBox.ObjectCollection roomItems = RoomListBox.Items;
            roomItems.AddRange(GetRoomNames().ToArray());
            RoomListBox.SelectedIndex = 0;

            //Organization tab
            ListBox.ObjectCollection organizationItems = OrganizationListBox.Items;
            organizationItems.AddRange(GetOrganizationNames().ToArray());
            OrganizationListBox.SelectedIndex = 0;

            //Team tab
            ListBox.ObjectCollection teamItems = TeamListBox.Items;
            teamItems.AddRange(GetTeamNames().ToArray());
            TeamListBox.SelectedIndex = 0;

            //Unit testing
            //selectedBuilding = central.getCompleteBuildingList()[0];

            //Console.Out.WriteLine("Selected building: " + selectedBuilding.toString());
            setBuildingFields();
            setRoomFields();
            
            //Hope this works
            Console.WriteLine("Businesses within central: " + central.businesses.Length);
            FillBusinessListBox();
            PopulateBusinessData();

            //Settlement tab
            updateSettlementPane();
        }

        private void updateSettlementPane()
        {
            if (central.command.derivedSettlement != null)
            {
                central.command.derivedSettlement.calculateModifiers();

                //Magic items
                MinorItemsLabel.Text = central.command.derivedSettlement.MinorItems.ToString();
                MajorItemsLabel.Text = central.command.derivedSettlement.MajorItems.ToString();
                MediumItemsLabel.Text = central.command.derivedSettlement.MediumItems.ToString();

                //raw stats
                SpellcastingLabel.Text = "Spellcasting: Spells up to level" + central.command.derivedSettlement.Spellcasting.ToString();
                PurchaseLimitLabel.Text = "Purchase Limit: 75% chance for items worth " + central.command.derivedSettlement.PurchaseLimit + "gp to be readily available";
                SettlementPopulationLabel.Text = "Population: " + central.command.derivedSettlement.Population;
                SizeLabel.Text = "Size: " + central.command.derivedSettlement.Size;
                AlignmentLabel.Text = "Alignment: " + central.command.derivedSettlement.Alignment;

                //Kingdom-wide stats
                LoyaltyWideUpDown = central.command.derivedSettlement;
                FameWideUpDown;
                EconomyWideUpDown;
                StabilityWideUpDown;
                UnrestWideUpDown;
                DefenseWideUpDown;

                //Settlement-wide stats
                CrimeWideUpDown;
                CorruptionWideUpDown;
                ProductivityWideUpDown;
                LawWideUpDown;
                LoreWideUpDown;
                SocietyWideUpDown;

                updatePlayerGroupBox();
            }
        }

        public void updatePlayerGroupBox()
        {
            //Populate the player list
            PlayerListBox;

            //Kingdom stats - lower player section
            LoyaltyPlayerUpDown;
            FamePlayerUpDown;
            EconomyPlayerUpDown;
            StabilityPlayerUpDown;
            UnrestPlayerUpDown;
            DefensePlayerUpDown;

            //Settlement stats - lower player section
            CrimePlayerUpDown;
            CorruptionPlayerUpDown;
            ProductivityPlayerUpDown;
            LawPlayerUpDown;
            LorePlayerUpDown;
            SocietyPlayerUpDown;
            SettlementWideGroupBox;
            DistrictsWithinTextBox;
            DistrictsWithinLabel;
            CoreStatsLabel;
        }

        private void BrokenBuildingCheckbox_CheckedChanged(object sender, EventArgs e)
        {

        }

        private void updateBuildingCost()
        {
            float rushCostMod = 0;
            float generalCostMod = 0;

            //Reset values from array
            FlagsGoldUpDown.Value = (decimal)selectedRoom.GPCreate;
            FlagsInfluenceCostUpDown.Value = (decimal)selectedRoom.InfluenceCreate;
            FlagsGoodsUpDown.Value = (decimal)selectedRoom.GoodsCreate;
            FlagsLaborUpDown.Value = (decimal)selectedRoom.LaborCreate;
            FlagsMagicUpDown.Value = (decimal)selectedRoom.MagicCreate;
            FlagsDaysToCreateLabel.Text = "Days to create: " + selectedRoom.DaysCreate + " days";

            rushCostMod = (float)RoomRushProductionCostUpDown.Value / 100;
            if (RoomCostModUpDown.Value < 0)
                generalCostMod = (float)(1 - (Math.Abs(RoomCostModUpDown.Value) / 100));
            if (RoomCostModUpDown.Value > 0)
                generalCostMod = (float)((RoomCostModUpDown.Value / 100) + 1);
            if (RoomCostModUpDown.Value == 0)
                generalCostMod = 1;
            if (RoomCostModUpDown.Value == -100)
                generalCostMod = 0;


            //RushCostModLabel.Text = "Rush Cost Mod: " + rushCostMod.ToString();
            //GeneralCostModLabel.Text = "General Cost Mod: " + generalCostMod.ToString();
            //DaysCreateMod.Text = "Days Create Mod: " + (1 - (((RoomRushProductionCostUpDown.Value - 100) / 100) / 2)).ToString();

            if (rushCostMod != 0)
            {
                FlagsGoldUpDown.Value = FlagsGoldUpDown.Value * (decimal)rushCostMod;
                FlagsInfluenceCostUpDown.Value = FlagsInfluenceCostUpDown.Value * (decimal)rushCostMod;
                FlagsGoodsUpDown.Value = FlagsGoodsUpDown.Value * (decimal)rushCostMod;
                FlagsLaborUpDown.Value = FlagsLaborUpDown.Value * (decimal)rushCostMod;
                FlagsMagicUpDown.Value = FlagsMagicUpDown.Value * (decimal)rushCostMod;
            }

            FlagsGoldUpDown.Value = FlagsGoldUpDown.Value * (decimal)generalCostMod;
            FlagsInfluenceCostUpDown.Value = FlagsInfluenceCostUpDown.Value * (decimal)generalCostMod;
            FlagsGoodsUpDown.Value = FlagsGoodsUpDown.Value * (decimal)generalCostMod;
            FlagsLaborUpDown.Value = FlagsLaborUpDown.Value * (decimal)generalCostMod;
            FlagsMagicUpDown.Value = FlagsMagicUpDown.Value * (decimal)generalCostMod;

            //Adjust creation time
            double daysToCreate = selectedRoom.DaysCreate;
            if (rushCostMod != 0)
                daysToCreate = Math.Ceiling(selectedRoom.DaysCreate * (float)(1 - (((RoomRushProductionCostUpDown.Value - 100) / 100)) / 2));
            if (RoomBrokenCheckbox.Checked)
                daysToCreate *= .5;

            FlagsDaysToCreateLabel.Text = "Days to create: " + daysToCreate + " days";
        }

        private void NewSettlementButton_Click(object sender, EventArgs e)
        {

        }
    }
}
