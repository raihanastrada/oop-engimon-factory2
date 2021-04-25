package com.boi.engimonfactory;

import javax.lang.model.type.NullType;

enum CellType {
    MOUNTAINS,  // Fire Engimon
    SEA,        // Water Engimon
    GRASSLAND,  // Ground or Electric Engimon
    TUNDRA      // Ice Engimon
}

public class Cell {
    private CellType type;
    private Position position;
    private Engimon enemy;

    public Cell(CellType type, Position position) {
        this.type = type;
        this.position = position;
        this.enemy = null;
    }

    public CellType getType() {
        return type;
    }

    public Position getPosition() {
        return position;
    }

    public Engimon getEnemy() {
        return enemy;
    }

    public void setEnemy(Engimon enemy) {
        this.enemy = enemy;
    }
}
