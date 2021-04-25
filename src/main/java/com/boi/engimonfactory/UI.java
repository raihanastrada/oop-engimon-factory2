package com.boi.engimonfactory;
import imgui.ImGui;
import imgui.type.ImInt;
import java.util.ArrayList;

public class UI {
    private boolean showText = false;
    private boolean showInv = false;
    private boolean showSwitch = false;
    private boolean showInteract = false;
    private boolean showBreed = false;
    private boolean isInventoryFull = false;
    private boolean breedClicked = false;
    private boolean showMenuBattle = false;
    private Player player;
    private Game game;
    private ImInt selectedActive = new ImInt();
    private ImInt selectedMom = new ImInt();
    private ImInt selectedDad = new ImInt();
    private String messageBreed = "";

    /*
        - Checkbox
        static boolean checkBox = false;
        ImGui.checkbox("Toggle", &checkbox);

        - Slider
        static int sliderInt = 0
        ImGui.SliderInt("SliderInt", &sliderInt, 0, 100)

        - ListBox
        static const string[] inventory("Inventory Engimon", "Inventory Skill")
        static int selectedItem = 0;
        ImGui.ListBox("ListBox", &selectedItem, inventory, IM_ARRAYSIZE(inventory))

     */

    public void insertGame(Game g) {
        this.game = g;
        this.player = g.getPlayer();
    }
    public void ui() {
        ImGui.begin("Engimon Factory");
        this.isInventoryFull = this.player.isInventoryFull();
        if (ImGui.button("I am a button")) {
            showText = true;
            System.out.println("This Works");
        }

        if (showText) {
            ImGui.text("You clicked a button");
            ImGui.sameLine();
            if (ImGui.button("Stop showing text")) {
                showText = false;
            }
        }

        if (ImGui.button("Show Inventory")) {
            showInv = true;
            System.out.println("Inventory clicked");
        }

        if (showInv) {
            ImGui.text("Showing inventory");
            ImGui.sameLine();
            if (ImGui.button("Close inventory")) showInv = false;
        }

        if (ImGui.button("Interact")) {
            showInteract = true;
            System.out.println("Interact clicked");
        }

        if (showInteract) {
            if (this.player.getActiveEngimon() == null) {
                ImGui.text("Tidak ada engimon aktif");
            } else {
                ImGui.text(this.player.interact());
            }
            ImGui.sameLine();
            if (ImGui.button("Close interact")) showInteract = false;
        }

        if (ImGui.button("Breed")) {
            showBreed = true;
            this.messageBreed = "";
            System.out.println("Breed clicked");
        }

        if (showBreed && isInventoryFull) {
            ImGui.text("Inventory Full");
            ImGui.sameLine();
        }

        if (showBreed && !isInventoryFull) {
            if (this.player.isInventoryFull()) {
                ImGui.text("Inventory full, cannot breed");
                this.showBreed = false;
            } else {
                ImGui.text("Showing breed menu");
                ImGui.sameLine();;
                if (ImGui.button("Close breed menu")) showBreed = false;
            }
        }

        if (ImGui.button("Show Switch Engimon")) {
            showSwitch = true;
            System.out.println("Switch clicked");
        }

        if (showSwitch) {
            ImGui.text("Switch Active engimon");
            ImGui.sameLine();
            if (ImGui.button("Close switch")) showSwitch = false;
        }

        if (ImGui.button("Battle")) {
            showMenuBattle = true;
            if (ImGui.button("Close battle")){
                showMenuBattle = false;
            }
        }

        // @TODO hapus ini
        if (ImGui.button("Add Random Engimon")) {
            this.game.addRandomEngimonPlayer();
            System.out.println("Added Engimon");
        }

        ImGui.end();

        if (showText)
            menu2();
        if (showInv)
            menuInventory();
        if (showSwitch)
            menuSwitchActive();
        if (showBreed && !isInventoryFull) {
            menuBreed();
        }
        if (showMenuBattle) {
            menuBattlePrep();
        }
    }

    // Bisa bikin di kelas beda
    public void menu2()
    {
        ImGui.begin("Menu2");
        ImGui.text("This is menu 2");
        ImGui.end();
    }

    // @TODO inventory menu not done
    public void menuInventory() {
        String message = "This is your inventory, " + this.player.getName() + "\n"
                + "Engimon:\n" + this.player.getPrintInventoryE()
                + "SkillItem:\n";
        ImGui.begin("Menu Inventory");
        ImGui.text(message);
        ImGui.end();
    }

    // @DONE
    public void menuSwitchActive() {
        ImGui.begin("Switch Active Engimon");
        String[] comboitems = new String[this.player.getInvE().getSize()];
        for (int i = 0; i < this.player.getInvE().getSize(); i++) {
            comboitems[i] = this.player.getInvE().getItemByIdx(i).getPrint();
        }
        ImGui.combo("Label", selectedActive, comboitems);
        ImGui.end();
        this.player.switchActive(this.selectedActive.getData()[0]);
    }

    public void menuBreed() {
        ImGui.begin("Breed Menu AWOOOGA");
        String[] comboitemsMom = new String[this.player.getInvE().getSize()];
        String[] comboitemsDad = new String[this.player.getInvE().getSize()];
        for (int i = 0; i < this.player.getInvE().getSize(); i++) {
            comboitemsMom[i] = this.player.getInvE().getItemByIdx(i).getPrint();
            comboitemsDad[i] = this.player.getInvE().getItemByIdx(i).getPrint();
        }
        ImGui.text("Mom");
        ImGui.combo("ChooseMom", selectedMom, comboitemsMom);
        ImGui.text("Dad");
        ImGui.combo("ChooseDad", selectedDad, comboitemsDad);
        if (ImGui.button("Breeeed"))
            this.messageBreed = this.player.breed(selectedMom.getData()[0], selectedDad.getData()[0]);
        ImGui.text(this.messageBreed);
        ImGui.end();
        // String message = this.player.breed()
    }

    public void menuBattlePrep(){
        ImGui.begin("Battle");
        try{
            // get battle engimons
            Pair<Engimon, Cell> p = game.getBattleEngimon();
            // show battle status
            // isi battle_status: [detail wild engimon, power active engimon, power wild engimon]
            ArrayList<String> battle_status = BattleUtil.getBattleStatus(p.getItem1(), p.getItem2().getEnemy());
            ImGui.text("Enemy engimon: " + battle_status.get(0));
            ImGui.text("Active engimon power: " + battle_status.get(1));
            ImGui.text("Enemy engimon power: " + battle_status.get(2));
            // kasih pilihan ke pemain
            ImGui.text("Commence battle?");
            ImGui.sameLine();
            if (ImGui.button("Yes")){
                ArrayList<String> messages = game.battle(p.getItem1(), p.getItem2());
                showMenuBattle = false;
                menuBattleResults(messages);
            }
            ImGui.sameLine();
            if (ImGui.button("No")) {
                showMenuBattle = false;
            }
        }
        catch (Exception e){
            ImGui.text(e.getMessage());
        }
        ImGui.end();
    }

    public void menuBattleResults(ArrayList<String> messages){
        ImGui.begin("Battle results");
        for (String s : messages){
            ImGui.text(s);
        }
        ImGui.end();
    }
}
