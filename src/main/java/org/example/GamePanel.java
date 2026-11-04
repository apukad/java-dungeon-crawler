package org.example;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int y = 0; y < gameMap.getHeight(); y++) {
            for (int x = 0; x < gameMap.getWidth(y); x++) {
                int tile = gameMap.getTile(x, y);
                if (tile == 0){
                    g.setColor(Color.DARK_GRAY);
                }
                else if (tile == 1){
                    g.setColor(Color.GRAY);
                }
                else if (tile == 2){
                    g.setColor(Color.BLUE);
                }
                else if (tile == 3){
                    g.setColor(Color.YELLOW);
                }

                g.fillRect(x * 64, y * 64, 64, 64);
            }
        }
        if (player.isAlive()){
            g.setColor(Color.GREEN);
            g.fillRect(player.getX() * 64 + 8, player.getY() * 64 + 8, 48, 48
            );
        }
        if (enemy.isAlive()){
            g.setColor(Color.RED);
            g.fillRect(enemy.getX() * 64 + 8, enemy.getY() * 64 + 8, 48, 48
            );
        }

        int uiX = 448;
        int barWidth = 200;
        int currentWidth = barWidth * player.getHealth() / 100;
        int currentEnemyWidth = barWidth * enemy.getHealth() / 100;
        g.setColor(Color.darkGray);
        g.fillRect(uiX, 0, getWidth()-uiX, getHeight());

        g.setColor(Color.white);
        g.drawString("CONTROLS: ",  uiX + 20, 225);
        g.drawString("WASD - MOVE", uiX + 20, 250);
        g.drawString("E - INTERACT", uiX + 20, 275);
        g.drawString("F - ATTACK",  uiX + 20, 300);
        g.drawString("Q - QUIT",  uiX + 20, 325);
        g.drawString("PLAYER", uiX + 20, 40);
        g.drawString("HP "+ player.getHealth(), uiX + 20, 65);
        g.setColor(Color.GRAY);
        g.fillRect(uiX+20, 80, 200, 15);
        g.setColor(Color.GREEN);
        g.fillRect(uiX + 20, 80, currentWidth, 15);
        if (game.isEnemyNearby()){
            g.setColor(Color.white);
            g.drawString("ENEMY", uiX + 20, 125);
            g.drawString("HP "+ enemy.getHealth(), uiX + 20, 150);
            g.setColor(Color.GRAY);
            g.fillRect(uiX+20, 165, 200, 15);
            g.setColor(Color.RED);
            g.fillRect(uiX + 20, 165, currentEnemyWidth, 15);
        }
        if (game.hasWon()){
            g.setColor(new Color(0, 0, 0, 220));
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            String message = "YOU WIN!";
            int textWidth = g.getFontMetrics().stringWidth(message);
            int x = (getWidth() - textWidth) / 2;

            g.drawString(message, x, 250);
        }
        else if (!player.isAlive()){
            g.setColor(new Color(0, 0, 0, 220));
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            String message = "YOU LOSE!";
            int textWidth = g.getFontMetrics().stringWidth(message);
            int x = (getWidth() - textWidth) / 2;

            g.drawString(message, x, 250);
        }
    }
    private GameMap gameMap;

    private Player player;

    private Enemy enemy;

    private Game game;

    public GamePanel(GameMap gameMap, Player player, Enemy enemy,Game game) {
        this.gameMap = gameMap;
        this.player = player;
        this.enemy = enemy;
        this.game = game;

        setupKeyBindings();
    }

    private void setupKeyBindings() {

        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("pressed W"), "moveUp");

        getActionMap().put("moveUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.processTurn("W");
                repaint();
            }
        });

        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("pressed S"), "moveDown");
        getActionMap().put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.processTurn("S");
                repaint();
            }
        });

        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("pressed A"), "moveLeft");
        getActionMap().put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.processTurn("A");
                repaint();
            }
        });
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("pressed D"), "moveRight");
        getActionMap().put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.processTurn("D");
                repaint();
            }
        });
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("pressed F"),"attack");
        getActionMap().put("attack", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.processTurn("F");
                repaint();
            }
        });
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("pressed E"),"interact");
        getActionMap().put("interact", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.processTurn("E");
                repaint();
            }
        });

    }
}