package com.boi.engimonfactory;

import java.util.ArrayList;

public class Engimon implements Storeable {
    protected int id, exp, level;
    protected String name;
    protected String[] parentNames;
    protected int lives = 1;
    protected boolean alive;
    private final Engidex.Species species;
    protected ArrayList<Skill> skills = new ArrayList<Skill>();

    static final int MAX_EXP = 100 * 100;
    static int engimon_count = 0;

    public Engimon()
    {
        this.species = new Engidex.Species();
    }

    public Engimon(Engidex.Species spec) {
        this.id = engimon_count;
        this.name = spec.getSpeciesName();
        engimon_count++;
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

    public void setLevel(int new_level) { this.level = new_level; }
    public void setName(String name) { this.name = name; }

    public boolean getAlive() { return alive; }
    public String getName() { return name; }
    public int getLevel() { return level; }
    public int getExp() { return exp % 100; }
    public int getCumulativeExp() { return exp; }
    public ArrayList<Skill> getSkills() { return this.skills; }
    public Skill getFirstSkill() {
        if (this.skills.size() == 0) return null;
        return this.skills.get(0);
    }

    @Override
    public String getPrint() {
        String msg = this.name + "/" + this.getElements().get(0).type().asString();
        if (this.getElements().size() == 2)
        {
            msg += "-" + this.getElements().get(1).type().asString();
        }
        msg += "/" + this.level;
        return msg;
    }

    public ArrayList<Element> getElements() { return this.species.getSpeciesElements(); }
    public Engidex.Species getSpecies() { return this.species; }
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

    public void addSkill(Skill newSkill) throws SkillAlreadyLearnedException, SkillNotCompatibleException, SkillSlotsFullException
    {
        String msg;
        if (skills.contains(newSkill))
        {
            msg = "Skill already learned";
            throw new SkillAlreadyLearnedException(msg);
        } else if (!newSkill.isCompatibleWith(this.getElements()))
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
    public void replaceSkill(Skill newSkill, int index)
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

    public String getDetailEngimon() {
        String detail = "Engimon " + this.getSpeciesName()
            + " [" + this.getName() + "]" + "\n";
        detail += "Parents\t: ";
        if (this.parentNames.length == 0) detail += "-";
        else detail += (parentNames[0] + ", " + parentNames[1] + "\n");
        detail += "Skill\t:\n";
        for (Skill skill : this.skills) {
            detail += "\t" + skill.toString() + "\n";
        }
        detail += ("Lives\t: " + String.valueOf(this.lives));
        detail += ("Lvl\t: " + String.valueOf(this.level));
        detail += ("CExp\t: " + String.valueOf(this.exp));
        detail += ("Exp\t: " + String.valueOf(getExp()));
        return detail;
    }

    public void decreaseLives() {
        this.lives--;
    }
}
