package com.boi.engimonfactory;

import java.util.ArrayList;
import java.util.HashMap;

public class Engidex {

    private static HashMap<Integer, Species> codex = new HashMap<Integer, Species>();

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
}
