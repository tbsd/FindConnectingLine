package com.gitgub.tbsd.findconnectingline;

import javafx.scene.shape.Line;

import java.util.List;

public class Controller {
    @FunctionalInterface
    private interface Function<One, Two, Return> {
        public Return eval(One one, Two two);
    }
    private Data data;

    Controller(Data data) {
        this.data = data;
    }

    public void findConnectingLine() {
        Line best = null;
        long bestCount = 0;
        List<Line> lines = data.getLines();
        if (lines.size() == 1)
            best =  new Line(lines.get(0).getStartX(), lines.get(0).getStartY(), 300, 300);
        else
//            for (int i = 0; i < lines.size() / 2 + 1; ++i) {
            for (int i = 0; i < lines.size(); ++i) {
                for (int j = i + 1; j < lines.size(); ++j) {
                    Line current = new Line(lines.get(i).getStartX(), lines.get(i).getStartY(),
                            lines.get(j).getStartX(), lines.get(j).getStartY());
                    long currentCount = howMuchIntersecions(current);
                    if (currentCount > bestCount) {
                        bestCount = currentCount;
                        best = current;
                    }
                }
                for (int j = i + 1; j < lines.size(); ++j) {
                    Line current = new Line(lines.get(i).getStartX(), lines.get(i).getStartY(),
                            lines.get(j).getEndX(), lines.get(j).getEndY());
                    long currentCount = howMuchIntersecions(current);
                    if (currentCount > bestCount) {
                        bestCount = currentCount;
                        best = current;
                    }
                }
                for (int j = i + 1; j < lines.size(); ++j) {
                    Line current = new Line(lines.get(i).getEndX(), lines.get(i).getEndY(),
                            lines.get(j).getStartX(), lines.get(j).getStartY());
                    long currentCount = howMuchIntersecions(current);
                    if (currentCount > bestCount) {
                        bestCount = currentCount;
                        best = current;
                    }
                }
                for (int j = i + 1; j < lines.size(); ++j) {
                    Line current = new Line(lines.get(i).getEndX(), lines.get(i).getEndY(),
                            lines.get(j).getEndX(), lines.get(j).getEndY());
                    long currentCount = howMuchIntersecions(current);
                    if (currentCount > bestCount) {
                        bestCount = currentCount;
                        best = current;
                    }
                }
            }

        data.setConnectingLine(best);
    }

    private boolean isIntersecting(Line l1, Line l2) {
        // (y2-y1)(x-x1)=(x2-x1)(y-y1)
        Function<Double, Double, Double> formula1 = (x, y) -> (l1.getEndY() - l1.getStartY())*(x - l1.getStartX()) -
                (l1.getEndX() - l1.getStartX())*(y - l1.getStartY());
        Function<Double, Double, Double> formula2 = (x, y) -> (l2.getEndY() - l2.getStartY())*(x - l2.getStartX()) -
                (l2.getEndX() - l2.getStartX())*(y - l2.getStartY());
        return (formula1.eval(l2.getStartX(), l2.getStartY())*formula1.eval(l2.getEndX(), l2.getEndY()) <= 0) &&
                (formula2.eval(l1.getStartX(), l1.getStartY())*formula2.eval(l1.getEndX(), l1.getEndY()) <= 0);
    }

    private long howMuchIntersecions(Line line) {
        List<Line> lines = data.getLines();
        return lines.stream().filter(l -> isIntersecting(line, l)).count();
    }
}
