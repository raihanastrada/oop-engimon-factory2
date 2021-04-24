package com.boi.engimonfactory;

import java.util.ArrayList;

public class PlayerEngimon extends Engimon {

    protected int id, exp, level;
    protected String name = "";
    protected String[] parentNames;
    protected int lives;
    protected boolean alive;
    protected ArrayList<ContohSkill> skills = new ArrayList<ContohSkill>();

    public PlayerEngimon(Engidex.Species spec) {
        super(spec);
        this.lives = 3;
    }

    public PlayerEngimon(Engimon wildEngimon)
    {
        super(wildEngimon.getSpecies());
        this.exp = wildEngimon.getCumulativeExp();
        this.level = wildEngimon.getLevel();

        this.lives = 3;
    }

    public static PlayerEngimon breed (Engimon mom, Engimon dad)
    {
        /*  Bikin id codex engimon anaknya dari id spesies mom sama dad
        *   Cara dapetin elemen pertama ama elemen kedua dari id spesies ada di Engidex line 62
        * */

        // Abis itu urusin masalah skill
        
        // Return playerengimon baruny
        return new PlayerEngimon(new Engidex.Species()); // placeholder
    }

    public String interact()
    {
        String msg = this.getSpecies().getSpeciesName().substring(0, 4) + this.getSpecies().getSpeciesName().substring(0, 4) + "!";
        System.out.println(msg);

        return msg;
    }

    public void setName(String n) { this.name = n; }
}
