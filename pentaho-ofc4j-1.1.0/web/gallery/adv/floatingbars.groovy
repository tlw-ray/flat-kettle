def b = new BarChart()
    .addValues(130)
    .addBars(
        new BarChart.Bar(150, 130),
        new BarChart.Bar(170, 150),
        new BarChart.Bar(190, 170),
        new BarChart.Bar(200, 150)
            .setColour("#000000")
            .setTooltip("Hello<br>#top#"),
        (BarChart.Bar) null)
    .addValues(150);

def b2 = new FilledBarChart()
    .setColour("#FFEF3F")
    .setTooltip("top:#top#<br>bottom:#bottom#<br>Value:#val#")
    .addValues(140)
    .addBars(
        new FilledBarChart.Bar(155, 135),
        new FilledBarChart.Bar(175, 155),
        new FilledBarChart.Bar(195, 175),
        new FilledBarChart.Bar(190, 145, "#FF00FF", "#900090")
            .setTooltip("Custom tooltip<br>top:#top#<br>bottom:#bottom#<br>Value:#val#"))
    .addValues(160, 130);

def y = new YAxis(); 
y.setRange(100, 200, 10);

def c = new Chart(new Date().toString())
    .setYAxis(y)
    .addElements(b, b2)
    .setBackgroundColour("#FFFFFF");

return c;