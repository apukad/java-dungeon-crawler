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
            if (direction.equals("Q")) {
                System.out.println("You Exited the game");
                return;
            }
            boolean playerActed = handleAction(direction);

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
    private boolean playerAttack(){
        if (isEnemyAdjacent() && skeleton.isAlive()) {
            skeleton.takeDamage(20);
            System.out.println("Skeleton remaining health: "  + skeleton.getHealth());
            if (skeleton.getHealth() <= 0) {
                System.out.println("You killed the skeleton!");
            }
            return true;
        }
        else if (!skeleton.isAlive()) {
            System.out.println("Skeleton is dead");
            return false;
        }
        else {
            System.out.println("Skeleton too far away!");
            return false;
        }
    }
    private boolean movePlayer(int dx, int dy){
        return player.move(dx,dy, gameMap,skeleton);
    }
    private boolean handleAction(String command){
        switch (command) {
            case "W":{
                return movePlayer(0,-1);

            }
            case "S":{
                return movePlayer(0,1);

            }
            case "D": {
                return movePlayer(1,0);

            }
            case "A":{
                return movePlayer(-1,0);
            }
            case "F": {
                return playerAttack();
            }
            default: {
                System.out.println("Invalid command");
                return false;
            }
        }

    }
}
