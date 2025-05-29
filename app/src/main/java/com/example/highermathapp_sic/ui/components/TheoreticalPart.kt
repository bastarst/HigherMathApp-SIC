package com.example.highermathapp_sic.ui.components

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
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
import com.example.highermathapp_sic.data.TaskGroup

@Composable
fun TheoreticalPart(taskGroup: TaskGroup, fileName: String) {
    val context = LocalContext.current
    val assetManager = context.assets

    val path = when (taskGroup) {
        TaskGroup.LINEAR_ALGEBRA -> "linearalgebra/texts/$fileName"
        TaskGroup.CALCULUS -> TODO()
        TaskGroup.DIFF_EQ -> TODO()
    }

    val lines = produceState<List<String>>(initialValue = emptyList(), path) {
        value = try {
            assetManager.open(path).bufferedReader().readLines()
        } catch (e: Exception) {
            listOf("Ошибка чтения файла: ${e.message}")
        }
    }

    Column {
        for (line in lines.value) {
            if (line.startsWith("[image")) {
                val regex = Regex("""\[image\s*,\s*(\d+)\s*,\s*(\d+)\s*]\s*(.+)""")
                val sizeMatch = regex.find(line)!!

                val widthDp = sizeMatch.groupValues[1].toInt().dp
                val heightDp = sizeMatch.groupValues[2].toInt().dp
                val imageName = sizeMatch.groupValues[3].trim()

                val assetPath = when (taskGroup) {
                    TaskGroup.LINEAR_ALGEBRA -> "linearalgebra/images/$imageName"
                    TaskGroup.CALCULUS -> "TODO1"
                    TaskGroup.DIFF_EQ -> "TODO2"
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
            } else {
                val annotatedText = buildAnnotatedString {
                    var currentIndex = 0
                    val regex = Regex("""\*\*(.+?)\*\*""")
                    for (match in regex.findAll(line)) {
                        val start = match.range.first
                        val end = match.range.last + 1

                        append(line.substring(currentIndex, start))

                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(match.groupValues[1])
                        }

                        currentIndex = end + 1
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