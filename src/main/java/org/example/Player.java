package org.example;

public class Player extends GameCharacter {
    private boolean hasKey;

    public Player(String name, int x, int y) {
        super(name, 100, x, y);
        this.hasKey = false;
    }

    public boolean move(int dx, int dy, GameMap gameMap, Enemy skeleton){
        int newX = getX() + dx;
        int newY = getY()+ dy;
        boolean enemyBlocking = (newX == skeleton.getX()) && (newY == skeleton.getY()) && (skeleton.isAlive());
        if (gameMap.isWalkable(newX, newY) && !enemyBlocking){
            setPosition(newX, newY);
            return true;
        }
        return false;
    }

    public void pickUpKey(){
        hasKey = true;
    }
    public boolean getHasKey(){
        return hasKey;
    }

    public void restoreKeyState(boolean hasKey){
        this.hasKey = hasKey;
    }

    @Override
    public int getAttackDamage() {
        return 20;
    }



}
