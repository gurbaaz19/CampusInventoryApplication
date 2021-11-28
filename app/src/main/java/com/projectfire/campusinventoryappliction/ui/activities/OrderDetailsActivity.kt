package com.projectfire.campusinventoryappliction.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.projectfire.campusinventoryappliction.Constants
import com.projectfire.campusinventoryappliction.R
import com.projectfire.campusinventoryappliction.models.Order
import com.projectfire.campusinventoryappliction.ui.adapters.CartItemsPlaceOrderListAdapter
import kotlinx.android.synthetic.main.activity_order_details.*
import kotlinx.android.synthetic.main.fragment_transaction.*

class OrderDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)

        setupActionBar()

        var orderDetails: Order

        if (intent.hasExtra(Constants.EXTRA_ORDER_DETAILS)) {
            orderDetails = intent.getParcelableExtra<Order>(Constants.EXTRA_ORDER_DETAILS)!!
            getUI(orderDetails)
        }

    }

    private fun setupActionBar() {                       // Remember this method
        setSupportActionBar(toolbar_order_details_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
            actionBar.title = ""
        }
        toolbar_order_details_activity.setNavigationOnClickListener { onBackPressed() }
    }

    private fun getUI(order: Order) {
        tv_order_id.text = order.order_id
        tv_order_full_name.text = order.user_name
        tv_order_date.text = order.date
        tv_order_address.text = "${order.address}, ${order.bitsid}"
        tv_order_mobile_number.text =order.mobile
        tv_sub_total.text = "₹${order.sub_total_amount}"
        tv_order_service.text= "₹${order.service_charge}"
        tv_total_amount.text= "₹${order.total_amount}"

        rv_ordered_items.layoutManager = LinearLayoutManager(this@OrderDetailsActivity,LinearLayoutManager.HORIZONTAL,false)
        rv_ordered_items.setHasFixedSize(true)

        rv_ordered_items.adapter =
            CartItemsPlaceOrderListAdapter(this@OrderDetailsActivity, order.items)
    }
}