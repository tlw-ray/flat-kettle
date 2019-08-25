return new Chart(new Date().toString(), "{color: #567300; font-size: 14px}")
    .addElements(new SketchBarChart("#81AC00", "#567300", 1)
        .addValues(9, 8, 7, 6)
        .addBars(new SketchBarChart.Bar(5, 8),
            new SketchBarChart.Bar(4).setOutlineColour("#000000"))
        .addValues(3, 2, 1)
    );