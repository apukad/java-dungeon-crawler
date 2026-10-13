package org.example;

import java.util.Scanner;

public class Game {
    private Player player;
    private GameMap gameMap;
    private Enemy skeleton;

    public Game(Player player, GameMap gameMap, Enemy skeleton) {
        this.player = player;
        this.gameMap = gameMap;
        this.skeleton = skeleton;
    }
    public void start() {

        ConsoleRenderer renderer = new ConsoleRenderer();
        Scanner scanner = new Scanner(System.in);
        String direction = "";
        while (!direction.equals("Q")) {
            renderer.printMap(player, gameMap, skeleton);
            System.out.print("W - Up | A - Left | S - Down | D - Right | Q - Quit: ");
            direction = scanner.nextLine().toUpperCase();
            switch (direction) {
                case "W":{
                    player.move(0,-1, gameMap);

                    break;
                }
                case "S":{
                    player.move(0,1, gameMap);

                    break;
                }
                case "D": {
                    player.move(1,0, gameMap);
                    break;
                }
                case "A":{
                    player.move(-1,0, gameMap);
                    break;
                }
                case "Q": {
                    System.out.print("You Exited the game");
                    return;
                }
                default:{
                    System.out.println("Invalid move");
                }
            }
            int currentTile = gameMap.getTile(player.getX(),player.getY());
            if (currentTile == 2) {
                player.pickUpKey();
                gameMap.removeKey(player.getX(), player.getY());
            }
            if (currentTile == 3 && player.getHasKey()) {
                System.out.println("Congratulations!");
                return;
            }
            if (currentTile == 3 && !player.getHasKey()) {
                System.out.println("The door is locked! Find the key!");
            }


        }
    }


}
