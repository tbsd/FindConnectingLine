package com.gitgub.tbsd.findconnectingline;


import javafx.scene.shape.Line;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Data {
    private LinkedList<Line> lines = new LinkedList<>();
    private Line connectingLine = null;

    public Line getConnectingLine() {
        return connectingLine;
    }

    public void setConnectingLine(Line connectingLine) {
        this.connectingLine = connectingLine;
    }

    public List<Line> getLines() {
        return Collections.unmodifiableList(lines);
    }

    public void addLine(Line line) {
        lines.add(line);
    }

    public void addLine(double x1, double y1, double x2, double y2) {
        lines.add(new Line(x1, y1, x2, y2));
    }

    public void clear() {
        connectingLine = null;
        lines.clear();
    }
}
