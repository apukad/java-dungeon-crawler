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
            System.out.print("W - Up | A - Left | S - Down | D - Right | F - Attack | Q - Quit: ");
            direction = scanner.nextLine().toUpperCase();
            boolean playerActed = false;
            switch (direction) {
                case "W":{
                    playerActed = player.move(0,-1, gameMap, skeleton);
                    break;
                }
                case "S":{
                    playerActed = player.move(0,1, gameMap, skeleton);

                    break;
                }
                case "D": {
                    playerActed = player.move(1,0, gameMap, skeleton);
                    break;
                }
                case "A":{
                    playerActed = player.move(-1,0, gameMap,skeleton);
                    break;
                }
                case "Q": {
                    System.out.print("You Exited the game");
                    return;
                }
                case "F": {
                    if (isEnemyAdjacent() && skeleton.isAlive()) {
                        skeleton.takeDamage(20);
                        System.out.println("Skeleton remaining health: "  + skeleton.getHealth());
                        if (skeleton.getHealth() <= 0) {
                            System.out.println("You killed the skeleton!");
                        }
                        playerActed = true;
                    }
                    else if (!skeleton.isAlive()) {
                        System.out.println("Skeleton is dead");
                    }
                    else {
                        System.out.println("Skeleton too far away!");
                    }
                    break;
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
            if (playerActed) {
                enemyTurn();
                if(!player.isAlive()) {
                    System.out.println("You have been killed by the skeleton!");
                    return;
                }
            }


        }
    }
    private boolean isEnemyAdjacent(){
        int distanceX = Math.abs(player.getX() - skeleton.getX());
        int distanceY = Math.abs(player.getY() - skeleton.getY());

        return distanceX + distanceY == 1;
    }
    private void enemyTurn(){
        if (skeleton.isAlive() && isEnemyAdjacent()) {
            player.takeDamage(skeleton.getDamage());
            System.out.println("The " + skeleton.getName() + " attacked you!");
            System.out.println("You received damage: " + skeleton.getDamage());
            System.out.println("Remaining health: " + player.getHealth());
        }
    }
}
