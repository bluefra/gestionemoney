package gestionemoney.compose.dashboard
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class PieChartData(val data: List<Pair<String, Float>>)

@Composable
fun DynamicPieChart(pieChartData: PieChartData) {

    var data by remember { mutableStateOf(pieChartData) }

    Canvas(modifier = Modifier.fillMaxSize()) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val radius = minOf(centerX, centerY) * 0.8f

        var startAngle = 0f
        data.data.forEach { (_, value) ->
            val sweepAngle = 360 * (value / data.data.sumByDouble { it.second.toDouble() }).toFloat()
            drawArc(
                color = Color.Blue,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true,
                topLeft = Offset(centerX - radius, centerY - radius),
                size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2),
                style = Stroke(width = 4f)
            )
            startAngle += sweepAngle
        }
    }
}

@Composable
fun PieChartExample() {
    var pieChartData by remember { mutableStateOf(PieChartData(emptyList())) }

    Column {
        DynamicPieChart(pieChartData = pieChartData)
        Button(
            onClick = {
                val newData = pieChartData.copy(
                    data = pieChartData.data + ("New Label" to (1..10).random().toFloat())
                )
                pieChartData = newData
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Aggiungi Dato")
        }
    }
}

@Preview
@Composable
fun PieCharPreview(){
    PieChartExample()
}
