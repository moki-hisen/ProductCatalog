package com.mk.productcatalog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mk.productcatalog.ui.navigation.ProductCatalogApp
import com.mk.productcatalog.ui.productlist.ProductListScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ProductCatalogApp()
        }
    }
}