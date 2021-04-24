package com.boi.engimonfactory;

import java.util.ArrayList;

public class Skill implements Storeable {
    // nama skill
    private String name;
    // base power skill
    private Integer base_power;
    // mastery level
    private Integer mastery_level;
    // elemen-elemen yang kompatibel dengan skill ini
    private ArrayList<Element> compatible_elements; 

    // constructor
    public Skill(String name, ArrayList<Element> compatible_elements, Integer base_power) {
        this.name = name;
        this.compatible_elements = compatible_elements; // ragu bisa assign apa nggak
        this.base_power = base_power;
    }

    // getter
    public int getBasePower(){
        return this.base_power;
    }
    public int getMasteryLevel(){
        return this.mastery_level;
    }
    public String getName(){
        return this.name;
    }
    public ArrayList<Element> getCompatibleElements(){
        return this.compatible_elements;
    }

    // setter
    // gak yakin int atau Integer
    void setbasePower(int base_power){
        this.base_power = base_power;
    }
    void setMasteryLevel(int mastery_level){
        this.mastery_level = mastery_level;
    }

    /* Tolong yang di bawah ini ada semua */
    /* Implementasi interface Storeable */
    /* Ini di copy-paste, disesuaikan */
    /* Tolong override ini semua */

    @Override
    public String toString() {
        return ("Skill " + this.name + "Element: "
            + this.element + " BP: " + this.base_power);
    }

    @Override
    public void print() {
        /* Kurang tau format printnya gimana jadi mungkin beginin aja udah cukup */
        System.out.println("Skill " + this.name + " Element: " 
            + this.element + " BP: " + this.base_power);
    }

    /* Tolong override ini berdasarkan nama skill */
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Skill)) return false;
        Skill other = (Skill) o;
        return this.name.equals(other.name);
    }

    // mengembalikan true jika irisan elements dan compatible_elements tidak kosong
    public boolean isCompatibleWith(ArrayList<Element> elements)
    {
        for (Element e1 : this.compatible_elements){
            for (Element e2 : elements){
                // warning: elements belum override equals
                if (e1.equals(e2)) return true;
            }
        }
        return false;
    }

    @Override
    public Integer getSortInt() {
        return this.base_power * -1; // Ini kopas aja sama persis
    }

    @Override
    public String getSortStr() {
        return ""; // Ini kopas aja sama persis
    }
}
