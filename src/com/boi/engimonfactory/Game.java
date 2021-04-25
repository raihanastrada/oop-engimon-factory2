package com.boi.engimonfactory;

import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.lang.ClassNotFoundException;
import java.io.IOException;

public class Game implements Serializable {
    Peta peta;
    Player player;
    Engidex engidex;

    public Game() {
        new Peta(new Position(5,8),new Position(5,9),"Peta.txt",maxEngimonCount);
        // ini belom selese
    }

    public void init() {
        // inisialisasi engidex
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
    public void writeSave() throws IOException {
        ObjectOutputStream out = 
            new ObjectOutputStream(new FileOutputStream("Save.bin"));
        out.writeObject(this);
        out.close();
    }

    // Read save
    public static Game readSave() throws IOException, ClassNotFoundException {
        ObjectInputStream inp = 
            new ObjectInputStream(new FileInputStream("Save.bin"));
        Game game = (Game) inp.readObject();
        inp.close();
        return game;
    }
}
