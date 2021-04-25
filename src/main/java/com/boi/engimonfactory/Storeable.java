package com.boi.engimonfactory;

import java.io.Serializable;

public interface Storeable extends Serializable {
    public void print(); // method untuk dioutput memperlihatkan inventory
    public Integer getSortInt(); // untuk membandingkan berdasarkan integer
    public String getSortStr(); // untuk membandingkan berdasarkan string
    public String getPrint(); // untuk mendapatkan string yang dapat dioutputkan
}
