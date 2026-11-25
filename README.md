# Dungeon Crawler

This is my Java project for Programming 2. I made a small dungeon crawler game where the player needs to find a key and escape from the dungeon.

The game has a console version and a simple Swing GUI.

## How to play

You control a knight inside a dungeon. There is one skeleton enemy, a key and a locked door.

You need to pick up the key and reach the door to win the game. The skeleton can attack you when you are standing next to it. If your health reaches 0, you lose.

## Controls

- W, A, S, D - Movement
- F - Attack
- E - Interact
- P - Save game
- L - Load game
- Q - Quit

## Requirements

- Java 21 or newer
- Maven

## How to run

First build the project:

`mvn clean verify`

To start the console version:

`java -cp target/classes org.example.Main`

To start the GUI version:

`java -cp target/classes org.example.GuiMain`

## OOP

I used the main OOP concepts in the project.

**Encapsulation:** The class variables are private, and I use methods to access or change them.

**Inheritance:** Player and Enemy both extend GameCharacter.

**Abstraction:** GameCharacter is an abstract class. It contains methods that both character types use.

**Polymorphism:** Player and Enemy have their own getAttackDamage() methods. The game uses these methods when a character attacks.

**Collections:** I used an ArrayList to store the characters. I also made a method to sort them by their health.

**Exceptions:** I created InvalidSaveException to handle invalid save data.

## Design pattern

I used the Command pattern with Swing AbstractAction.

In GamePanel, keyboard buttons are connected to actions. These actions call methods in the Game class.

This way, the keyboard handling and the game logic are separated.

## Save and load

The game uses a file called save.properties.

It saves the player's position, health, key state, the skeleton's position and health, and whether the game has been won.

The saved data can be loaded after restarting the game.

## Testing

I used JUnit 5 to test some of the main game features, including movement, damage, collision, keys and sorting.

Tests can be run with:

`mvn test`

## Limitations

The game currently has only one map, one enemy and one key. The skeleton cannot move around the map.

## Documentation

The UML diagram and user stories are in the docs folder.

## Author

Bóvári Márk