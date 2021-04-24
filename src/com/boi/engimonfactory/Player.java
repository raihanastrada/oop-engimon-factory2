package com.boi.engimonfactory;

// @TODO
// replace ContohEngimon ke Engimon nanti
// replace ContohSkill ke Skill nanti

public class Player {
    private ContohEngimon activeEngimon; // Reference ke Active Engimon, jika tidak ada, null
    private String name; // Nama Player
    private Integer maxCap; // Maksimum kapasitas inventory
    private Inventory<ContohEngimon> InvE; // Inventory Engimon
    private Inventory<ContohSkill> InvS; // Inventory SkillItem
    public Player() {
        this(null, 10);
    }

    public Player(String name, Integer maxCap) {
        this.name = name;
        this.maxCap = maxCap;
        this.InvE = new Inventory<ContohEngimon>();
        this.InvS = new Inventory<ContohSkill>();
    }

    // Asumsi engimon yang masuk ke inventory selalu memiliki 3 nyawa
    // Jika berhasil memasukkan engimon, return true, jika tidak, false
    public Boolean insertItem(ContohEngimon e) {
        if (this.getInvCount() == maxCap) return false;
        this.InvE.insertItem(e, 3);
        return true;
    }

    // Asumsi memasukkan skill item hanya satu
    // Jika berhasil memasukkan skill item, return true, jika tidak, false
    public Boolean insertItem(ContohSkill s) {
        if (this.getInvCount() == maxCap) return false;
        this.InvS.insertItem(s);
        return true;
    }

    // Release Engimon dengan input engimon
    public void releaseEngimon(ContohEngimon e) {
        this.InvE.remove(e);
        if (this.activeEngimon.equals(e)) this.activeEngimon = null;
    }

    // Release Engimon dengan input index
    public void releaseEngimon(int index) {
        this.InvE.remove(index);
    }

    // Membuang item skill pada inventory berdasarkan input skill
    // Jika tidak terdapat mengembalikan null
    // Jika count > jumlah yang dimiliki return false
    // Jika berhasil dibuang, return true
    public Boolean buangItemSkill(ContohSkill skill, Integer count) {
        return this.InvS.detractItem(skill, count);
    }

    // Membuang item skill pada inventory sebanyak count berdasarkan index
    // Jika tidak terdapat (index tidak valid) mengembalikan null
    // Jika count > jumlah yang dimiliki return false
    // Jika berhasil dibuang, return true
    public Boolean buangItemSkill(int index, Integer count) {
        return this.InvS.detractItemByIdx(index, count);
    }

    // Output inventory engimon
    public void printInventoryEngimon() {
        this.InvE.printAll("Lives");
    }

    // Output inventory skill item
    public void printInventorySkill() {
        this.InvS.printAll("Count");
    }

    public Integer getInvCount() {
        return this.InvE.getCount() + this.InvS.getCount();
    }

    // melakukan battle
    public void battle(){
        // cek ada active engimon atau tidak

        // cari adjacent wild engimon

        // output status wild engimon & power level dua engimon

        // battle

        // jika menang, kurangi life active engimon dengan 1
        // jika kalah, beri player engimon & skill item, hapus wild engimon dari map
        
    }
}