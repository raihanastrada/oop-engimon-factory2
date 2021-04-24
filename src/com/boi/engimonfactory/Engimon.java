package com.boi.engimonfactory;

import java.util.ArrayList;

public class Engimon implements Storeable {
    private int id, exp, level;
    private String name;
    private String[] parentNames;
    private boolean alive;
    private final Engidex.Species species;
    private ArrayList<ContohSkill> skills = new ArrayList<ContohSkill>();

    static final int MAX_EXP = 100 * 100;
    static int engimon_count = 0;

    public Engimon(String name, Engidex.Species spec) {
        this.id = engimon_count;
        engimon_count++;

        this.name = name;
        this.species = spec;
        skills.add(spec.getSpeciesSpecialSkill());
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == this) return true;
        if (!(o instanceof Engimon)) return false;
        Engimon other = (Engimon) o;
        return this.id == other.id;
    }
    
    public void setName(String n) { this.name = n; }
    public void setLevel(int new_level) { this.level = new_level; }

    public boolean getAlive() { return alive; }
    public String getName() { return name; }
    public int getLevel() { return level; }
    public int getExp() { return exp % 100; }
    public int getCumulativeExp() { return exp; }

    public ArrayList<Element> getElements() { return this.species.getSpeciesElements(); }
    public Engidex.Species getspecies() { return this.species; }
    public String getSpeciesName() { return this.species.getSpeciesName(); }

    public void addExp(int new_exp)
    {
        if (exp + new_exp > MAX_EXP)
        {
            exp = MAX_EXP;
            alive = false;
            level++;
        } else {
            exp += new_exp;
            if (getExp() > 100)
            {
                level++;
            }
        }
    }

    public void addSkill(ContohSkill newSkill) throws SkillAlreadyLearnedException, SkillNotCompatibleException, SkillSlotsFullException
    {
        String msg;
        if (skills.contains(newSkill))
        {
            msg = "Skill already learned";
            throw new SkillAlreadyLearnedException(msg);
        } else if (!newSkill.isCompatible(this.getElements()))
        {
            msg = "Skill not compatible";
            throw new SkillNotCompatibleException(msg);
        } else if (skills.size() >= 4) {
            msg = "Skill slots full";
            throw new SkillSlotsFullException(msg);
        } else {
            skills.add(newSkill);
        }
    }

    // index starts at 0
    public void replaceSkill(ContohSkill newSkill, int index)
    {
        skills.set(index, newSkill);
    }

    // Format print: Engimon<Spesies><Nama>/Element/Lvl
    public void print()
    {
        String msg = this.name + "/" + this.getElements().get(0).type().asString();
        if (this.getElements().size() == 2)
        {
            msg += "-" + this.getElements().get(1).type().asString();
        }
        msg += "/" + Integer.toString(this.level);

        System.out.println(msg);
    };

    public Integer getSortInt()
    {
        return this.level * -1;
    };

    public String getSortStr()
    {
        return this.getElements().get(0).type().asString();
    };


}
