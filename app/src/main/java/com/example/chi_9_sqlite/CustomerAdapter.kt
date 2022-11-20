package com.example.chi_9_sqlite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chi_9_sqlite.data.Customer
import com.example.chi_9_sqlite.databinding.CustomerItemBinding

class CustomerAdapter : RecyclerView.Adapter<CustomerAdapter.CustomerHolder>() {
    private lateinit var customers: List<Customer>
    private var version = 1

    fun updateVersion(newVersion: Int){
        this.version = newVersion
        notifyDataSetChanged()
    }

    fun setupCustomersList(list: List<Customer>) {
        this.customers = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerHolder {
        val binding = CustomerItemBinding.inflate(
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

    inner class CustomerHolder(private val binding: CustomerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(customer: Customer) {
            binding.clientItemId.text = customer.id.toString()
            binding.clientItemName.text = customer.name
            if(version == 2){
                binding.clientItemAge.text = customer.age.toString()
                binding.clientItemAge.visibility = View.VISIBLE
            }
        }
    }
}