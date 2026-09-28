package com.example.myapplication_darts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication_darts.game.Dart
import com.example.myapplication_darts.game.InMode
import com.example.myapplication_darts.game.OutMode
import com.example.myapplication_darts.game.X01Engine
import androidx.compose.ui.text.style.TextAlign

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF101010)
                ) {

                    YadraDartsApp()
                }
            }
        }
    }
}


/*
==========================================================
YADRA DARTS APP
==========================================================
*/

@Composable
fun YadraDartsApp() {

    var screen by remember {
        mutableStateOf("intro")
    }

    var startingScore by remember {
        mutableIntStateOf(501)
    }

    var players by remember {
        mutableIntStateOf(2)
    }

    var inMode by remember {
        mutableStateOf(InMode.STRAIGHT)
    }

    var outMode by remember {
        mutableStateOf(OutMode.DOUBLE)
    }

    when (screen) {

        "intro" -> {

            IntroScreen(
                onContinue = {
                    screen = "home"
                }
            )
        }

        "home" -> {

            HomeScreen(
                onStart = {
                    screen = "setup"
                }
            )
        }

        "setup" -> {

            SetupScreen(
                startingScore = startingScore,
                players = players,
                inMode = inMode,
                outMode = outMode,

                onScoreChange = {
                    startingScore = it
                },

                onPlayersChange = {
                    players = it
                },

                onInModeChange = {
                    inMode = it
                },

                onOutModeChange = {
                    outMode = it
                },

                onBack = {
                    screen = "home"
                },

                onContinue = {
                    screen = "game"
                }
            )
        }

        "game" -> {

            GameScreen(
                startingScore = startingScore,
                players = players,
                inMode = inMode,
                outMode = outMode,

                onBack = {
                    screen = "setup"
                }
            )
        }
    }
}


/*
==========================================================
INTRO SCREEN
==========================================================
*/

@Composable
fun IntroScreen(
    onContinue: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        Image(
            painter = painterResource(
                id = R.drawable.yadra_intro
            ),
            contentDescription = "YADRA DARTS",
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp),
            contentScale = ContentScale.Fit
        )

        Button(
            onClick = onContinue,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(
                    start = 30.dp,
                    end = 30.dp,
                    bottom = 40.dp
                )
                .height(58.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00695C)
            )
        ) {

            Text(
                text = "ENTER",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


/*
==========================================================
HOME
==========================================================
*/

@Composable
fun HomeScreen(
    onStart: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010))
            .padding(20.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(
            modifier = Modifier.height(30.dp)
        )



        Text(
            text = "DART",
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFFD700),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            textAlign = TextAlign.Center
        )



        Spacer(
            modifier = Modifier.height(40.dp)
        )

        MenuButton(
            text = "START NEW GAME",
            modifier = Modifier.fillMaxWidth(),
            onClick = onStart
        )

        MenuButton(
            text = "PRACTICE",
            modifier = Modifier.fillMaxWidth(),
            onClick = {}
        )

        MenuButton(
            text = "PLAYERS",
            modifier = Modifier.fillMaxWidth(),
            onClick = {}
        )

        MenuButton(
            text = "STATISTICS",
            modifier = Modifier.fillMaxWidth(),
            onClick = {}
        )

        MenuButton(
            text = "HISTORY",
            modifier = Modifier.fillMaxWidth(),
            onClick = {}
        )

        MenuButton(
            text = "TOURNAMENT",
            modifier = Modifier.fillMaxWidth(),
            onClick = {}
        )
    }
}


/*
==========================================================
SETUP
==========================================================
*/

@Composable
fun SetupScreen(
    startingScore: Int,
    players: Int,
    inMode: InMode,
    outMode: OutMode,
    onScoreChange: (Int) -> Unit,
    onPlayersChange: (Int) -> Unit,
    onInModeChange: (InMode) -> Unit,
    onOutModeChange: (OutMode) -> Unit,
    onBack: () -> Unit,
    onContinue: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010))
            .padding(20.dp)
    ) {

        Text(
            text = "GAME SETUP",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(25.dp)
        )

        SetupTitle(
            text = "STARTING SCORE"
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            ScoreButton(
                score = 301,
                selected = startingScore == 301,
                modifier = Modifier.weight(1f),
                onClick = {
                    onScoreChange(301)
                }
            )

            ScoreButton(
                score = 501,
                selected = startingScore == 501,
                modifier = Modifier.weight(1f),
                onClick = {
                    onScoreChange(501)
                }
            )

            ScoreButton(
                score = 701,
                selected = startingScore == 701,
                modifier = Modifier.weight(1f),
                onClick = {
                    onScoreChange(701)
                }
            )
        }

        Spacer(
            modifier = Modifier.height(25.dp)
        )

        SetupTitle(
            text = "PLAYERS"
        )

        PlayerSelector(
            players = players,

            onMinus = {
                if (players > 1) {
                    onPlayersChange(players - 1)
                }
            },

            onPlus = {
                if (players < 8) {
                    onPlayersChange(players + 1)
                }
            }
        )

        Spacer(
            modifier = Modifier.height(25.dp)
        )

        SetupTitle(
            text = "IN"
        )

        ModeRow(
            straight = inMode == InMode.STRAIGHT,
            double = inMode == InMode.DOUBLE,
            master = inMode == InMode.MASTER,

            onStraight = {
                onInModeChange(InMode.STRAIGHT)
            },

            onDouble = {
                onInModeChange(InMode.DOUBLE)
            },

            onMaster = {
                onInModeChange(InMode.MASTER)
            }
        )

        Spacer(
            modifier = Modifier.height(25.dp)
        )

        SetupTitle(
            text = "OUT"
        )

        ModeRow(
            straight = outMode == OutMode.STRAIGHT,
            double = outMode == OutMode.DOUBLE,
            master = outMode == OutMode.MASTER,

            onStraight = {
                onOutModeChange(OutMode.STRAIGHT)
            },

            onDouble = {
                onOutModeChange(OutMode.DOUBLE)
            },

            onMaster = {
                onOutModeChange(OutMode.MASTER)
            }
        )

        Spacer(
            modifier = Modifier.weight(1f)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            GameButton(
                text = "BACK",
                modifier = Modifier.weight(1f),
                onClick = onBack
            )

            GameButton(
                text = "CONTINUE",
                modifier = Modifier.weight(1f),
                onClick = onContinue
            )
        }
    }
}


/*
==========================================================
GAME
==========================================================
*/

@Composable
fun GameScreen(
    startingScore: Int,
    players: Int,
    inMode: InMode,
    outMode: OutMode,
    onBack: () -> Unit
) {

    val engine = remember {
        X01Engine(
            startingScore = startingScore,
            inMode = inMode,
            outMode = outMode
        )
    }

    var currentScore by remember {
        mutableIntStateOf(startingScore)
    }

    var dart1 by remember {
        mutableStateOf<Dart?>(null)
    }

    var dart2 by remember {
        mutableStateOf<Dart?>(null)
    }

    var dart3 by remember {
        mutableStateOf<Dart?>(null)
    }

    val darts = listOfNotNull(
        dart1,
        dart2,
        dart3
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010))
            .padding(12.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            GameButton(
                text = "BACK",
                modifier = Modifier.width(90.dp),
                onClick = onBack
            )

            Spacer(
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "PLAYER 1",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = currentScore.toString(),
            modifier = Modifier.fillMaxWidth(),
            color = Color.White,
            fontSize = 64.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Players: $players",
            color = Color.Gray,
            fontSize = 14.sp
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            DartSlot(
                dart = dart1,
                modifier = Modifier.weight(1f)
            )

            DartSlot(
                dart = dart2,
                modifier = Modifier.weight(1f)
            )

            DartSlot(
                dart = dart3,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(
            modifier = Modifier.height(15.dp)
        )

        NumberPad(
            onDart = { dart ->

                when {

                    dart1 == null -> {
                        dart1 = dart
                    }

                    dart2 == null -> {
                        dart2 = dart
                    }

                    dart3 == null -> {
                        dart3 = dart
                    }
                }
            }
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            GameButton(
                text = "UNDO",
                modifier = Modifier.weight(1f),
                onClick = {

                    when {

                        dart3 != null -> {
                            dart3 = null
                        }

                        dart2 != null -> {
                            dart2 = null
                        }

                        dart1 != null -> {
                            dart1 = null
                        }
                    }
                }
            )

            GameButton(
                text = "SUBMIT",
                modifier = Modifier.weight(1f),
                onClick = {

                    if (darts.isNotEmpty()) {

                        val result =
                            engine.submitVisit(darts)

                        currentScore =
                            result.newScore

                        dart1 = null
                        dart2 = null
                        dart3 = null
                    }
                }
            )
        }
    }
}


/*
==========================================================
NUMBER PAD
==========================================================
*/

@Composable
fun NumberPad(
    onDart: (Dart) -> Unit
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(5),

            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),

            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            items(
                items = (1..20).toList()
            ) { number ->

                NumberButton(
                    number = number,
                    multiplier = 1,

                    onClick = {

                        onDart(
                            Dart(
                                number = number,
                                multiplier = 1
                            )
                        )
                    }
                )
            }

            item {

                NumberButton(
                    number = 25,
                    label = "BULL",
                    multiplier = 2,

                    onClick = {
                        onDart(Dart.BULL)
                    }
                )
            }

            item {

                NumberButton(
                    number = 0,
                    label = "MISS",
                    multiplier = 0,

                    onClick = {
                        onDart(Dart.MISS)
                    }
                )
            }

            item {

                MultiplierButton(
                    text = "D20",

                    onClick = {

                        onDart(
                            Dart(
                                number = 20,
                                multiplier = 2
                            )
                        )
                    }
                )
            }

            item {

                MultiplierButton(
                    text = "T20",

                    onClick = {

                        onDart(
                            Dart(
                                number = 20,
                                multiplier = 3
                            )
                        )
                    }
                )
            }
        }
    }
}


/*
==========================================================
DART SLOT
==========================================================
*/

@Composable
fun DartSlot(
    dart: Dart?,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .height(60.dp)
            .border(
                width = 1.dp,
                color = Color.DarkGray
            ),

        contentAlignment = Alignment.Center
    ) {

        if (dart == null) {

            Text(
                text = "-",
                color = Color.Gray,
                fontSize = 24.sp
            )

        } else {

            Text(
                text = dartLabel(dart),
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


/*
==========================================================
NUMBER BUTTON
==========================================================
*/

@Composable
fun NumberButton(
    number: Int,
    multiplier: Int = 1,
    modifier: Modifier = Modifier,
    label: String? = null,
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,

        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),

        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF242424)
        )
    ) {

        Text(
            text = label ?: number.toString(),
            fontSize = 16.sp
        )
    }
}


/*
==========================================================
MULTIPLIER BUTTON
==========================================================
*/

@Composable
fun MultiplierButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,

        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),

        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF383838)
        )
    ) {

        Text(
            text = text,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


/*
==========================================================
GAME BUTTON
==========================================================
*/

@Composable
fun GameButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,

        modifier = modifier.height(52.dp),

        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF303030)
        )
    ) {

        Text(
            text = text,
            fontWeight = FontWeight.Bold
        )
    }
}


/*
==========================================================
SETUP TITLE
==========================================================
*/

@Composable
fun SetupTitle(
    text: String
) {

    Text(
        text = text,
        color = Color.Gray,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold
    )

    Spacer(
        modifier = Modifier.height(8.dp)
    )
}


/*
==========================================================
SCORE BUTTON
==========================================================
*/

@Composable
fun ScoreButton(
    score: Int,
    selected: Boolean,
    modifier: Modifier,
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,

        modifier = modifier
            .padding(4.dp)
            .height(55.dp),

        colors = ButtonDefaults.buttonColors(
            containerColor =
                if (selected) {
                    Color(0xFF00695C)
                } else {
                    Color(0xFF242424)
                }
        )
    ) {

        Text(
            text = score.toString(),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


/*
==========================================================
PLAYER SELECTOR
==========================================================
*/

@Composable
fun PlayerSelector(
    players: Int,
    onMinus: () -> Unit,
    onPlus: () -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),

        horizontalArrangement = Arrangement.Center,

        verticalAlignment = Alignment.CenterVertically
    ) {

        Button(
            onClick = onMinus,
            modifier = Modifier.size(55.dp)
        ) {

            Text(
                text = "-"
            )
        }

        Text(
            text = players.toString(),

            modifier = Modifier.padding(
                horizontal = 30.dp
            ),

            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Button(
            onClick = onPlus,
            modifier = Modifier.size(55.dp)
        ) {

            Text(
                text = "+"
            )
        }
    }
}


/*
==========================================================
MODE ROW
==========================================================
*/

@Composable
fun ModeRow(
    straight: Boolean,
    double: Boolean,
    master: Boolean,
    onStraight: () -> Unit,
    onDouble: () -> Unit,
    onMaster: () -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),

        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {

        ModeButton(
            text = "STRAIGHT",
            selected = straight,
            modifier = Modifier.weight(1f),
            onClick = onStraight
        )

        ModeButton(
            text = "DOUBLE",
            selected = double,
            modifier = Modifier.weight(1f),
            onClick = onDouble
        )

        ModeButton(
            text = "MASTER",
            selected = master,
            modifier = Modifier.weight(1f),
            onClick = onMaster
        )
    }
}


/*
==========================================================
MODE BUTTON
==========================================================
*/

@Composable
fun ModeButton(
    text: String,
    selected: Boolean,
    modifier: Modifier,
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,

        modifier = modifier.height(55.dp),

        colors = ButtonDefaults.buttonColors(
            containerColor =
                if (selected) {
                    Color(0xFF00695C)
                } else {
                    Color(0xFF242424)
                }
        )
    ) {

        Text(
            text = text,
            fontSize = 12.sp
        )
    }
}


/*
==========================================================
MENU ROW
==========================================================
*/

@Composable
fun MenuRow(
    left: String,
    right: String
) {

    Row(
        modifier = Modifier.fillMaxWidth(),

        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = left,
            color = Color.White
        )

        Text(
            text = right,
            color = Color.Gray
        )
    }
}


/*
==========================================================
MENU BUTTON
==========================================================
*/

@Composable
fun MenuButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,

        modifier = modifier
            .padding(vertical = 5.dp)
            .height(58.dp),

        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF242424)
        )
    ) {

        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


/*
==========================================================
DART LABEL
==========================================================
*/

fun dartLabel(
    dart: Dart
): String {

    if (dart.isMiss) {
        return "MISS"
    }

    if (dart.number == 25) {

        return if (dart.multiplier == 2) {
            "BULL"
        } else {
            "25"
        }
    }

    return when (dart.multiplier) {

        1 -> "S${dart.number}"

        2 -> "D${dart.number}"

        3 -> "T${dart.number}"

        else -> dart.number.toString()
    }
}