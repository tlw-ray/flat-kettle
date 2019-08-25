Chart c = new Chart("Pie Chart")
  .addElements(new PieChart()
    .setAnimate(true)
    .setStartAngle(35)
    .setBorder(2)
    .setAlpha(0.6f)
    .addValues(2, 3)
    .addSlice(6.5f, "hello (6.5)")
    .setColours("#d01f3c", "#356aa0", "#C79810")
    .setTooltip("#val# of #total#<br>#percent# of 100%"));
return c;