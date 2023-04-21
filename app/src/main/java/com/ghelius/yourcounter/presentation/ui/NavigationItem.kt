package com.ghelius.yourcounter.presentation.ui

import com.ghelius.yourcounter.R

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    object New : NavigationItem("new", R.drawable.shopping_cart_checkout_24px,"New")
    object History : NavigationItem("history", R.drawable.calendar_month_24px, "History")
    object Bindings : NavigationItem("bindings", R.drawable.sync_alt_24px, "Bindings")
    object Test: NavigationItem("test", R.drawable.build_24px, "Test")
}