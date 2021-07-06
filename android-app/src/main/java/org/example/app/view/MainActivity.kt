package org.example.app.view

import android.os.Bundle
import android.widget.EditText
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import dev.icerock.moko.mvvm.ViewModelFactory
import dev.icerock.moko.mvvm.asState
import dev.icerock.moko.mvvm.dispatcher.eventsDispatcherOnMain
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.example.app.AppComponent
import org.example.library.feature.main.presentation.MainViewModel
import org.example.library.feature.main.presentation.Worker

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
        WorkerData(
            it.successCounter.collectAsState(),
            it.failCounter.collectAsState(),
            it.lastTime.collectAsState()
        )
    }
    val error = viewModel.fatalError.collectAsState()
    Column() {
        Text(text = error.value)
        WorkerTable(
            pairs,
            { viewModel.onStartTap() },
            viewModel.isButtonEnabled.collectAsState(),
            viewModel.successCounters.collectAsState(""),
            viewModel.failCounter.collectAsState(""),
            viewModel.successPercentage.collectAsState(""),
            viewModel.workTime.collectAsState("")
        )
    }
}

data class WorkerData(
    val success: State<Int>,
    val fail: State<Int>,
    val time: State<Long>
)

@ExperimentalFoundationApi
@Composable
private fun WorkerTable(
    pairs: List<WorkerData>,
    startTap: () -> Unit,
    isEnabled: State<Boolean>,
    success: State<String>,
    fails: State<String>,
    percent: State<String>,
    time: State<String>
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(3),
        modifier = Modifier
            .paddingFromBaseline(top = 16.dp)
            .fillMaxWidth()
    ) {
        item {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.height(IntrinsicSize.Max)
            ) {
                StartButton({ startTap() }, isEnabled.value)
            }
        }
        item {
            Column {
                Text(text = "Успех", fontWeight = FontWeight.Light)
                Text(text = success.value, fontWeight = FontWeight.Bold)
                Text(text = "Неудача", fontWeight = FontWeight.Light)
                Text(text = fails.value, fontWeight = FontWeight.Bold)
            }
        }
        item {
            Column {
                Text(text = "Процент успеха", fontWeight = FontWeight.Light)
                Text(text = percent.value, fontWeight = FontWeight.Bold)
                Text(text = "среднее время расчета", fontWeight = FontWeight.Light)
                Text(text = time.value, fontWeight = FontWeight.Bold)

            }
        }
        pairs.forEach {
            this.item {
                PhotographerCard(it.fail.value, it.success.value, it.time.value)
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
@Preview
fun TablePreview() {
    val liveData = MutableStateFlow<Int>(0)
    val state = liveData.collectAsState()
    val longState = MutableStateFlow<Long>(0L).collectAsState()
    val textState = MutableStateFlow("").collectAsState()
    val booleanState = MutableStateFlow(false).collectAsState()
    BasicsCodelabTheme {
        WorkerTable(
            listOf(
                WorkerData(state, state, longState),
                WorkerData(state, state, longState),
                WorkerData(state, state, longState),
                WorkerData(state, state, longState),
                WorkerData(state, state, longState),
                WorkerData(state, state, longState),
                WorkerData(state, state, longState),
                WorkerData(state, state, longState),
                WorkerData(state, state, longState)
            ),
            {},
            booleanState,
            textState,
            textState,
            textState,
            textState
        )
    }
}

@Composable
fun PhotographerCard(failcounters: Int, counter: Int, time: Long) {
    val color = if ((failcounters + counter) == Worker.REPEAT_COUNT) Color.Green else Color.Yellow
    Row(
        Modifier
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
            Text("$time ms", style = MaterialTheme.typography.body1)
        }
    }
}

@Composable
private fun StartButton(onTap: () -> Unit, isEnabled: Boolean) {
    Button(
        onClick = onTap,
        modifier = Modifier
            .padding(8.dp),
        enabled = isEnabled
    ) {
        Text(
            text = "Start", fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
private fun ButtonPreview() {
    BasicsCodelabTheme {
        Column() {
            StartButton({ }, true)
            StartButton({ }, false)
        }
    }
}

@Preview
@Composable
fun PhotographerCardPreview() {
    BasicsCodelabTheme {
        PhotographerCard(1, 2, 3)
    }
}
