package com.gitgub.tbsd.findconnectingline;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Pair;

public class View extends VBox {
    private int canvasW;
    private int canvasH;
    private Data data;
    private Controller controller;
    private StackPane stackPane = new StackPane();
    private Canvas canvas = new Canvas();
    private Canvas layer = new Canvas();
    private Pair<Double, Double> initialTouch;

    public View(int canvasW, int canvasH, Data data, Controller controller) {
        this.canvasH = canvasH;
        this.canvasW = canvasW;
        this.data = data;
        this.controller = controller;
        createCanvas();
        Button clearBtn = new Button("Очистить"); // Clear
        clearBtn.setOnAction(e -> {
            clearCanvas();
            data.clear();
        });

        this.setAlignment(Pos.CENTER_RIGHT);
        this.setSpacing(5.0);
        this.setPadding(new Insets(5, 5, 5, 5));
        this.getChildren().addAll(stackPane, new Separator(), clearBtn);
    }

    private void createCanvas() {
        canvas = new Canvas(canvasW, canvasH);
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            Canvas newLayer = new Canvas(canvasW,canvasH);
            layer = newLayer;
            stackPane.getChildren().add(0,newLayer);
            initialTouch = new Pair<>(e.getSceneX(), e.getSceneY());
        });
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
            GraphicsContext gc = layer.getGraphicsContext2D();
            gc.setLineWidth(1);
            gc.setStroke(Color.BLACK);
            gc.clearRect(0, 0, layer.getWidth(), layer.getHeight());
            gc.strokeLine(initialTouch.getKey(), initialTouch.getValue(), e.getSceneX(), e.getSceneY());
        });
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
            data.addLine(initialTouch.getKey(), initialTouch.getValue(), e.getSceneX(), e.getSceneY());
            controller.findConnectingLine();
            drawConnectingLine();
        });
        stackPane.getChildren().add(canvas);
    }

    private void clearCanvas() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, layer.getWidth(), layer.getHeight());
        stackPane.getChildren().clear();
        stackPane.getChildren().add(canvas);
    }

    private void drawConnectingLine() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(1);
        gc.setStroke(Color.BLUE);
        gc.clearRect(0, 0, layer.getWidth(), layer.getHeight());
        Line cl = data.getConnectingLine();
        if (cl != null)
            gc.strokeLine(cl.getStartX(), cl.getStartY(), cl.getEndX(), cl.getEndY());
    }
}
