package com.boi.engimonfactory;

public class ContohEngimon implements Storeable {
    /* Placeholder atribut */
    private Integer id; // Unik untuk tiap engimon
    private String name;
    private String element; // Elemen engimon (bukan string)
    private Integer level; // Level engimon
    private Integer lives; // nyawa engimon
    // Default Konstruktor placeholder
    public ContohEngimon() { 
        
    }
    // Konstruktor placeholder
    public ContohEngimon(Integer id, String name, String element, Integer level) {
        this.lives = 3;
        this.id = id;
        this.name = name;
        this.element = element;
        this.level = level;
    }
    
    /* Tolong yang di bawah ini ada semua */
    /* Implementasi interface Storeable */
    /* Ini di copy-paste, disesuaikan */
    /* Tolong override ini semua */
    /* Contoh print di spek: */
    /*
        1. EngimonA/Fire/Lv.30
        2. EngimonB/Fire/Lv.6
        3. EngimonC/Water/Lv.15
        4. EngimonD/Ground/Lv.17
    */

    // Format print: Engimon<Spesies><Nama>/Element/Lvl
    @Override
    public void print() {
        /* untuk print inventory */
        System.out.println("Engimon " + this.name + " Element: " + this.element 
            + " Level: " + this.level); // ini placeholder, tolong diganti
    }

    @Override
    public Integer getSortInt() {
        return this.level * -1; // Ini kopas aja sama persis
    }

    @Override
    public String getSortStr() {
        /* tolong return format print elemen engimon di atas */
        /* 
            kalo ada dua elemen tolong konsisten yang mana duluan,
            misalnya kalo ada Ground/Water jangan ada lagi Water/Ground,
            urutannya disamain aja
        */
        return this.element; // placeholder
    }

    /* Tolong override ini juga */
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof ContohEngimon)) return false;
        ContohEngimon other = (ContohEngimon) o;
        return this.id == other.id;
    }
    
    // Format string: Engimon<Spesies><Nama>/Element/Lvl
    @Override
    public String toString() {
        return ("Engimon " + this.name + " Element: " 
            + this.element + " Level: " + this.level);
    }
}