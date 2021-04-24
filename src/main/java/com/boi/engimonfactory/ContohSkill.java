package com.boi.engimonfactory;

import java.util.ArrayList;

public class ContohSkill implements Storeable {
    /* Placeholder atribut */
    private String name; // nama skill
    private String element; // elemen skill
    private Integer bp; // Base Power skill
    public ContohSkill(String name, String element, Integer bp) {
        this.name = name;
        this.element = element;
        this.bp = bp;
    }
    
    /* Tolong yang di bawah ini ada semua */
    /* Implementasi interface Storeable */
    /* Ini di copy-paste, disesuaikan */
    /* Tolong override ini semua */

    @Override
    public String toString() {
        return ("Skill " + this.name + "Element: "
            + this.element + " BP: " + this.bp);
    }

    @Override
    public void print() {
        /* Kurang tau format printnya gimana jadi mungkin beginin aja udah cukup */
        System.out.println("Skill " + this.name + " Element: " 
            + this.element + " BP: " + this.bp);
    }

    /* Tolong override ini berdasarkan nama skill */
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof ContohSkill)) return false;
        ContohSkill other = (ContohSkill) o;
        return this.name.equals(other.name);
    }

    public boolean isCompatible(ArrayList<Element> elements)
    {
        return true;
    }


    @Override
    public Integer getSortInt() {
        return this.bp * -1; // Ini kopas aja sama persis
    }

    @Override
    public String getSortStr() {
        return ""; // Ini kopas aja sama persis
    }
}
