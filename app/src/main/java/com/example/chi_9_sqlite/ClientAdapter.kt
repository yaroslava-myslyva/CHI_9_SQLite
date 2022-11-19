package com.example.chi_9_sqlite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chi_9_sqlite.data.Customer
import com.example.chi_9_sqlite.databinding.CustomerItemFirstBinding

class CustomerAdapter : RecyclerView.Adapter<CustomerAdapter.CustomerHolder>() {
    private lateinit var customers: List<Customer>

    fun updateList(photosUpd: List<Customer>) {
        customers = photosUpd
        notifyDataSetChanged()
    }

    fun updateItem(position: Int){
        notifyItemChanged(position)
    }

    fun setupCustomersList(list :List<Customer>){
        this.customers = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerHolder {
        val binding = com.example.chi_9_sqlite.databinding.CustomerItemFirstBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return CustomerHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomerHolder, position: Int) {
        val client = customers[position]
        holder.bind(client)
    }

    override fun getItemCount(): Int {
        return customers.size
    }

    inner class CustomerHolder(private val binding: CustomerItemFirstBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(customer: Customer) {
          binding.clientItem1Id.text = customer.id.toString()
            binding.clientItem1Name.text = customer.name
        }
    }
}