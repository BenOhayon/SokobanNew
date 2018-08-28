package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model.entities.Level;

public class LevelDrawer extends Canvas
{
    private Level level;
    private GraphicsContext gc;

    private String imagesPath = "./resources";

    // flags used for displaying the character's moving animation.
    private boolean up = false;
    private boolean down = false;
    private boolean left = false;
    private boolean right = false;

    // images for the level objects
    private Image wall = null, box = null, target = null, player = null, floor = null;

    // Images for actual displaying the character's drawing.
    private Image characterUp1 = null, characterUp2 = null, characterDown1 = null, characterDown2 = null, characterLeft1 = null, characterLeft2 = null,
            characterRight1 = null, characterRight2 = null;

    // Used for calculating the dimensions of a cell in the board according to the board size.
    private double cellWidth;
    private double cellHeight;

    public LevelDrawer() {
        loadImages();
    }

    private void loadImages() {
        try {
            wall = new Image(new FileInputStream(imagesPath + "/wall3.png"));
            box = new Image(new FileInputStream(imagesPath + "/box4.png"));
            target = new Image(new FileInputStream(imagesPath + "/anchor4.png"));
            player = new Image(new FileInputStream(imagesPath + "/Character4.png"));
            floor = new Image(new FileInputStream( imagesPath + "/GroundGravel_Concrete.png"));

            characterUp1 = new Image(new File(imagesPath + "/Character8.png").toURI().toString());
            characterUp2 = new Image(new File(imagesPath + "/Character9.png").toURI().toString());
            characterDown1 = new Image(new File(imagesPath + "/Character6.png").toURI().toString());
            characterDown2 = new Image(new File(imagesPath + "/Character5.png").toURI().toString());
            characterLeft1 = new Image(new File(imagesPath + "/Character1.png").toURI().toString());
            characterLeft2 = new Image(new File(imagesPath + "/Character10.png").toURI().toString());
            characterRight1 = new Image(new File(imagesPath + "/Character2.png").toURI().toString());
            characterRight2 = new Image(new File(imagesPath + "/Character3.png").toURI().toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public Level getLevel()
    {
        return this.level;
    }

    public void setLevel(Level lvl) {
        if (lvl != null)
            this.level = lvl;
    }

    public void draw() {
        double canvasWidth = this.getWidth();
        double canvasHeight = this.getHeight();

        cellWidth = canvasWidth / level.getNumberOfColumns();
        cellHeight = canvasHeight / level.getNumberOfRows();

        gc = getGraphicsContext2D();

        for (int i = 0; i < level.getBoard().length; i++) {
            for (int j = 0; j < level.getBoard()[i].length; j++) {

                char item = level.getItemAt(i, j);
                gc.clearRect(j * cellWidth, i * cellHeight, cellWidth, cellHeight);

                switch (item) {
                    case '#':
                        gc.drawImage(wall, j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                        break;

                    case '@':
                        gc.drawImage(box, j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                        break;

                    case 'A':
                        gc.drawImage(player, j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                        break;

                    case 'X':
                        gc.drawImage(target, j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                        break;

                    case ' ':
                        gc.drawImage(floor, j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                        break;
                }
            }
        }
    }

    public void drawCharacter(String direction) {

        gc.clearRect(level.getCharacterPosition().getY() * cellWidth, level.getCharacterPosition().getX() * cellHeight, cellWidth, cellHeight);

        switch(direction) {
            case "up":
                gc.drawImage(characterUp1, level.getCharacterPosition().getY() * cellWidth, level.getCharacterPosition().getX() * cellHeight, cellWidth, cellHeight);
                break;

            case "down":
                gc.drawImage(characterDown1, level.getCharacterPosition().getY() * cellWidth, level.getCharacterPosition().getX() * cellHeight, cellWidth, cellHeight);
                break;

            case "left":
                gc.drawImage(characterLeft1, level.getCharacterPosition().getY() * cellWidth, level.getCharacterPosition().getX() * cellHeight, cellWidth, cellHeight);
                break;

            case "right":
                gc.drawImage(characterRight1, level.getCharacterPosition().getY() * cellWidth, level.getCharacterPosition().getX() * cellHeight, cellWidth, cellHeight);
                break;
        }
    }

//    private void drawPlayerAnimation(Image charImage1, Image charImage2, String direction) {
//        if(imageFlag)
//            gc.drawImage(charImage1, level.getCharacterPosition().getY() * cellWidth, level.getCharacterPosition().getX() * cellHeight, cellWidth, cellHeight);
//        else
//            gc.drawImage(charImage2, level.getCharacterPosition().getY() * cellWidth, level.getCharacterPosition().getX() * cellHeight, cellWidth, cellHeight);
//    }

    public void drawWinImage() {
        gc.drawImage(new Image(new File("./Resources/win-pic3.jpg").toURI().toString()), 0, 0, getWidth(), getHeight());
    }

    private void drawCharacterUp(){

    }

//    private void drawCharacterDown(){
//
//    }
//
//    private void drawCharacterLeft(){
//        if(left) {
//            gc.drawImage(characterLeft1, level.getCharacterPosition().getY() * cellWidth, level.getCharacterPosition().getX() * cellHeight, cellWidth, cellHeight);
//            left = false;
//        }
//        else {
//            gc.drawImage(characterLeft2, level.getCharacterPosition().getY() * cellWidth, level.getCharacterPosition().getX() * cellHeight, cellWidth, cellHeight);
//            left = true;
//        }
//    }
//
//    private void drawCharacterRight(){
//        if(right) {
//            gc.drawImage(characterRight1, level.getCharacterPosition().getY() * cellWidth, level.getCharacterPosition().getX() * cellHeight, cellWidth, cellHeight);
//            right = false;
//        }
//        else {
//            gc.drawImage(characterRight2, level.getCharacterPosition().getY() * cellWidth, level.getCharacterPosition().getX() * cellHeight, cellWidth, cellHeight);
//            right = true;
//        }
//    }
}
