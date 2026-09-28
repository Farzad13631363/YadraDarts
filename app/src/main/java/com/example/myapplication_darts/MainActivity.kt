package com.example.myapplication_darts

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.myapplication_darts.game.Dart
import com.example.myapplication_darts.game.InMode
import com.example.myapplication_darts.game.OutMode
import com.example.myapplication_darts.game.X01Engine


private val BackgroundColor = Color(0xFF050505)
private val GoldColor = Color(0xFFFFD700)
private val DarkButtonColor = Color(0xFF151515)
private val GreenColor = Color(0xFF1B5E20)
private val RedColor = Color(0xFFB71C1C)
private val WhiteColor = Color.White
private val GrayColor = Color(0xFFAAAAAA)


/*
==========================================================
MAIN ACTIVITY
==========================================================
*/

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            MaterialTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BackgroundColor
                ) {

                    YadraDartsApp()
                }
            }
        }
    }
}


/*
==========================================================
APP
==========================================================
*/

@Composable
fun YadraDartsApp() {

    var screen by remember {
        mutableStateOf("intro")
    }

    when (screen) {

        "intro" -> {

            IntroScreen {

                screen = "home"
            }
        }

        "home" -> {

            HomeScreen(
                onGames = {
                    screen = "games"
                },

                onPlayers = {
                    screen = "players"
                },

                onStatistics = {
                    screen = "statistics"
                },

                onHistory = {
                    screen = "history"
                },

                onTournament = {
                    screen = "tournament"
                },

                onSettings = {
                    screen = "settings"
                }
            )
        }

        "games" -> {

            GamesScreen(
                onBack = {
                    screen = "home"
                },

                onX01 = {
                    screen = "x01_setup"
                },

                onCricket = {
                    screen = "cricket"
                },

                onAroundClock = {
                    screen = "around_clock"
                },

                onPractice = {
                    screen = "practice"
                }
            )
        }

        "x01_setup" -> {

            X01SetupScreen(
                onBack = {
                    screen = "games"
                },

                onStart = {
                        score,
                        players,
                        inMode,
                        outMode,
                        names
                    ->

                    X01GameHolder.start(
                        score = score,
                        players = players,
                        inMode = inMode,
                        outMode = outMode,
                        names = names
                    )

                    screen = "x01_game"
                }
            )
        }

        "x01_game" -> {

            X01GameScreen(
                onBack = {
                    screen = "x01_setup"
                }
            )
        }

        "cricket" -> {

            PlaceholderScreen(
                title = "CRICKET",
                onBack = {
                    screen = "games"
                }
            )
        }

        "around_clock" -> {

            PlaceholderScreen(
                title = "AROUND THE CLOCK",
                onBack = {
                    screen = "games"
                }
            )
        }

        "practice" -> {

            PlaceholderScreen(
                title = "PRACTICE",
                onBack = {
                    screen = "games"
                }
            )
        }

        "players" -> {

            PlaceholderScreen(
                title = "PLAYERS",
                onBack = {
                    screen = "home"
                }
            )
        }

        "statistics" -> {

            PlaceholderScreen(
                title = "STATISTICS",
                onBack = {
                    screen = "home"
                }
            )
        }

        "history" -> {

            PlaceholderScreen(
                title = "HISTORY",
                onBack = {
                    screen = "home"
                }
            )
        }

        "tournament" -> {

            PlaceholderScreen(
                title = "TOURNAMENT",
                onBack = {
                    screen = "home"
                }
            )
        }

        "settings" -> {

            PlaceholderScreen(
                title = "SETTINGS",
                onBack = {
                    screen = "home"
                }
            )
        }
    }
}


/*
==========================================================
INTRO
==========================================================
*/

@Composable
fun IntroScreen(
    onEnter: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(20.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = "YADRA",
            color = GoldColor,
            fontSize = 46.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Text(
            text = "DARTS",
            color = WhiteColor,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Image(
            painter = painterResource(
                id = R.drawable.yadra_intro
            ),

            contentDescription = "YADRA DARTS",

            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(
                    horizontal = 10.dp,
                    vertical = 20.dp
                ),

            contentScale = ContentScale.Fit
        )

        Button(
            onClick = onEnter,

            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = GoldColor,
                contentColor = Color.Black
            ),

            shape = RoundedCornerShape(14.dp)
        ) {

            Text(
                text = "ENTER",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }

        Spacer(
            modifier = Modifier.height(10.dp)
        )
    }
}


/*
==========================================================
HOME
==========================================================
*/

@Composable
fun HomeScreen(
    onGames: () -> Unit,
    onPlayers: () -> Unit,
    onStatistics: () -> Unit,
    onHistory: () -> Unit,
    onTournament: () -> Unit,
    onSettings: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(20.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "DART",
            color = GoldColor,
            fontSize = 42.sp,
            fontWeight = FontWeight.ExtraBold,

            modifier = Modifier.padding(
                top = 20.dp,
                bottom = 35.dp
            )
        )

        MenuButton(
            text = "GAMES",
            onClick = onGames
        )

        MenuButton(
            text = "PLAYERS",
            onClick = onPlayers
        )

        MenuButton(
            text = "STATISTICS",
            onClick = onStatistics
        )

        MenuButton(
            text = "HISTORY",
            onClick = onHistory
        )

        MenuButton(
            text = "TOURNAMENT",
            onClick = onTournament
        )

        MenuButton(
            text = "SETTINGS",
            onClick = onSettings
        )
    }
}


@Composable
fun MenuButton(
    text: String,
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,

        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .height(55.dp),

        colors = ButtonDefaults.buttonColors(
            containerColor = DarkButtonColor,
            contentColor = WhiteColor
        ),

        shape = RoundedCornerShape(12.dp)
    ) {

        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


/*
==========================================================
GAMES
==========================================================
*/

@Composable
fun GamesScreen(
    onBack: () -> Unit,
    onX01: () -> Unit,
    onCricket: () -> Unit,
    onAroundClock: () -> Unit,
    onPractice: () -> Unit
) {

    AppPage(
        title = "GAMES",
        onBack = onBack
    ) {

        GameButton(
            title = "X01",
            subtitle = "301 • 501 • 701 • 901 • 1001",
            onClick = onX01
        )

        GameButton(
            title = "CRICKET",
            subtitle = "Standard • Cut-Throat • Team",
            onClick = onCricket
        )

        GameButton(
            title = "AROUND THE CLOCK",
            subtitle = "1 → 20 → Bull",
            onClick = onAroundClock
        )

        GameButton(
            title = "PRACTICE",
            subtitle = "Scoring • Doubles • Trebles • Checkout",
            onClick = onPractice
        )
    }
}


@Composable
fun GameButton(
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,

        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .height(75.dp),

        colors = ButtonDefaults.buttonColors(
            containerColor = DarkButtonColor
        ),

        shape = RoundedCornerShape(12.dp)
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = title,
                color = GoldColor,
                fontSize = 19.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Text(
                text = subtitle,
                color = GrayColor,
                fontSize = 13.sp
            )
        }
    }
}


/*
==========================================================
X01 HOLDER
==========================================================
*/

object X01GameHolder {

    var score: Int = 501

    var players: Int = 2

    var inMode: InMode =
        InMode.STRAIGHT

    var outMode: OutMode =
        OutMode.DOUBLE

    var names: List<String> =
        listOf(
            "Player 1",
            "Player 2"
        )

    fun start(
        score: Int,
        players: Int,
        inMode: InMode,
        outMode: OutMode,
        names: List<String>
    ) {

        this.score = score

        this.players = players

        this.inMode = inMode

        this.outMode = outMode

        this.names = names
    }
}


/*
==========================================================
X01 SETUP
==========================================================
*/

@Composable
fun X01SetupScreen(
    onBack: () -> Unit,

    onStart: (
        Int,
        Int,
        InMode,
        OutMode,
        List<String>
    ) -> Unit
) {

    var startingScore by remember {
        mutableIntStateOf(501)
    }

    var playerCount by remember {
        mutableIntStateOf(2)
    }

    var inMode by remember {
        mutableStateOf(InMode.STRAIGHT)
    }

    var outMode by remember {
        mutableStateOf(OutMode.DOUBLE)
    }

    var names by remember {

        mutableStateOf(
            List(8) { index ->
                "Player ${index + 1}"
            }
        )
    }

    AppPage(
        title = "X01 SETUP",
        onBack = onBack
    ) {

        Text(
            text = "STARTING SCORE",
            color = GoldColor,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement =
                Arrangement.SpaceEvenly
        ) {

            listOf(
                301,
                501,
                701,
                901,
                1001
            ).forEach { value ->

                SmallChoiceButton(
                    text = value.toString(),
                    selected =
                        startingScore == value
                ) {

                    startingScore = value
                }
            }
        }

        Spacer(
            modifier = Modifier.height(15.dp)
        )

        Text(
            text = "PLAYERS",
            color = GoldColor,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement =
                Arrangement.SpaceEvenly
        ) {

            (1..8).forEach { count ->

                SmallChoiceButton(
                    text = count.toString(),
                    selected =
                        playerCount == count
                ) {

                    playerCount = count
                }
            }
        }

        Spacer(
            modifier = Modifier.height(15.dp)
        )

        Text(
            text = "IN",
            color = GoldColor,
            fontWeight = FontWeight.Bold
        )

        ModeSelector(
            selected = inMode,

            values = InMode.entries,

            label = { mode ->

                when (mode) {

                    InMode.STRAIGHT ->
                        "STRAIGHT"

                    InMode.DOUBLE ->
                        "DOUBLE"

                    InMode.MASTER ->
                        "MASTER"
                }
            },

            onSelect = {
                inMode = it
            }
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Text(
            text = "OUT",
            color = GoldColor,
            fontWeight = FontWeight.Bold
        )

        ModeSelector(
            selected = outMode,

            values = OutMode.entries,

            label = { mode ->

                when (mode) {

                    OutMode.STRAIGHT ->
                        "STRAIGHT"

                    OutMode.DOUBLE ->
                        "DOUBLE"

                    OutMode.MASTER ->
                        "MASTER"
                }
            },

            onSelect = {
                outMode = it
            }
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Text(
            text = "PLAYER NAMES",
            color = GoldColor,
            fontWeight = FontWeight.Bold
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {

            items(playerCount) { index ->

                TextField(
                    value = names[index],

                    onValueChange = { newName ->

                        val updated =
                            names.toMutableList()

                        updated[index] =
                            newName

                        names = updated
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = 3.dp
                        ),

                    singleLine = true,

                    label = {

                        Text(
                            text =
                                "Player ${index + 1}"
                        )
                    }
                )
            }
        }

        Button(
            onClick = {

                onStart(
                    startingScore,
                    playerCount,
                    inMode,
                    outMode,
                    names.take(playerCount)
                )
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = GoldColor,
                contentColor = Color.Black
            )
        ) {

            Text(
                text = "START GAME",
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}


/*
==========================================================
MODE SELECTOR
==========================================================
*/

@Composable
fun <T> ModeSelector(
    selected: T,
    values: List<T>,
    label: (T) -> String,
    onSelect: (T) -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement =
            Arrangement.SpaceEvenly
    ) {

        values.forEach { value ->

            SmallChoiceButton(
                text = label(value),
                selected = selected == value
            ) {

                onSelect(value)
            }
        }
    }
}


@Composable
fun SmallChoiceButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,

        modifier = Modifier.padding(2.dp),

        colors = ButtonDefaults.buttonColors(

            containerColor =
                if (selected) {
                    GoldColor
                } else {
                    DarkButtonColor
                },

            contentColor =
                if (selected) {
                    Color.Black
                } else {
                    WhiteColor
                }
        )
    ) {

        Text(
            text = text,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


/*
==========================================================
X01 GAME
==========================================================
*/

@Composable
fun X01GameScreen(
    onBack: () -> Unit
) {

    val startingScore =
        X01GameHolder.score

    val playerCount =
        X01GameHolder.players

    val inMode =
        X01GameHolder.inMode

    val outMode =
        X01GameHolder.outMode

    val playerNames =
        X01GameHolder.names

    val engines = remember {

        List(playerCount) {

            X01Engine(
                startingScore = startingScore,
                inMode = inMode,
                outMode = outMode
            )
        }
    }

    var currentPlayer by remember {
        mutableIntStateOf(0)
    }

    var winner by remember {
        mutableIntStateOf(-1)
    }

    var selectedDarts by remember {
        mutableStateOf<List<Dart>>(
            emptyList()
        )
    }

    var message by remember {
        mutableStateOf("")
    }

    var dartMultiplier by remember {
        mutableIntStateOf(1)
    }

    var showManualScore by remember {
        mutableStateOf(false)
    }

    var showCheckout by remember {
        mutableStateOf(false)
    }

    val currentEngine =
        engines[currentPlayer]

    val scores =
        engines.map {
            it.score
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(10.dp)
    ) {

        /*
        HEADER
        */

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            TextButton(
                onClick = onBack
            ) {

                Text(
                    text = "BACK",
                    color = GoldColor
                )
            }

            Text(
                text = "$startingScore X01",
                color = GoldColor,
                fontSize = 21.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,

                modifier = Modifier.weight(1f)
            )

            Text(
                text = "P${currentPlayer + 1}",
                color = WhiteColor,
                fontWeight = FontWeight.Bold
            )
        }

        HorizontalDivider(
            color = Color.DarkGray
        )

        Spacer(
            modifier = Modifier.height(5.dp)
        )


        /*
        PLAYER CARDS
        */

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {

            items(playerCount) { index ->

                PlayerScoreCard(
                    name = playerNames[index],

                    score = scores[index],

                    active =
                        currentPlayer == index,

                    winner =
                        winner == index,

                    outMode = outMode,

                    onFinishClick = {

                        if (index == currentPlayer) {

                            showCheckout = true

                        } else {

                            message =
                                "نوبت ${playerNames[index]} نیست"
                        }
                    }
                )
            }
        }


        /*
        MESSAGE
        */

        if (message.isNotEmpty()) {

            Text(
                text = message,
                color = GoldColor,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp)
            )
        }


        /*
        SELECTED DARTS
        */

        if (selectedDarts.isNotEmpty()) {

            Text(
                text =
                    selectedDarts.joinToString(" ") {
                        it.toString()
                    },

                color = WhiteColor,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,

                modifier =
                    Modifier.fillMaxWidth()
            )

            Text(
                text =
                    "Visit: ${
                        selectedDarts.sumOf {
                            it.score
                        }
                    }",

                color = GrayColor,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,

                modifier =
                    Modifier.fillMaxWidth()
            )
        }


        /*
        MULTIPLIER
        */

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement =
                Arrangement.SpaceEvenly
        ) {

            MultiplierButton(
                text = "SINGLE",
                selected =
                    dartMultiplier == 1
            ) {

                dartMultiplier = 1
            }

            MultiplierButton(
                text = "DOUBLE",
                selected =
                    dartMultiplier == 2
            ) {

                dartMultiplier = 2
            }

            MultiplierButton(
                text = "TREBLE",
                selected =
                    dartMultiplier == 3
            ) {

                dartMultiplier = 3
            }
        }

        Spacer(
            modifier = Modifier.height(4.dp)
        )


        /*
        NUMBER KEYBOARD
        */

        listOf(
            listOf(1, 2, 3, 4, 5),
            listOf(6, 7, 8, 9, 10),
            listOf(11, 12, 13, 14, 15),
            listOf(16, 17, 18, 19, 20)
        ).forEach { numbers ->

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),

                horizontalArrangement =
                    Arrangement.spacedBy(4.dp)
            ) {

                numbers.forEach { number ->

                    NumberButton(
                        number = number,

                        modifier =
                            Modifier.weight(1f)
                    ) {

                        if (
                            selectedDarts.size < 3 &&
                            winner == -1
                        ) {

                            selectedDarts =
                                selectedDarts +
                                        Dart(
                                            number = number,
                                            multiplier =
                                                dartMultiplier
                                        )
                        }
                    }
                }
            }
        }


        /*
        BULL / MISS / UNDO DART
        */

        Row(
            modifier = Modifier.fillMaxWidth(),

            horizontalArrangement =
                Arrangement.spacedBy(5.dp)
        ) {

            ActionButton(
                text = "BULL",

                modifier =
                    Modifier.weight(1f)
            ) {

                if (
                    selectedDarts.size < 3 &&
                    winner == -1
                ) {

                    selectedDarts =
                        selectedDarts +
                                Dart.BULL
                }
            }

            ActionButton(
                text = "MISS",

                modifier =
                    Modifier.weight(1f)
            ) {

                if (
                    selectedDarts.size < 3 &&
                    winner == -1
                ) {

                    selectedDarts =
                        selectedDarts +
                                Dart.MISS
                }
            }

            ActionButton(
                text = "UNDO DART",

                modifier =
                    Modifier.weight(1f)
            ) {

                if (selectedDarts.isNotEmpty()) {

                    selectedDarts =
                        selectedDarts.dropLast(1)
                }
            }
        }


        /*
        CONTROL BUTTONS
        */

        Row(
            modifier = Modifier.fillMaxWidth(),

            horizontalArrangement =
                Arrangement.spacedBy(5.dp)
        ) {

            ActionButton(
                text = "SCORE",

                modifier =
                    Modifier.weight(1f)
            ) {

                showManualScore = true
            }

            ActionButton(
                text = "FINISH",

                modifier =
                    Modifier.weight(1f),

                enabled =
                    generateCheckoutOptions(
                        currentEngine.score,
                        outMode
                    ).isNotEmpty()
            ) {

                showCheckout = true
            }

            ActionButton(
                text = "UNDO VISIT",

                modifier =
                    Modifier.weight(1f)
            ) {

                val undone =
                    currentEngine.undo()

                message =
                    if (undone) {

                        "Visit برگشت داده شد"

                    } else {

                        "Visit برای برگشت وجود ندارد"
                    }

                selectedDarts =
                    emptyList()
            }

            ActionButton(
                text = "END VISIT",

                modifier =
                    Modifier.weight(1f),

                enabled =
                    selectedDarts.isNotEmpty()
            ) {

                val result =
                    currentEngine.submitVisit(
                        selectedDarts
                    )

                if (result.bust) {

                    message =
                        "BUST — امتیاز برگشت داده شد"

                    selectedDarts =
                        emptyList()

                } else {

                    message =
                        "Visit: ${result.total}"

                    selectedDarts =
                        emptyList()

                    if (result.checkout) {

                        winner =
                            currentPlayer

                        message =
                            "CHECKOUT — ${playerNames[currentPlayer]} WIN"

                    } else {

                        currentPlayer =
                            (
                                    currentPlayer + 1
                                    ) % playerCount
                    }
                }
            }
        }
    }


    /*
    MANUAL SCORE
    */

    if (showManualScore) {

        ManualScoreDialog(
            currentScore =
                currentEngine.score,

            onDismiss = {

                showManualScore = false
            },

            onConfirm = { value ->

                val result =
                    currentEngine.submitManualScore(
                        value
                    )

                if (result.bust) {

                    message = "BUST"

                } else {

                    message =
                        "Score: ${result.total}"

                    showManualScore =
                        false

                    if (result.checkout) {

                        winner =
                            currentPlayer

                        message =
                            "CHECKOUT — ${playerNames[currentPlayer]} WIN"

                    } else {

                        currentPlayer =
                            (
                                    currentPlayer + 1
                                    ) % playerCount
                    }
                }
            }
        )
    }


    /*
    CHECKOUT
    */

    if (showCheckout) {

        CheckoutDialog(
            score =
                currentEngine.score,

            outMode =
                outMode,

            onDismiss = {

                showCheckout = false
            },

            onSelect = { darts ->

                selectedDarts = darts

                showCheckout = false
            }
        )
    }
}


/*
==========================================================
PLAYER SCORE CARD
==========================================================
*/

@Composable
fun PlayerScoreCard(
    name: String,
    score: Int,
    active: Boolean,
    winner: Boolean,
    outMode: OutMode,
    onFinishClick: () -> Unit
) {

    val checkoutOptions =
        generateCheckoutOptions(
            score,
            outMode
        )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp)
            .background(
                if (active) {
                    Color(0xFF202020)
                } else {
                    Color(0xFF101010)
                },

                RoundedCornerShape(10.dp)
            )
            .padding(9.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Text(
                text = name,

                color =
                    if (active) {
                        GoldColor
                    } else {
                        WhiteColor
                    },

                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,

                modifier =
                    Modifier.weight(1f)
            )

            Text(
                text = score.toString(),

                color = GoldColor,
                fontSize = 27.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }

        if (winner) {

            Text(
                text = "WINNER",
                color = GreenColor,
                fontWeight = FontWeight.ExtraBold
            )
        }


        /*
        FINISH RED
        */

        if (
            checkoutOptions.isNotEmpty() &&
            !winner
        ) {

            val finish =
                checkoutOptions.first()

            Text(
                text =
                    "FINISH  ${
                        finish.darts.joinToString(" ") {
                            it.toString()
                        }
                    }",

                color = RedColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold,

                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onFinishClick()
                    }
                    .padding(
                        top = 4.dp
                    )
            )
        }
    }
}


/*
==========================================================
MULTIPLIER
==========================================================
*/

@Composable
fun MultiplierButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,

        colors = ButtonDefaults.buttonColors(

            containerColor =
                if (selected) {
                    GoldColor
                } else {
                    DarkButtonColor
                },

            contentColor =
                if (selected) {
                    Color.Black
                } else {
                    WhiteColor
                }
        ),

        modifier =
            Modifier.padding(2.dp)
    ) {

        Text(
            text = text,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


/*
==========================================================
NUMBER
==========================================================
*/

@Composable
fun NumberButton(
    number: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,

        modifier = modifier
            .height(43.dp),

        colors = ButtonDefaults.buttonColors(
            containerColor = DarkButtonColor,
            contentColor = WhiteColor
        ),

        shape = RoundedCornerShape(8.dp),

        contentPadding = PaddingValues(
            horizontal = 2.dp,
            vertical = 0.dp
        )
    ) {

        Text(
            text = number.toString(),
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


/*
==========================================================
ACTION
==========================================================
*/

@Composable
fun ActionButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,

        enabled = enabled,

        modifier = modifier
            .padding(2.dp),

        colors = ButtonDefaults.buttonColors(
            containerColor = DarkButtonColor,
            contentColor = WhiteColor
        )
    ) {

        Text(
            text = text,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}


/*
==========================================================
MANUAL SCORE
==========================================================
*/

@Composable
fun ManualScoreDialog(
    currentScore: Int,
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit
) {

    var value by remember {
        mutableStateOf("")
    }

    var error by remember {
        mutableStateOf("")
    }

    AlertDialog(
        onDismissRequest = onDismiss,

        title = {

            Text(
                text = "ENTER SCORE"
            )
        },

        text = {

            Column {

                Text(
                    text =
                        "Remaining: $currentScore",

                    color = GrayColor
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                TextField(
                    value = value,

                    onValueChange = { newValue ->

                        if (
                            newValue.all {
                                it.isDigit()
                            }
                        ) {

                            value = newValue

                            val number =
                                newValue.toIntOrNull()

                            error =
                                when {

                                    number == null ->
                                        ""

                                    number > 180 ->
                                        "خارج از رنج"

                                    number > currentScore ->
                                        "امتیاز بیشتر از باقی‌مانده است"

                                    else ->
                                        ""
                                }
                        }
                    },

                    singleLine = true,

                    label = {
                        Text("Score")
                    },

                    isError =
                        error.isNotEmpty()
                )

                if (error.isNotEmpty()) {

                    Text(
                        text = error,
                        color = RedColor,
                        fontWeight = FontWeight.Bold,

                        modifier =
                            Modifier.padding(
                                top = 5.dp
                            )
                    )
                }
            }
        },

        confirmButton = {

            TextButton(
                onClick = {

                    val number =
                        value.toIntOrNull()

                    when {

                        number == null -> {

                            error =
                                "امتیاز را وارد کنید"
                        }

                        number > 180 -> {

                            error =
                                "خارج از رنج"
                        }

                        number > currentScore -> {

                            error =
                                "امتیاز بیشتر از باقی‌مانده است"
                        }

                        else -> {

                            onConfirm(number)
                        }
                    }
                }
            ) {

                Text(
                    text = "OK",
                    color = GoldColor
                )
            }
        },

        dismissButton = {

            TextButton(
                onClick = onDismiss
            ) {

                Text("CANCEL")
            }
        }
    )
}


/*
==========================================================
CHECKOUT OPTION
==========================================================
*/

data class CheckoutOption(
    val darts: List<Dart>
)


/*
==========================================================
CHECKOUT GENERATOR
==========================================================
*/

fun generateCheckoutOptions(
    score: Int,
    outMode: OutMode
): List<CheckoutOption> {

    if (
        score < 2 ||
        score > 170
    ) {

        return emptyList()
    }

    val darts =
        buildList {

            for (number in 1..20) {

                add(
                    Dart(
                        number = number,
                        multiplier = 1
                    )
                )

                add(
                    Dart(
                        number = number,
                        multiplier = 2
                    )
                )

                add(
                    Dart(
                        number = number,
                        multiplier = 3
                    )
                )
            }

            add(Dart.BULL)
        }


    fun validFinalDart(
        dart: Dart
    ): Boolean {

        return when (outMode) {

            OutMode.STRAIGHT ->
                true

            OutMode.DOUBLE ->
                dart.isDouble ||
                        dart.isBull

            OutMode.MASTER ->
                dart.isDouble ||
                        dart.isTreble ||
                        dart.isBull
        }
    }


    val result =
        mutableListOf<CheckoutOption>()


    /*
    ONE DART
    */

    for (a in darts) {

        if (
            a.score == score &&
            validFinalDart(a)
        ) {

            result.add(
                CheckoutOption(
                    darts = listOf(a)
                )
            )
        }
    }


    /*
    TWO DART
    */

    for (a in darts) {

        for (b in darts) {

            if (
                a.score + b.score == score &&
                validFinalDart(b)
            ) {

                result.add(
                    CheckoutOption(
                        darts = listOf(
                            a,
                            b
                        )
                    )
                )
            }
        }
    }


    /*
    THREE DART
    */

    for (a in darts) {

        for (b in darts) {

            val remaining =
                score -
                        a.score -
                        b.score

            if (remaining <= 0) {

                continue
            }

            for (c in darts) {

                if (
                    c.score == remaining &&
                    validFinalDart(c)
                ) {

                    result.add(
                        CheckoutOption(
                            darts = listOf(
                                a,
                                b,
                                c
                            )
                        )
                    )
                }
            }
        }
    }


    /*
    UNIQUE
    */

    val unique =
        result.distinctBy { option ->

            option.darts.joinToString("|") {
                it.toString()
            }
        }


    /*
    ROUTE PRIORITY
    */

    fun priority(
        dart: Dart
    ): Int {

        return when {

            dart.isBull ->
                100

            dart.isDouble &&
                    dart.number == 20 ->
                99

            dart.isTreble &&
                    dart.number == 20 ->
                98

            dart.isDouble &&
                    dart.number == 16 ->
                97

            dart.isTreble &&
                    dart.number == 19 ->
                96

            dart.isTreble &&
                    dart.number == 18 ->
                95

            dart.isTreble &&
                    dart.number == 17 ->
                94

            dart.isTreble &&
                    dart.number == 16 ->
                93

            dart.isDouble ->
                80 + dart.number

            dart.isTreble ->
                50 + dart.number

            else ->
                dart.number
        }
    }


    return unique
        .sortedWith(

            compareBy<CheckoutOption> {

                it.darts.size

            }.thenByDescending {

                it.darts.lastOrNull()
                    ?.let(::priority)
                    ?: 0

            }.thenByDescending {

                it.darts.sumOf(::priority)
            }
        )

        .take(20)
}


/*
==========================================================
CHECKOUT DIALOG
==========================================================
*/

@Composable
fun CheckoutDialog(
    score: Int,
    outMode: OutMode,
    onDismiss: () -> Unit,
    onSelect: (List<Dart>) -> Unit
) {

    val options =
        remember(score, outMode) {

            generateCheckoutOptions(
                score,
                outMode
            )
        }

    AlertDialog(
        onDismissRequest = onDismiss,

        title = {

            Text(
                text = "FINISH $score",
                color = GoldColor,
                fontWeight = FontWeight.ExtraBold
            )
        },

        text = {

            if (options.isEmpty()) {

                Text(
                    text =
                        "No checkout available."
                )

            } else {

                LazyColumn {

                    items(options) { option ->

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {

                                    onSelect(
                                        option.darts
                                    )
                                }
                                .padding(
                                    vertical = 10.dp
                                )
                        ) {

                            Text(
                                text =
                                    option.darts
                                        .joinToString(" ") {
                                            it.toString()
                                        },

                                color = WhiteColor,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        HorizontalDivider(
                            color = Color.DarkGray
                        )
                    }
                }
            }
        },

        confirmButton = {

            TextButton(
                onClick = onDismiss
            ) {

                Text(
                    text = "CLOSE",
                    color = GoldColor
                )
            }
        }
    )
}


/*
==========================================================
PLACEHOLDER
==========================================================
*/

@Composable
fun PlaceholderScreen(
    title: String,
    onBack: () -> Unit
) {

    AppPage(
        title = title,
        onBack = onBack
    ) {

        Box(
            modifier = Modifier.fillMaxSize(),

            contentAlignment =
                Alignment.Center
        ) {

            Text(
                text = "COMING SOON",
                color = GoldColor,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


/*
==========================================================
APP PAGE
==========================================================
*/

@Composable
fun AppPage(
    title: String,
    onBack: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(15.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            TextButton(
                onClick = onBack
            ) {

                Text(
                    text = "BACK",
                    color = GoldColor
                )
            }

            Text(
                text = title,
                color = GoldColor,
                fontSize = 25.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,

                modifier =
                    Modifier.weight(1f)
            )

            Spacer(
                modifier = Modifier.width(50.dp)
            )
        }

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        content()
    }
}