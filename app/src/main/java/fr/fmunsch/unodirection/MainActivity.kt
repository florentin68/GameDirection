package fr.fmunsch.unodirection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                UnoDirectionApp()
            }
        }
    }
}

@Composable
fun UnoDirectionApp() {
    // Variable d'état pour le sens
    var isClockwise by remember { mutableStateOf(true) }

    // Animations fluides pour le changement de sens
    val scaleX by animateFloatAsState(
        targetValue = if (isClockwise) 1f else -1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scaleX"
    )

    val contentColor by animateColorAsState(
        targetValue = if (isClockwise) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
        animationSpec = spring(stiffness = Spring.StiffnessLow),
        label = "color"
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        // La colonne occupe tout l'écran et détecte les clics
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable { isClockwise = !isClockwise }
                .padding(48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = if (isClockwise) "SENS HORAIRE" else "SENS ANTI-HORAIRE",
                fontSize = 64.sp,
                fontWeight = FontWeight.Black,
                color = contentColor,
                textAlign = TextAlign.Center,
                lineHeight = 72.sp
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(vertical = 32.dp)
            ) {
                // Un cercle décoratif en fond pour donner du relief
                Surface(
                    shape = CircleShape,
                    color = contentColor.copy(alpha = 0.05f),
                    modifier = Modifier.size(550.dp)
                ) {}

                // Icône de flèche circulaire agrandie
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Flèche de direction",
                    modifier = Modifier
                        .size(450.dp)
                        .graphicsLayer {
                            // Animation du miroir horizontal
                            this.scaleX = scaleX
                        },
                    tint = contentColor
                )
            }

            Text(
                text = "Appuyez n'importe où pour inverser",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                textAlign = TextAlign.Center
            )
        }
    }
}