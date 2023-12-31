package com.thebrownfoxx.petrealm.ui.screens.owners.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Edit
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.components.FilledTonalButton
import com.thebrownfoxx.components.HorizontalSpacer
import com.thebrownfoxx.components.IconButton
import com.thebrownfoxx.components.VerticalSpacer
import com.thebrownfoxx.components.extension.Elevation
import com.thebrownfoxx.components.extension.minus
import com.thebrownfoxx.petrealm.models.Owner
import com.thebrownfoxx.petrealm.models.Sample
import com.thebrownfoxx.petrealm.ui.components.icon
import com.thebrownfoxx.petrealm.ui.theme.AppTheme

@Composable
fun ColumnScope.OwnerCardContent(
    owner: Owner,
    expanded: Boolean,
    modifier: Modifier = Modifier,
    onRemove: (() -> Unit)? = null,
    onEdit: (() -> Unit)? = null,
) {
    val vetoedExpanded = expanded && (onRemove != null || owner.pets.isNotEmpty())

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = owner.name.ifBlank { "Owner name" },
                style = MaterialTheme.typography.titleMedium,
            )
            AnimatedVisibility(visible = !vetoedExpanded || owner.pets.isEmpty()) {
                Text(
                    text = "${owner.pets.size} ${if (owner.pets.size == 1) "pet" else "pets"}",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
        HorizontalSpacer(width = 16.dp)
        AnimatedVisibility(
            visible = expanded && onEdit != null,
            enter = expandIn() + fadeIn(),
            exit = shrinkOut() + fadeOut(),
        ) {
            IconButton(
                imageVector = Icons.TwoTone.Edit,
                contentDescription = null,
                onClick = { onEdit?.invoke() },
            )
        }
    }
    AnimatedVisibility(visible = vetoedExpanded) {
        Surface(tonalElevation = Elevation.level(2)) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
            ) {
                AnimatedVisibility(visible = owner.pets.isNotEmpty()) {
                    Column {
                        for (pet in owner.pets) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = pet.type.icon,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                )
                                HorizontalSpacer(width = 4.dp)
                                Text(
                                    text = pet.name,
                                    style = MaterialTheme.typography.bodySmall,
                                )
                            }
                        }
                        if (onRemove != null) {
                            VerticalSpacer(height = 16.dp)
                        }
                    }
                }
                if (onRemove != null) {
                    FilledTonalButton(
                        text = "Unregister",
                        onClick = onRemove,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer,
                            contentColor = MaterialTheme.colorScheme.onErrorContainer,
                        )
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OwnerCard(
    owner: Owner,
    expanded: Boolean,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        onClick = onClick,
    ) {
        OwnerCardContent(
            owner = owner,
            expanded = expanded,
            onEdit = onEdit,
            onRemove = onRemove,
            modifier = Modifier
                .padding(PaddingValues(16.dp) - PaddingValues(end = 8.dp))
                .fillMaxWidth(),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OwnerCard(
    owner: Owner,
    expanded: Boolean,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier) {
        OwnerCardContent(
            owner = owner,
            expanded = expanded,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        )
    }
}

@Preview
@Composable
fun OwnerCardPreview() {
    AppTheme {
        OwnerCard(
            owner = Sample.Owner,
            expanded = false,
            onClick = {},
            onEdit = {},
            onRemove = {},
        )
    }
}

@Preview
@Composable
fun OwnerCardExpandedPreview() {
    AppTheme {
        OwnerCard(
            owner = Sample.Owner,
            expanded = true,
            onClick = {},
            onEdit = {},
            onRemove = {},
        )
    }
}