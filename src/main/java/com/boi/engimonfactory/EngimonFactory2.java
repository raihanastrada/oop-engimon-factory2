package com.boi.engimonfactory;

public class EngimonFactory2 {

    public static void main(String[] args) {

        System.out.println("FUCK YOU AND YOUR FRIENDS");
        System.out.println("FUCK YOU AND THE GUI");

//        new Renderer().run();


        // Window window = new Window(new InventoryUI());

        /*
        // try-catch block to handle exceptions
        try {

            // Create a file object
            File f = new File("program.txt");

            // Get the absolute path of file f
            String absolute = f.getAbsolutePath();

            // Display the file path of the file object
            // and also the file path of absolute file
            System.out.println("Original  path: "
                    + f.getPath());
            System.out.println("Absolute  path: "
                    + absolute);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        */

        Game g = new Game();
        g.init();
        g.addRandomEngimonPlayer();
        g.addRandomEngimonPlayer();
        g.addRandomEngimonPlayer();
        g.addRandomEngimonPlayer();
        g.addRandomEngimonPlayer();
        g.addRandomEngimonPlayer();
        g.addRandomEngimonPlayer();
        g.addRandomEngimonPlayer();
        g.getPlayer().printInventoryEngimon();
        g.getPlayer().switchActive(0);
        System.out.println(g.getPlayer().getInvCount());

        g.save();


        // Game g;
        // g = Game.load();

        g.getPlayer().printInventoryEngimon();
        UI a = new UI();
        a.insertPlayer(g.getPlayer());
        Window window = new Window(a);
        window.init();
        window.run();
        window.destroy();
    }
}