package org.jetbrains.kotlinconf.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import kotlinconfapp.shared.generated.resources.Res
import kotlinconfapp.shared.generated.resources.bookmark
import kotlinconfapp.shared.generated.resources.bookmark_active
import kotlinconfapp.shared.generated.resources.speakers
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.kotlinconf.AppController
import org.jetbrains.kotlinconf.SessionCardView
import org.jetbrains.kotlinconf.Speaker
import org.jetbrains.kotlinconf.ui.components.AsyncImage
import org.jetbrains.kotlinconf.ui.components.NavigationBar
import org.jetbrains.kotlinconf.ui.theme.grey50
import org.jetbrains.kotlinconf.ui.theme.grey5Black
import org.jetbrains.kotlinconf.ui.theme.greyGrey20
import org.jetbrains.kotlinconf.ui.theme.greyGrey5
import org.jetbrains.kotlinconf.ui.theme.greyWhite
import org.jetbrains.kotlinconf.ui.theme.orange
import org.jetbrains.kotlinconf.ui.theme.whiteGrey

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SpeakersDetailsScreen(
    controller: AppController,
    speakers: List<Speaker>,
    focusedSpeakerId: String
) {
    val sessions: List<SessionCardView> by controller.sessions.collectAsState()

    val state = rememberLazyListState()
    if (focusedSpeakerId.isNotEmpty()) {
        val index = speakers.indexOfFirst { it.id == focusedSpeakerId }
        if (index >= 0) {
            LaunchedEffect(Unit) {
                state.scrollToItem(index)
            }
        }
    }

    Column(Modifier.fillMaxWidth()) {
        NavigationBar(
            title = stringResource(Res.string.speakers),
            isLeftVisible = true,
            onLeftClick = {
                controller.back()
            },
            isRightVisible = false
        )
        LazyColumn(state = state) {
            items(speakers) {
                val speakerSessions =
                    sessions.filter { session -> session.speakerIds.contains(it.id) }

                SpeakerDetailed(
                    it.name,
                    it.position,
                    it.photoUrl,
                    it.description,
                    speakerSessions,
                    showSession = { session ->
                        controller.showSession(session)
                    },
                    onFavoriteClick = { session ->
                        controller.toggleFavorite(session.id)
                    }
                )
            }
        }
    }
}

@Composable
private fun SpeakerDetailed(
    name: String,
    position: String,
    photoUrl: String,
    description: String,
    sessions: List<SessionCardView>,
    showSession: (String) -> Unit,
    onFavoriteClick: (SessionCardView) -> Unit = {},
) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.grey5Black)
    ) {
        HDivider()
        Column(Modifier.padding(16.dp)) {
            Text(
                name, style = MaterialTheme.typography.h2.copy(
                    color = MaterialTheme.colors.greyGrey5
                )
            )

            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = position,
                style = MaterialTheme.typography.body2.copy(
                    color = MaterialTheme.colors.greyGrey20
                )
            )
        }

        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            imageUrl = photoUrl,
            contentDescription = "Speaker photo",
            contentScale = ContentScale.FillWidth,
        )
        Column(
            Modifier
                .fillMaxWidth()
        ) {
            Column(Modifier.padding(16.dp)) {
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = description,
                    style = MaterialTheme.typography.body2.copy(
                        color = MaterialTheme.colors.greyGrey20
                    )
                )

                if (sessions.isNotEmpty()) {
                    Text(
                        "Talks: ", style = MaterialTheme.typography.h4.copy(
                            color = MaterialTheme.colors.greyGrey20
                        ), modifier = Modifier.padding(top = 24.dp)
                    )
                }
            }

            sessions.forEach {
                SessionCardCompact(
                    it.title,
                    it.locationLine,
                    it.timeLine,
                    it.isFavorite,
                    onSessionClick = { showSession(it.id) }
                ) { onFavoriteClick(it) }
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun SessionCardCompact(
    title: String,
    locationLine: String,
    timeLine: String,
    favorite: Boolean,
    onSessionClick: () -> Unit,
    onFavoriteClick: () -> Unit,
) {
    Column(
        Modifier
            .background(MaterialTheme.colors.whiteGrey)
            .clickable {
                onSessionClick()
            }
    ) {
        HDivider()
        Box(Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        end = 48.dp,
                        top = 16.dp,
                        bottom = 16.dp
                    )
                    .fillMaxWidth()
            ) {
                Text(
                    title,
                    style = MaterialTheme.typography.h4.copy(
                        color = MaterialTheme.colors.primary
                    )
                )
                LocationRow(location = locationLine, modifier = Modifier.padding(top = 16.dp))
                Text(
                    timeLine, style = MaterialTheme.typography.body2.copy(
                        color = grey50
                    ),
                    maxLines = 1
                )
            }
            Row {
                Spacer(modifier = Modifier.weight(1.0f))
                IconButton(onClick = {
                    onFavoriteClick()
                }) {
                    val icon = if (favorite) Res.drawable.bookmark_active else Res.drawable.bookmark
                    Icon(
                        painter = icon.painter(),
                        contentDescription = "bookmark",
                        tint = if (favorite) orange else MaterialTheme.colors.greyWhite
                    )
                }
            }
        }
    }
}
