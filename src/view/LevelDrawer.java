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

    private String charDirection;

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

    public LevelDrawer() {
        loadImages();
    }

    private void loadImages() {
        try {
            String imagesPath = "./resources";
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

    void setCharDirection(String direction) {
        this.charDirection = direction.toLowerCase();
    }

    public Level getLevel()
    {
        return this.level;
    }

    public void setLevel(Level lvl) {
        if (lvl != null)
            this.level = lvl;
    }

    void draw() {
        double canvasWidth = this.getWidth();
        double canvasHeight = this.getHeight();

        // Used for calculating the dimensions of a cell in the board according to the board size.
        double cellWidth = canvasWidth / level.getNumberOfColumns();
        double cellHeight = canvasHeight / level.getNumberOfRows();

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
                        if(this.charDirection == null)
                            gc.drawImage(player, j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                        else switch(this.charDirection) {
                            case "right":
                                if (!right) {
                                    gc.drawImage(characterRight2, j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                                    right = true;
                                } else {
                                    gc.drawImage(characterRight1, j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                                    right = false;
                                }
                                break;

                            case "left":
                                if(left) {
                                    gc.drawImage(characterLeft2, j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                                    left = false;
                                } else {
                                    gc.drawImage(characterLeft1, j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                                    left = true;
                                }
                                break;

                            case "up":
                                if(up){
                                    up = false;
                                    gc.drawImage(characterUp2, j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                                } else {
                                    left = true;
                                    gc.drawImage(characterUp1, j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                                }
                                break;

                            case "down":
                                if(!down) {
                                    down = true;
                                    gc.drawImage(characterDown1, j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                                } else {
                                    gc.drawImage(characterDown2, j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                                    down = false;
                                }
                                break;
                        }
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

    void drawWinImage() {
        gc.drawImage(new Image(new File("./resources/win-pic.gif").toURI().toString()), 0, 0, getWidth(), getHeight());
    }
}
