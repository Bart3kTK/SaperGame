import java.util.ArrayList;

import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class GUIPane {
    private int mouseClicks = 0;
    private boolean firsClick = true;
    public GUIPane(Pane pane){
        ArrayList<MySquare> objList = new ArrayList<>();
        ArrayList<Integer> usedFields = new ArrayList<>(); //1 - in game 0 - out of game
        for (int i = 0; i<Settings.getRows() * Settings.getColumns(); i++)
            usedFields.add(1);

        for (int i = 0; i < Settings.getRows() * Settings.getColumns(); i++){
          MySquare sq = new MySquare();
          pane.getChildren().add(sq);
          objList.add(sq);
        }

        pane.setOnMouseClicked(event -> {
            for (MySquare r : objList){
                if (r.isClicked() == true){
                    FieldsGenerator.setActualField(r.getIndex());
                    //r.setIsClicked();
                    break;
                }
            }
            // for (MySquare r : objList){
                
                
            // }
            if (event.getButton() == MouseButton.PRIMARY){
                if (firsClick == true){
                    firsClick = false;
                    ArrayList<Integer> startFields = FieldsGenerator.generateStartFields(FieldsGenerator.getActualField());
                    FieldsGenerator.genBombAndNumbers(startFields);
                    for (MySquare r : objList){

                        if(startFields.contains(r.getIndex())){
                            r.setVisible(false);
                            usedFields.set(r.getIndex(), 0);
                        }
                    }
                }
                System.out.println(usedFields);
                System.out.println(FieldsGenerator.getGeneratedFields());
                for (MySquare r : objList){

                    if ( FieldsGenerator.getGeneratedFields().get(r.getIndex()) == -1) r.setFill(Color.BLACK);


                    if ( FieldsGenerator.getGeneratedFields().get(r.getIndex()) == 0){
                        for (int neighbor : FieldsGenerator.getNeighbors(r.getIndex())){
                            if (usedFields.get(neighbor) == 0) {
                                r.setVisible(false); 
                                usedFields.set(r.getIndex(), 0);
                            }
                        }
                    }

                    
                    if (r.isClicked()){
                        System.out.println("Moj index to " + r.getIndex() +" a tyle bomb jest obok: " + FieldsGenerator.getGeneratedFields().get(r.getIndex()));
                        r.setIsClicked();
                    }
            }
                
            }
        });

    }
    
}
