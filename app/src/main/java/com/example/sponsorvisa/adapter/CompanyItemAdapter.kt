package com.example.sponsorvisa.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sponsorvisa.CompanyItemView
import com.example.sponsorvisa.model.Company

class CompanyItemAdapter internal constructor(private val context: Context) :
    ListAdapter<Company, CompanyItemAdapter.CompanyItemViewHolder>(
        CompanyItemDiffCallback()
    ) {


    inner class CompanyItemViewHolder(val companyItemView: CompanyItemView) :
        RecyclerView.ViewHolder(companyItemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyItemViewHolder {
        return CompanyItemViewHolder(CompanyItemView(context))
    }

    override fun onBindViewHolder(holder: CompanyItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.companyItemView.setItem(item)
    }
}