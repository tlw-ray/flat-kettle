def b1 = new BarChart();
b1.addValues(3, 8, 2);
b1.addBars(
    new BarChart.Bar(7),
    new BarChart.Bar(3, "#000000").setTooltip("BLACK BAR<br>#top#"),
    (BarChart.Bar) null);
b1.addValues(5); 
b1.setTooltip("Blue bar<br>Value:#val#");

def b2 = new BarChart();
b2.addValues(4, 9, 8, 4, 1, 6, 3);
b2.setColour("FFEF3F");
b2.setTooltip("Yello bar<br>Value:#val#");

def c = new Chart(new Date().toString())
    .addElements(b1, b2)
    .setBackgroundColour("#FFFFFF");

return c;