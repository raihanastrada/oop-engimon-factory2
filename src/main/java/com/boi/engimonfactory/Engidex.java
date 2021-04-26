package com.boi.engimonfactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Engidex implements Serializable {

    private static HashMap<Integer, Species> codex = new HashMap<Integer, Species>();
    public static final Integer engimonCountPerElement = 2;

    public static class Species implements Serializable {
        /*
        * SpeciesID format:
        *       XYZZ
        *       X: first element code (1-5)
        *       Y: second element code (0-5)
        *       Z: unique index
        * */
        private int speciesID;
        private String speciesName;

        private Skill speciesSpecialSkill;
        private ArrayList<Element> speciesElements = new ArrayList<Element>();

        public Species()
        {
            this.speciesName = "";
            this.speciesID = 0;
        }

        public Species(int id, String name, Skill specialSkill)
        {
            this.speciesName = name;
            this.speciesID = id;
            this.speciesSpecialSkill = specialSkill;

            this.speciesElements.add(Element.getElement((id/1000)));
            // System.out.println("Added element " + speciesElements.get(0).type().asString() + " to " + name + " as type 1");
            if ((id/100) % 10 != 0)
            {
                this.speciesElements.add(Element.getElement((id/100) % 10));
                // System.out.println("Added element " + speciesElements.get(1).type().asString() + " to " + name + " as type 2 " + (id/100) % 10);

            }
        }

        public int getSpeciesID() { return this.speciesID; }
        public String getSpeciesName() { return this.speciesName; }
        public Skill getSpeciesSpecialSkill() { return speciesSpecialSkill; }
        public ArrayList<Element> getSpeciesElements() { return this.speciesElements; }
    }

    public static void addSpecies(int id, String name, Skill specialSkill)
    {
        Species newSpecies = new Species(id, name, specialSkill);
        codex.put(id, newSpecies);
    }

    public static Engimon spawnEngimon(int engidexCode)
    {
        Engimon ret = new Engimon(codex.get(engidexCode)); // random level engimon (spawn Engimon)
        Random rand = new Random();
        ret.setLevel(rand.nextInt(100) + 1);
        return ret;
        // @TODO random level
    }

    public static Engimon spawnRandomEngimon()
    {
        Random gen = new Random();
        int firstElement = gen.nextInt(5) + 1;

        int secondElement = gen.nextInt(3);
        if (secondElement != 0)
        {
            if (firstElement == 1)
            {
                secondElement = 2;
            } else if (firstElement == 5)
            {
                secondElement = gen.nextInt(2) + 3;
            } else {
                secondElement = 0;
            }
        }

        int index = gen.nextInt(engimonCountPerElement) + 1;

        int fullcode = firstElement * 1000 + secondElement * 100 + index;
        return spawnEngimon(fullcode);
    }

    public static Engimon generateEngimon(int firstElement, int secondElement) {
        Random gen = new Random();
        int index = gen.nextInt(engimonCountPerElement) + 1;
        int fullcode = 0;
        if (firstElement == 5 || secondElement == 5) {
            if (firstElement > secondElement) {
                fullcode = firstElement * 1000 + secondElement * 100 + index;
            }
            else if (firstElement < secondElement) {
                fullcode = secondElement * 1000 + firstElement * 100 + index;
            }
            else { fullcode = firstElement * 1000 + index; }
        }
        else {
            if (firstElement < secondElement) {
                fullcode = firstElement * 1000 + secondElement * 100 + index;
            }
            else if (firstElement > secondElement) {
                fullcode = secondElement * 1000 + firstElement * 100 + index;
            }
            else { fullcode = firstElement * 1000 + index; }
        }
        return spawnEngimon(fullcode);
    }
}
