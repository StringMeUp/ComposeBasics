package com.sr.composebasics.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.sr.composebasics.MainViewModel
import com.sr.composebasics.R

@Preview
@Composable
fun Header(viewModel: MainViewModel = viewModel()) {
    Surface(modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp),
        shape = RoundedCornerShape(6.dp),
        color = colorResource(id = R.color.purple_200)) {
        Column(modifier = Modifier.wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(textAlign = TextAlign.Start,
                text = "Total Tip Result",
                modifier = Modifier.padding(top = 32.dp),
                color = Color.Blue,
                fontSize = 14.sp)
            Text(textAlign = TextAlign.Center,
                text = viewModel.tip.value.toString(),
                modifier = Modifier.padding(bottom = 32.dp),
                color = Color.Black,
                fontSize = 24.sp)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun Body(viewModel: MainViewModel = viewModel()) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Surface(modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth(), shape = RoundedCornerShape(6.dp)) {
        Column(modifier = Modifier.padding(12.dp)) {
            Box(modifier = Modifier.wrapContentHeight(), contentAlignment = Alignment.TopStart) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.total.observeAsState().value.toString(),
                    maxLines = 1,
                    leadingIcon = {
                        Icon(imageVector = ImageVector.vectorResource(id = R.drawable.ic_money),
                            contentDescription = null)
                    },
                    label = { Text(text = "Enter Bill", fontSize = 12.sp) },
                    keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number),
                    onValueChange = {

                        viewModel.setTotal(it)
                        viewModel.calculatePercentage()

                    })


            }

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(24.dp))

            SplitSection()
        }
    }
}

@Composable
fun SplitSection(viewModel: MainViewModel = viewModel()) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (splitText, tipText, incBtn, decBtn, splitAmountText, tipResultText, slider) = createRefs()
        Text(text = "Split by:", modifier = Modifier
            .wrapContentWidth()
            .constrainAs(splitText) {
                top.linkTo(incBtn.top)
                bottom.linkTo(incBtn.bottom)
            }, fontSize = 18.sp)

        Button(modifier = Modifier
            .size(50.dp)
            .constrainAs(incBtn) {
                top.linkTo(parent.top, margin = 24.dp)
                end.linkTo(splitAmountText.start)
            },
            onClick = { viewModel.addSplit() },
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.btn_color))) {
            Image(painterResource(id = R.drawable.ic_add), contentDescription = "Add")
        }

        Text(text = viewModel.splitCount.value.toString(), modifier = Modifier
            .constrainAs(splitAmountText) {
                end.linkTo(decBtn.start)
                top.linkTo(incBtn.top)
                bottom.linkTo(incBtn.bottom)
            }
            .padding(12.dp))

        Button(modifier = Modifier
            .size(50.dp)
            .constrainAs(decBtn) {
                top.linkTo(splitAmountText.top)
                end.linkTo(parent.end, margin = 12.dp)
                bottom.linkTo(splitAmountText.bottom)
            },
            onClick = { viewModel.subSplit() },
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.btn_color))) {
            Image(painterResource(id = R.drawable.ic_subtract), contentDescription = "Add")
        }

        Text(text = "Tip:", modifier = Modifier
            .wrapContentWidth()
            .constrainAs(tipText) {
                this.top.linkTo(incBtn.bottom, margin = 36.dp)
            }, fontSize = 18.sp)

        Slider(
            value = viewModel.percentage.value,
            onValueChange = {
                viewModel.setPercentage(it)
                viewModel.calculatePercentage()
            },
            modifier = Modifier
                .constrainAs(slider) {
                    top.linkTo(tipText.bottom, margin = 12.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(6.dp),
            valueRange = 0f..100f,
            steps = 9)

        Text(text = "${viewModel.percentage.value.toInt()}%",
            modifier = Modifier.constrainAs(tipResultText) {
                top.linkTo(slider.bottom)
                start.linkTo(slider.start)
                end.linkTo(slider.end)
            },
            fontSize = 20.sp)

    }
}

