package com.example.pz8_kilnevoy_pr_24102

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pz8_kilnevoy_pr_24102.ui.theme.Pz8_Kilnevoy_PR24102Theme
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemDark = isSystemInDarkTheme()
            var isDarkTheme by remember { mutableStateOf(systemDark) }

            Pz8_Kilnevoy_PR24102Theme(darkTheme = isDarkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ClockAndDateScreen(
                        isDarkTheme = isDarkTheme,
                        onToggleTheme = { isDarkTheme = it }
                    )
                }
            }
        }
    }
}

// Функции Kotlin для получения актуального времени и даты
fun getCurrentTime(): String {
    val formatter = SimpleDateFormat("HH:mm:ss", Locale.forLanguageTag("ru-RU"))
    return formatter.format(Date())
}

fun getCurrentDate(): String {
    val formatter = SimpleDateFormat("dd MMMM yyyy", Locale.forLanguageTag("ru-RU"))
    return formatter.format(Date())
}

@Composable
fun ClockAndDateScreen(
    isDarkTheme: Boolean,
    onToggleTheme: (Boolean) -> Unit
) {
    var currentTime by remember { mutableStateOf(getCurrentTime()) }
    var currentDate by remember { mutableStateOf(getCurrentDate()) }

    // Обновление времени и даты каждую секунду в реальном времени
    LaunchedEffect(Unit) {
        while (true) {
            currentTime = getCurrentTime()
            currentDate = getCurrentDate()
            delay(1000L)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Практическая работа №8",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        // Переключатель светлой и тёмной темы
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (isDarkTheme) "Тёмная тема" else "Светлая тема",
                    style = MaterialTheme.typography.titleMedium
                )
                Switch(
                    checked = isDarkTheme,
                    onCheckedChange = onToggleTheme
                )
            }
        }

        // Элемент 1: часы в реальном времени (ЧЧ:ММ:СС)
        ClockElement(time = currentTime)

        // Элемент 2: текущая дата (день, месяц полностью, год 4 знака)
        DateElement(date = currentDate)
    }
}

@Composable
fun ClockElement(time: String) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Текущее время",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = time,
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun DateElement(date: String) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Текущая дата",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.tertiary
            )
            Text(
                text = date,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview(showBackground = true, name = "Светлая тема")
@Composable
fun LightThemePreview() {
    Pz8_Kilnevoy_PR24102Theme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            ClockAndDateScreen(isDarkTheme = false, onToggleTheme = {})
        }
    }
}

@Preview(showBackground = true, name = "Тёмная тема")
@Composable
fun DarkThemePreview() {
    Pz8_Kilnevoy_PR24102Theme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            ClockAndDateScreen(isDarkTheme = true, onToggleTheme = {})
        }
    }
}