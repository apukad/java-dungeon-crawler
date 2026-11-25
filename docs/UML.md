
# UML Class Diagram

This diagram shows the main classes of my Dungeon Crawler game.

```mermaid
classDiagram
    class GameCharacter {
        <<abstract>>
        -String name
        -int health
        -int x
        -int y
        +takeDamage(int damage) void
        +getHealth() int
        +isAlive() boolean
        +getAttackDamage() int
        +restoreState(int health, int x, int y) void
    }

    class Player {
        -boolean hasKey
        +move(int dx, int dy, GameMap map, Enemy enemy) boolean
        +pickUpKey() void
        +getAttackDamage() int
    }

    class Enemy {
        -int damage
        +getDamage() int
        +getAttackDamage() int
    }

    class Game {
        -Player player
        -Enemy skeleton
        -GameMap gameMap
        -boolean gameWon
        -List~GameCharacter~ characters
        +start() void
        +processTurn(String command) void
        +saveGame() void
        +loadGame() void
    }

    class GameMap {
        -int[][] map
        +isWalkable(int x, int y) boolean
        +getTile(int x, int y) int
        +removeKey(int x, int y) void
    }

    class GamePanel {
        -Game game
        -Player player
        -Enemy enemy
        -GameMap gameMap
        -setupKeyBindings() void
    }

    class ConsoleRenderer
    class Main
    class GuiMain
    class InvalidSaveException

    GameCharacter <|-- Player
    GameCharacter <|-- Enemy
    Exception <|-- InvalidSaveException

    Game "1" --> "1" Player
    Game "1" --> "1" Enemy
    Game "1" --> "1" GameMap
    Game "1" o-- "2" GameCharacter : characters

    GamePanel --> Game
    GamePanel --> GameMap
    GamePanel --> Player
    GamePanel --> Enemy
    Game ..> InvalidSaveException

    Main ..> Game
    GuiMain ..> GamePanel
    ConsoleRenderer ..> GameMap
```

## Design pattern

I used the Command pattern with Swing AbstractAction.

In GamePanel, the keyboard keys are connected to actions.
These actions call methods in the Game class.

This keeps keyboard input separate from the game logic.
