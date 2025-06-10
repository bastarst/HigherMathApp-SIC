package com.example.highermathapp_sic.ui.components

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.svg.SvgDecoder
import com.example.highermathapp_sic.model.TaskGroup

@Composable
fun TheoreticalPart(taskGroup: TaskGroup? = null, fileName: String) {
    val context = LocalContext.current
    val assetManager = context.assets

    val path = when (taskGroup) {
        TaskGroup.LINEAR_ALGEBRA -> "linearalgebra/texts/$fileName"
        TaskGroup.CALCULUS -> "calculus/texts/$fileName"
        null -> fileName
    }

    val lines = produceState<List<String>>(initialValue = emptyList(), path) {
        value = try {
            assetManager.open(path).bufferedReader().readLines()
        } catch (e: Exception) {
            listOf("Ошибка чтения файла: ${e.message}")
        }
    }

    Column {
        var i = 0
        while (i < lines.value.size) {
            val line = lines.value[i]

            when {
                line.startsWith("[image") -> {
                    val regex = Regex("""\[image\s*,\s*(\d+)\s*,\s*(\d+)\s*]\s*(.+)""")
                    val sizeMatch = regex.find(line)!!

                    val widthDp = sizeMatch.groupValues[1].toInt().dp
                    val heightDp = sizeMatch.groupValues[2].toInt().dp
                    val imageName = sizeMatch.groupValues[3].trim()

                    val assetPath = when (taskGroup) {
                        TaskGroup.LINEAR_ALGEBRA -> "linearalgebra/images/$imageName"
                        TaskGroup.CALCULUS -> "calculus/images/$imageName"
                        null -> fileName
                    }

                    if (assetPath.endsWith(".svg")) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            LoadSvgFromAssets(assetPath, widthDp, heightDp)
                        }
                    } else {
                        val bitmap = remember(assetPath) {
                            try {
                                assetManager.open(assetPath).use { input ->
                                    BitmapFactory.decodeStream(input)?.asImageBitmap()
                                }
                            } catch (e: Exception) {
                                null
                            }
                        }

                        if (bitmap != null) {
                            Image(
                                bitmap = bitmap,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        } else {
                            Text("Ошибка загрузки изображения: $assetPath")
                        }
                    }

                    i++
                }

                line.startsWith("[formula") -> {
                    val regex = Regex("""\[formula\s*,\s*(\w+)\s*]\s*(.*?)(?=(\[formula|\Z))""", RegexOption.IGNORE_CASE)
                    val matches = regex.findAll(line).toList()


                    if (matches.size > 1) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(
                                space = 8.dp,
                                alignment = Alignment.CenterHorizontally
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            matches.forEach { match ->
                                val type = match.groupValues[1]
                                val formula = match.groupValues[2]

                                MathFormulaView(type, formula)
                            }
                        }
                    } else {
                        val match = matches.firstOrNull()
                        if (match != null) {
                            val type = match.groupValues[1]
                            val formula = match.groupValues[2]

                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                MathFormulaView(type, formula)
                            }
                        } else {
                            Text("Ошибка")
                        }
                    }

                    i++
                }

                line.trim() == "[table]" -> {
                    val tableRows = mutableListOf<List<String>>()
                    i++
                    while (i < lines.value.size && lines.value[i].trim() != "[table]") {
                        val rowLine = lines.value[i].trim()
                        if (rowLine.startsWith("|") && rowLine.endsWith("|")) {
                            val cells = rowLine.trim('|').split("|").map { it.trim() }
                            tableRows.add(cells)
                        }
                        i++
                    }
                    i++

                    TableView(tableRows)
                }

                else -> {
                    val annotatedText = buildAnnotatedString {
                        var currentIndex = 0
                        val boldPattern = Regex("""\*\*(.+?)\*\*""")
                        val dollarPattern = Regex("""\$(.+?)\$""")

                        val matches = mutableListOf<MatchResult>()
                        matches.addAll(boldPattern.findAll(line))
                        matches.addAll(dollarPattern.findAll(line))

                        matches.sortBy { it.range.first }

                        for (match in matches) {
                            val start = match.range.first
                            val end = match.range.last + 1

                            if (currentIndex < start) {
                                append(line.substring(currentIndex, start))
                            }

                            val content = when {
                                boldPattern.matches(match.value) -> boldPattern.find(match.value)!!.groupValues[1]
                                dollarPattern.matches(match.value) -> dollarPattern.find(match.value)!!.groupValues[1]
                                else -> match.value
                            }

                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append(content)
                            }

                            currentIndex = end
                        }

                        if (currentIndex < line.length) {
                            append(line.substring(currentIndex))
                        }
                    }

                    Text(
                        text = annotatedText,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(8.dp)
                    )

                    i++
                }
            }
        }
    }
}

@Composable
fun LoadSvgFromAssets(assetPath: String, width: Dp, height: Dp) {
    val context = LocalContext.current

    val imageLoader = remember {
        ImageLoader.Builder(context)
            .components {
                add(SvgDecoder.Factory())
            }
            .build()
    }

    val assetUri = "file:///android_asset/$assetPath"

    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(assetUri)
            .build(),
        contentDescription = null,
        imageLoader = imageLoader,
        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
        modifier = Modifier
            .size(width, height)
    )
}

@Composable
fun MathFormulaView(
    type: String,
    formula: String,
    fontWeight: FontWeight = FontWeight.Bold
) {
    val parts = formula.split(",").map { it.trim() }
    val color = MaterialTheme.colorScheme.onBackground
    val horizontalDividerWidth = remember { mutableIntStateOf(0) }

    when (type) {
        "lim" -> {
            when (parts.size) {
                3 -> LimitView(parts[0], parts[1], parts[2], fontWeight = fontWeight)
                4 -> LimitView(parts[0], parts[1], parts[2], parts[3], fontWeight = fontWeight)
                else -> Text("Ошибка")
                }
        }

        "fraction" -> {
            val boxHeight = remember { mutableIntStateOf(0) }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (parts.size == 3) {
                    Text(
                        text = parts[0],
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = fontWeight
                    )

                    Text(
                        text = "=",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = fontWeight
                    )
                }

                Box(
                    modifier = Modifier
                        .drawBehind {
                            if(parts.size >= 4) {
                                drawLine(
                                    color = color,
                                    start = Offset(0f, 0f),
                                    end = Offset(16f, 0f),
                                    strokeWidth = 2.dp.toPx()
                                )

                                drawLine(
                                    color = color,
                                    start = Offset(0f, size.height),
                                    end = Offset(16f, size.height),
                                    strokeWidth = 2.dp.toPx()
                                )

                                drawLine(
                                    color = color,
                                    start = Offset(size.width, 0f),
                                    end = Offset(size.width - 16f, 0f),
                                    strokeWidth = 2.dp.toPx()
                                )

                                drawLine(
                                    color = color,
                                    start = Offset(size.width, size.height),
                                    end = Offset(size.width - 16f, size.height),
                                    strokeWidth = 2.dp.toPx()
                                )

                                drawLine(
                                    color = color,
                                    start = Offset(0f, 0f),
                                    end = Offset(0f, size.height),
                                    strokeWidth = 2.dp.toPx()
                                )

                                drawLine(
                                    color = color,
                                    start = Offset(size.width, 0f),
                                    end = Offset(size.width, size.height),
                                    strokeWidth = 2.dp.toPx()
                                )
                            }
                        }
                        .padding(4.dp)
                        .onGloballyPositioned { layoutCoordinate ->
                            boxHeight.intValue = layoutCoordinate.size.height
                        }
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = if (parts.size == 3) parts[1] else parts[0],
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = fontWeight,
                            modifier = Modifier.onSizeChanged {
                                horizontalDividerWidth.intValue =
                                    if (horizontalDividerWidth.intValue < it.width)
                                        it.width
                                    else horizontalDividerWidth.intValue
                            }
                        )

                        HorizontalDivider(
                            modifier = Modifier
                                .width(with(LocalDensity.current) { horizontalDividerWidth.intValue.toDp() }),
                            thickness = 2.dp,
                            color = color
                        )

                        Text(
                            text = if (parts.size == 3) parts[2] else parts[1],
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = fontWeight,
                            modifier = Modifier.onSizeChanged {
                                horizontalDividerWidth.intValue =
                                    if (horizontalDividerWidth.intValue < it.width)
                                        it.width
                                    else horizontalDividerWidth.intValue
                            }
                        )
                    }
                }

                if(parts.size >= 4) {
                    Column(
                        modifier = Modifier.height(boxHeight.intValue.dp / 2),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = parts[2],
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = fontWeight
                        )

                        Text(
                            text = parts[3],
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = fontWeight
                        )
                    }
                }
            }
        }

        "integral" -> {
            when (parts.size) {
                1 -> IntegralView(parts[0], fontWeight = fontWeight)
                3 -> IntegralView(parts[0], parts[1], parts[2], fontWeight = fontWeight)
                else -> Text("Ошибка")
            }
        }

        "other" -> Text(
            text = formula,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = fontWeight
        )

        else -> Text("Ошибка")
    }
}

@Composable
fun TableView(rows: List<List<String>>) {
    val color = MaterialTheme.colorScheme.onBackground

    Column(
        modifier = Modifier
            .padding(8.dp)
            .border(1.dp, color)
    ) {
        rows.forEach { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .border(0.5.dp, color)
            ) {
                row.forEach { cell ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .border(0.5.dp, color)
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if(cell.startsWith("[formula")) {
                            val regex = Regex("""\[formula\s*,\s*(\w+)\s*]\s*(.*?)(?=(\[formula|\Z))""", RegexOption.IGNORE_CASE)
                            val matches = regex.findAll(cell).toList()

                            if (matches.size > 1) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(
                                        space = 8.dp,
                                        alignment = Alignment.CenterHorizontally
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    matches.forEach { match ->
                                        val type = match.groupValues[1]
                                        val formula = match.groupValues[2]

                                        MathFormulaView(type, formula, FontWeight.Normal)
                                    }
                                }
                            } else {
                                val match = matches.firstOrNull()
                                if (match != null) {
                                    val type = match.groupValues[1]
                                    val formula = match.groupValues[2]

                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        MathFormulaView(type, formula, FontWeight.Normal)
                                    }
                                } else {
                                    Text("Ошибка")
                                }
                            }
                        } else {
                            Text(
                                text = cell,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Normal
                            )
                        }
                    }
                }
            }
        }
    }
}