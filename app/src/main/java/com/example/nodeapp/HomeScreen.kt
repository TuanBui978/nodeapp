package com.example.nodeapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    var showDialog by remember {
        mutableStateOf(false)
    }
    val todos = remember {
        mutableStateListOf<Todo>()
    }
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = true) {
        scope.launch {
            val list = FireBaseRepo.instance.getTodos(Session.instance.user!!.uid)
            todos.addAll(list)
        }
    }
    if (showDialog) {
        Dialog(onDismissRequest = {}, ) {
            Card {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    var header by remember {
                        mutableStateOf("")
                    }
                    var content by remember {
                        mutableStateOf("")
                    }
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        value = header,
                        onValueChange = {
                            header = it
                        },
                        label = {
                            Text(text = "Header")
                        }
                    )
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        value = content,
                        onValueChange = {
                            content = it
                        },
                        label = {
                            Text(text = "Content")
                        }
                    )
                    Button(onClick = {
                        scope.launch {
                            todos.add(Todo(header = header, content = content, isDone = false))
                            FireBaseRepo.instance.addTodos(todos)
                        }
                        showDialog = false
                    }) {
                        Text(text = "OK")
                    }
                }
            }
        }
    }
    Scaffold(modifier =  modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                showDialog = true
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add to do")
            }
        },
        floatingActionButtonPosition = FabPosition.End) {
        paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Text(text = "", modifier =  Modifier.padding(10.dp))
            LazyColumn(contentPadding = PaddingValues(horizontal = 20.dp, vertical = 5.dp)) {
                todos.forEach {
                    todo->
                    item {
                        TodoItem(modifier = Modifier.fillMaxWidth(), todo = todo)
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TodoItem(modifier: Modifier = Modifier, todo: Todo = Todo(header = "Do SomeThing", content = "Do SomeThing With firebase bla bla bla bla bla bla asdadsasdasd ", isDone = false)) {
    Card(modifier = modifier.padding(horizontal = 5.dp, vertical = 3.dp), elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)) {
        Row(modifier = Modifier.padding(8.dp)) {
            var isDone by remember {
                mutableStateOf(todo.isDone?: false)
            }
            Column(modifier = Modifier.weight(1f)) {
                todo.header?.let { Text(text = it, maxLines = 1, fontSize = 18.sp, fontWeight = FontWeight.Bold) }
                todo.content?.let { Text(text = it, maxLines = 3, fontStyle = FontStyle.Italic) }
            }
            Checkbox(checked = isDone, onCheckedChange = {
                isDone = it
                }
            )
        }
    }
}