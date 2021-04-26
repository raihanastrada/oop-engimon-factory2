package com.boi.engimonfactory;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.Serializable;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;
import java.util.Collections;
import java.lang.*;

public class Peta implements Serializable {
    private Position playerPosition;                         // player location in map
    private Position activeEngimonPosition;                  // active engimon location in map
    private Cell[][] map;                                    // matrix storing map
    private ArrayList<Pair<Engimon, Position>> enemyEngimon; // list of enemy engimon & position in map
    private int maxEnemyCount;                               // max count of engimon in map
    private long time;

    public Peta(Position playerPosition, Position activeEngimonPosition, String filename,
                int maxEnemyCount, long time) {
        this.playerPosition = playerPosition;
        this.activeEngimonPosition = activeEngimonPosition;
        this.map = new Cell[10][12];
        this.maxEnemyCount = maxEnemyCount;
        this.enemyEngimon = new ArrayList<Pair<Engimon, Position>>();
        this.time = time;
        // Load map from extern file
        try {
            File myFile = new File(filename);
            Scanner myReader = new Scanner(myFile);
            int i = 0;
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                char c;
                for (int j = 0; j < line.length(); j++){
                    c = line.charAt(j);
                    switch (c) {
                        case 'm':
                            this.map[i][j] = new Cell(CellType.MOUNTAINS, new Position(j, i));
                            break;
                        case 's' :
                            this.map[i][j] = new Cell(CellType.SEA, new Position(j, i));
                            break;
                        case 't' :
                            this.map[i][j] = new Cell(CellType.TUNDRA, new Position(j, i));
                            break;
                        case 'g' :
                            this.map[i][j] = new Cell(CellType.GRASSLAND, new Position(j, i));
                            break;
                        default :
                            System.out.println("An error occurred while loading file from external file.");
                    }
                }
                i += 1;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public Position getPlayerPosition() {
        return playerPosition;
    }

    public List<Cell> getSurroundingPlayers() {
        /*
            Mengembalikan list cell disekitar posisi player
         */
        ArrayList<Cell> cells = new ArrayList<>();
        int x = getPlayerPosition().getX();
        int y = getPlayerPosition().getY();
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                // Don't include the current position
                if (i != x && j != y) {
                    try {
                        isValidIdx(i, j);
                        cells.add(this.map[j][i]);
                    } catch (Exception e) {
                        // Do Nothing
                    }
                }
            }
        }
        return cells;
    }

    // mengembalikan satu wild engimon di sekitar player
    // apabila terdapat banyak wild engimon, dipilih satu secara random
    // apabila tidak ada wild engimon, mengembalikan null
    public Engimon getAdjacentEnemy() {
        Cell c = getAdjacentEnemyCell();
        if (c == null) return null;
        else return c.getEnemy();
    }

    public Cell getAdjacentEnemyCell() {
        List<Cell> surroundingCells = getSurroundingPlayers();
        Cell ret = null;
        Collections.shuffle(surroundingCells);
        for (Cell c : surroundingCells){
            if (c != null && c.getEnemy() != null){
                ret = c;
                break;
            }
        }
        return ret;
    }

    public Position getActiveEngimonPosition() {
        return activeEngimonPosition;
    }

    public long getTime() {
        return time;
    }

    public Cell getCell(int i, int j) {
        return map[j][i];
    }

    public ArrayList<Pair<Engimon, Position>> getEnemyEngimon() {
        return enemyEngimon;
    }

    private int getEnemyCount() {
        return enemyEngimon.size();
    }

    public void setActiveEngimonPosition(Position activeEngimonPosition) {
        this.activeEngimonPosition.setX(activeEngimonPosition.getX());
        this.activeEngimonPosition.setY(activeEngimonPosition.getY());
    }

    public void addEnemy(Pair<Engimon, Position> enemy) {
        /*
            Menambahkan enemy engimon pada list enemyEngimon
         */
        if (this.getEnemyCount() < maxEnemyCount && ((System.currentTimeMillis()-this.time) > 30000)) {
            enemyEngimon.add(enemy);
            getCell(enemy.getItem2().getX(),enemy.getItem2().getX()).setEnemy(enemy.getItem1());
            this.time = System.currentTimeMillis();
        }
    }

    public void removeEnemy(Pair<Engimon, Position> enemy) {
        /*
            Menghapus enemy engimon pada list enemyEngimon
         */
        enemyEngimon.remove(enemy);
        getCell(enemy.getItem2().getX(),enemy.getItem2().getX()).setEnemy(null);
    }

    public void moveEnemy() {
        /*
            Membuat enemy engimon bergerak. Saat engimon bergerak tidak dapat bergerak ke posisi
            yang tidak valid, posisi yang sudah ditempati Player, Active,Engimon dan enemyEngimon lain,
            serta type cell yang bukan habitatnya.
         */
        if ((System.currentTimeMillis()-this.time) > 45000) {
            for (Pair<Engimon, Position> enemy: enemyEngimon) {
                Random gen = new Random();
                int newx = gen.nextInt(1) - 1;
                int newy = gen.nextInt(1) - 1;
                boolean reroll = handleException(enemy.getItem1(),newx,newy);
                while (euclideanDistance(getPlayerPosition().getX(),getPlayerPosition().getY(),newx,newy) > 1 || reroll) {
                    newx = gen.nextInt(1) - 1;
                    newy = gen.nextInt(1) - 1;
                    reroll = handleException(enemy.getItem1(),newx,newy);
                }
                getCell(enemy.getItem2().getX(),enemy.getItem2().getX()).setEnemy(null);
                enemy.setItem2(new Position(newx,newy));
                getCell(enemy.getItem2().getX(),enemy.getItem2().getX()).setEnemy(enemy.getItem1());
            }
            this.time = System.currentTimeMillis();
        }
    }

    public void movePlayer(char key) throws Exception {
        /*
            Menggerakkan player berdasarkan key yang dimasukkan. Saat bergerak, player tidak dapat
            pergi ke posisi yang tidak valid.
         */
        int newx;
        int newy;
        switch (key) {
            case 'w':
                newy = this.getPlayerPosition().getY() - 1;
                newx = this.getPlayerPosition().getX();
                break;
            case 'a':
                newy = this.getPlayerPosition().getY();
                newx = this.getPlayerPosition().getX() - 1;
                break;
            case 's':
                newy = this.getPlayerPosition().getY() + 1;
                newx = this.getPlayerPosition().getX();
                break;
            case 'd':
                newy = this.getPlayerPosition().getY();
                newx = this.getPlayerPosition().getX() + 1;
                break;
            default:
                newx = -1;
                newy = -1;
        }
        try {
            isValidIdx(newx, newy);
            setActiveEngimonPosition(getPlayerPosition());
            getPlayerPosition().setX(newx);
            getPlayerPosition().setY(newy);
        } catch (PetaIdxOutOfBoundException e) {
            System.out.println("Anda menabrak dinding");
            e.printStackTrace();
        }
    }

    private double euclideanDistance(int x1, int y1, int x2, int y2) {
        /*
            Menghitung euclidean distance antara (x1, y1) dan (x2, y2)
         */
        return Math.sqrt(
                Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));

    }

    private void isValidIdx(int x, int y) throws Exception {
        /*
            Mengecek apakah x,y merupakan koordinat yang valid. Jika tidak valid,
            maka akan melempar Exception
         */
        if (x < 0 || x > 11 || y < 0 || y > 9) {
            throw new PetaIdxOutOfBoundException("Index bukan merupakan index yang valid.");
        }
    }

    private void isAvailableCell(int x, int y) throws Exception {
        /*
            Mengecek apakah x,y merupakan koordinat yang kosong / belum ditempati oleh
            Player, ActiveEngimon, dan EnemyEngimon
         */
        Position temp = new Position(x,y);
        boolean enemyLocation = false;
        for (Pair<Engimon, Position> entry: enemyEngimon) {
            enemyLocation = (temp == entry.getItem2());
        }
        if (temp == playerPosition || temp == activeEngimonPosition || enemyLocation) {
            throw new PetaCellNotEmptyException("_");
        }
    }

    private void isRightCellType(Engimon enemy, int x, int y) throws Exception {
        /*
            Mengecek apakah enemy engimon dapat menempati posisi pada x,y.
            Jika tidak maka akan melemparkan exception
         */
        CellType type = getCell(x,y).getType();
        switch (type) {
            case GRASSLAND:
                // ELECTRIC(1, "Electric"),
                // GROUND(3, "Ground"),
                if (!(enemy.getElements().get(0).type() == ElementType.ELECTRIC
                        || enemy.getElements().get(1).type() == ElementType.ELECTRIC
                        || enemy.getElements().get(0).type() == ElementType.GROUND
                        || enemy.getElements().get(0).type() == ElementType.GROUND)) {
                    throw new PetaCellNotHabitatException("+");
                }
                break;
            case MOUNTAINS:
                // FIRE(2, "Fire"),
                if (!(enemy.getElements().get(0).type() == ElementType.FIRE
                        || enemy.getElements().get(1).type() == ElementType.FIRE)) {
                    throw new PetaCellNotHabitatException("+");
                }
                break;
            case SEA:
                // WATER(5, "Water");
                if (!(enemy.getElements().get(0).type() == ElementType.WATER
                        || enemy.getElements().get(1).type() == ElementType.WATER)) {
                    throw new PetaCellNotHabitatException("+");
                }
                break;
            case TUNDRA:
                // ICE(4, "Ice"),
                if (!(enemy.getElements().get(0).type() == ElementType.ICE
                        || enemy.getElements().get(1).type() == ElementType.ICE)) {
                    throw new PetaCellNotHabitatException("+");
                }
                break;
            default:
                throw new PetaCellNotHabitatException("+");
        }
    }

    private Boolean handleException(Engimon enemy, int x, int y) {
        /*
            Mengembalikan false jika tidak terdapat exception yang terjadi
         */
        try {
            isValidIdx(x,y);
            isAvailableCell(x, y);
            isRightCellType(enemy, x, y);
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public Position spawnRandomPosition(Engimon enemy) {
        Random gen = new Random();
        int newx = gen.nextInt(12);
        int newy = gen.nextInt(10);
        boolean reroll = handleException(enemy,newx,newy);
        while (reroll) {
            newx = gen.nextInt(12);
            newy = gen.nextInt(10);
            reroll = handleException(enemy,newx,newy);
        }
        return new Position(newx,newy);
    }
}
