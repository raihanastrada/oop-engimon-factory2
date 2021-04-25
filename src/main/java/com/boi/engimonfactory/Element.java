package com.boi.engimonfactory;

import java.io.Serializable;
import java.util.List;

enum ElementType {
    NONE(0, "None"),
    ELECTRIC(1, "Electric"),
    FIRE(2, "Fire"),
    GROUND(3, "Ground"),
    ICE(4, "Ice"),
    WATER(5, "Water");

    int code;
    String name;
    ElementType(int c, String n)
    {
        name = n;
        code = c;
    }
    int getCode() { return code; }
    String asString() { return name; }
}

public class Element implements Serializable {

    //   usage: advantageIndex[user][target]
    private static final double advantageIndex[][] = {
    //   E    F    G    I    W
        {1.0, 1.0, 0.0, 1.5, 2.0}, // ELECTRIC vs
        {1.0, 1.0, 0.5, 2.0, 0.0}, // FIRE vs
        {2.0, 1.5, 1.0, 0.0, 1.0}, // GROUND vs
        {0.5, 0.0, 2.0, 1.0, 1.0}, // ICE vs
        {0.0, 2.0, 1.0, 1.0, 1.0}  // WATER vs
    };

    private ElementType type;

    public Element(ElementType t)
    {
        this.type = t;
    }

    public Element(int code)
    {
        this.type = ElementType.values()[code];
    }

    public static Element getElement(int code)
    {
        return new Element(code);
    }


    public ElementType type()
    {
        return this.type;
    }

    public static double getAdvantage(Element a, Element b)
    {
        return advantageIndex[a.type.getCode()-1][b.type.getCode()-1];
    }

    public static double getAdvantage(List<Element> a, List<Element> b)
    {
        double max = 0.0;
        if (a.size() != 0 && b.size() != 0)
        {
            for (Element i: a)
            {
                for (Element j: b)
                {
                    if (getAdvantage(i, j) > max)
                    {
                        max = getAdvantage(i, j);
                    }
                }
            }
        }
        return max;
    }


}
