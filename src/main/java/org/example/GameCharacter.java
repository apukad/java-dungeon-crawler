package org.example;


public abstract class GameCharacter {
    private String name;
    private int health;
    private int x;
    private int y;

    public GameCharacter(String name, int health, int x, int y) {
        this.name = name;
        this.health = health;
        this.x = x;
        this.y = y;
    }


    public void takeDamage(int damage){
        if(damage <= 0){
            return;
        }
        else if (damage > 0 && damage < health){
            this.health -= damage;
        } else if (damage >= health) {
            health = 0;
        }

    }
    public int getHealth(){
        return health;
    }
    public boolean isAlive(){
        return health > 0;
    }

    public String getName() {
        return name;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    protected void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }
    public void restoreState(int health, int x, int y) {
        this.health = Math.max(0, health);
        setPosition(x, y);
    }

    public abstract int getAttackDamage();

}
