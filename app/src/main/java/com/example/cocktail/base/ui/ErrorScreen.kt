package com.example.cocktail.base.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.cocktail.R

@Composable
fun ErrorScreen(
    errorMessage: String = "something went wrong",
    errorIcon: Int = R.drawable.ic_baseline_error_outline_24,
    retry: () -> Unit = {}
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val (errorIconImg, errorMessageTxt, retryBtn) = createRefs()

        Image(
            painter = painterResource(id = errorIcon),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color.Black),
            modifier = Modifier
                .size(100.dp)
                .constrainAs(errorIconImg) {
                    linkTo(
                        top = parent.top,
                        bottom = parent.bottom,
                        bias = 0.4f
                    )
                    linkTo(
                        start = parent.start,
                        end = parent.end
                    )
                }
        )

        Text(
            text = errorMessage,
            fontSize = 16.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(errorMessageTxt) {
                    top.linkTo(errorIconImg.bottom, margin = 16.dp)
                    linkTo(
                        start = parent.start,
                        end = parent.end,
                        startMargin = 16.dp,
                        endMargin = 16.dp
                    )
                }
        )

        Button(
            onClick = { retry() },
            modifier = Modifier.constrainAs(retryBtn) {
                top.linkTo(errorMessageTxt.bottom, margin = 16.dp)
                linkTo(
                    start = parent.start,
                    end = parent.end
                )
            }
        ) {
            Text(text = "Retry")
        }
    }
}
