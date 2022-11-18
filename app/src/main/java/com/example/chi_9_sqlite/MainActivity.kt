package com.example.chi_9_sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chi_9_sqlite.data.Book
import com.example.chi_9_sqlite.data.Client
import com.example.chi_9_sqlite.data.Order
import com.example.chi_9_sqlite.data.OrderBook
import com.example.chi_9_sqlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var dbManager: DBManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbManager = DBManager(this)
        setButtonsOnClickListeners()
    }

    private fun setButtonsOnClickListeners() {
        binding.buttonCreate.setOnClickListener {
            dbManager?.insertBooks(
                listOf(
                    Book(1, "Bible", "Good"),
                    Book(2, "Hiba revut' voly", "Ivan Nechyj-Levytskyj"),
                    Book(3, "Kobzar", "Taras Shevchenko")
                )
            )
            dbManager?.insertClients(
                listOf(
                    Client(1, "Nadija")
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

        binding.buttonDeleteAll.setOnClickListener {
            dbManager?.deleteAll()
        }
    }
}