package com.boi.engimonfactory;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class Inventory<T extends Storeable> {
    /* Private class Item (Tuple T dengan Integer) */
    private class Item implements Storeable {
        private T item;
        private Integer count;
        
        public Item(T item) {
            this.item = item;
            this.count = 1; // default count 1
        }

        public Item(T item, Integer count) {
            this.item = item;
            this.count = count; // user defined (count)
        }

        public T getItem() { // getter item
            return this.item;
        }

        public Integer getCount() { // getter jumlah item
            return this.count;
        }

        public void incrementCount() {
            this.count++;
        }

        public void decrementCount() {
            this.count--;
        }

        public void incrementBy(Integer count) {
            this.count += count;
        }

        public void decrementBy(Integer count) {
            this.count -= count;
        }

        @Override
        public void print() {
            item.print();
        }

        @Override
        public Integer getSortInt() {
            return item.getSortInt();
        }

        @Override
        public String getSortStr() {
            return item.getSortStr();
        }

        @Override
        public boolean equals(Object obj) {
            return this.item.equals(obj);
        }
    }

    /* Atribut Inventory */
    private ArrayList<Item> inv;
    private Integer count;

    /* Method Inventory */
    // Constructor inventory
    public Inventory() {
        this.inv = new ArrayList<Item>();
        this.count = 0;
    }

    // Mengembalikan True jika terdapat item pada inventory, False jika tidak
    public Boolean contains(T item) {
        for (Item itItem : this.inv) {
            if (itItem.getItem().equals(item)) return true;
        }
        return false;
    }

    // Mengembalikan indeks item pada inventory jika terdapat, jika tidak, dikembalikan -1
    public Integer containsID(T item) {
        for (int i = 0; i < this.inv.size(); i++) {
            if (this.inv.get(i).equals(item)) return i;
        }
        return -1;
    }

    // Menambahkan item baru pada inventory, default berjumlah 1
    public void insertItem(T item) {
        this.count++;
        if (!this.contains(item)) {
            this.inv.add(new Item(item));
            this.sort();
            return;
        }
        this.inv.get(containsID(item)).incrementCount();
    }

    // Menambahkan item berdasarkan input item dan jumlahnya
    // Jika belum terdapat maka akan dibuat objek Item baru dengan count yang ada
    // Jika sudah ada jumlah item tersebut pada inventory akan ditambah sebanyak count
    // (Digunakan untuk engimon saja, karena count pada item merepresentasikan nyawa engimon)
    public void insertItem(T item, Integer count) {
        this.count++;
        if (!this.contains(item)) {
            this.inv.add(new Item(item, count));
            this.sort();
            return;
        }
        this.inv.get(containsID(item)).incrementBy(count);
    }

    // Mengurangkan jumlah item untuk satu item pada inventory tanpa mengurangkan
    // count dari inventory (untuk engimon), hanya digunakan untuk engimon
    public Boolean minusItem(T item) {
        int ind = containsID(item);
        if (ind == -1) return null;
        if (this.inv.get(ind).getCount() == 1) {
            this.inv.remove(ind);
            this.count--;
            return true;
        }
        this.inv.get(ind).decrementCount();
        return false;
    }

    // Mengurangkan item berdasarkan input item yang sama, untuk engimon
    public void detractItem(T item) {
        Integer index = this.containsID(item);
        if (index == -1) return;
        detractItemByIdx(index);
    }

    // Mengurangkan count item berdasarkan index (mengurangkan satu)
    public void detractItemByIdx(int index) {
        if (index >= this.inv.size() || index < 0) return;
        if (this.inv.get(index).getCount() == 1) this.inv.remove(index);
        else this.inv.get(index).decrementCount();
        this.count--;
    }

    // Mengurangkan jumlah item dengan input item dan jumlah yang ingin dikurangkan
    // Mengembalikan null jika item tidak terdapat
    // Mengembalikan false jika input count lebih dari jumlah yang tersedia
    // Mengembalikan true jika berhasil dikurangkan
    public Boolean detractItem(T item, Integer count) {
        int index = this.containsID(item);
        if (index == -1) return null;
        return detractItemByIdx(index, count);
    }

    // Mengurangkan jumlah item dengan input index dan jumlah yang ingin dikurangkan
    // Mengembalikan value seperti method detractItem
    public Boolean detractItemByIdx(int index, Integer count) {
        if (index >= this.inv.size() || index < 0) return null;
        if (this.inv.get(index).getCount() == count) this.inv.remove(index);
        else if (this.inv.get(index).getCount() < count) return false;
        else this.inv.get(index).decrementBy(count);
        this.count -= count;
        return true;
    }
    
    // Mengembalikan item dengan index
    public T getItemByIdx(int index) {
        if (index >= this.inv.size() || index < 0) return null;
        return this.inv.get(index).getItem();
    }

    // Mengembalikan jumlah item dengan parameter index
    public Integer getCountByIdx(int index) {
        if (index >= this.inv.size() || index < 0) return null;
        return this.inv.get(index).getCount();
    }

    // Mengembalikan jumlah item pada inventory
    public Integer getCount() {
        return this.count;
    }
    // Mengembalikan size dari ArrayList inv
    public Integer getSize() {
        return this.inv.size();
    }

    // Membuang item dari inventory (Release engimon)
    public void remove(int index) {
        if (index >= this.inv.size() || index < 0) return;
        this.inv.remove(index);
        this.count--;
    }

    // Membuang item dari inventory yang sama dengan input Item
    public void remove (T item) {
        int index = this.containsID(item);
        if (index == -1) return;
        this.inv.remove(index);
        this.count--;
    }

    // Meng-outputkan isi inventory dengan counter dapat diganti
    // Untuk engimon diperlihatkan lives engimon tersebut
    // Untuk skillitem diperlihatkan jumlah item tersebut
    public void printAll(String counter) {
        Integer count = 1;
        for (Item i : this.inv) {
            System.out.print(count + ".\t");
            i.getItem().print();
            System.out.println("\t" + counter + ": " + i.getCount());
            count++;
        }
    }

    // Getter Inventory dalam bentuk string
    public String getPrintAll(String counter) {
        String message = "";
        Integer count = 1;
        for (Item i : this.inv) {
            message += (count + ".\t" + i.toString());
            count++;
        }
        return message;
    }

    // Sort Inventory sesuai spesifikasi
    // Engimon dikelompokkan berdasarkan elemen, lalu berdasarkan level (tinggi ke rendah)
    // Skill item berdasarkan base power
    private void sort() {
        Comparator<Item> comparator = Comparator
                                            .comparing(Item::getSortStr)
                                            .thenComparingInt(Item::getSortInt);
        List<Item> sortedInv = this.inv.stream()
                                    .sorted(comparator)
                                    .collect(Collectors.toList());
        this.inv = new ArrayList<Item>(sortedInv);
    }
}