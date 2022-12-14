package com.daviper.sponsorvisa.adapter

import androidx.recyclerview.widget.DiffUtil
import com.daviper.sponsorvisa.data.Company

class CompanyItemDiffCallback : DiffUtil.ItemCallback<Company>() {
    override fun areItemsTheSame(oldItem: Company, newItem: Company): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Company, newItem: Company): Boolean {
        return oldItem == newItem
    }
}