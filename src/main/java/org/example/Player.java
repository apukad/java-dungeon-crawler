package org.example;

public class Player {
    private String name;
    private int health;
    private int x;
    private int y;
    private boolean hasKey;
    public Player(String name,  int x, int y) {
        this.name = name;
        this.health = 100;
        this.x = x;
        this.y = y;
        this.hasKey = false;
    }

    public void move(int dx, int dy, GameMap gameMap){
        int newX = this.x + dx;
        int newY = this.y + dy;
        if (gameMap.isWalkable(newX, newY)){
            this.x = newX;
            this.y = newY;
        }
    }
    public String getName(){
        return name;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public void pickUpKey(){
        hasKey = true;
    }
    public boolean getHasKey(){
        return hasKey;
    }
}
