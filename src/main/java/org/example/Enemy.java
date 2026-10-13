package org.example;

public class Enemy extends GameCharacter {
    private int damage;
    public Enemy(String name, int health,int damage, int x, int y) {
        super(name, health, x, y);
        this.damage = damage;
    }
}

