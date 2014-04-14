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
    public partial class BusinessCreationForm : Form
    {
        //Sub-forms to display the lists of buildings and organizations
        private BuildingPickerWindow BuildingPicker;
        private OrganizationPickerWindow OrganizationPicker;
        private SubForms.RoomPickerWindow RoomPicker;
        private SubForms.TeamPickerWindow TeamPicker;

        //Pull source data from central class
        private CentralCommand central = new CentralCommand(Properties.Resources.OwnersCSV, Properties.Resources.settlementExample);
        
        public Business selectedBusiness = new Business();

        //Internal data-passers
        private Building selectedBuilding;
        private Organization selectedOrganization;
        private Room selectedRoom;
        private Team selectedTeam;

        //Used to restore class data in case of a catastrophe
        private Business backup = new Business();

        //Organization selectedOrganization = new Organization();

        public BusinessCreationForm()
        {
            InitializeComponent();
            PopulateBuildingList();
            PopulateOrganizationList();

            UnitTestPopulating();
        }

        private void UnitTestPopulating()
        {
            CharacterNameTextBox.Text = "Brom Burnedbeard";
            PlayerNameTextBox.Text = "Judd Hunter";
            BusinessNameTextBox.Text = "Judd's Bar and Tavern";
        }

        public BusinessCreationForm(Business selectedBusiness)
        {
            InitializeComponent();

            //Clone the business in case of a wierd cancellation
            backup = new Business(selectedBusiness);

            //Set the business, then propegate fields
            this.selectedBusiness = selectedBusiness;

            //Fill in the list boxes
            PopulateBuildingList();
            PopulateOrganizationList();
            PopulateOptionalTab();
        }

        private void AddBuildingButton_Click(object sender, EventArgs e)
        {
            BuildingPicker = new BuildingPickerWindow();
            BuildingPicker.ShowDialog();

            if (BuildingPicker.DialogResult == DialogResult.OK)
            {
                //MessageBox.Show(BuildingPicker.selectedBuilding.toString());
                selectedBusiness.addBuilding(BuildingPicker.selectedBuilding);
                PopulateBuildingList();
                BuildingsListBox.SelectedIndex = selectedBusiness.getBuildings().Length - 1;

                selectedBuilding = BuildingPicker.selectedBuilding;
                setDescription(selectedBuilding);
            }
            else if (BuildingPicker.DialogResult == DialogResult.Cancel)
            {
                //MessageBox.Show("Nothing selected, boss");
            }
            else
            {
                //MessageBox.Show("You didn't even pick a button :(");
            }
        }

        private void RemoveBuildingButton_Click(object sender, EventArgs e)
        {
            //We need to get the most recent copy from the Business
            Building toBeRemoved = selectedBusiness.getBuildings()[BuildingsListBox.SelectedIndex];

            //Debugging junk
            if (selectedBusiness.removeBuilding(toBeRemoved))
            {
                //MessageBox.Show("Successfully Removed. New length is " + selectedBusiness.getBuildings().Length + ".");
            }
            else
            {
                MessageBox.Show("Failed to remove " + toBeRemoved.name + ".");
            }

            PopulateBuildingList();
        }   

        //Fills the BuildingsListBox with the list of buildings currently associated with this building.
        private void PopulateBuildingList()
        {
            //Only proceed if there are buildings to populate
            if (selectedBusiness.getBuildings().Length > 0)
            {
                List<String> names = new List<String>(0);

                Building[] properties = selectedBusiness.getBuildings();

                foreach (Building element in properties)
                {
                    //MessageBox.Show(element.name);
                    names.Add(element.name);
                }

                //Pull items array, clear it, add our rebuilt structure
                ListBox.ObjectCollection items = BuildingsListBox.Items;
                items.Clear();
                items.AddRange(names.ToArray());
                BuildingsListBox.SelectedIndex = 0;
            }
            else
            {
                //if there aren't any buildings, clear the list to reflect this
                ListBox.ObjectCollection items = BuildingsListBox.Items;
                items.Clear();
            }
        }

        private void PopulateOrganizationList()
        {
            //Only proceed if there are organizations to populate
            if (selectedBusiness.getOrganizations().Length > 0)
            {
                List<String> names = new List<String>(0);

                Organization[] personnel = selectedBusiness.getOrganizations();

                foreach (Organization element in personnel)
                {
                    //MessageBox.Show(element.name);
                    names.Add(element.name);
                }

                ListBox.ObjectCollection items = OrganizationListBox.Items;
                items.Clear();
                items.AddRange(names.ToArray());
                OrganizationListBox.SelectedIndex = 0;
            }
            else
            {
                //Otherwise, just clear the list
                ListBox.ObjectCollection items = OrganizationListBox.Items;
                items.Clear();
            }
        }

        private void PopulateOptionalTab()
        {
            //Buildings List Box
            if (selectedBuilding != null)
            {
                //Alter label above ListBox
                RoomsField.Text = "Rooms within " + selectedBuilding.name;
                if (selectedBuilding.rooms.Length > 0)
                {
                    List<String> names = new List<string>(0);
                    foreach (Room item in selectedBuilding.rooms)
                    {
                        names.Add(item.Name);
                    }

                    ListBox.ObjectCollection items = AdditionalRoomsListBox.Items;
                    items.Clear();
                    items.AddRange(names.ToArray());

                    if (names.Count > 0)
                        AdditionalRoomsListBox.SelectedIndex = 0;
                }
                else
                {
                    ListBox.ObjectCollection items = AdditionalRoomsListBox.Items;
                    items.Clear();
                }
            }
            else
                RoomsField.Text = "<No Building Selected>";

            //Organization List Box
            if (selectedOrganization != null)
            {
                //Alter label above ListBox
                AdditionalEmployeesLabel.Text = "Employees within " + selectedOrganization.name;
                
                //Only proceed if a Organization's been selected - might not be necessary
                if (selectedOrganization.teams.Length > 0)
                {
                    List<String> names = new List<string>(0);
                    foreach (Team item in selectedOrganization.teams)
                    {
                        names.Add(item.Name);
                    }

                    ListBox.ObjectCollection items = AdditionalEmployeesListBox.Items;
                    items.Clear();
                    items.AddRange(names.ToArray());

                    if (names.Count > 0)
                        AdditionalEmployeesListBox.SelectedIndex = 0;
                }
                else
                {
                    ListBox.ObjectCollection items = AdditionalRoomsListBox.Items;
                    items.Clear();
                }
            }
            else
                AdditionalEmployeesLabel.Text = "<No Organization Selected>";

            /*if (AdditionalRoomsListBox.SelectedIndex >= 0)
            {
                setOptionalDescription(selectedBuilding.rooms[AdditionalRoomsListBox.SelectedIndex]);
            }
            else
            {
                if (AdditionalEmployeesListBox.SelectedIndex >= 0)
                    setOptionalDescription(selectedOrganization.teams[AdditionalEmployeesListBox.SelectedIndex]);
            }*/
        }

        //Set the generic text box field with some information, based off the last thing selected
        private void setDescription(Object obj)
        {
            if (obj is Building)
            {
                Building sample = (Building)obj;
                GeneralDescriptionTextBox.Text = sample.getDescriptionTestBoxString();
            }
            if (obj is Organization)
            {
                Organization sample = (Organization)obj;
                GeneralDescriptionTextBox.Text = sample.toTextBoxOutput();
            }
        }

        //Set the optional generic text box field with some information, based off the last thing selected
        private void setOptionalDescription(Object obj)
        {
            if (obj is Room)
            {
                Room sample = (Room)obj;
                OptionalFieldTextBox.Text = sample.toStringNormalizedOutput();
            }
            if (obj is Team)
            {
                Team sample = (Team)obj;
                OptionalFieldTextBox.Text = sample.toStringForTextBox();
            }
        }

        private void BuildingsListBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (BuildingsListBox.SelectedIndex >= 0 && BuildingsListBox.SelectedIndex < BuildingsListBox.Items.Count)
            {
                Building toBeDescribed = selectedBusiness.getBuildings()[BuildingsListBox.SelectedIndex];
                //MessageBox.Show("Selected Index: " + BuildingsListBox.SelectedIndex);
                
                //Set currently selected building in global structure, for editing later
                selectedBuilding = toBeDescribed;
                setDescription(toBeDescribed);
            }
        }

        private void AddOrganizationButton_Click(object sender, EventArgs e)
        {
            OrganizationPicker = new OrganizationPickerWindow();
            OrganizationPicker.ShowDialog();

            if (OrganizationPicker.DialogResult == DialogResult.OK)
            {
                //MessageBox.Show(BuildingPicker.selectedBuilding.toString());
                selectedBusiness.addOrganization(OrganizationPicker.selectedOrganization);
                PopulateOrganizationList();

                OrganizationListBox.SelectedIndex = selectedBusiness.getOrganizations().Length - 1;
                selectedOrganization = OrganizationPicker.selectedOrganization;
                setDescription(OrganizationPicker.selectedOrganization);
            }
            else if (OrganizationPicker.DialogResult == DialogResult.Cancel)
            {
                //MessageBox.Show("Nothing selected, boss");
            }
            else
            {
                //MessageBox.Show("You didn't even pick a button :(");
            }
        }

        private void RemoveOrganizationButton_Click(object sender, EventArgs e)
        {
            //We need to get the most recent copy from the Business
            Organization toBeRemoved = selectedBusiness.getOrganizations()[OrganizationListBox.SelectedIndex];

            //Debugging junk
            if (selectedBusiness.removeOrganization(toBeRemoved))
            {
               //MessageBox.Show("Successfully Removed. New length is " + selectedBusiness.getBuildings().Length + ".");
            }
            else
            {
                MessageBox.Show("Failed to remove " + toBeRemoved.name + ".");
            }

            PopulateOrganizationList();
        }

        private void OrganizationListBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (OrganizationListBox.SelectedIndex >= 0 && OrganizationListBox.SelectedIndex < OrganizationListBox.Items.Count)
            {
                Organization toBeDescribed = selectedBusiness.getOrganizations()[OrganizationListBox.SelectedIndex];

                //Set currently selected building in global structure, for editing later
                selectedOrganization = toBeDescribed;
                //MessageBox.Show("Selected Index: " + BuildingsListBox.SelectedIndex);
                setDescription(toBeDescribed);
            }
        }

        private void UpdateOptionalTab()
        {
            if (BuildingsListBox.SelectedIndex >= 0 && BuildingsListBox.SelectedIndex < BuildingsListBox.Items.Count)
            {
                //Set currently selected building in global structure, for editing later
                selectedBuilding = selectedBusiness.getBuildings()[BuildingsListBox.SelectedIndex];
            }
            if (OrganizationListBox.SelectedIndex >= 0 && OrganizationListBox.SelectedIndex < OrganizationListBox.Items.Count)
            {
                //Set currently selected building in global structure, for editing later
                selectedOrganization = selectedBusiness.getOrganizations()[OrganizationListBox.SelectedIndex];
            }

            PopulateOptionalTab();
        }

        private void EditBuildingButton_Click(object sender, EventArgs e)
        {
            UpdateOptionalTab();
            
            NewBusinessTabControl.SelectedIndex = 1;
        }

        private void EditOrganizationButton_Click(object sender, EventArgs e)
        {
            UpdateOptionalTab();

            NewBusinessTabControl.SelectedIndex = 1;
        }

        private void AdditionalRoomsListBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (AdditionalRoomsListBox.SelectedIndex >= 0 && AdditionalRoomsListBox.SelectedIndex < selectedBuilding.rooms.Length)
            {
                setOptionalDescription(selectedBuilding.rooms[AdditionalRoomsListBox.SelectedIndex]);
            }
        }

        private void AdditionalEmployeesListBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (AdditionalEmployeesListBox.SelectedIndex >= 0 && AdditionalEmployeesListBox.SelectedIndex < selectedOrganization.teams.Length)
            {
                setOptionalDescription(selectedOrganization.teams[AdditionalEmployeesListBox.SelectedIndex]);
            }
        }

        private void RemoveRoomButton_Click(object sender, EventArgs e)
        {
            Room toBeRemoved = selectedBuilding.rooms[AdditionalRoomsListBox.SelectedIndex];
            //Debugging junk
            if (selectedBuilding.removeRoom(toBeRemoved))
            {
                //MessageBox.Show("Successfully Removed. New length is " + selectedBusiness.getBuildings().Length + ".");
            }
            else
            {
                MessageBox.Show("Failed to remove " + toBeRemoved.Name + ".");
            }

            PopulateOptionalTab();
        }

        private void RemoveEmployeeButton_Click(object sender, EventArgs e)
        {
            Team toBeRemoved = selectedOrganization.teams[AdditionalRoomsListBox.SelectedIndex];
            //Debugging junk
            if (selectedOrganization.removeTeam(toBeRemoved))
            {
                //MessageBox.Show("Successfully Removed. New length is " + selectedBusiness.getBuildings().Length + ".");
            }
            else
            {
                MessageBox.Show("Failed to remove " + toBeRemoved.Name + ".");
            }

            PopulateOptionalTab();
        }

        private void AddRoomButton_Click(object sender, EventArgs e)
        {
            RoomPicker = new SubForms.RoomPickerWindow();
            RoomPicker.ShowDialog();

            if (RoomPicker.DialogResult == DialogResult.OK)
            {
                //MessageBox.Show(BuildingPicker.selectedBuilding.toString());
                selectedBuilding.addRoom(RoomPicker.selectedRoom);

                PopulateOptionalTab();
                AdditionalRoomsListBox.SelectedIndex = selectedBuilding.rooms.Length - 1;

                setOptionalDescription(RoomPicker.selectedRoom);
            }
            else if (BuildingPicker.DialogResult == DialogResult.Cancel)
            {
                //MessageBox.Show("Nothing selected, boss");
            }
            else
            {
                //MessageBox.Show("You didn't even pick a button :(");
            }
        }

        private void CancelFormButton_Click(object sender, EventArgs e)
        {
            selectedBusiness = new Business(backup);
            this.Close();
        }

        private Boolean CompileBusiness()
        {
            String errorList = "";

            if (CharacterNameTextBox.Text.Equals(""))
                errorList += "Character name is blank\r\n";
            if (PlayerNameTextBox.Text.Equals(""))
                errorList += "Player name is blank\r\n";
            if (BusinessNameTextBox.Text.Equals(""))
                errorList += "Business name is blank\r\n";
            if (BuildingsListBox.Items.Count == 0 && OrganizationListBox.Items.Count == 0)
                errorList += "No Buildings or Organizations in business\r\n";

            //Buildings and Organization arrays should be set, but clear them and throw an error if there's a mismatch
            if (selectedBusiness.getBuildings().Length != BuildingsListBox.Items.Count)
                errorList += "Buildings in business don't match ListBox. What did you do?!\r\n";
            if (selectedBusiness.getOrganizations().Length != OrganizationListBox.Items.Count)
                errorList += "Employees in business don't match ListBox. What did you do?!\r\n";

            //Set business name
            selectedBusiness.setName(BusinessNameTextBox.Text);

            if (errorList.Equals(""))
            {
                //No errors, create and update the business
                //Build the manager, if present
                if (!ManagerNameTextBox.Text.Equals(""))
                {
                    Manager newManager = new Manager();
                    newManager.Name = ManagerNameTextBox.Text;
                    newManager.Wage = 0;
                    newManager.Description = CharacterNameTextBox.Text + "'s designated manager for " + BusinessNameTextBox.Text;

                    //Now for the 'fun' part
                    int Appraise = (int)AppraiseUpDown.Value;
                    int Bluff = (int)BluffUpDown.Value;
                    int Climb = (int)ClimbUpDown.Value;
                    int Craft = (int)CraftUpDown.Value;
                    int Diplomacy = (int)DiplomacyUpDown.Value;
                    int DisableDevice = (int)DisableDeviceUpDown.Value;
                    int HandleAnimal = (int)HandleAnimalUpDown.Value;
                    int Intimidate = (int)IntimidateUpDown.Value;
                    int Heal = (int)HealUpDown.Value;
                    int Knowledge = (int)KnowledgeUpDown.Value;
                    int Linguistics = (int)LinguisticsUpDown.Value;
                    int Perform = (int)PerformUpDown.Value;
                    int Profession = (int)ProfessionUpDown.Value;
                    int SleightofHand = (int)SleightOfHandUpDown.Value;
                    int Stealth = (int)StealthUpDown.Value;
                    int Ride = (int)RideUpDown.Value;
                    int Survival = (int)SurvivalUpDown.Value;
                    int Swim = (int)SwimUpDown.Value;
                    int Spellcraft = (int)SpellcraftUpDown.Value;
                    int UseMagicDevice = (int)UseMagicDeviceUpDown.Value;

                    Skill[] skillArray = new Skill[20];

                    skillArray[0] = new Skill("Appraise",Appraise);
                    skillArray[1] = new Skill("Bluff",Bluff);
                    skillArray[2] = new Skill("Climb",Climb);
                    skillArray[3] = new Skill("Craft",Craft);
                    skillArray[4] = new Skill("Diplomacy",Diplomacy);
                    skillArray[5] = new Skill("Disable Device",DisableDevice);
                    skillArray[6] = new Skill("Handle Animal",HandleAnimal);
                    skillArray[7] = new Skill("Intimidate",Intimidate);
                    skillArray[8] = new Skill("Heal",Heal);
                    skillArray[9] = new Skill("Knowledge",Knowledge);
                    skillArray[10] = new Skill("Linguistics",Linguistics);
                    skillArray[11] = new Skill("Perform",Perform);
                    skillArray[12] = new Skill("Profession",Profession);
                    skillArray[13] = new Skill("Sleight of Hand",SleightofHand);
                    skillArray[14] = new Skill("Stealth",Stealth);
                    skillArray[15] = new Skill("Ride",Ride);
                    skillArray[16] = new Skill("Survival",Survival);
                    skillArray[17] = new Skill("Swim",Swim);
                    skillArray[18] = new Skill("Spellcraft",Spellcraft);
                    skillArray[19] = new Skill("Use Magic Device",UseMagicDevice);
                    
                    newManager.Skills = skillArray;

                    //Final assignment
                    selectedBusiness.setManager(newManager);
                }

                if (selectedBusiness.getOwner().OwnerName.Equals(""))
                {
                    //Seems like the owner needs to be created from scratch!
                    Owner toBeSet = new Owner();
                    toBeSet.OwnerName = CharacterNameTextBox.Text;
                    toBeSet.PlayerName = PlayerNameTextBox.Text;
                    selectedBusiness.setOwner(toBeSet);
                }
                else
                {
                    Owner toBeSet = new Owner(selectedBusiness.getOwner());
                    toBeSet.OwnerName = CharacterNameTextBox.Text;
                    toBeSet.PlayerName = PlayerNameTextBox.Text;
                    selectedBusiness.setOwner(toBeSet);
                }

                //Finally, set the ownership of the buildings, and map the owner in
                selectedBusiness.assignOwner();

                Console.WriteLine("Sending back the following:\r\n" + selectedBusiness.getHoldings());

                return true;
            }
            else
            {
                MessageBox.Show("Encountered some errors while trying to save this business:\r\n" + errorList, "Error saving changes", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return false;
            }
        }

        private void SaveChangesButton_Click(object sender, EventArgs e)
        {
            Boolean flag = CompileBusiness();
            if (flag)
            {
                //MessageBox.Show("Changes Saved!", "Success!", MessageBoxButtons.OK, MessageBoxIcon.Information);
                this.Close();
            }
            else
            {
                //Error message will occur in compile service
            }
        }
    }
}
