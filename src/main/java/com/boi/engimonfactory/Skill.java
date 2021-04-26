package com.boi.engimonfactory;

import java.io.Serializable;
import java.util.ArrayList;

public class Skill implements Storeable, Serializable {
    // nama skill
    private String name;
    // base power skill
    private int base_power;
    // mastery level
    private int mastery_level;
    // elemen-elemen yang kompatibel dengan skill ini
    private ArrayList<Element> compatible_elements;

    // constructor
    public Skill(String name, ArrayList<Element> compatible_elements, int base_power) {
        this.name = name;
        this.compatible_elements = compatible_elements;
        this.base_power = base_power;
        this.mastery_level = 1;
    }
    // copy constructor
    // mastery level hasil copy adalah 1
    public Skill(Skill other){
        this.name = other.name;
        this.compatible_elements = other.compatible_elements;
        this.base_power = other.base_power;
        this.mastery_level = 1;
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
    public String getPrint() {
        String message = "Skill " + this.name + "/Element: ";
        for (int i = 0; i < this.compatible_elements.size(); i++) {
            message += this.compatible_elements.get(i).type().asString();
            if (i != this.compatible_elements.size() - 1) message += ", ";
        }
        message += "/BP: " + this.base_power;
        return message;
    }

    @Override
    public void print() {
        /* Kurang tau format printnya gimana jadi mungkin beginin aja udah cukup */
        System.out.println("Skill " + this.name + " Element: "
                + this.compatible_elements.get(0).type().asString() + " BP: " + this.base_power);
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
