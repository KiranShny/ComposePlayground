package com.spacemonk.composedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.spacemonk.composedemo.model.Category
import com.spacemonk.composedemo.ui.theme.ComposeDemoTheme
import com.spacemonk.composedemo.ui.theme.parse

class MainActivity : ComponentActivity() {
    private val staticCategories = listOf(
        Category(
            title = "Generic",
            imageUrl = "https://pharmacyone-images.s3.ap-south-1.amazonaws.com/app-images/capsule.png",
            backgroundColor = "#fff6f2"
        ),
        Category(
            title = "Ayurvedic",
            imageUrl = "https://pharmacyone-images.s3.ap-south-1.amazonaws.com/app-images/ayurveda.png",
            backgroundColor = "#e9fff5"
        ),
        Category(
            title = "Vaccines",
            imageUrl = "https://pharmacyone-images.s3.ap-south-1.amazonaws.com/app-images/medicine.png",
            backgroundColor = "#f1fcff"
        ),
        Category(
            title = "Surgical",
            imageUrl = "https://pharmacyone-images.s3.ap-south-1.amazonaws.com/app-images/surgical-instrument.png",
            backgroundColor = "#f1f5ff"
        ),
        Category(
            title = "Cosmetics",
            imageUrl = "https://pharmacyone-images.s3.ap-south-1.amazonaws.com/app-images/skincare.png",
            backgroundColor = "#fffde9"
        ),
        Category(
            title = "SADSSDAJ",
            imageUrl = "https://pharmacyone-images.s3.ap-south-1.amazonaws.com/app-images/skincare.png",
            backgroundColor = "#fffdee"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeDemoTheme {
                MainScreen(
                    categories = staticCategories
                )
            }
        }
    }
}

@Composable
fun MainScreen(categories: List<Category>) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Andromeda") },
                backgroundColor = MaterialTheme.colors.background
            )
        }
    ) {
        Column {
            SearchBarCard()
            CategoriesCarousel(categories)
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun SearchBarCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        backgroundColor = Color.parse("#f9f9f9"),
        shape = RoundedCornerShape(16.dp),
        elevation = 12.dp
    ) {
        Row {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = "Search",
                tint = Color.Black,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = "Search",
                modifier = Modifier.padding(0.dp, 16.dp, 16.dp, 16.dp)
            )
        }
    }
}

@Composable
fun CategoriesCarousel(categories: List<Category>) {
    LazyRow {
        itemsIndexed(
            items = categories,
            itemContent = { _, item ->
                CategoryCard(categoryData = item)
            }
        )
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun CategoryCard(categoryData: Category) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp, 16.dp, 8.dp, 0.dp)
    ) {
        Card(
            backgroundColor = Color.parse(categoryData.backgroundColor),
            shape = RoundedCornerShape(12.dp),
            elevation = 0.dp
        ) {
            Image(
                painter = rememberImagePainter(
                    data = categoryData.imageUrl,
                    builder = {

                    }
                ),
                contentDescription = categoryData.title,
                modifier = Modifier
                    .size(60.dp),
                contentScale = ContentScale.Crop
            )
        }
        Text(text = categoryData.title)
    }
}