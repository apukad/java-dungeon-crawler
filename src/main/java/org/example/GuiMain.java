package org.example;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
public class GuiMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->{
            JFrame window = new JFrame("Dungeon Crawler");
            int[][] map =  {
                    {1, 1, 1, 1, 1, 1, 1},
                    {1, 0, 0, 0, 0, 0, 1},
                    {1, 0, 1, 1, 0, 0, 1},
                    {1, 0, 0, 2, 0, 0, 1},
                    {1, 0, 1, 1, 0, 0, 1},
                    {1, 0, 0, 0, 0, 3, 1},
                    {1, 1, 1, 1, 1, 1, 1}
            };
            Player player = new Player("Knight", 1,2);

            Enemy enemy = new Enemy("Skeleton",100,15,5,1);

            window.setSize(800, 600);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            GameMap gameMap = new GameMap(map);
            GamePanel panel = new GamePanel(gameMap, player, enemy);
            window.add(panel);
            window.setLocationRelativeTo(null);
            window.setVisible(true);
        });
    }


}
