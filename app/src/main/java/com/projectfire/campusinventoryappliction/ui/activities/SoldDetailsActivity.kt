package com.projectfire.campusinventoryappliction.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.projectfire.campusinventoryappliction.Constants
import com.projectfire.campusinventoryappliction.GlideLoader
import com.projectfire.campusinventoryappliction.R
import com.projectfire.campusinventoryappliction.models.Sold
import kotlinx.android.synthetic.main.activity_sold_details.*

class SoldDetailsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sold_details)

        var productDetails: Sold

        if (intent.hasExtra(Constants.EXTRA_SOLD_DETAILS)) {
            productDetails =
                intent.getParcelableExtra(Constants.EXTRA_SOLD_DETAILS)!!

            setupActionBar()
            getUI(productDetails)
        }
    }

    private fun setupActionBar() {                       // Remember this method
        setSupportActionBar(toolbar_sold_details_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
            actionBar.title = ""
        }
        toolbar_sold_details_activity.setNavigationOnClickListener { onBackPressed() }
    }

    private fun getUI(sold: Sold) {
        GlideLoader(this).loadProductPicture(sold.image, iv_sold_items)
        tv_sold_id.text = sold.order_id
        tv_sold_full_name.text = sold.name
        tv_sold_date.text = sold.order_date
        tv_sold_address.text = "${sold.address}"
        tv_sold_mobile_number.text = sold.mobile
        var totalCost = (((sold.price).toDouble()) * ((sold.sold_quantity).toDouble())).toString()
        tv_sold_quantity.text = sold.sold_quantity
        tv_per_amount.text = "₹${sold.price}"
        tv_sold_total_amount.text = "₹${totalCost}"
    }
}