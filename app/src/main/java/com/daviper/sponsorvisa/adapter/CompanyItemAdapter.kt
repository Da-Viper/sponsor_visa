package com.daviper.sponsorvisa.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.daviper.sponsorvisa.data.Company
import com.daviper.sponsorvisa.databinding.ItemCompanyBinding

class CompanyItemAdapter :
    PagingDataAdapter<Company, CompanyItemAdapter.CompanyItemViewHolder>(CompanyItemDiffCallback()) {

    inner class CompanyItemViewHolder(private val binding: ItemCompanyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(company: Company) {
            binding.tvCompanyName.text = company.name
            binding.tvCity.text = company.city
            binding.tvRating.text = company.rating
            binding.tvRoute.text = company.route
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyItemViewHolder {
        val binding = ItemCompanyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CompanyItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CompanyItemViewHolder, position: Int) {
        val item = getItem(position) ?: return
        holder.bindItem(item)
    }
}