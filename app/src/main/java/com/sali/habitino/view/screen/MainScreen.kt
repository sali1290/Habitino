package com.sali.habitino.view.screen

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sali.habitino.R
import com.sali.habitino.view.component.HabitTypeItem
import com.sali.habitino.view.component.RemoteHabitsList
import com.sali.habitino.view.component.SelfAddedHabitsList
import com.sali.habitino.view.theme.LightBlue
import com.sali.habitino.viewmodel.ScoreStateViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainHabitScreen() {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 2 })

    val scoreStateViewModel: ScoreStateViewModel = hiltViewModel()
    val scoreState by scoreStateViewModel.score.collectAsState()
    var score by remember { mutableIntStateOf(0) }
    LaunchedEffect(key1 = Unit) {
        scoreStateViewModel.getScore()
    }
    LaunchedEffect(key1 = scoreState) {
        score = scoreState
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 10.dp)
    ) {
        PagerTitles(pagerState = pagerState)

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = stringResource(R.string.your_score, score),
            fontSize = 20.sp,
            color = LightBlue,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            textAlign = TextAlign.Start
        )

        HorizontalPager(state = pagerState) { page ->
            when (page) {
                0 -> {
                    RemoteHabitsList {
                        score += it
                        scoreStateViewModel.saveScore(score)
                    }
                }

                1 -> {
                    SelfAddedHabitsList {
                        score += it
                        scoreStateViewModel.saveScore(score)
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PagerTitles(pagerState: PagerState) {
    val coroutineScope = rememberCoroutineScope()
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        HabitTypeItem(
            modifier = Modifier.weight(0.33f),
            text = stringResource(R.string.common_habits),
            enabled = pagerState.currentPage == 0
        ) {
            coroutineScope.launch {
                pagerState.animateScrollToPage(0, animationSpec = tween(500))
            }
        }

        HabitTypeItem(
            modifier = Modifier.weight(0.33f),
            text = stringResource(R.string.self_added),
            enabled = pagerState.currentPage == 1
        ) {
            coroutineScope.launch {
                pagerState.animateScrollToPage(1, animationSpec = tween(500))
            }
        }
    }
}