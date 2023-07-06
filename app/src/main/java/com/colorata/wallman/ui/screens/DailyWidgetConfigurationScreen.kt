package com.colorata.wallman.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.colorata.wallman.R
import com.colorata.wallman.core.data.Polyglot
import com.colorata.wallman.core.data.rememberString
import com.colorata.wallman.ui.theme.spacing
import com.colorata.wallman.core.data.Strings
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyWidgetConfigurationScreen(@DrawableRes selected: Int?, onClick: (Int) -> Unit) {
    val selectedItem = remember(selected) {
        shapeConfigs.indexOfFirst { it.resId == selected }
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        modifier = Modifier.fillMaxSize()
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            LargeTopAppBar(title = {
                Text(text = rememberString(string = Strings.configureWidget))
            })
        }
        itemsIndexed(shapeConfigs) { index, it ->
            Column(
                Modifier
                    .clip(RoundedCornerShape(MaterialTheme.spacing.large))
                    .clickable {
                        onClick(it.resId)
                    }
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(
                    modifier = Modifier
                        .size(160.dp)
                        .aspectRatio(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = it.resId),
                        contentDescription = rememberString(
                            string = it.name
                        ),
                        modifier = Modifier
                            .size(160.dp)
                            .aspectRatio(1f),
                        tint = MaterialTheme.colorScheme.surfaceVariant
                    )
                    if (selectedItem == index) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = "Done",
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary)
                                .padding(MaterialTheme.spacing.small)
                                .align(
                                    Alignment.BottomEnd
                                ),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                Text(text = rememberString(string = it.name))
            }
        }
    }
}

private data class ShapeConfig(
    @DrawableRes val resId: Int,
    val name: Polyglot
)

private val shapeConfigs = persistentListOf(
    ShapeConfig(
        R.drawable.ic_clever,
        Strings.clever
    ),
    ShapeConfig(
        R.drawable.ic_flower,
        Strings.flower
    ),
    ShapeConfig(
        R.drawable.ic_scallop,
        Strings.scallop
    ),
    ShapeConfig(
        R.drawable.ic_circle,
        Strings.circle
    ),
    ShapeConfig(
        R.drawable.ic_square,
        Strings.square
    )
)