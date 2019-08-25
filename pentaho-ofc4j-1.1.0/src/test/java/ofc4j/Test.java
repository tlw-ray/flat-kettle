/*
This file is part of OFC4J.

OFC4J is free software: you can redistribute it and/or modify
it under the terms of the Lesser GNU General Public License as
published by the Free Software Foundation, either version 3 of
the License, or (at your option) any later version.

OFC4J is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

See <http://www.gnu.org/licenses/lgpl-3.0.txt>.
*/

package ofc4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ofc4j.model.Chart;
import ofc4j.model.axis.Label;
import ofc4j.model.axis.XAxis;
import ofc4j.model.axis.Label.Rotation;
import ofc4j.model.elements.LineChart;
import ofc4j.model.elements.LineChart.Style;

public class Test {
    private static final int INDENT = 2;
    public static void main(String... args) {
        System.out.println( 
            OFC.getInstance().prettyPrint(xaxisLabels3(), INDENT)
        );
    }
    
    public static Chart lineDemo() {
        return new Chart(new Date().toString())
        .addElements(new LineChart()
            .addValues(9,8,7,6,5,4,3,2,1));
    }
    
    public static Chart labels() {
        Chart c = new Chart("Label Test")
            .addElements(new LineChart()
                .addValues(9,8,7,6,5,4,3,2,1))
            .setXAxis(new XAxis()
                .addLabels("one", "two", "three")
                .addLabels(new Label("four")
                    .setRotation(Rotation.DIAGONAL)));
        
        //change default rotation to vertical
        c.getXAxis().getLabels().setRotation(Rotation.VERTICAL);
        return c;
    }
    
    public static Chart xaxisLabels3() {
        Chart c = new Chart("X Axis Labels Complex Example")
            .addElements(new LineChart(Style.DOT)
                .addValues(9, 8, 7, 6, 5, 4, 3, 2, 1))
            .setXAxis(new XAxis()
                .setTickHeight(5)
                .addLabels("one", "two", "three", "four", "five")
                .addLabels(
                    new Label("six")
                        .setColour("#0000FF")
                        .setSize(30)
                        .setRotation(Rotation.VERTICAL),
                    new Label("seven")
                        .setColour("#0000FF")
                        .setSize(30)
                        .setRotation(Rotation.VERTICAL),
                    new Label("eight")
                        .setColour("#8C773E")
                        .setSize(16)
                        .setRotation(Rotation.DIAGONAL)
                        .setVisible(true),
                    new Label("nine")
                        .setColour("#2683CF")
                        .setSize(16)
                        .setRotation(Rotation.HORIZONTAL)));
        
       c.getXAxis()
           .setStroke(1)
           .setColour("#428C3E")
           .setGridColour("#86BF83")
           .setSteps(1);
       
       c.getXAxis().getLabels()
           .setSteps(2)
           .setRotation(Rotation.VERTICAL)
           .setColour("#ff0000")
           .setSize(16);
       
       return c;
    }
    
    public static Chart customLabelChart() {
        return new Chart("Custom Label Test").setXAxis(new XAxis()
            .addLabels(
                new CustomLabel("one"),
                new CustomLabel("two")));
    }
    
    public static Chart withCollections() {
        List<Number> list1 = new ArrayList<Number>();
        list1.add(1);
        list1.add(2);
        list1.add(3);
        list1.add(4);
        list1.add(5.3f);
        return new Chart("Collection-1")
            .addElements(new LineChart()
                .addValues(OFC.toArray(list1, Number.class)));
    }
    
    private static class CustomLabel extends Label {
        public CustomLabel(String text) {
            setSize(30);
            setRotation(Rotation.DIAGONAL);
            setColour("#0000ff");
            setText(text);
        }
    }
}
