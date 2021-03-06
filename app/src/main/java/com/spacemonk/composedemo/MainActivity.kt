package com.spacemonk.composedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.airbnb.lottie.model.content.GradientColor
import com.google.accompanist.pager.ExperimentalPagerApi
import com.spacemonk.andromeda.compose.AndromedaComposeTheme
import com.spacemonk.andromeda.compose.navbar.AndromedaNavBar
import com.spacemonk.composedemo.exts.gradientBackground
import com.spacemonk.composedemo.model.Category
import com.spacemonk.composedemo.model.OffersModel
import com.spacemonk.composedemo.model.RecentOrder
import com.spacemonk.composedemo.ui.theme.parse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.selects.whileSelect

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
private val recentOrderData = listOf(
    RecentOrder(
        title = "Crocin 650",
        distributor = "Mercury Agencies",
        amountAfter = "$30",
        amountBefore = "$70",
        availability = "130 Available",
        imageUrl = "https://static2.medplusmart.com/products/_a17f3e_/DOLO0011_L.jpg"
    ),
    RecentOrder(
        title = "Festal N Strip Of 10 Tablet",
        distributor = "Mercury Agencies",
        amountAfter = "$30",
        amountBefore = "$70",
        availability = "130 Available",
        imageUrl = "https://static2.medplusmart.com/products/_25d684_/PRIM0007_T.jpg"
    )
)
private val availableStaticOffers = listOf(
    OffersModel(
        title = "SUN Pharma",
        subtitle = "Get 10% off for all the SUN Pharma purchase"
    ),
    OffersModel(
        title = "SUN Pharma 2",
        subtitle = "Get 20% off for all the SUN Pharma purchase"
    ),
    OffersModel(
        title = "SUN Pharma 3",
        subtitle = "Get 25% off for all the SUN Pharma purchase"
    )
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndromedaComposeTheme {
                MainScreen()
            }
        }
    }
}

@Preview(name = "Full Screen")
@Composable
fun MainScreenDemo() {
    AndromedaComposeTheme {
        MainScreen()
    }
}


@Composable
fun MainScreen(
    categories: List<Category> = staticCategories,
    recentOrders: List<RecentOrder> = recentOrderData,
    availableOffers: List<OffersModel> = availableStaticOffers
) {
    Scaffold(
        topBar = {
            AndromedaNavBar(
                title = "Andromeda",
                backgroundColor = AndromedaComposeTheme.colors.fillActiveSecondary
            )
        }
    ) {
        Column {
            SearchBarCard()
            CategoriesCarousel(categories)
            RecentOrders(
                recentOrders,
                modifier = Modifier.padding(0.dp, 16.dp)
            )
            Offers(offers = availableOffers)
        }
    }
}

@Composable
fun RecentOrders(
    recentOrders: List<RecentOrder>,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.padding(16.dp, 0.dp)
        ) {
            Text(
                text = "Recent Items",
                color = Color.parse("#212121"),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "View all",
                textAlign = TextAlign.End,
                color = Color.parse("#0064bf"),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        Text(
            text = "from your past orders",
            color = Color.parse("#212121"),
            fontSize = 14.sp,
            modifier = Modifier.padding(16.dp, 0.dp)
        )
        LazyColumn {
            itemsIndexed(
                items = recentOrders,
                itemContent = { _, item ->
                    RecentItemCard(item)
                }
            )
        }
    }
}

@Composable
fun RecentItemCard(
    orderItem: RecentOrder,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
    ) {
        Card(
            backgroundColor = Color.parse("#f9f9f9"),
            shape = RoundedCornerShape(12.dp),
            elevation = 0.dp,
            modifier = Modifier
                .height(80.dp)
                .width(70.dp)
        ) {
            Image(
                painter = rememberImagePainter(
                    data = orderItem.imageUrl
                ),
                contentDescription = orderItem.title,
                modifier = Modifier.padding(16.dp, 0.dp, 0.dp, 0.dp)
            )
        }
        Column {
            Text(
                text = orderItem.title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.parse("#222222"),
                modifier = Modifier.padding(16.dp, 8.dp, 16.dp, 4.dp)
            )
            Text(
                text = orderItem.distributor,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                color = Color.parse("#222222"),
                modifier = Modifier.padding(16.dp, 0.dp, 16.dp, 4.dp)
            )
            Text(
                text = orderItem.amountAfter,
                fontSize = 10.sp,
                color = Color.parse("#222222"),
                modifier = Modifier.padding(16.dp, 0.dp, 16.dp, 4.dp)
            )
            Text(
                text = orderItem.availability,
                fontSize = 10.sp,
                color = Color.parse("#222222"),
                modifier = Modifier.padding(16.dp, 0.dp, 16.dp, 8.dp)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        OutlinedButton(
            onClick = { /*TODO: Handle click*/ },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.Bottom)
        ) {
            Text(text = "Add")
        }
    }
}

@Composable
fun SearchBarCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        backgroundColor = Color.parse("#f9f9f9"),
        shape = RoundedCornerShape(16.dp),
        elevation = 0.dp
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
    LazyRow(modifier = Modifier.padding(8.dp, 0.dp)) {
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
                    data = categoryData.imageUrl
                ),
                contentDescription = categoryData.title,
                modifier = Modifier
                    .size(60.dp)
                    .padding(12.dp)
            )
        }
        Text(
            text = categoryData.title,
            fontSize = 12.sp,
            color = Color.parse("#666666")
        )
    }
}

@ExperimentalCoroutinesApi
@Composable
fun Offers(offers: List<OffersModel>) {
    LazyRow {
        itemsIndexed(
            items = offers,
            itemContent = { _, item ->
                OffersCard(item)
            }
        )
        /*whileSelect {

        }*/
    }
    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        itemsIndexed(
            items = offers,
            itemContent = { _, _ ->
                Text(text = "???")
            }
        )
    }
}

@Composable
fun OffersCard(item: OffersModel) {
    Card(
        modifier = Modifier
            .width(330.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier
                .gradientBackground(
                    colors = listOf(
                        Color.parse("#00b08c"),
                        Color.parse("#35e2bf")
                    ),
                    angle = 270f
                )
        ) {
            Text(
                text = item.title,
                fontSize = 12.sp,
                color = AndromedaComposeTheme.colors.staticWhiteFontColor,
                modifier = Modifier.padding(top = 16.dp, start = 16.dp, bottom = 8.dp, end = 16.dp)
            )
            Text(
                text = item.subtitle,
                fontSize = 22.sp,
                modifier = Modifier.padding(top = 8.dp, start = 16.dp, bottom = 16.dp, end = 16.dp),
                color = AndromedaComposeTheme.colors.staticWhiteFontColor,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
