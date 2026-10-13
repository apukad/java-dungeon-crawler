package org.example;

public class ConsoleRenderer {

    public void printMap(Player player, GameMap gameMap, Enemy skeleton){
        int x = player.getX();
        int y = player.getY();
        int ex = skeleton.getX();
        int ey = skeleton.getY();

        for(int i = 0; i < gameMap.getHeight(); i++){
            for(int j = 0; j < gameMap.getWidth(i); j++){
                if (i==y && j==x){
                    System.out.print("P");
                }
                else if ((i==ey && j==ex) && skeleton.isAlive()){
                    System.out.print("S");
                }
                else if(gameMap.getTile(j,i) == 1 ) {
                    System.out.print("#");
                }
                else if(gameMap.getTile(j,i) == 2 ) {
                    System.out.print("K");
                }
                else if(gameMap.getTile(j,i) == 3 ) {
                    System.out.print("D");
                }
                else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }
}
