package com.example.cocktail.base.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun ErrorItem(
    errorMessage: String,
    retry: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(bottom = 16.dp)
    ) {
        val (errorMessageTxt, retryBtn) = createRefs()

        Text(
            text = errorMessage,
            fontSize = 12.sp,
            color = Color.Red,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(errorMessageTxt) {
                    top.linkTo(parent.top, margin = 8.dp)
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
