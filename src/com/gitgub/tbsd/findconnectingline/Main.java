package com.gitgub.tbsd.findconnectingline;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Data data = new Data();
        Controller controller = new Controller(data);
        int width = 800;
        int height = 600;
        View view = new View(width, height - 50, data, controller);
        // Draw a line that intersects as much lines as possible
        primaryStage.setTitle("Нарисовать линию, пересекающую больше всего других");
        primaryStage.setScene(new Scene(view, width, height));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
