def c = new Chart("Our New House Schedule")
    .addElements(new HorizontalBarChart()
        .addBar(0, 4)
        .addBar(4, 8)
        .addBar(8, 11)
        .setColour("#9933CC")
        .setFontSize(10)
        .setText("Time"))
    .setXAxis(new XAxis()
        .setLabels("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"))
    .setYAxis(new YAxis()
        .setLabels("Make garden look sexy", "Paint house", "Move into house"));

c.getXAxis().setOffset(false);
c.getYAxis().setOffset(true);
return c;