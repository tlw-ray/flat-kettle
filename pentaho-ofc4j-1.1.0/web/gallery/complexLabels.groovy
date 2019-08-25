Chart c = new Chart("X Axis Labels Complex Example")
    .addElements(new LineChart(LineChart.Style.DOT)
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
