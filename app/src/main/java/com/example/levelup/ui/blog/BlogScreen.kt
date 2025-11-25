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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.levelup.viewmodel.BlogViewModel

private val PrimaryBlue   = Color(0xFF1E90FF)
private val SecondaryNeon = Color(0xFF39FF14)
private val BgBlack       = Color(0xFF000000)
private val SurfaceDark   = Color(0xFF18181C)
private val OnSurface     = Color.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlogScreen(
    navController: NavHostController,
    vm: BlogViewModel = viewModel()
) {
    val state = vm.ui.value
    val ctx   = LocalContext.current

    LaunchedEffect(Unit) { vm.load() }

    Scaffold(
        containerColor = BgBlack,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = OnSurface
                        )
                    }
                },
                title = {
                    Text(
                        "Blog & Noticias",
                        fontWeight = FontWeight.Bold,
                        color = OnSurface
                    )
                },
                actions = {
                    IconButton(onClick = { navController.navigate("cart") }) {
                        Icon(
                            Icons.Default.ShoppingCart,
                            contentDescription = "Carrito",
                            tint = SecondaryNeon
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SurfaceDark,
                    titleContentColor = OnSurface
                )
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 12.dp)
        ) {

            if (state.isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    color = SecondaryNeon,
                    trackColor = SurfaceDark
                )
            }

            if (state.error != null) {
                Text(
                    text = "⚠ ${state.error}",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 12.dp),
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
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.elevatedCardColors(
                            containerColor = SurfaceDark
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                            // 🖼 Imagen de la noticia (si existe)
                            if (post.imageUrl != null) {
                                AsyncImage(
                                    model = post.imageUrl,
                                    contentDescription = post.title,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(180.dp)
                                )
                            }

                            Text(
                                post.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = OnSurface,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )

                            Text(
                                "${post.author} • ${post.date}",
                                style = MaterialTheme.typography.labelMedium,
                                color = PrimaryBlue
                            )

                            Text(
                                post.excerpt,
                                style = MaterialTheme.typography.bodyMedium,
                                color = OnSurface,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis
                            )

                            if (post.externalUrl != null) {
                                Text(
                                    "Leer más",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = SecondaryNeon,
                                    modifier = Modifier
                                        .padding(top = 4.dp)
                                        .clickable {
                                            val i = Intent(
                                                Intent.ACTION_VIEW,
                                                Uri.parse(post.externalUrl)
                                            )
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
