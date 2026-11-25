
package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DungeonCrawlerTest {

    @Test
    public void testPlayerDamage() {
        Player player = new Player("Knight", 1, 1);

        player.takeDamage(30);
        assertEquals(70, player.getHealth());

        player.takeDamage(100);
        assertEquals(0, player.getHealth());
        assertFalse(player.isAlive());
    }

    @Test
    public void testPlayerAttackDamage() {
        Player player = new Player("Knight", 1, 1);
        Enemy skeleton = new Enemy("Skeleton", 50, 10, 5, 1);

        assertEquals(20, player.getAttackDamage());
        assertEquals(10, skeleton.getAttackDamage());
    }

    @Test
    public void testPlayerMovement() {
        int[][] map = {
                {1,1,1,1,1},
                {1,0,0,0,1},
                {1,0,0,0,1},
                {1,1,1,1,1}
        };

        GameMap gameMap = new GameMap(map);
        Player player = new Player("Knight", 1, 1);
        Enemy skeleton = new Enemy("Skeleton", 50, 10, 3, 2);

        boolean moved = player.move(1, 0, gameMap, skeleton);

        assertTrue(moved);
        assertEquals(2, player.getX());

        moved = player.move(0, -1, gameMap, skeleton);

        assertFalse(moved);
        assertEquals(1, player.getY());
    }

    @Test
    public void testEnemyCollision() {
        int[][] map = {
                {1,1,1,1},
                {1,0,0,1},
                {1,1,1,1}
        };

        GameMap gameMap = new GameMap(map);
        Player player = new Player("Knight", 1, 1);
        Enemy skeleton = new Enemy("Skeleton", 50, 10, 2, 1);

        assertFalse(player.move(1, 0, gameMap, skeleton));

        skeleton.takeDamage(50);

        assertTrue(player.move(1, 0, gameMap, skeleton));
    }

    @Test
    public void testKey() {
        Player player = new Player("Knight", 1, 1);

        assertFalse(player.getHasKey());

        player.pickUpKey();

        assertTrue(player.getHasKey());

        player.restoreKeyState(false);

        assertFalse(player.getHasKey());
    }

    @Test
    public void testCharacterSorting() {
        int[][] map = {
                {1,1,1,1},
                {1,0,0,1},
                {1,1,1,1}
        };

        GameMap gameMap = new GameMap(map);
        Player player = new Player("Knight", 1, 1);
        Enemy skeleton = new Enemy("Skeleton", 50, 10, 2, 1);

        Game game = new Game(player, gameMap, skeleton);

        player.takeDamage(80);

        assertEquals("Skeleton",
                game.getCharactersSortedByHealth().get(0).getName());

        assertEquals("Knight",
                game.getCharactersSortedByHealth().get(1).getName());
    }
}
