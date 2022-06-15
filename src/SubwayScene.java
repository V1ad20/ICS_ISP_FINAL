import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class SubwayScene extends Scene {
    Player player;
    Cell[][] cellMap;
    static Group g = new Group();
    ActionHint actionHint = new ActionHint();
    Inventory inventory;
    Map map;

    public boolean started = false;
    public boolean finished = false;

    public SubwayScene() {
        super(g, 640, 640);

        cellMap = createMap(13, 45);
        CellArrayTextParser.parseAndApply("src/SubwayScene/SubwaySceneMap.txt",
                cellMap);

        player = new Player(9.0 / 2 * Cell.sideLength, 21.0 / 2 * Cell.sideLength, 64, 64, true, cellMap);
        player.applyTo(this);

        Image img = new Image("SubwayScene/SubwaySceneWithoutGrid.jpg");
        BackgroundImage background = new BackgroundImage(getWidth() / 2,
                getHeight() / 2,
                player, img);

        inventory = new Inventory(640, 640, 0, false);
        g.getChildren().addAll(background, player, actionHint);

        CharacterSpeechBox askToTrade = new CharacterSpeechBox("src/SubwayScene/Trade/AskToTrade.txt", true);
        TraderSpeechBox loafTrade = new TraderSpeechBox("src/SubwayScene/Trade/LoafTrade.txt", false, 1);
        TraderSpeechBox toyTrade = new TraderSpeechBox("src/SubwayScene/Trade/ToyTrade.txt", false, 2);
        TraderSpeechBox blanketTrade = new TraderSpeechBox("src/SubwayScene/Trade/BlanketTrade.txt", false, 3);

        g.getChildren().addAll(askToTrade, loafTrade, toyTrade, blanketTrade, inventory);

        CharacterSpeechBox gettingAttacked = new CharacterSpeechBox("src/SubwayScene/GettingAttacked.txt", false);

        map = new Map(getWidth(), getHeight(), 0, false);

        CharacterSpeechBox mapClueA = new CharacterSpeechBox("src/SubwayScene/Maps/MapClueA.txt", false);
        CharacterSpeechBox mapClueB = new CharacterSpeechBox("src/SubwayScene/Maps/MapClueB.txt", false);

        g.getChildren().addAll(gettingAttacked, map, mapClueA, mapClueB);

        CharacterSpeechBox timeToGo = new CharacterSpeechBox("src/SubwayScene/TimeToGo.txt", false);
        g.getChildren().add(timeToGo);

        addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.SPACE) {
                if (askToTrade.activated && !askToTrade.completed) {
                    askToTrade.timesSpacePressed++;
                }
                if (blanketTrade.activated && !blanketTrade.completed) {
                    blanketTrade.timesSpacePressed++;
                }
                if (loafTrade.activated && !loafTrade.completed) {
                    loafTrade.timesSpacePressed++;
                }
                if (toyTrade.activated && !toyTrade.completed) {
                    toyTrade.timesSpacePressed++;
                }
                if (gettingAttacked.activated && !gettingAttacked.completed) {
                    gettingAttacked.timesSpacePressed++;
                }
                if (mapClueA.activated && !mapClueA.completed) {
                    mapClueA.timesSpacePressed++;
                }
                if (mapClueB.activated && !mapClueB.completed) {
                    mapClueB.timesSpacePressed++;
                }
                if (timeToGo.activated && !timeToGo.completed) {
                    timeToGo.timesSpacePressed++;
                }
            }

            if (e.getCode() == KeyCode.ENTER) {
                switch (player.currentCell.type) {
                    case "BlanketTrade":
                        blanketTrade.activated = true;
                        break;
                    case "LoafTrade":
                        loafTrade.activated = true;
                        break;
                    case "ToyTrade":
                        toyTrade.activated = true;
                        break;
                    case "FamilyArea":
                        if (blanketTrade.completed && !gettingAttacked.activated) {
                            gettingAttacked.activated = true;
                        }
                        if ((mapClueA.completed && mapClueB.completed) && !timeToGo.activated) {
                            timeToGo.activated = true;
                        }
                        break;
                    case "MapClueA":
                        if (gettingAttacked.completed)
                            mapClueA.activated = true;
                        break;
                    case "MapClueB":
                        if (gettingAttacked.completed)
                            mapClueB.activated = true;
                        break;
                    case "StairTop":
                        if (timeToGo.completed)
                            finished = true;
                        break;
                }
            }

            if (e.getCode() == KeyCode.E) {
                if (inventory.canShow) {
                    inventory.setVisible(!inventory.isVisible());
                }
            }
            if (e.getCode() == KeyCode.Q) {
                if (map.canShow) {
                    map.setVisible(!map.isVisible());
                }
            }
        });

        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(long nanos) {
                if (started && !finished) {
                    int millis = (int) nanos / 1000000;

                    player.canMove = true;
                    inventory.canShow = true;
                    map.canShow = true;

                    if (askToTrade.activated && !askToTrade.completed) {
                        inventory.canShow = false;
                        player.canMove = false;
                        askToTrade.display(millis);
                    }

                    switch (player.currentCell.type) {
                        case "BlanketTrade":
                            handlePotentialTrade(blanketTrade, millis);
                            break;
                        case "LoafTrade":
                            handlePotentialTrade(loafTrade, millis);
                            break;
                        case "ToyTrade":
                            handlePotentialTrade(toyTrade, millis);
                            break;
                        case "FamilyArea":
                            if (mapClueA.completed && mapClueB.completed)
                                handleNormalSpeech(timeToGo, millis);
                            else if (blanketTrade.completed)
                                handleNormalSpeech(gettingAttacked, millis);
                            break;
                        case "MapClueA":
                            if (gettingAttacked.completed)
                                handleMapClue(mapClueA, millis, "MapClueA");
                            break;
                        case "MapClueB":
                            if (gettingAttacked.completed)
                                handleMapClue(mapClueB, millis, "MapClueB");
                            break;
                        default:
                            actionHint.setVisible(false);
                            break;
                    }

                    if (!gettingAttacked.completed || timeToGo.activated) {
                        map.canShow = false;
                    }

                    if (gettingAttacked.activated) {
                        inventory.canShow = false;
                        inventory.setVisible(false);
                    }

                    player.updateGamePosition();
                    player.updateCostume(millis);
                    player.updateScreenPosition();
                    background.updatePosition();
                }
            }
        };
        timer.start();
    }

    /**
     * @param rows
     * @param cols
     * @return Cell[][]
     */
    private Cell[][] createMap(int rows, int cols) {
        Cell[][] cellMap = new Cell[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                cellMap[row][col] = new Cell(row, col, "Default");
            }
        }
        return cellMap;
    }

    /**
     * @param b
     * @param millis
     */
    private void handlePotentialTrade(TraderSpeechBox b, int millis) {
        if (!b.activated) {
            actionHint.setVisible(true);
            actionHint.setText("Talk");
        } else {
            actionHint.setVisible(false);
            if (!b.completed) {
                player.canMove = false;
                inventory.canShow = false;
                b.display(millis, inventory.inventoryIndex);
            } else {
                if (b.goodNum > inventory.inventoryIndex) {
                    inventory.inventoryIndex++;
                    inventory.updateItem();
                }
            }
        }
    }

    /**
     * @param b
     * @param millis
     */
    private void handleNormalSpeech(CharacterSpeechBox b, int millis) {
        if (!b.activated) {
            actionHint.setVisible(true);
            actionHint.setText("Talk");
        } else {
            actionHint.setVisible(false);

            if (!b.completed) {
                map.canShow = false;
                player.canMove = false;
                b.display(millis);
            } else {
                map.canShow = true;
            }
        }
    }

    /**
     * @param b
     * @param millis
     * @param type
     */
    private void handleMapClue(CharacterSpeechBox b, int millis, String type) {
        if (!b.activated) {
            actionHint.setVisible(true);
            actionHint.setText("Talk");
        } else {
            if (!b.completed) {
                if (actionHint.isVisible()) {
                    if (map.mapIndex == 0) {
                        if (type.equals("MapClueA")) {
                            map.mapIndex = 1;
                        } else {
                            map.mapIndex = 2;
                        }
                    } else {
                        map.mapIndex = 3;
                    }
                    map.updateMap();
                }
                actionHint.setVisible(false);
                player.canMove = false;
                map.canShow = false;
                b.display(millis);
            }
        }
    }
}
