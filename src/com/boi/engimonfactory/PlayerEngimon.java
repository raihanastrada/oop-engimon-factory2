package com.boi.engimonfactory;

// import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Random;

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

    public PlayerEngimon(Engimon breedEngimon, String newName, String momName, String dadName) {
        super(breedEngimon.getSpecies());
        name = newName;
        parentNames[0] = momName;
        parentNames[1] = dadName;
        lives = 3;
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
        else {
            System.out.println("Tolong anaknya dikasih nama yaa");
            String newName = "namaAnakHere"; // nama anak
            String momName = mom.getName(); // nama ibu
            String dadName = dad.getName(); // nama bapak
            Engimon anak = new Engimon(); // placeholder engimon anak
            anak = PlayerEngimon.determineEngimon(mom, dad);
            PlayerEngimon toReturn = new PlayerEngimon(anak, newName, momName, dadName);

        }
        // Abis itu urusin masalah skill

        // Return playerengimon barunya
        return new PlayerEngimon(new Engidex.Species()); // placeholder
    }

    public static Engimon determineEngimon(Engimon mom, Engimon dad) {
        Random index = new Random();
        ArrayList<Element> listElemenIbu = mom.getElements();
        ArrayList<Element> listElemenBapak = dad.getElements();
        // case 1, elemen sama
        if (listElemenBapak.equals(listElemenIbu)) {
            return new Engimon(mom.getSpecies());
        }

        // sisa case, elemen beda
        Element elemenIbu = listElemenIbu.get(0);
        Element elemenBapak = listElemenBapak.get(0);
        // handle case dual element
        if (listElemenBapak.size() == 2) {
            elemenBapak = listElemenBapak.get(index.nextInt(2));
        }
        if (listElemenIbu.size() == 2) {
            elemenIbu = listElemenIbu.get(index.nextInt(2));
        }
        double advantageIbu = Element.getAdvantage(elemenIbu, elemenBapak);
        double advantageBapak = Element.getAdvantage(elemenBapak, elemenIbu);
        if (advantageIbu > advantageBapak) {
            return new Engimon(mom.getSpecies());
        }
        else if (advantageIbu < advantageBapak) {
            return new Engimon(dad.getSpecies());
        }
        else { // advantage sama, anak jadi dual elemen
            int codeIbu = elemenIbu.type().getCode();
            int codeBapak = elemenBapak.type().getCode();
            Engimon toReturn = Engidex.generateEngimon(codeIbu,codeBapak);
            return new Engimon(toReturn.getSpecies());
        }
    }

    public String interact()
    {
        String msg = this.getSpecies().getSpeciesName().substring(0, 4) + this.getSpecies().getSpeciesName().substring(0, 4) + "!";
        System.out.println(msg);

        return msg;
    }

    public void setName(String n) { this.name = n; }
}
