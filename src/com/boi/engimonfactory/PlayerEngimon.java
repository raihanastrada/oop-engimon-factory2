package com.boi.engimonfactory;

public class PlayerEngimon extends Engimon {

    // protected int id, exp, level;
    // protected String name = "";
    // protected String[] parentNames;
    // protected int lives;
    // protected boolean alive;
    // protected ArrayList<ContohSkill> skills = new ArrayList<ContohSkill>();

    public PlayerEngimon(Engidex.Species spec) {
        super(spec);
        this.lives = 3;
        this.name = this.getSpeciesName();
    }

    public PlayerEngimon(Engimon wildEngimon)
    {
        super(wildEngimon.getSpecies());
        this.exp = wildEngimon.getCumulativeExp();
        this.level = wildEngimon.getLevel();

        this.lives = 3;
    }

    // Buat ngetame wildEngimon, makenya PlayerEngimon.tame(wildEngimon)
    public static PlayerEngimon tame(Engimon wildEngimon)
    {
        return new PlayerEngimon(wildEngimon);
    }

    public static PlayerEngimon breed(Engimon mom, Engimon dad) throws OneEngimonBreedException, InsufficientLevelException
    {
        if (mom.equals(dad)) {
            String msg = "Tolong Jangan Membelah Diri ya Bro";
            throw new OneEngimonBreedException(msg);
        }
        else if (mom.getLevel()<4 || dad.getLevel()<4) {
            String msg = "Lemah kok mau bikin Anak Bro";
            throw new InsufficientLevelException(msg);
        }
        /*  Bikin id codex engimon anaknya dari id spesies mom sama dad
        *   Cara dapetin elemen pertama ama elemen kedua dari id spesies ada di Engidex line 62
        * */
        else {

        }
        // Abis itu urusin masalah skill

        // Return playerengimon barunya
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
