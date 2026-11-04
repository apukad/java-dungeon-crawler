package org.example;

import java.util.Scanner;

public class Game {
    private Player player;
    private GameMap gameMap;
    private Enemy skeleton;
    private boolean gameWon = false;

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

            int currentTile = gameMap.getTile(player.getX(), player.getY());
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
                if (!player.isAlive()) {
                    System.out.println("You have been killed by the skeleton!");
                    return;
                }
            }


        }
    }

    private boolean isEnemyAdjacent() {
        int distanceX = Math.abs(player.getX() - skeleton.getX());
        int distanceY = Math.abs(player.getY() - skeleton.getY());

        return distanceX + distanceY == 1;
    }
    public boolean isEnemyNearby(){
        return skeleton.isAlive() && isEnemyAdjacent();
    }

    private void enemyTurn() {
        if (skeleton.isAlive() && isEnemyAdjacent()) {
            player.takeDamage(skeleton.getDamage());
            System.out.println("The " + skeleton.getName() + " attacked you!");
            System.out.println("You received damage: " + skeleton.getDamage());
            System.out.println("Remaining health: " + player.getHealth());
        }
    }

    private boolean playerAttack() {
        if (isEnemyAdjacent() && skeleton.isAlive()) {
            skeleton.takeDamage(20);
            System.out.println("Skeleton remaining health: " + skeleton.getHealth());
            if (skeleton.getHealth() <= 0) {
                System.out.println("You killed the skeleton!");
            }
            return true;
        } else if (!skeleton.isAlive()) {
            System.out.println("Skeleton is dead");
            return false;
        } else {
            System.out.println("Skeleton too far away!");
            return false;
        }
    }

    private boolean movePlayer(int dx, int dy) {
        return player.move(dx, dy, gameMap, skeleton);
    }

    private boolean interact() {
        int x = player.getX();
        int y = player.getY();

        if (gameMap.getTile(x + 1, y) == 2) {
            player.pickUpKey();
            gameMap.removeKey(x + 1, y);
            System.out.println("You picked up the key!");
            return true;
        } else if (gameMap.getTile(x - 1, y) == 2) {
            player.pickUpKey();
            gameMap.removeKey(x - 1, y);
            System.out.println("You picked up the key!");
            return true;
        } else if (gameMap.getTile(x, y + 1) == 2) {
            player.pickUpKey();
            gameMap.removeKey(x, y + 1);
            System.out.println("You picked up the key!");
            return true;
        } else if (gameMap.getTile(x, y - 1) == 2) {
            player.pickUpKey();
            gameMap.removeKey(x, y - 1);
            System.out.println("You picked up the key!");
            return true;
        }
        if (gameMap.getTile(x + 1, y) == 3 && player.getHasKey()) {
            endGame();
            return true;
        } else if (gameMap.getTile(x - 1, y) == 3 && player.getHasKey()) {
            endGame();
            return true;
        } else if (gameMap.getTile(x, y + 1) == 3 && player.getHasKey()) {
            endGame();
            return true;
        } else if (gameMap.getTile(x, y + 1) == 3 && player.getHasKey()) {
            endGame();
            return true;
        }

        System.out.println("No key nearby!");
        return false;
    }

    ;

    public void endGame() {
        gameWon = true;
        System.out.println("Congratulations! You escaped the dungeon!");
    }

    public boolean handleAction(String command) {
        if (gameWon || !player.isAlive()) {
            return false;
        }
        switch (command) {
            case "W": {
                return movePlayer(0, -1);
            }
            case "S": {
                return movePlayer(0, 1);
            }
            case "D": {
                return movePlayer(1, 0);
            }
            case "A": {
                return movePlayer(-1, 0);
            }
            case "F": {
                return playerAttack();
            }
            case "E": {
                return interact();
            }
            default: {
                System.out.println("Invalid command");
                return false;
            }
        }

    }
    public boolean hasWon(){
        return gameWon;
    };
    public void processTurn(String command) {
        if (player.isAlive() && !gameWon) {
            boolean playerActed = handleAction(command);
            if (playerActed && !gameWon) {
                enemyTurn();
            }
        }
    }
}