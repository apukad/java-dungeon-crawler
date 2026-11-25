package org.example;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class Game {
    private Player player;
    private GameMap gameMap;
    private Enemy skeleton;
    private boolean gameWon = false;
    private final List<GameCharacter> characters = new ArrayList<>();


    public Game(Player player, GameMap gameMap, Enemy skeleton) {
        this.player = player;
        this.gameMap = gameMap;
        this.skeleton = skeleton;
        characters.add(player);
        characters.add(skeleton);
    }

    public List<GameCharacter> getCharactersSortedByHealth() {
        List<GameCharacter> sorted = new ArrayList<>(characters);

        sorted.sort(
                Comparator.comparingInt(GameCharacter::getHealth).reversed()
        );

        return sorted;
    }

    public void start() {

        ConsoleRenderer renderer = new ConsoleRenderer();
        Scanner scanner = new Scanner(System.in);
        String direction = "";
        while (true) {
            renderer.printMap(player, gameMap, skeleton);
            System.out.println("CHARACTERS BY HP:");

            for (GameCharacter character : getCharactersSortedByHealth()) {
                System.out.println(
                        character.getName() + ": " + character.getHealth() + " HP"
                );
            }
            System.out.print("WASD - Move | F - Attack | E - Interact | Q - Quit | P - Save | L - load: ");
            direction = scanner.nextLine().toUpperCase();
            if (direction.equals("Q")) {
                System.out.println("You Exited the game");
                return;
            }
            if (direction.equals("P")) {
                saveGame();
                continue;
            }
            if (direction.equals("L")) {
                loadGame();
                if (gameWon|| !player.isAlive()) {
                    return;
                }
                continue;
            }

            processTurn(direction);

            if (gameWon) {
                return;
            }

            if (!player.isAlive()) {
                System.out.println("You have been killed by the skeleton!");
                return;
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
            dealDamage(skeleton, player);
            System.out.println("The " + skeleton.getName() + " attacked you!");
            System.out.println("You received damage: " + skeleton.getDamage());
            System.out.println("Remaining health: " + player.getHealth());
        }
    }

    private boolean playerAttack() {
        if (isEnemyAdjacent() && skeleton.isAlive()) {
            dealDamage(player, skeleton);
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
        } else if (gameMap.getTile(x, y - 1) == 3 && player.getHasKey()) {
            endGame();
            return true;
        }

        System.out.println("No key nearby!");
        return false;
    }

    ;

    private void endGame() {
        gameWon = true;
        System.out.println("Congratulations! You escaped the dungeon!");
    }

    private boolean handleAction(String command) {
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
    private void validateLoadedState(
            int playerX,
            int playerY,
            int playerHealth,
            int skeletonX,
            int skeletonY,
            int skeletonHealth
    ) throws InvalidSaveException {

        if (playerHealth < 0 || playerHealth > 100) {
            throw new InvalidSaveException("Invalid player health!");
        }

        if (skeletonHealth < 0 || skeletonHealth > 100) {
            throw new InvalidSaveException("Invalid skeleton health!");
        }

        if (!gameMap.isWalkable(playerX, playerY)) {
            throw new InvalidSaveException("Invalid player position!");
        }

        if (!gameMap.isWalkable(skeletonX, skeletonY)) {
            throw new InvalidSaveException("Invalid skeleton position!");
        }

        if (skeletonHealth > 0
                && playerX == skeletonX
                && playerY == skeletonY) {
            throw new InvalidSaveException("Characters overlap!");
        }
    }
    public void saveGame(){
        Properties save = new  Properties();
        save.setProperty("PlayerX", String.valueOf(player.getX()));
        save.setProperty("PlayerY", String.valueOf(player.getY()));
        save.setProperty("PlayerHealth", String.valueOf(player.getHealth()));
        save.setProperty("HasKey", String.valueOf(player.getHasKey()));
        save.setProperty("SkeletonHealth", String.valueOf(skeleton.getHealth()));
        save.setProperty("SkeletonX", String.valueOf(skeleton.getX()));
        save.setProperty("SkeletonY", String.valueOf(skeleton.getY()));
        save.setProperty("GameWon", String.valueOf(gameWon));
        try (FileOutputStream file = new FileOutputStream("save.properties")){
            save.store(file, "Dungeon Crawler Save");
            System.out.println("Dungeon Crawler Save has been saved!");
        }
        catch (IOException e){
            System.out.println("Failed to save: " + e.getMessage());
        }
    }
    public void loadGame(){
        Properties save = new   Properties();
        try (FileInputStream file = new FileInputStream("save.properties")){
            save.load(file);

        int playerX = Integer.parseInt(save.getProperty("PlayerX"));
        int playerY = Integer.parseInt(save.getProperty("PlayerY"));
        int playerHealth = Integer.parseInt(save.getProperty("PlayerHealth"));
        boolean haskey = readBoolean(save, "HasKey");
        int skeletonHealth = Integer.parseInt(save.getProperty("SkeletonHealth"));
        int skeletonX = Integer.parseInt(save.getProperty("SkeletonX"));
        int skeletonY = Integer.parseInt(save.getProperty("SkeletonY"));
        boolean savedGameWon = readBoolean(save, "GameWon");
        System.out.println("Loaded X: " + playerX);
        System.out.println("Loaded Y: " + playerY);
        System.out.println("Loaded Health: " + playerHealth);
        System.out.println("Loaded HasKey: " + haskey);
        System.out.println("Loaded SkeletonHealth: " + skeletonHealth);
        System.out.println("Loaded SkeletonX: " + skeletonX);
        System.out.println("Loaded SkeletonY: " + skeletonY);
        System.out.println("Loaded GameWon: " + savedGameWon);
        validateLoadedState(
                playerX, playerY, playerHealth,
                skeletonX, skeletonY, skeletonHealth
        );
        player.restoreState(playerHealth, playerX, playerY);
        skeleton.restoreState(skeletonHealth, skeletonX, skeletonY);
        player.restoreKeyState(haskey);
        gameMap.restoreKeyState(haskey);
        this.gameWon = savedGameWon;
        } catch (IOException e) {
            System.out.println("Failed to load game: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid save data: " + e.getMessage());
        } catch (InvalidSaveException e) {
            System.out.println("Invalid save: " + e.getMessage());
        }
    }

    private void dealDamage(GameCharacter attacker, GameCharacter target) {
        target.takeDamage(attacker.getAttackDamage());
    }

    private boolean readBoolean(Properties save, String key)
            throws InvalidSaveException {

        String value = save.getProperty(key);

        if (!"true".equalsIgnoreCase(value)
                && !"false".equalsIgnoreCase(value)) {
            throw new InvalidSaveException(
                    "Invalid boolean value for: " + key
            );
        }

        return Boolean.parseBoolean(value);
    }





}