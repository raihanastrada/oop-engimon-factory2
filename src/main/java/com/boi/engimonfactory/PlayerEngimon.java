package com.boi.engimonfactory;

// import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Random;

public class PlayerEngimon extends Engimon {

    // protected int id, exp, level;
    // protected String name = "";
    // protected String[] parentNames;
    // protected int lives;
    // protected boolean alive;
    // protected ArrayList<Skill> skills = new ArrayList<Skill>();

    public PlayerEngimon(Engidex.Species spec) {
        super(spec);
        this.lives = 3;
        this.name = this.getSpeciesName();
    }

    public PlayerEngimon(Engimon wildEngimon)
    {
        super(wildEngimon.getSpecies());
        this.exp = wildEngimon.getCumulativeExp();
        this.level = wildEngimon.getLevel();
        this.lives = 3;
    }

    public PlayerEngimon(Engimon breedEngimon, String newName, String momName, String dadName) {
        super(breedEngimon.getSpecies());
        name = newName;
        parentNames[0] = momName;
        parentNames[1] = dadName;
        lives = 3;
    }

    // Buat ngetame wildEngimon, makenya PlayerEngimon.tame(wildEngimon)
    public static PlayerEngimon tame(Engimon wildEngimon)
    {
        return new PlayerEngimon(wildEngimon);
    }

    public static PlayerEngimon breed(Engimon mom, Engimon dad) throws OneEngimonBreedException, InsufficientLevelException
    {
        if (mom.equals(dad)) {
            String msg = "Tolong Jangan Membelah Diri ya Bro";
            throw new OneEngimonBreedException(msg);
        }
        else if (mom.getLevel()<4 || dad.getLevel()<4) {
            String msg = "Lemah kok mau bikin Anak Bro";
            throw new InsufficientLevelException(msg);
        }
        else {
            // determine id, name, parents
            System.out.println("Tolong anaknya dikasih nama yaa");
            String newName = "namaAnakHere"; // nama anak
            String momName = mom.getName(); // nama ibu
            String dadName = dad.getName(); // nama bapak
            Engimon anak = new Engimon(); // placeholder engimon anak
            anak = PlayerEngimon.determineEngimon(mom, dad);
            PlayerEngimon toReturn = new PlayerEngimon(anak, newName, momName, dadName);

            // inherit skill
            if (mom.getSkills().size() < dad.getSkills().size()) {
                for (int i = 0; i < mom.getSkills().size(); i++) {
                    toReturn.inheritSkill(mom.getSkills().get(i));
                    toReturn.inheritSkill(dad.getSkills().get(i));
                }
                for (int i = mom.getSkills().size(); i < dad.getSkills().size(); i++) {
                    toReturn.inheritSkill(dad.getSkills().get(i));
                }
            }
            else if (dad.getSkills().size() < mom.getSkills().size()) {
                for (int i = 0; i < dad.getSkills().size(); i++) {
                    toReturn.inheritSkill(mom.getSkills().get(i));
                    toReturn.inheritSkill(dad.getSkills().get(i));
                }
                for (int i = dad.getSkills().size(); i < mom.getSkills().size(); i++) {
                    toReturn.inheritSkill(mom.getSkills().get(i));
                }
            }
            else {
                for (int i = 0; i < mom.getSkills().size(); i++) {
                    toReturn.inheritSkill(mom.getSkills().get(i));
                    toReturn.inheritSkill(dad.getSkills().get(i));
                }
            }
            return toReturn;
        }
    }

    public void inheritSkill(Skill newSkill) {
        Integer newSkillMasteryLevel = newSkill.getMasteryLevel();
        if (skills.size()<4 && !skills.contains(newSkill)) {
            skills.add(newSkill);
        }
        else {
            if (skills.contains(newSkill)) {
                int index = 0;
                for (int i=0;i<skills.size();i++) {
                    if (skills.get(i).equals(newSkill)) {
                        index = i;
                    }
                }
                Integer currentMasteryLevel = skills.get(index).getMasteryLevel();
                if (currentMasteryLevel.equals(newSkillMasteryLevel) && currentMasteryLevel<3) {
                    skills.get(index).setMasteryLevel(currentMasteryLevel+1);
                }
                else if (currentMasteryLevel < newSkillMasteryLevel) {
                    skills.get(index).setMasteryLevel(newSkillMasteryLevel);
                }
            }
            else if (skills.size()==4) {
                ArrayList<Skill> temp = new ArrayList<Skill>();
                for (int i=1;i<skills.size();i++) {
                    Integer currentMasteryLevel = skills.get(i).getMasteryLevel();
                    if (currentMasteryLevel < newSkillMasteryLevel) {
                        temp.add(skills.get(i));
                        skills.remove(i);
                        skills.add(newSkill);
                        break;
                    }
                }
                // cek seluruh skillnya lagi, agar skill final memiliki mastery level tinggi
                while (temp.size()>0) {
                    for (int i=1;i<skills.size();i++) {
                        Integer currentMasteryLevel = skills.get(i).getMasteryLevel();
                        if (currentMasteryLevel < temp.get(0).getMasteryLevel()) {
                            temp.add(skills.get(i));
                            skills.remove(i);
                            skills.add(temp.get(0));
                            break;
                        }
                    }
                    temp.remove(0);
                }
            }
        }
    }

    public static Engimon determineEngimon(Engimon mom, Engimon dad) {
        Random index = new Random();
        ArrayList<Element> listElemenIbu = mom.getElements();
        ArrayList<Element> listElemenBapak = dad.getElements();
        // case 1, elemen sama
        if (listElemenBapak.equals(listElemenIbu)) {
            return new Engimon(mom.getSpecies());
        }

        // sisa case, elemen beda
        Element elemenIbu = listElemenIbu.get(0);
        Element elemenBapak = listElemenBapak.get(0);
        // handle case dual element
        if (listElemenBapak.size() == 2) {
            elemenBapak = listElemenBapak.get(index.nextInt(2));
        }
        if (listElemenIbu.size() == 2) {
            elemenIbu = listElemenIbu.get(index.nextInt(2));
        }
        double advantageIbu = Element.getAdvantage(elemenIbu, elemenBapak);
        double advantageBapak = Element.getAdvantage(elemenBapak, elemenIbu);
        if (advantageIbu > advantageBapak) {
            return new Engimon(mom.getSpecies());
        }
        else if (advantageIbu < advantageBapak) {
            return new Engimon(dad.getSpecies());
        }
        else { // advantage sama, anak jadi dual elemen
            int codeIbu = elemenIbu.type().getCode();
            int codeBapak = elemenBapak.type().getCode();
            Engimon toReturn = Engidex.generateEngimon(codeIbu,codeBapak);
            return new Engimon(toReturn.getSpecies());
        }
    }

    public String interact()
    {
        String msg = this.getSpecies().getSpeciesName().substring(0, 4) + this.getSpecies().getSpeciesName().substring(0, 4) + "!";
        System.out.println(msg);

        return msg;
    }

    public void setName(String n) { this.name = n; }
}
