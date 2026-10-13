package org.example;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        int[][] map =  {
                {1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 1},
                {1, 0, 1, 1, 0, 0, 1},
                {1, 0, 0, 2, 0, 0, 1},
                {1, 0, 1, 1, 0, 0, 1},
                {1, 0, 0, 0, 0, 3, 1},
                {1, 1, 1, 1, 1, 1, 1}
        };
        GameMap gameMap = new GameMap(map);
        Player player = new Player("Knight", 1, 2);
        Game game = new Game(player, gameMap);
        game.start();

    }


}
