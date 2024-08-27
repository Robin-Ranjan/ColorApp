package com.task.janitritask

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.task.janitritask.ui.theme.syncBackground
import com.task.janitritask.ui.theme.toolbarColor

@Preview(showSystemUi = true)
@Composable
fun ColorScreen(viewModel: ColorViewModal = viewModel()) {
    val colors = viewModel.colors.observeAsState(emptyList())
    val unsyncedColors = viewModel.unsyncedColors.observeAsState(emptyList())
    val unsyncedCount by viewModel.unsyncedColorsCount.observeAsState(0)
    Scaffold(
        topBar = {
            ColorAppToolbar(
                unsyncedCount = unsyncedCount,
                onSyncClick = { viewModel.syncColors() }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.addRandomColor() }) {
                Icon(Icons.Default.Add, contentDescription = "Add Color")
            }
        },
        content = { paddingValues ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(
                    start = 8.dp,
                    top = paddingValues.calculateTopPadding() + 8.dp,
                    end = 8.dp,
                    bottom = paddingValues.calculateBottomPadding() + 8.dp
                ),
            ) {
                items(colors.value) { colorData ->
                    ColorCard(colorData.colorCode,colorData.date)
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorAppToolbar(unsyncedCount: Int, onSyncClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = "Color App", color = Color.White)
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = toolbarColor
        ),
        actions = {
            val height = 40.dp

            Box(
                modifier = Modifier
                    .background(toolbarColor)
                    .clip(RoundedCornerShape(3000.dp, 3000.dp, 3000.dp, 3000.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .height(height)
            )
            {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .background(syncBackground)
                        .padding(5.dp)
                ) {
                    Text(
                        text = unsyncedCount.toString(),
                        color = Color.White,
                        modifier = Modifier.padding(end = 1.dp)
                    )
                    IconButton(onClick = { onSyncClick() }) {
                        val syncIcon: Painter = painterResource(id = R.drawable.sync)
                        Icon(
                            painter = syncIcon,
                            contentDescription = "Sync",
                            modifier = Modifier.padding(end = 5.dp)
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun ColorCard(colorCode: String, date: String) {
    val cardColor = try {
        Color(android.graphics.Color.parseColor(colorCode))
    } catch (e: IllegalArgumentException) {
        Color.Gray
    }

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(20)),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Box(
            modifier = Modifier
                .background(cardColor)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Column {
                Text(
                    text = colorCode,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                        textDecoration = TextDecoration.Underline
                    ),
                    modifier = Modifier
                        .padding(bottom = 15.dp)
                )
                Spacer(modifier = Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()

                ) {
                    Text(
                        text = "Created At",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 14.sp
                        ),
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(bottom = 20.dp)
                    )

                    Text(
                        text = date,
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 14.sp
                        ),
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                    )
                }
            }
        }
    }
}