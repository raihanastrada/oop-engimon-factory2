package com.boi.engimonfactory;

public class BattleUtil{
	// mengembalikan power engimon a terhadap engimon b
	private float getPowerAgainst(Engimon a, Engimon b){
		float ret = a.getLevel()*Element.getAdvantage(a.getElements(), b.getElements());
		for (Skill s : a.getSkills()) ret += s.getBasePower()*s.getMasteryLevel(); // belum ada getter skills
		return ret;
	}

	// menampilkan status battle
	public void showBattleStatus(Engimon player_engimon, Engimon wild_engimon){
		wild_engimon.print();
		System.out.println("power active engimon = " + getPowerAgainst(player_engimon, wild_engimon));
		System.out.println("power wild engimon = " + getPowerAgainst(wild_engimon, player_engimon));
	}

	// mengembalikan true apabila player engimon menang
	public boolean comparePower(Engimon player_engimon, Engimon wild_engimon){
		return getPowerAgainst(player_engimon, wild_engimon) >= getPowerAgainst(wild_engimon, player_engimon);
	}

}