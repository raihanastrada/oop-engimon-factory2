package com.boi.engimonfactory;
import imgui.ImGui;
import imgui.type.ImInt;

public class UI {
    private boolean showText = false;
    private boolean showInv = false;
    private boolean showSwitch = false;
    private boolean showInteract = false;
    private Player player;
    private Game game;
    private ImInt selectedActive = new ImInt();

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

        // @TODO hapus ini
        if (ImGui.button("Add Random Engimon")) {
            this.game.addRandomEngimonPlayer();
            System.out.println("Added Engimon");
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

        ImGui.end();

        if (showText)
            menu2();
        if (showInv)
            menuInventory();
        if (showSwitch)
            menuSwitchActive();
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

}
