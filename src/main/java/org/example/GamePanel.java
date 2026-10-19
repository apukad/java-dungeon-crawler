package org.example;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
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

    }
    private GameMap gameMap;

    private Player player;

    private Enemy enemy;

    public GamePanel(GameMap gameMap, Player player, Enemy enemy) {
        this.gameMap = gameMap;
        this.player = player;
        this.enemy = enemy;

        setupKeyBindings();
    }

    private void setupKeyBindings() {

        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("pressed W"), "moveUp");

        getActionMap().put("moveUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.move(0, -1, gameMap, enemy);
                repaint();
            }
        });

        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("pressed S"), "moveDown");
        getActionMap().put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.move(0, 1, gameMap, enemy);
                repaint();
            }
        });

        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("pressed A"), "moveLeft");
        getActionMap().put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.move(-1,0,gameMap,enemy);
                repaint();
            }
        });
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("pressed D"), "moveRight");
        getActionMap().put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.move(1,0,gameMap,enemy);
                repaint();
            }
        });
    }
}