package com.example.chi_9_sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chi_9_sqlite.data.Book
import com.example.chi_9_sqlite.data.Customer
import com.example.chi_9_sqlite.data.Order
import com.example.chi_9_sqlite.data.OrderBook
import com.example.chi_9_sqlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var dbManager: DBManager? = null
    private var customerAdapter: CustomerAdapter? = null
    private var cbAdapter: JoinedDataAdapter? = null
    var version = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbManager = DBManager(this)
        setButtonsOnClickListeners()
    }

    private fun setButtonsOnClickListeners() {
        binding.buttonCreate.setOnClickListener {
            version = 1
            deleteAll()
            dbManager?.dataBaseHelper?.onUpgrade(dbManager?.dataBaseHelper?.writableDatabase, 1, 1)
            dbManager?.insertBooks(
                listOf(
                    Book(1, "Bible", "Good"),
                    Book(2, "Hiba revut' voly", "Ivan Nechyj-Levytskyj"),
                    Book(3, "Kobzar", "Taras Shevchenko")
                )
            )
            dbManager?.insertCustomers(
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

        binding.buttonSetupCustomers.setOnClickListener {
            setupCustomers(version)
        }

        binding.buttonDeleteAll.setOnClickListener {
            deleteAll()
        }

        binding.buttonJoin.setOnClickListener {
            setupJoinedRecyclerview()
        }

        binding.buttonMigrate.setOnClickListener {
            version = 2
            dbManager?.dataBaseHelper?.onUpgrade(dbManager?.dataBaseHelper?.writableDatabase, 1, 2)
            dbManager = DBManager(this)
            it.isClickable = false
        }

        binding.buttonAddTrigger.setOnClickListener {
            dbManager?.createTrigger()
        }

        binding.buttonAddNewCustomer.setOnClickListener {
            dbManager?.insertCustomers(
                listOf(
                    Customer(2, "Sania")
                )
            )
        }
    }

    private fun setupCustomers(version: Int) {
        customerAdapter = CustomerAdapter()
        dbManager?.fetchCustomers(version)?.let {
            customerAdapter?.setupCustomersList(it)
            customerAdapter?.updateVersion(version)
            binding.customersList.layoutManager = LinearLayoutManager(this)
            binding.customersList.addItemDecoration(
                DividerItemDecoration(
                    this,
                    LinearLayoutManager(this).orientation
                )
            )
            binding.customersList.adapter = customerAdapter
            binding.layoutCustomers.visibility = View.VISIBLE
        }
    }

    private fun setupJoinedRecyclerview() {
        cbAdapter = JoinedDataAdapter()
        dbManager?.fetchJoinedData()?.let {
            cbAdapter?.setupJoinedDataList(it)
            binding.customersBooksList.layoutManager = LinearLayoutManager(this)
            binding.customersBooksList.addItemDecoration(
                DividerItemDecoration(
                    this,
                    LinearLayoutManager(this).orientation
                )
            )
            binding.customersBooksList.adapter = cbAdapter
            binding.layoutCustomersBooks.visibility = View.VISIBLE
        }
    }

    private fun deleteAll() {
        dbManager?.deleteAll()

        binding.layoutCustomers.visibility = View.GONE
        customerAdapter?.setupCustomersList(emptyList())
        binding.customersList.adapter = customerAdapter

        binding.layoutCustomersBooks.visibility = View.GONE
        cbAdapter?.setupJoinedDataList(emptyList())
        binding.customersBooksList.adapter = cbAdapter
    }
}