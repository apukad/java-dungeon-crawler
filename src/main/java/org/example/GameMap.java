package org.example;

public class GameMap {
    private int[][] map;

    public GameMap(int[][] map){
        this.map = map ;

    }
    public  boolean isWalkable( int x, int y){
        if((y < 0) || (y >= map.length) || (x < 0) || (x >= map[y].length))
        {
            return false;
        }
        return map[y][x] == 0;
    }
    public int getHeight(){
        return map.length;
    }
    public int getWidth(int y){
        return map[y].length;
    }
    public int getTile(int x, int y){
        return map[y][x];
    }
    public void removeKey(int x, int y){
       if (map[y][x] == 2) {
        map[y][x] = 0;
       }
    }
    public void restoreKeyState(boolean hasKey){
        if (hasKey){
            removeKey(3,3);
        }
        else {
            map[3][3] = 2;
        }
    }

}
