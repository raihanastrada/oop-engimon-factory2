package com.boi.engimonfactory;
import imgui.ImGui;
import imgui.flag.ImGuiInputTextFlags;
import imgui.type.ImInt;
import imgui.type.ImString;
import java.util.ArrayList;

public class UI {
    private Player player;
    private Game game;
    private boolean running = false;
    /* Buat Menu Start */
    private boolean showNewGame = false;
    private boolean showLoadGame = false;
    private boolean showInputName = false;
    private ImString name = new ImString(8);
    private int[] maxCapInv = new int[1];
    private int[] maxCapEng = new int[1];

    private int time = 0;
    //
    private boolean showText = false;
    private boolean showInv = false;
    private boolean showSwitch = false;
    private boolean showInteract = false;
    private boolean showBreed = false;
    private boolean isInventoryFull = false;
    private boolean isInventorySkillEmpty = false;
    private boolean showMenuBattle1 = false;
    private boolean showMenuBattle2 = false;
    private boolean showRelease = false;
    private boolean showBuang = false;
    private boolean showDetail = false;
    private ImInt selectedRelease = new ImInt();
    private ImInt selectedBuang = new ImInt();
    private ImInt selectedBuangCount = new ImInt();
    private ImInt selectedActive = new ImInt();
    private ImInt selectedDetail = new ImInt();
    private ImInt selectedMom = new ImInt();
    private ImInt selectedDad = new ImInt();
    private String messageBreed = "";
    private ImString breedName = new ImString();
    private int[] buangCount = new int[1];

    // Replace Engimon Skill
    private boolean showLearn = false;
    private ImInt selectedEngimon = new ImInt();
    private ImInt selectedSkillItem = new ImInt();
    private boolean showReplace = false;
    private ImInt selectedReplaced = new ImInt();
    private String messageLearn = "";

    // Battle result message container
    private ArrayList<String> battleResultMessages = new ArrayList<>();

    // atribut buat testing battle
    private boolean testBattle = false;

    private boolean showSave = false;
    private boolean showNotification = false;
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
    public Peta getMap()
    {
        return game.getPeta();
    }
    public int getActiveEngimonLevel() { return game.getActiveLevel(); }
    public int getActiveEngimonSpeciesID() { return game.getActiveID(); }

    public void insertGame(Game g) {
        this.game = g;
        this.player = g.getPlayer();
    }

    public void ui() {
        if (!running) {
            ImGui.begin("Start");
            showNewGame = true;
            showLoadGame = true;
        }
        else {
            ImGui.begin("Engimon Factory");
            ImGui.text("Player Commands:");
            this.isInventoryFull = this.player.isInventoryFull();
            this.isInventorySkillEmpty = (this.player.getInvS().getSize() == 0);

            time+=1;
            if (time % 45000 == 0) {
                this.game.Update();
            }

            if (ImGui.button("Show Inventory")) {
                showInv = true;
            }

            if (showInv) {
                ImGui.text("Showing inventory");
            }

            if (ImGui.button("Release Engimon")) {
                showRelease = true;
            }

            if (showRelease) {
                ImGui.text("Showing release engimon");
            }

            if (ImGui.button("Buang Item")) {
                showBuang = true;
            }
            if (showBuang && isInventorySkillEmpty) {
                ImGui.text("\tInventory Skill Item Empty");
            }

            if (showBuang && !isInventorySkillEmpty) {
                ImGui.text("Showing buang item");
            }

            if (ImGui.button("Use SkillItem")) {
                showReplace = false;
                showLearn = true;
            }

            if (showLearn && isInventorySkillEmpty) {
                ImGui.text("\tInventory Skill Item Empty, cannot learn skill");
            }

            if (showLearn && !isInventorySkillEmpty) {
                ImGui.text("Showing Learn Menu");
            }

            if (ImGui.button("Interact")) {
                showInteract = true;
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

            if (ImGui.button("Detail Engimon")) {
                showDetail = true;
                System.out.println("Detail clicked");
            }

            if (showDetail) {
                ImGui.text("Showing Engimon detail");
            }

            if (ImGui.button("Breed")) {
                showBreed = true;
                this.messageBreed = "";
                System.out.println("Breed clicked");
            }

            if (showBreed && isInventoryFull) {
                ImGui.text("\tInventory Full");
            }

            if (showBreed && !isInventoryFull) {
                if (this.player.isInventoryFull()) {
                    ImGui.text("Inventory full, cannot breed");
                    this.showBreed = false;
                } else {
                    ImGui.text("Showing breed menu");
                }
            }

            if (ImGui.button("Show Switch Engimon")) {
                showSwitch = true;
            }

            if (showSwitch) {
                ImGui.text("Switch Active engimon");
            }

            if (ImGui.button("Move Left"))
                this.game.movePlayer('a');

            if (ImGui.button("Move Forward"))
                this.game.movePlayer('w');

            if (ImGui.button("Move Right"))
                this.game.movePlayer('d');
            
            if (ImGui.button("Move Backwards"))
                this.game.movePlayer('s');

//            System.out.println(getMap().getPlayerPosition().getX() + " is X, " + getMap().getPlayerPosition().getY() + " is Y");

            if (ImGui.button("Battle")) {
                showMenuBattle1 = true;
            }

            if (showMenuBattle1 || showMenuBattle2) {
                ImGui.text("Showing battle window");
                if (ImGui.button("Close battle window")){
                    showMenuBattle1 = false;
                    showMenuBattle2 = false;
                }
            }

            if (ImGui.button("Save")) {
                showSave = true;
            }

            ImGui.text("CHEATS:");
            // @TODO hapus ini
            if (ImGui.button("Add Random Engimon")) {
                this.game.addRandomEngimonPlayer();
//                System.out.println("Added Engimon");
            }
            if (ImGui.button("Add Random Skill Item")) {
                this.game.addRandomSkillItem();
//                System.out.println("Added SkillItem");
            }
            if (ImGui.button("Update")) {
                this.game.Update();
            }
//            if (ImGui.button("Battle random engimons")) {
//                showMenuBattle2 = true;
//                Cell c = new Cell(CellType.SEA, new Position(1, 1));
//                c.setEnemy(Engidex.spawnRandomEngimon());
//                this.battleResultMessages = game.battle(Engidex.spawnRandomEngimon(), c);
//                menuBattleResults();
//            }
        }

        ImGui.end();

        if (showNewGame && showLoadGame) {
            menuStart();
        }
        if (showInv)
            menuInventory();
        if (showRelease)
            menuRelease();
        if (showBuang && !isInventorySkillEmpty)
            menuBuang();
        if (showLearn && !isInventorySkillEmpty)
            menuLearn();
        if (showSwitch)
            menuSwitchActive();
        if (showBreed && !isInventoryFull)
            menuBreed();
        if (showDetail)
            menuDetail();
        if (showMenuBattle1) {
            menuBattlePrep();
        }
        if (showMenuBattle2) {
            menuBattleResults();
        }
        if (showSave) {
            game.setEngCount(Engimon.getCount());
            showNotification = game.save();
            menuSave(showNotification);
        }
    }

    public void menuStart()
    {
        ImGui.begin("Start");
        if (showNewGame) {
            if (ImGui.button("NEW GAME")) {
                System.out.println("ini dari new game");
                showInputName = true;
            }
            if (showInputName) {
                inputName();
            }
        }

        if (showLoadGame) {
            if (ImGui.button("LOAD GAME")) {
                System.out.println("ini dari load game");
                Game g = Game.load();
                g.init();
                Engimon.setCount(g.getEngCount() + 1);
                insertGame(g); // FIXME; engimon yang udah ada levelnya kedobel kalo addRandom
                showLoadGame = false;
                showNewGame = false;
                running = true;
            }
        }


//        ImGui.imageButton(Texture.getTexture("GRASS").getId(), 256.0f, 256.0f);
        ImGui.end();
    }

    public void notification(String title, String message) {
        ImGui.begin(title);
        ImGui.text(message);
        if(ImGui.button("Ok")){
            if (showSave) showSave = false;
        }
        ImGui.end();
    }

    public void inputName() {
        ImGui.begin("Input Name");
        ImGui.text("What's your name?");
        ImGui.inputText("", name, ImGuiInputTextFlags.CallbackResize);
        ImGui.text("Choose inventory maximum capacity");
        ImGui.sliderInt(" ", maxCapInv, 5, 30);
        ImGui.text("Choose wild engimon maximum capacity");
        ImGui.sliderInt("  ", maxCapEng, 5, 30);
        if (ImGui.button("OK")) {
            System.out.print("Masukan namanya: ");
            System.out.println(name);
            System.out.print("Max cap inv: ");
            System.out.println(maxCapInv[0]);
            System.out.print("Max cap eng: ");
            System.out.println(maxCapEng[0]);
            Game g = new Game(name.toString(), maxCapInv[0], maxCapEng[0]);
            g.init();
            insertGame(g);
            game.addRandomEngimonPlayer();
            game.addRandomEngimonPlayer();
            game.addRandomEngimonPlayer();

            showInputName = false;
            showLoadGame = false;
            showNewGame = false;
            running = true;
        }
        ImGui.end();
    }

    // @TODO inventory menu not done
    public void menuInventory() {
        ImGui.begin("Menu Inventory");
        String message = "This is your inventory, " + this.player.getName() + "\n"
                + "Engimon:\n" + this.player.getPrintInventoryE()
                + "SkillItem:\n";
        ImGui.text(message);
        for (int i = 0; i < this.player.getInvS().getSize(); i++) {
            try {
                Skill s = this.player.getInvS().getItemByIdx(i);
                Texture tex = new Texture(resolveSkillImage(s, false));
                ImGui.imageButton(tex.getId(), 50.0f, 50.0f);
                ImGui.sameLine();
                ImGui.text(s.getPrint());
                ImGui.sameLine();
                ImGui.text("Count: "+this.player.getInvS().getCountByIdx(i));
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        ImGui.text("Item in inventory: " + this.player.getInvCount() + "/" + this.player.getMaxCap());
        ImGui.newLine();
        if (ImGui.button("Close inventory")) showInv = false;
        ImGui.end();
    }

    public void menuRelease() {
        ImGui.begin("Menu Release Engimon");
        ImGui.text("Pilih engimon untuk di release");
        String[] comboRelease = new String[this.player.getInvE().getSize()];
        for (int i = 0; i < this.player.getInvE().getSize(); i++) {
            comboRelease[i] = this.player.getInvE().getItemByIdx(i).getPrint();
        }
        ImGui.combo("Release", selectedRelease, comboRelease);
        if (ImGui.button("Release Engimon"))
            this.player.releaseEngimon(this.selectedRelease.get());
        ImGui.newLine();
        if (ImGui.button("Close release menu")) showRelease = false;
        ImGui.end();
    }

    public void menuBuang() {
        // @TODO CHECK MENU BUANG FORMATNYA GIMANA
        ImGui.begin("Menu Buang Item");
        ImGui.text("Pilih item untuk dibuang");
        String[] comboBuang = new String[this.player.getInvS().getSize()];
        for (int i = 0; i < this.player.getInvS().getSize(); i++) {
            String message = "";
            message += this.player.getInvS().getItemByIdx(i).getPrint();
            message += "\tCount: " + this.player.getInvS().getCountByIdx(i);
            comboBuang[i] = message;
        }
        ImGui.combo("Buang", selectedBuang, comboBuang);
        int count = this.player.getInvS().getCountByIdx(selectedBuang.get());
        if (buangCount[0] > count)
            buangCount[0] = 1;
        ImGui.sliderInt(" ", buangCount, 1, count);
        Skill s = this.player.getInvS().getItemByIdx(selectedBuang.get());
        Texture tex;
        try {
            tex = new Texture(resolveSkillImage(s, false));
            ImGui.imageButton(tex.getId(), 50.0f, 50.0f);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (ImGui.button("Buang Item"))
            this.player.buangItemSkill(selectedBuang.get(), buangCount[0]);
        ImGui.newLine();
        if (ImGui.button("Close buang item menu")) showBuang = false;
        ImGui.end();
    }

    public void menuLearn() {
        ImGui.begin("Menu Learn SkillItem");
        Integer sizeE = this.player.getInvE().getSize();
        Integer sizeS = this.player.getInvS().getSize();
        String[] comboLearnE = new String[sizeE];
        String[] comboLearnS = new String[sizeS];
        for (int i = 0; i < sizeE; i++) {
            comboLearnE[i] = this.player.getInvE().getItemByIdx(i).getPrint();
        }
        for (int i = 0; i < sizeS; i++) {
            String message = this.player.getInvS().getItemByIdx(i).getPrint();
            message += " Count: " + this.player.getInvS().getCountByIdx(i);
            comboLearnS[i] = message;
        }
        ImGui.combo("EngimonLearn", selectedEngimon, comboLearnE);
        ImGui.combo("SkillItem", selectedSkillItem, comboLearnS);
        String hasil;
        if (ImGui.button("Learn")) {
            hasil = this.player.learnSkill(selectedEngimon.get(), selectedSkillItem.get());
            if (hasil == null) {
                showReplace = false;
                showLearn = false;
            }
            else {
                if (hasil.equals("Skill already learned")) messageLearn = hasil;
                else if (hasil.equals("Skill not compatible")) messageLearn = hasil;
                else if (hasil.equals("Skill slots full")) messageLearn = hasil;
                else if (hasil.equals("berhasil")) messageLearn = "Engimon berhasil learn skill";
                else {
                    showReplace = false;
                    showLearn = false;
                }
            }
        }
        ImGui.text(messageLearn);
        if (messageLearn.equals("Skill slots full")
                && player.getInvE().getItemByIdx(selectedEngimon.get()).getSkills().size() == 4
                && player.getInvS().getItemByIdx(selectedSkillItem.get())
                .isCompatibleWith(player.getInvE().getItemByIdx(selectedEngimon.get()).getElements())) {
            ImGui.text("Do you want to replace the skill of the Engimon?");
            if (ImGui.button("Yes")) {
                showReplace = true;
            }
        }


        ImGui.newLine();
        ImGui.newLine();
        if (ImGui.button("Close Learn Skill menu")) {
            showLearn = false;
            showReplace = false;
        }

        ImGui.end();
        if (showReplace)
            menuReplace(
                    selectedEngimon.get(),
                    selectedSkillItem.get());
    }

    public void menuReplace(int idxE, int idxS) {
        ImGui.begin("Menu Replace Skill");
        String[] comboSkills = new String[4];
        Engimon e = this.player.getInvE().getItemByIdx(idxE);
        int i = 0;
        for (Skill skill: e.getSkills()) {
            String message = skill.getPrint();
            message += "MLevel: " + skill.getMasteryLevel();
            comboSkills[i] = message;
            i++;
        }
        ImGui.combo("Replace Skill", selectedReplaced, comboSkills);
        if (ImGui.button("Replace")) {
            this.player.replaceSkill(idxE, idxS, selectedReplaced.get());
            showLearn = false;
            showReplace = false;
            messageLearn = "";
        }

        if (ImGui.button("aDD RANDOM")) {
            this.addRandom();
        }
        ImGui.end();
    }

    // @DONE
    public void menuSwitchActive() {
        ImGui.begin("Switch Active Engimon");
        String[] comboitems = new String[this.player.getInvE().getSize()];
        for (int i = 0; i < this.player.getInvE().getSize(); i++) {
            comboitems[i] = this.player.getInvE().getItemByIdx(i).getPrint();
        }
        ImGui.combo("Switch", selectedActive, comboitems);
        ImGui.newLine();
        ImGui.newLine();
        ImGui.newLine();
        if (ImGui.button("Close switch")) showSwitch = false;
        ImGui.end();
        this.player.switchActive(this.selectedActive.getData()[0]);
    }

    public void addRandom() {
        Engimon e = this.game.getEngidex().spawnRandomEngimon();
        Position p = this.game.getPeta().spawnEngimonPosition(e);
        this.game.getPeta().addEnemy(new Pair <Engimon,Position>(e,p));

    }
    public void menuDetail() {
        ImGui.begin("Detail Engimon");
        Integer size = this.player.getInvE().getSize();
        String[] comboEngimon = new String[size];
        for (int i = 0; i < size; i++) {
            comboEngimon[i] = this.player.getInvE().getItemByIdx(i).getPrint();
        }
        ImGui.combo("Detail", selectedDetail, comboEngimon);
        int selected = selectedDetail.get();
        Engimon e = this.player.getInvE().getItemByIdx(selected);
        String message1 = e.getPrint() + "\nParents: ";
        if (e.getParentNames() == null) message1 += "None";
        else message1 += (e.getParentNames()[0] + ", " + e.getParentNames()[1]);
        message1 += "\nSkills:\n";
        ImGui.text(message1);
        for (Skill s: e.getSkills()) {
            try {
                Texture tex = new Texture(resolveSkillImage(s, true));
                ImGui.imageButton(tex.getId(), 50.0f, 50.0f);
                ImGui.sameLine();
                ImGui.text(s.getPrint() + " MLevel: " + s.getMasteryLevel());
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        String message2 = ("Lives\t: " + e.getLives() + "\n");
        message2 += ("LVL\t: " + e.getLevel() + "\n");
        message2 += ("CExp\t: " + e.getCumulativeExp() + "\n");
        message2 += ("Exp\t: " + e.getExp() + "\n");
        ImGui.text(message2);

        ImGui.newLine();
        ImGui.newLine();
        if (ImGui.button("Close Detail")) showDetail = false;

        ImGui.end();
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
        ImGui.text("Name the kid!");
        ImGui.inputText("", breedName, ImGuiInputTextFlags.CallbackResize);
        if (ImGui.button("Breeeed"))
            this.messageBreed = this.player.breed(selectedMom.getData()[0], selectedDad.getData()[0], breedName.toString());
        ImGui.text(this.messageBreed);
        ImGui.newLine();
        ImGui.newLine();
        if (ImGui.button("Close breed menu")) showBreed = false;
        ImGui.end();
        // String message = this.player.breed()
    }

    public void menuBattlePrep(){
        ImGui.begin("Battle");
        try{
            // get battle engimons
            Pair<Engimon, Cell> p = game.getBattleEngimon();
            Engimon active_engimon = p.getItem1();
            Engimon wild_engimon = p.getItem2().getEnemy();

            // show battle status
            // isi battle_status: [detail wild engimon, power active engimon, power wild engimon]
            ArrayList<String> battle_status = BattleUtil.getBattleStatus(active_engimon, wild_engimon);
            // ImGui.text("Enemy engimon: " + battle_status.get(0));
            ImGui.text("Enemy engimon:");
            ImGui.text("Skills:");
            for (Skill s: wild_engimon.getSkills()) {
                try {
                    Texture tex = new Texture(resolveSkillImage(s, true));
                    ImGui.imageButton(tex.getId(), 50.0f, 50.0f);
                    ImGui.sameLine();
                    ImGui.text(s.getPrint() + " MLevel: " + s.getMasteryLevel());
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
            String message2 = "";
            message2 += ("LVL\t: " + wild_engimon.getLevel() + "\n");
            ImGui.text(message2);
            ImGui.text("Active engimon power: " + battle_status.get(1));
            ImGui.text("Enemy engimon power: " + battle_status.get(2));

            // kasih pilihan ke pemain
            ImGui.text("Commence battle?");
            ImGui.sameLine();
            if (ImGui.button("Yes")){
                this.battleResultMessages = game.battle(p.getItem1(), p.getItem2());
                showMenuBattle1 = false;
                showMenuBattle2 = true;
            }
            ImGui.sameLine();
            if (ImGui.button("No")) {
                showMenuBattle1 = false;
            }
        }
        catch (Exception e){
            ImGui.text(e.getMessage());
        }
        ImGui.end();
    }

    public void menuBattleResults(){
        ImGui.begin("Battle results");
        for (String s : this.battleResultMessages){
            ImGui.text(s);
        }
        ImGui.end();
    }

    public void menuSave(boolean saved) {
        if (saved) notification("Notification","Game is Saved");
        else notification("Notification","Failed to save game");
    }

    public int resolveSkillImage(Skill s, boolean mlevel) {
        String toret = "";
        if (s.getName().equals("Shock")) toret = "12";
        else if (s.getName().equals("Awe")) toret = "4";
        else if (s.getName().equals("Fireball")) toret = "3";
        else if (s.getName().equals("Rosenthal")) toret = "5";
        else if (s.getName().equals("Ground Pound")) toret = "15";
        else if (s.getName().equals("Yubi")) toret = "6";
        else if (s.getName().equals("Icicle")) toret = "13";
        else if (s.getName().equals("Devil's Snare")) toret = "1";
        else if (s.getName().equals("Splash")) toret = "9";
        else if (s.getName().equals("Wave")) toret = "14";
        else if (s.getName().equals("Magnetize")) toret = "8";
        else if (s.getName().equals("Gravitate")) toret = "16";
        else if (s.getName().equals("Shuba")) toret = "10";
        else if (s.getName().equals("Onionize")) toret = "11";
        else if (s.getName().equals("Angle Supreme Freeze")) toret = "7";
        else if (s.getName().equals("Shien Freeze")) toret = "2";
        if (mlevel)
            toret += ("_" + s.getMasteryLevel());
        return Texture.getTexture(toret).getId();
    }
}
