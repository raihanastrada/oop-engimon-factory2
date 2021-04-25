package com.boi.engimonfactory;

import java.io.Serializable;

public interface Storeable extends Serializable {
    public void print();
    public Integer getSortInt();
    public String getSortStr();
}
