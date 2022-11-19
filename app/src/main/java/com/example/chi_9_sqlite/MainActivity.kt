package com.example.chi_9_sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chi_9_sqlite.data.Book
import com.example.chi_9_sqlite.data.Customer
import com.example.chi_9_sqlite.data.Order
import com.example.chi_9_sqlite.data.OrderBook
import com.example.chi_9_sqlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var dbManager: DBManager? = null
    private var customerAdapter :CustomerAdapter? = null
    private var cbAdapter :JoinedDataAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbManager = DBManager(this)
        setButtonsOnClickListeners()
    }

    private fun setButtonsOnClickListeners() {
        binding.buttonCreate.setOnClickListener {
            dbManager?.dataBaseHelper?.onUpgrade(dbManager?.dataBaseHelper?.writableDatabase, 1, 1)
            dbManager?.insertBooks(
                listOf(
                    Book(1, "Bible", "Good"),
                    Book(2, "Hiba revut' voly", "Ivan Nechyj-Levytskyj"),
                    Book(3, "Kobzar", "Taras Shevchenko")
                )
            )
            dbManager?.insertClients(
                listOf(
                    Customer(1, "Nadija")
                )
            )
            dbManager?.insertOrders(
                listOf(
                    Order(1, 1),
                    Order(2, 1)
                )
            )
           dbManager?.insertOrderBooks(
               listOf(
                   OrderBook(1, 1),
                   OrderBook(1, 2),
                   OrderBook(2, 2),
                   OrderBook(2, 3)
               )
           )
        }

        binding.buttonSetupRecyclerView.setOnClickListener {
            setupRecyclerView()
        }

        binding.buttonDeleteAll.setOnClickListener {
            dbManager?.deleteAll()
            binding.tvNameCustomersList.visibility = View.GONE
            binding.customersList.visibility = View.GONE
            customerAdapter?.setupCustomersList(emptyList())
            binding.customersList.adapter = customerAdapter
        }
    }

    private fun setupRecyclerView(){
        binding.tvNameCustomersList.visibility = View.VISIBLE
        customerAdapter = CustomerAdapter()
        dbManager?.fetchFilms()?.let {
            customerAdapter?.setupCustomersList(it)
            binding.customersList.layoutManager = LinearLayoutManager(this)
            binding.customersList.adapter = customerAdapter
            binding.customersList.visibility = View.VISIBLE
        }

        binding.tvNameCbList.visibility = View.VISIBLE
        cbAdapter = JoinedDataAdapter()
        dbManager?.fetchJoinedData()?.let {
            cbAdapter?.setupJoinedDataList(it)
            binding.customersBooksList.layoutManager = LinearLayoutManager(this)
            binding.customersBooksList.adapter = cbAdapter
            binding.customersBooksList.visibility = View.VISIBLE
        }

    }
}