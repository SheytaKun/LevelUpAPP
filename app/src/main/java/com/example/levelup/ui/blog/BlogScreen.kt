package com.example.levelup.ui.blog

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlogScreen(
    navController: NavHostController,
    vm: BlogViewModel = viewModel()
) {
    val state = vm.ui.value
    val ctx = LocalContext.current

    LaunchedEffect(Unit) { vm.load() }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                title = { Text("Blog & Noticias", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = { navController.navigate("cart") }) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito")
                    }
                }
            )
        }
    ) { inner ->
        Column(
            Modifier.padding(inner).fillMaxSize()
        ) {
            if (state.isLoading) {
                LinearProgressIndicator(Modifier.fillMaxWidth())
            }
            if (state.error != null) {
                Text(
                    text = "⚠ ${state.error}",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.posts, key = { it.id }) { post ->
                    ElevatedCard(
                        onClick = {
                            post.externalUrl?.let { url ->
                                val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                ctx.startActivity(i)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(post.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                            Text("${post.author} • ${post.date}", style = MaterialTheme.typography.labelMedium)
                            Text(post.excerpt, style = MaterialTheme.typography.bodyMedium)
                            if (post.externalUrl != null) {
                                Text(
                                    "Leer más",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(top = 6.dp).clickable {
                                        val i = Intent(Intent.ACTION_VIEW, Uri.parse(post.externalUrl))
                                        ctx.startActivity(i)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
