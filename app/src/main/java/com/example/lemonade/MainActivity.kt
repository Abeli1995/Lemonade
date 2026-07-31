package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeApp()
        }
    }
}


data class LemonadeStage(val imageRes: Int, val descriptionRes: Int)

@Composable
fun LemonadeApp() {
    LemonadeTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { contentPadding ->
            Column(modifier = Modifier.padding(contentPadding)) {
                var result by remember { mutableIntStateOf(1) }
                var squeezeCount by remember { mutableIntStateOf(0) }
                val stage = when (result) {
                    1 -> LemonadeStage(R.drawable.lemon_tree, R.string.tap_lemon_tree)
                    2 -> LemonadeStage(R.drawable.lemon_squeeze, R.string.squeeze_lemon)
                    3 -> LemonadeStage(R.drawable.lemon_drink, R.string.drink_lemonade)
                    4 -> LemonadeStage(R.drawable.lemon_restart, R.string.start_again)
                    else -> LemonadeStage(R.drawable.lemon_tree, R.string.tap_lemon_tree)
                }
                AppBar("Lemonade")
                LemonadeCard(
                    image = stage.imageRes,
                    description = stringResource(stage.descriptionRes),
                    contentDescription = stringResource(R.string.lemon_tree_content_description),
                    onClick = {
                        when (result) {
                            1 -> {
                                result = 2
                                squeezeCount = (2..6).random()
                            }

                            2 -> {
                                squeezeCount--
                                if (squeezeCount <= 0) {
                                    result = 3
                                }
                            }

                            3 -> result = 4
                            else -> result = 1
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun AppBar(name: String, modifier: Modifier = Modifier) {
    Box(
        modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color.Yellow)
    ) {
        Text(
            name,
            modifier = Modifier.align(Alignment.Center),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun LemonadeCard(
    image: Int,
    description: String,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = contentDescription,
            modifier = Modifier
                .size(width = 250.dp, height = 250.dp)
                .background(color = Color(0xFFB3E39D), shape = RoundedCornerShape(24.dp))
                .clickable(onClick = onClick),

            )
        Spacer(modifier = Modifier.height(16.dp))
        Text(description)
    }
}

@Preview(showBackground = true)
@Composable
fun LemonadePreview() {
    LemonadeApp()
}