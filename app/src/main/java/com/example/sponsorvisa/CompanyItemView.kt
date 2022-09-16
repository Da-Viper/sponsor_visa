package com.example.sponsorvisa

import android.content.Context
import android.widget.TextView
import com.example.sponsorvisa.model.Company
import com.google.android.material.card.MaterialCardView

class CompanyItemView(context: Context): MaterialCardView(context) {

    private val tvName: TextView
    private val tvCity: TextView
    private val tvRating: TextView
    private val tvRoute: TextView


    init {
        inflate(context, R.layout.item_company, this)
        val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        params.marginStart = 40
        params.marginEnd = 40
        layoutParams = params
        tvCity = findViewById(R.id.tvCity)
        tvName = findViewById(R.id.tvCompanyName)
        tvRating = findViewById(R.id.tvRating)
        tvRoute = findViewById(R.id.tvRoute)
    }

    fun setItem(listItem: Company) {
        tvName.text = listItem.name
        tvCity.text = listItem.city
        tvRating.text = listItem.rating
        tvRoute.text = listItem.route
    }
}