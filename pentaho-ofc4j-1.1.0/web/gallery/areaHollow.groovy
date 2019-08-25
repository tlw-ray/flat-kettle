List<Number> data = new ArrayList<Number>(30);
for (float i = 0; i < 6.2; i += 0.2) {
    data.add(new Float(Math.sin(i) * 1.9));
}
Chart c = new Chart("Area Chart")
    .addElements(new AreaHollowChart()
        .addValues(data)
        .setWidth(1));
XAxis xaxis = new XAxis();
xaxis.setSteps(2);
xaxis.getLabels().setSteps(4);
xaxis.getLabels().setRotation(Rotation.VERTICAL);

YAxis yaxis = new YAxis();
yaxis.setRange(-2, 2, 2);
yaxis.setOffset(false);

c.setYAxis(yaxis);
c.setXAxis(xaxis);
return c;
