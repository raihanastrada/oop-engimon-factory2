package com.boi.engimonfactory;
import imgui.ImGui;

public class UI {
    private boolean showText = false;

    public void ui() {
        ImGui.begin("Player");

        if (ImGui.button("I am a button")) {
            showText = true;
        }

        if (showText) {
            ImGui.text("You clicked a button");
            ImGui.sameLine();
            if (ImGui.button("Stop showing text")) {
                showText = false;
            }
        }

        ImGui.end();

        if (showText)
        {
            menu2();
        }
    }

    public void menu2()
    {
        ImGui.begin("Menu2");
        ImGui.text("This is menu 2");
        ImGui.end();
    }
}
