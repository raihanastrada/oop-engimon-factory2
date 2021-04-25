package com.boi.engimonfactory;

import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.lang.ClassNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game implements Serializable {
    Peta peta;
    Player player;
    Engidex engidex;

    public Game() {
        this.peta = new Peta(new Position(5,8),new Position(5,9),"Peta.txt", 15);
        this.player = new Player();
        this.engidex = new Engidex();
        // ini belom selese
    }

    public void init() {
        /*
            ELECTRIC(1, "Electric"),
            FIRE(2, "Fire"),
            GROUND(3, "Ground"),
            ICE(4, "Ice"),
            WATER(5, "Water");
         */
        // Compatible: Electric
        Element electric = new Element(1);
        Element fire = new Element(2);
        Element ground = new Element(3);
        Element ice = new Element(4);
        Element water = new Element(5);
        ArrayList<Element> comp1 = new ArrayList<Element>(); // Electric & Fire ***
            comp1.add(electric);
            comp1.add(fire);
        ArrayList<Element> comp2 = new ArrayList<Element>(); // Ice & Water ***
            comp2.add(ice);
            comp2.add(water);
        ArrayList<Element> comp3 = new ArrayList<Element>(); // Ground & Water ***
            comp3.add(ground);
            comp3.add(water);
        ArrayList<Element> comp4 = new ArrayList<Element>(); // Ground & Fire ***
            comp4.add(ground);
            comp4.add(fire);
        ArrayList<Element> comp5 = new ArrayList<Element>(); // Electric & Ice ***
            comp5.add(electric);
            comp5.add(ice);
        ArrayList<Element> comp6 = new ArrayList<Element>(); // Ice ***
            comp6.add(ice);
        ArrayList<Element> comp7 = new ArrayList<Element>(); // Electric ***
            comp7.add(electric);
        ArrayList<Element> comp8 = new ArrayList<Element>(); // Ground ***
            comp8.add(ground);
        ArrayList<Element> comp9 = new ArrayList<Element>(); // Fire ***
            comp9.add(fire);
        ArrayList<Element> comp10 = new ArrayList<Element>(); // Water ***
            comp10.add(water);
        ArrayList<Element> comp11 = new ArrayList<Element>(); // Water, Ice, & Ground ***
            comp11.add(water);
            comp11.add(ice);
            comp11.add(ground);
        ArrayList<Element> comp12 = new ArrayList<Element>(); // Fire, Electric, & Ground
            comp12.add(fire);
            comp12.add(electric);
            comp12.add(ground);

        Random gen = new Random();
        // inisialisasi engidex
        Engidex.addSpecies(1001, "Pikamee", new Skill("Shock",comp7 ,gen.nextInt(100)+1));
        Engidex.addSpecies(1002, "Tokino Sora", new Skill("Awe",comp1 ,gen.nextInt(100)+1));

        Engidex.addSpecies(2001, "Takanasahi Kiara", new Skill("Fireball",comp9 ,gen.nextInt(100)+1));
        Engidex.addSpecies(2002, "Aki Rosenthal", new Skill("Rosenthal",comp4 ,gen.nextInt(100)+1));

        Engidex.addSpecies(3001, "Amelia Watson", new Skill("Ground Pound",comp8 ,gen.nextInt(100)+1));
        Engidex.addSpecies(3002, "Yubi, Inugami Korone", new Skill("Yubi",comp3 ,gen.nextInt(100)+1));

        Engidex.addSpecies(4001, "Amane Kanata", new Skill("Icicle",comp6 ,gen.nextInt(100)+1));
        Engidex.addSpecies(4002, "Tokoyami Towa", new Skill("Devil's Snare",comp5 ,gen.nextInt(100)+1));

        Engidex.addSpecies(5001, "Gawr Gura", new Skill("Splash",comp10 ,gen.nextInt(100)+1));
        Engidex.addSpecies(5002, "Spade Echo", new Skill("Wave",comp11 ,gen.nextInt(100)+1));

        Engidex.addSpecies(1201, "Mori Calliope", new Skill("Magnetize",comp1,gen.nextInt(100)+1));
        Engidex.addSpecies(1202, "Moona Hoshinova", new Skill("Gravitate",comp12,gen.nextInt(100)+1));

        Engidex.addSpecies(5301, "Suba, Oozora Subaru", new Skill("Shuba",comp10,gen.nextInt(100)+1));
        Engidex.addSpecies(5302, "Minato Aqua", new Skill("Onionize",comp11,gen.nextInt(100)+1));

        Engidex.addSpecies(5401, "Amatsuka Uto", new Skill("Angle Supreme Freeze",comp6,gen.nextInt(100)+1));
        Engidex.addSpecies(5402, "Kageyama Shien", new Skill("Shien Freeze",comp2,gen.nextInt(100)+1));
    }

    public Peta getPeta() {
        return this.peta;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Engidex getEngidex() {
        return this.engidex;
    }

    public void movePlayer(char d) {
        try {
            this.peta.movePlayer(d);
            Peta.increaseTurn();
            if (Peta.getTurn() % 5 == 0) {
                Engimon wild = Engidex.spawnRandomEngimon();
                Position wildPosition = peta.spawnRandomPosition(wild);
                peta.addEnemy(new Pair<>(wild, wildPosition));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Debugging Methods

    public void addRandomEngimonPlayer() {
        var e = Engidex.spawnRandomEngimon();
        this.player.insertItem(PlayerEngimon.tame(e)); // Inserts Engimon
    }

    public Inventory<Engimon> getPInvEngimon() { return this.player.getInvE(); }

    public Inventory<Skill> getPInvSkill() { return this.player.getInvS(); }

    // Debugging Methods ends here

    public Boolean save() {
        try {
            this.writeSave();
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static Game load() {
        try {
            Game g = readSave();
            return g;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    // Write save
    private void writeSave() throws IOException {
        ObjectOutputStream out = 
            new ObjectOutputStream(new FileOutputStream("Save.bin"));
        out.writeObject(this);
        out.close();
    }

    // Read save
    private static Game readSave() throws IOException, ClassNotFoundException {
        ObjectInputStream inp = 
            new ObjectInputStream(new FileInputStream("Save.bin"));
        Game game = (Game) inp.readObject();
        inp.close();
        return game;
    }

    public void run() {
        Scanner scan = new Scanner(System.in);
        boolean running = true;
        while (running) {
            String command = scan.nextLine();
            if (command == "w" || command == "a" || command == "s" || command == "d") {
                try {
                    peta.movePlayer(command.charAt(0));
                    peta.increaseTurn();
                    if (Peta.getTurn() % 5 == 0) {
                        Engimon wild = engidex.spawnRandomEngimon();
                        Position wildPosition = peta.spawnRandomPosition(wild);
                        peta.addEnemy(new Pair<>(wild, wildPosition));
                    }
                } catch (Exception e) {
                    // SEHARUSNYA KOSONG
                }
            }
            else if (command.equals("inventory")) {
                // TODO
                // player.
            }
            else if (command.equals("switch")) {
                // TODO
            }
            else if (command.equals("interact")) {
                // TODO
            }
            else if (command.equals("use")) {
                // TODO
            }
            else if (command.equals("breed")) {
                // TODO
            }
            else if (command.equals("detail")) {
                // TODO
            }
            else if (command.equals("help")) {
                // TODO
            }
            else if (command.equals("battle")) {
                // TODO
            }
            else if (command.equals("save")) {
                if (!save()) {
                    System.out.println("Failed to save");
                }
            }
            else if (command == "exit") {
                // TODO
                // Konfirmasi Save / Not
                running = false;
            }
            else {
                System.out.println("Command not found");
            }
        }
        System.out.println("INI PESAN EXIT");
        scan.close();
    }

}
