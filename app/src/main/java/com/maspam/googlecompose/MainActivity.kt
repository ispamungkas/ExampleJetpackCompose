package com.maspam.googlecompose

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maspam.googlecompose.ui.theme.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoogleComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface() {
                    MyApp(Modifier.fillMaxSize())
                }
            }
        }
    }
}

val myList: List<String> = List(100) { "$it" }

@Composable
fun MyApp(modifier: Modifier = Modifier) {

    var shouldCallOnBoardingScreen by rememberSaveable {
        mutableStateOf(true)
    }

    Surface(modifier) {
        if (shouldCallOnBoardingScreen) {
            OnBoardingScreen(
                Modifier.fillMaxSize(),
                onClickContinue = { shouldCallOnBoardingScreen = false })
        } else {
            Greetings(list = myList)
        }
    }
}

@Composable
fun Greetings(mdiifier: Modifier = Modifier, list: List<String>) {
//    Column() {
//        for (item in list){
//            Greeting(item)
//        }
//    }

    // Pakai lazy Rou/Column biar efisien

    LazyColumn(modifier = Modifier.padding(all = 4.dp)) {
        items(items = list) { listData ->
            GreetingCard(name = listData)
        }
    }
}

@Composable
fun OnBoardingScreen(modifier: Modifier = Modifier, onClickContinue: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Welcome to my apps")
        Button(onClick = onClickContinue) {
            Text("Continue")
        }
    }
}

@Composable
fun GreetingCard(name: String) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.padding(all = 5.dp)
    ) {
        CardDetail(name = name)
    }
}

@Composable
fun CardDetail(name: String) {
    var expanded by remember {
        mutableStateOf(false)
    }

    Surface(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
        color = MaterialTheme.colorScheme.primary
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp) // coerceAtLeast
            ) {
                Text(
                    text = "hey, ", style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp
                    )
                )
                Text(name)
                if (expanded) {
                    Text(
                        text = "Compose ipum color blablabal" +
                                "blaablablabalbalblablabl".repeat(4),
                    )
                }
            }
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (expanded) {
                        stringResource(
                            id = R.string.show_less
                        )
                    } else {
                        stringResource(id = R.string.show_more)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyAppPreview() {
    GoogleComposeTheme() {
        Surface() {
            OnBoardingScreen(onClickContinue = {})
        }
    }
}

@Preview(showBackground = true, name = "Dark", uiMode = UI_MODE_NIGHT_YES)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GoogleComposeTheme {
        Greetings(list = myList)
    }
}