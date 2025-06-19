package com.hmyh.moviecompose.ui.tutorial

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hmyh.moviecompose.ui.components.InputField
import com.hmyh.moviecompose.ui.theme.MovieComposeTheme
import com.hmyh.moviecompose.ui.widget.RoundIconButton
import com.hmyh.moviecompose.util.calculateTotalPerPerson
import com.hmyh.moviecompose.util.calculateTotalTip

class Calculator : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieComposeTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Calculator(innerPadding)
                }
            }
        }
    }

}

@Composable
private fun Calculator(innerPadding: PaddingValues) {

    val totalPerPersonState = remember { mutableStateOf(0.0) }

    val splitByState = remember {
        mutableStateOf(1)
    }

    val tipAmountState = remember {
        mutableStateOf(0.0)
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            TopHeader(totalPerPerson = totalPerPersonState.value)
            Spacer(modifier = Modifier.height(8.dp))
            MainContent(
                splitByState = splitByState,
                tipAmountState = tipAmountState,
                totalPerPersonState = totalPerPersonState
            )
        }
    }

}


@Preview
@Composable
fun TopHeader(totalPerPerson: Double = 0.0) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .height(150.dp)
            .clip(shape = RoundedCornerShape(corner = CornerSize(12.dp))),
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(20.dp)
        ) {
            val total = "%.2f".format(totalPerPerson)

            Text(
                text = "Total Per Person",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "$$total",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

        }
    }

}

@Composable()
fun MainContent(
    splitByState: MutableState<Int>,
    tipAmountState: MutableState<Double>,
    totalPerPersonState: MutableState<Double>
) {

    BillForm(
        modifier = Modifier,
        splitByState = splitByState,
        tipAmountState = tipAmountState,
        totalPerPersonState = totalPerPersonState
    ) { billAmt ->
        Log.d("AMT", "MainContent: $billAmt")
    }

}


@Composable
fun BillForm(
    modifier: Modifier = Modifier,
    range: IntRange = 1..100,
    splitByState: MutableState<Int>,
    tipAmountState: MutableState<Double>,
    totalPerPersonState: MutableState<Double>,
    onValChange: (String) -> Unit = {}
) {

    val totalBillState = remember {
        mutableStateOf("")
    }
    val validState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    val sliderPositionState = remember {
        mutableStateOf(0f)
    }

    var updatedTipPercentage by remember { mutableStateOf(0) }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(corner = CornerSize(8.dp))),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {

        Column(
            modifier = modifier.padding(8.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {

            //if you want live data when user type, onValChange(totalBillState.value.trim()) this function place under TextField
            InputField(
                valueState = totalBillState,
                labelId = "Enter Bill",
                enabled = true,
                isSingleLine = true,
                onAction = KeyboardActions {
                    if (!validState) return@KeyboardActions
                    onValChange(totalBillState.value.trim())

                    keyboardController?.hide()
                }
            )

            if (validState) {

                //Split
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {

                    Box(
                        modifier = modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically)
                    ) {

                        Text(
                            text = "Split",
                            modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
                        )

                    }
                    Box(
                        modifier = modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically)
                    ) {

                        Row {
                            RoundIconButton(
                                imageVector = Icons.Default.Remove,
                                onClick = {
                                    if (splitByState.value > 1) {
                                        splitByState.value = splitByState.value - 1
                                    } else {
                                        splitByState.value = 1
                                    }

                                    totalPerPersonState.value =
                                        calculateTotalPerPerson(
                                            totalBill = totalBillState.value.toDouble(),
                                            splitBy = splitByState.value,
                                            tipPercentage = updatedTipPercentage
                                        )
                                }
                            )
                            Text(
                                text = "${splitByState.value}",
                                modifier = modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(start = 8.dp, end = 8.dp)
                            )
                            RoundIconButton(
                                imageVector = Icons.Default.Add,
                                onClick = {
                                    if (splitByState.value < range.last) {
                                        splitByState.value += 1
                                    }

                                    totalPerPersonState.value =
                                        calculateTotalPerPerson(
                                            totalBill = totalBillState.value.toDouble(),
                                            splitBy = splitByState.value,
                                            tipPercentage = updatedTipPercentage
                                        )
                                }
                            )
                        }

                    }

                }

                //Tip
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
                ) {

                    Box(
                        modifier = modifier
                            .weight(2f)
                            .align(Alignment.CenterVertically)
                    ) {

                        Text(
                            text = "Tip",
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp, bottom = 4.dp)
                        )

                    }

                    Box(
                        modifier = modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically)
                    ) {
                        Text(
                            text = "$ ${tipAmountState.value}",
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp, bottom = 4.dp)
                        )

                    }

                }

                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

//                "$${(sliderPositionState.value * 100).toInt()} %"

                    Text(
                        text = "$$updatedTipPercentage %"
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Slider(
                        value = sliderPositionState.value,
                        onValueChange = {
                            sliderPositionState.value = it
                            Log.d("sliderValue", sliderPositionState.value.toString())

                            updatedTipPercentage = (sliderPositionState.value * 100).toInt()

                            tipAmountState.value =
                                calculateTotalTip(
                                    totalBill = totalBillState.value.toDoubleOrNull(),
                                    tipPercentage = updatedTipPercentage
                                ) ?: 0.0.toDouble()

                            totalPerPersonState?.value =
                                calculateTotalPerPerson(
                                    totalBill = totalBillState.value.toDouble(),
                                    splitBy = splitByState.value,
                                    tipPercentage = updatedTipPercentage
                                )

                        },
                        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        steps = 5
                    )

                }

            } else {
                Box() {

                }
            }

        }

    }

}




