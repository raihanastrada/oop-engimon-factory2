package com.boi.engimonfactory;

// @TODO
// replace Engimon ke Engimon nanti
// replace ContohSkill ke Skill nanti

public class Player {
    private Engimon activeEngimon; // Reference ke Active Engimon, jika tidak ada, null
    private String name; // Nama Player
    private Integer maxCap; // Maksimum kapasitas inventory
    private Inventory<Engimon> invE; // Inventory Engimon
    private Inventory<ContohSkill> invS; // Inventory SkillItem
    public Player() { // Default Constructor
        this(null, 10);
    }

    public Player(String name, Integer maxCap) { // User-defined Constructor
        this.name = name;
        this.maxCap = maxCap;
        this.invE = new Inventory<Engimon>();
        this.invS = new Inventory<ContohSkill>();
    }

    // Getter String nama player
    public String getName() {
        return this.name;
    }

    // Asumsi engimon yang masuk ke inventory selalu memiliki 3 nyawa
    // Jika berhasil memasukkan engimon, return true, jika tidak, false
    public Boolean insertItem(Engimon e) {
        if (this.getInvCount() == maxCap) return false;
        this.invE.insertItem(e, 3);
        return true;
    }

    // Asumsi memasukkan skill item hanya satu
    // Jika berhasil memasukkan skill item, return true, jika tidak, false
    public Boolean insertItem(ContohSkill s) {
        if (this.getInvCount() == maxCap) return false;
        this.invS.insertItem(s);
        return true;
    }

    // Release Engimon dengan input engimon
    public void releaseEngimon(Engimon e) {
        this.invE.remove(e);
        if (this.activeEngimon.equals(e)) this.activeEngimon = null;
    }

    // Release Engimon dengan input index
    public void releaseEngimon(int index) {
        this.invE.remove(index);
    }

    // Membuang item skill pada inventory berdasarkan input skill
    // Jika tidak terdapat mengembalikan null
    // Jika count > jumlah yang dimiliki return false
    // Jika berhasil dibuang, return true
    public Boolean buangItemSkill(ContohSkill skill, Integer count) {
        return this.invS.detractItem(skill, count);
    }

    // Membuang item skill pada inventory sebanyak count berdasarkan index
    // Jika tidak terdapat (index tidak valid) mengembalikan null
    // Jika count > jumlah yang dimiliki return false
    // Jika berhasil dibuang, return true
    public Boolean buangItemSkill(int index, Integer count) {
        return this.invS.detractItemByIdx(index, count);
    }

    // Output inventory engimon
    public void printInventoryEngimon() {
        this.invE.printAll("Lives");
    }

    // Output inventory skill item
    public void printInventorySkill() {
        this.invS.printAll("Count");
    }

    // Output inventory Engimon Player dalam bentuk String
    public String getPrintInventoryE() {
        return this.invE.getPrintAll("Lives");
    }

    // Output inventory Skill Item Player dalam bentuk STring
    public String getPrintInventoryS() {
        return this.invS.getPrintAll("Count");
    }

    // Mengembalikan jumlah item yang terdapat pada inventory
    public Integer getInvCount() {
        return this.invE.getCount() + this.invS.getCount();
    }

    /* SEMUA USER INPUT -1 dulu */

    // Menukar active engimon dengan engimon yang terdapat dalam inventory
    public void switchActive(int index) { // Parameter input indeks invE
        if (index >= this.invE.getSize() || index < 0) return;
        this.activeEngimon = this.invE.getItemByIdx(index);
    }

    // Learn skill untuk engimon pada inventory dari skill item pada inventory
    // Mengembalikan message error jika tidak berhasil, dan "berhasil" jika berhasil
    // Mengembalikan null jika index tidak valid
    // @TODO add skill harus membuat item skill baru baru dimasukkan
    //      agar tidak reference ke objek yang sama
    public String learnSkill(int idxE, int idxS) {
        if (idxE >= this.invE.getSize() || idxE < 0) return null;
        if (idxS >= this.invS.getSize() || idxS < 0) return null;
        try {
            this.invE.getItemByIdx(idxE)
                .addSkill(this.invS.getItemByIdx(idxS));
            this.invS.detractItemByIdx(idxS);
            return "berhasil";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    // Setelash learnskill, tanya ke user jika mau replace atau tidak
    // printDetail Engimon (beserta skill yang dimiliki) dan nomornya
    // Minta user input untuk skill engimon yang ingin di replace
    // Mengembalikan false jika indeks salah, true jika berhasil
    public Boolean replaceSkill(int idxE, int idxS, int idxSE) {
        if (idxE >= this.invE.getSize() || idxE < 0) return false;
        if (idxS >= this.invS.getSize() || idxS < 0) return false;
        if (idxSE >= 4 || idxSE < 0) return false;
        this.invE.getItemByIdx(idxE)
            .replaceSkill(this.invS.getItemByIdx(idxS), idxSE);
        this.invS.detractItemByIdx(idxS);
        return true;
    }

    // Mengurangi nyawa active engimon setelah battle jika kalah
    // Menghilangkan dari inventory jika nyawa engimon habis
    // Mengembalikan true jika active engimon mati dan dihapus dari inventory
    // Mengembalikan false jika active engimon hidup
    // Mengembalikan null jika tidak ada active engimon
    public Boolean activeLost() {
        if (this.activeEngimon == null) return null;
        this.activeEngimon.decreaseLives(); // Mengurangkan nyawa engimon
        if (this.invE.minusItem(this.activeEngimon)) {
            this.activeEngimon = null;
            return true;
        }
        return false;
    }

    // Mengganti nama engimon pada inventory
    // Mengembalikan false jika idx tidak valid
    // Mengembalikan true jika terganti
    public Boolean changeName(int idxE, String name) {
        if (idxE >= this.invE.getSize() || idxE < 0) return false;
        this.invE.getItemByIdx(idxE).setName(name);
        return true;
    }

    // Mengembalikan string detail engimon pada inventory dengan 
    // indeks pada inventory = index
    public String getDetailEngimon(int index) {
        if (index >= this.invE.getSize() || index < 0) return null;
        return this.invE.getItemByIdx(index).toString();
    }

    // Mengembalikan string cek active engimon
    public String checkActive() {
        if (this.activeEngimon == null) return null;
        return this.activeEngimon.toString();
    }

    // Interact dengan active engimon
    // [<Nama_Engimon>]: <teks bebas> misal spesies pikachu dengan nama pepo
    // [pepo]: PikaPika
    public String interact() {
        if (this.activeEngimon == null) return null;
        String message = "[" + this.activeEngimon.getName() + "]: ";
        for (int i = 0; i < 8; i++) {
            message += this.activeEngimon.getSpeciesName().charAt(i % 4);
        }
        return message;
    }

    // Boolean menentukan jika player mati atau tidak
    // Jika mati mengembalikan true, jika tidak mengembalikan false
    public Boolean isDead() {
        return this.invE.getCount() <= 0;
    }

    // melakukan battle
    public void battle(){
        // cek ada active engimon atau tidak

        // cari adjacent wild engimon

        // output status wild engimon & power level dua engimon

        // battle

        // jika menang, kurangi life active engimon dengan 1
        // jika kalah, beri player engimon & skill item, hapus wild engimon dari map
        // menggunakan activeLost() (mengurangkan nyawa activeEngimon)
        //      sekaligus menghilangkan dari inventory jika mati
    }

    public void breed(int idxE1, int idxE2) { // placeholder breed
        // return type placeholder mungkin diganti Boolean
    }
}