package org.example.app.view

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.app.view.theme.BasicsCodelabTheme
import androidx.compose.ui.text.font.FontWeight
import kotlin.math.cos
import kotlin.math.sin
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import dev.icerock.moko.mvvm.ViewModelFactory
import dev.icerock.moko.mvvm.dispatcher.eventsDispatcherOnMain
import org.example.app.AppComponent
import org.example.library.feature.main.presentation.MainViewModel

@ExperimentalFoundationApi
class MainActivity : AppCompatActivity(), MainViewModel.EventsListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val provider = ViewModelProvider(this, ViewModelFactory {
            AppComponent.factory.mainFactory.createMainViewModel(eventsDispatcherOnMain())
        })
        val viewModel = provider.get(MainViewModel::class.java)
        viewModel.eventsDispatcher.bind(this, this)
        setContent {
            screen(viewModel)
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun screen(viewModel: MainViewModel) {
    val scrollState = rememberScrollState()
    val pairs = viewModel.workerList.filterIndexed { index, worker -> index < 24 }.map {
        val failcounters = it.failCounter.collectAsState()
        val counters = it.successCounter.collectAsState()
        Pair(failcounters, counters)
    }
    LazyVerticalGrid(
        cells = GridCells.Fixed(3),
        modifier = Modifier
            .paddingFromBaseline(top = 16.dp)
            .fillMaxWidth()
    ) {
        pairs.forEach {
            this.item {
                PhotographerCard(it.first.value, it.second.value)
            }
        }
    }
}

enum class State {
    Pressed, UnPressed
}

@Composable
fun PhotographerCard(failcounters: Int, counter: Int) {
    val color = if ((failcounters + counter)  == 1000) Color.Green else Color.Yellow
    Row(
        Modifier
            .size(100.dp, 100.dp)
            .padding(8.dp)
            .clip(shape = CircleShape)
            .background(
                color = color
            )
            .padding(8.dp)
            .clip(shape = CircleShape)
            .background(
                color = Color.White
            )
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text(counter.toString(), fontWeight = FontWeight.Bold)
            Text(failcounters.toString(), style = MaterialTheme.typography.body2)
        }
    }
}

@Preview
@Composable
fun PhotographerCardPreview() {
    BasicsCodelabTheme {
        PhotographerCard(1, 2)
    }
}

@Preview
@Composable
fun screenPreview() {
    BasicsCodelabTheme {

    }
}