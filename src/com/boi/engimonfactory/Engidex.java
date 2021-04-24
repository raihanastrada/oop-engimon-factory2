package com.boi.engimonfactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Engidex {

    private static HashMap<Integer, Species> codex = new HashMap<Integer, Species>();
    private static final Integer engimonCountPerElement = 2;

    public static class Species {
        /*
        * SpeciesID format:
        *       XYZZ
        *       X: first element code (1-5)
        *       Y: second element code (0-5)
        *       Z: unique index
        * */
        private int speciesID;
        private String speciesName;

        private ContohSkill speciesSpecialSkill;
        private ArrayList<Element> speciesElements = new ArrayList<Element>();

        public Species()
        {
            this.speciesName = "";
            this.speciesID = 0;
        }

        public Species(int id, String name, ContohSkill specialSkill)
        {
            this.speciesName = name;
            this.speciesID = id;
            this.speciesSpecialSkill = specialSkill;

            this.speciesElements.add(Element.getElement(id/1000));
            if ((id/100) % 10 != 0)
            {
                this.speciesElements.add(Element.getElement((id/100) % 10));
            }
        }

        public int getSpeciesID() { return this.speciesID; }
        public String getSpeciesName() { return this.speciesName; }
        public ContohSkill getSpeciesSpecialSkill() { return speciesSpecialSkill; }
        public ArrayList<Element> getSpeciesElements() { return this.speciesElements; }
    }

    public static void addSpecies(int id, String name, ContohSkill specialSkill)
    {
        Species newSpecies = new Species(id, name, specialSkill);
        codex.put(id, newSpecies);
    }

    public static Engimon spawnEngimon(int engidexCode)
    {
        return new Engimon(codex.get(engidexCode));
    }

    public static Engimon spawnRandomEngimon()
    {
        Random gen = new Random();
        int firstElement = gen.nextInt(5) + 1;
        int secondElement = gen.nextInt(6);
        int index = gen.nextInt(engimonCountPerElement) + 1;

        int fullcode = firstElement * 1000 + secondElement * 100 + index;
        return spawnEngimon(fullcode);
    }

}
