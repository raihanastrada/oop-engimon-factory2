package com.boi.engimonfactory;

import java.util.ArrayList;

public class BattleUtil{
	// mengembalikan power engimon a terhadap engimon b
	private static double getPowerAgainst(Engimon a, Engimon b){
		double ret = a.getLevel()*Element.getAdvantage(a.getElements(), b.getElements());
		for (Skill s : a.getSkills()) ret += s.getBasePower()*s.getMasteryLevel(); // belum ada getter skills
		return ret;
	}

	// mengembalikan ArrayList berisi status battle
	// isi ArrayList: [detail wild engimon, power active engimon, power wild engimon]
	public static ArrayList<String> getBattleStatus(Engimon player_engimon, Engimon wild_engimon) {
		ArrayList<String> ret = new ArrayList<>();
		ret.add(wild_engimon.getPrint());
		ret.add(String.valueOf(getPowerAgainst(player_engimon, wild_engimon)));
		ret.add(String.valueOf(getPowerAgainst(wild_engimon, player_engimon)));
		return ret;
	}
	// mengembalikan true apabila player engimon menang
	public static boolean comparePower(Engimon player_engimon, Engimon wild_engimon){
		return getPowerAgainst(player_engimon, wild_engimon) >= getPowerAgainst(wild_engimon, player_engimon);
	}
}