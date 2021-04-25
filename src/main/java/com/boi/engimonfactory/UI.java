package com.boi.engimonfactory;
import imgui.ImGui;

public class UI {
    private boolean showText = false;
    private boolean showInv = false;
    private Player player;

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
    public void insertPlayer(Player p) {
        this.player = p;
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

    public void menuInventory() {
        ImGui.begin("Menu Inventory");
        ImGui.text("This is your inventory, " + this.player.getName() + "\n"
            + this.player.getPrintInventoryE() + this.player.getPrintInventoryS());
        ImGui.end();
    }
}
