package com.thebrownfoxx.petrealm.ui.screens.owners

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thebrownfoxx.petrealm.models.Owner
import com.thebrownfoxx.petrealm.models.Sample
import com.thebrownfoxx.petrealm.ui.components.RemoveDialogState
import com.thebrownfoxx.petrealm.ui.components.RemoveDialogStateChangeListener
import com.thebrownfoxx.petrealm.ui.components.SearchableLazyColumnScreen
import com.thebrownfoxx.petrealm.ui.components.getListState
import com.thebrownfoxx.petrealm.ui.screens.owners.components.EditOwnerDialog
import com.thebrownfoxx.petrealm.ui.screens.owners.components.RemoveOwnerDialog
import com.thebrownfoxx.petrealm.ui.screens.owners.components.SwipeableOwnerCard
import com.thebrownfoxx.petrealm.ui.screens.owners.state.EditOwnerDialogState
import com.thebrownfoxx.petrealm.ui.screens.owners.state.EditOwnerDialogStateChangeListener
import com.thebrownfoxx.petrealm.ui.theme.AppTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OwnersScreen(
    owners: List<Owner>?,
    selectedOwner: Owner?,
    onSelectedOwnerChange: (Owner?) -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    editDialogState: EditOwnerDialogState,
    editDialogStateChangeListener: EditOwnerDialogStateChangeListener,
    removeDialogState: RemoveDialogState<Owner>,
    removeDialogStateChangeListener: RemoveDialogStateChangeListener<Owner>,
    modifier: Modifier = Modifier,
) {
    SearchableLazyColumnScreen(
        modifier = modifier,
        topBarExpandedContent = { Text(text = "Owners") },
        searchQuery = searchQuery,
        onSearchQueryChange = onSearchQueryChange,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        listState = owners.getListState(
            emptyText = "No owners registered",
            searching = searchQuery != "",
        ),
    ) {
        items(
            items = owners ?: emptyList(),
            key = { it.id }
        ) { owner ->
            val selected = owner == selectedOwner

            SwipeableOwnerCard(
                owner = owner,
                expanded = selected,
                onClick = {
                    if (!selected) onSelectedOwnerChange(owner) else onSelectedOwnerChange(null)
                },
                onEdit = { editDialogStateChangeListener.onInitiateEdit(owner) },
                onInitiateRemove = { removeDialogStateChangeListener.onInitiateRemove(owner) },
                contentPadding = PaddingValues(horizontal = 16.dp),
                modifier = Modifier.animateItemPlacement(),
            )
        }
    }
    EditOwnerDialog(
        state = editDialogState,
        stateChangeListener = editDialogStateChangeListener,
    )
    RemoveOwnerDialog(
        state = removeDialogState,
        stateChangeListener = removeDialogStateChangeListener,
    )
}

@Preview
@Composable
fun OwnersScreenPreview() {
    AppTheme {
        OwnersScreen(
            owners = Sample.Owners,
            selectedOwner = null,
            onSelectedOwnerChange = {},
            searchQuery = "",
            onSearchQueryChange = {},
            editDialogState = EditOwnerDialogState.Hidden,
            editDialogStateChangeListener = EditOwnerDialogStateChangeListener(
                onInitiateEdit = {},
                onOwnerNameChange = {},
                onCancelEdit = {},
                onSave = {},
            ),
            removeDialogState = RemoveDialogState.Hidden(),
            removeDialogStateChangeListener = RemoveDialogStateChangeListener(
                onInitiateRemove = { true },
                onCancelRemove = {},
                onRemove = {},
            ),
        )
    }
}