package com.boi.engimonfactory;

import java.util.Scanner;

public class EngimonFactory2 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        // Bagian minta input setting
        Integer maxCap = scan.nextInt();
        Integer maxEngimonCount = scan.nextInt();

        Engidex E = new Engidex();

        // Intro
        System.out.print("What is your name: ");
        String name = scan.nextLine();

        Engimon first = E.spawnRandomEngimon();
        Engimon second = E.spawnRandomEngimon();
        Engimon third = E.spawnRandomEngimon();

        Player P = new Player(name, maxCap);
        Peta M = new Peta(new Position(5,8),new Position(5,9),"Peta.txt",maxEngimonCount);
        P.insertItem(first);
        P.insertItem(second);
        P.insertItem(third);

        boolean running = false;
        while (running) {
            String command = scan.nextLine();
            if (command == "w" || command == "a" || command == "s" || command == "d") {
                try {
                    M.movePlayer(command.charAt(0));
                    M.increaseTurn();
                    if (M.getTurn() % 5 == 0) {
                        Engimon wild = E.spawnRandomEngimon();
                        Position wildPosition = M.spawnRandomPosition(wild);
                        M.addEnemy(new Pair<>(wild, wildPosition));
                    }
                } catch (Exception e) {

                }
            }
            else if (command == "inventory") {
                // TODO
            }
            else if (command == "switch") {
                // TODO
            }
            else if (command == "interact") {
                // TODO
            }
            else if (command == "use") {
                // TODO
            }
            else if (command == "breed") {
                // TODO
            }
            else if (command == "detail") {
                // TODO
            }
            else if (command == "help") {
                // TODO
            }
            else if (command == "battle") {
                // TODO
            }
            else if (command == "save") {
                // TODO
            }
            else if (command == "exit") {
                // TODO
                // Konfirmasi Save / Not
                running = false;
            }
            else {
                System.out.println("Command not found");
            }
        }
        System.out.println("INI PESAN EXIT");
        scan.close();
    }
}
