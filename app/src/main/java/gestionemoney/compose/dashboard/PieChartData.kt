package gestionemoney.compose.dashboard
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import gestionemoney.compose.R

@Composable
fun PieChart(data: Map<String, Float>) {
    val total = data.values.sum()
    val myText = stringResource(R.string.pieTitle) +"\n$total"
    val textMeasurer = rememberTextMeasurer()
    val textLayoutResult = textMeasurer.measure(text = AnnotatedString(myText))
    val textSize = textLayoutResult.size
    val colors = getColorList(data.size)
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Canvas(modifier = Modifier.size(200.dp)) {
            var startAngle = 0f
            var index = 0
            data.forEach { (name, value) ->
                val sweepAngle = 360 * (value / total)
                drawArc(
                    color = colors[index],
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(60f),
                )
                startAngle += sweepAngle
                index++
            }
            drawText(
                textMeasurer, myText,
                topLeft = Offset(
                    (this.size.width - textSize.width) / 2f,
                    (this.size.height - textSize.height) / 2f
                ),)
        }
    }
}

@Composable
fun PieLegend(data: Map<String, Float>) {
    val colors = getColorList(data.size)
    var index = 0
    Column {
        data.forEach { (name, value) ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(16.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = " ",
                        color = colors[index],
                        modifier = Modifier
                            .size(16.dp)
                            .background(colors[index])
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "$name: $value")
            }
            index++
        }
    }
}

@Composable
fun Pie(entry: Map<String, Float>){
    Spacer(modifier = Modifier.height(10.dp))
    PieChart(entry)
    Spacer(modifier = Modifier.height(16.dp))
    PieLegend(entry)
}
fun getColorList(number: Int): List<Color> {
    val colors: MutableList<Color> = mutableListOf()
    for(i in 0..<number) {
        colors.add(generateColor(i))
    }
    return colors
}
fun generateColor(index: Int): Color {
    val numColorsPerGroup = 2 // Numero di colori in ciascun gruppo (rosso, arancione, giallo, ecc.)
    val groupIndex = index / numColorsPerGroup
    val colorMajorSweep = 51 * 3
    val colorMinorSweep = 25 * 3
    val colorIndexInGroup = index % numColorsPerGroup
    val minorColor = colorMinorSweep * colorIndexInGroup
    val majorColor = colorMajorSweep * colorIndexInGroup
    // Determina il colore basato sull'indice fornito
    return when (groupIndex) {
        0 -> Color(255, majorColor, majorColor)
        1 -> Color(255, 128 + minorColor, majorColor)
        2 -> Color(255, 255, majorColor)
        3 -> Color(majorColor, 255, majorColor)
        4 -> Color(majorColor, 255, 255)
        5 -> Color(majorColor, majorColor, 255)
        6 -> Color(128 + minorColor, majorColor, 255)
        else -> Color(255,255,255)
    }
}
