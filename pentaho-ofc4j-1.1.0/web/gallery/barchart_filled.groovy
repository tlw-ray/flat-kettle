return new Chart(new Date().toString())
    .addElements(new FilledBarChart()
        .setOutlineColour("#577261")
        .setColour("#E2D66A")
        .addValues(9, 8, 7, 6, 5, 4, 3, 2, 1))
    .setBackgroundColour("#FFFFFF");