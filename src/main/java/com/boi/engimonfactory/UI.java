package com.boi.engimonfactory;
import imgui.ImGui;

public class UI {
    private boolean showText = false;
    private boolean showInv = false;
    private Player player;
    private Game game;

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
            if (ImGui.button("Close inventory")) {
                showInv = false;
            }
        }

        // @TODO hapus ini
        if (ImGui.button("Add Random Engimon")) {
            this.game.addRandomEngimonPlayer();
            System.out.println("Added Engimon");
        }

        ImGui.end();

        if (showText)
        {
            menu2();
        }
        if (showInv) {
            menuInventory();
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
        ImGui.text("");
        ImGui.end();
    }

    public void menuSwitchActive() {
        ImGui.begin("Switch Active Engimon");
        String[] comboitems;
        // for (int i = 0; i < this.player.getacti)
        // ImGui.combo();
    }
}
