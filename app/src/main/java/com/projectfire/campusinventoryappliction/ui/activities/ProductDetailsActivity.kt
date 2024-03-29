package com.projectfire.campusinventoryappliction.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.projectfire.campusinventoryappliction.Constants
import com.projectfire.campusinventoryappliction.FirebaseFunctionsClass
import com.projectfire.campusinventoryappliction.GlideLoader
import com.projectfire.campusinventoryappliction.R
import com.projectfire.campusinventoryappliction.models.CartItem
import com.projectfire.campusinventoryappliction.models.Product
import kotlinx.android.synthetic.main.activity_product_details.*
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.item_sell_layout.view.*

class ProductDetailsActivity : BaseActivity(), View.OnClickListener {

    private var mProductID: String = ""
    private lateinit var mProductDetails: Product
    private var mProductOwnerID = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        setupActionBar()

        if (intent.hasExtra(Constants.EXTRA_PRODUCT_ID)) {
            mProductID = intent.getStringExtra(Constants.EXTRA_PRODUCT_ID)!!
        }

        if (intent.hasExtra(Constants.EXTRA_PRODUCT_OWNER_ID)) {
            mProductOwnerID = intent.getStringExtra(Constants.EXTRA_PRODUCT_OWNER_ID)!!
        }

        if (FirebaseFunctionsClass().getCurrentUserID() == mProductOwnerID) {
            btn_add_to_cart.visibility = View.GONE
            btn_go_to_cart.visibility = View.GONE
        } else {
            btn_add_to_cart.visibility = View.VISIBLE
        }

        getProductDetails()

        btn_add_to_cart.setOnClickListener(this)
        btn_go_to_cart.setOnClickListener(this)
    }

    private fun setupActionBar() {                       // Remember this method
        setSupportActionBar(toolbar_product_details_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
            actionBar.title = ""
        }

        toolbar_product_details_activity.setNavigationOnClickListener { onBackPressed() }
    }

    fun productDetailsSuccess(product: Product) {
        mProductDetails = product
        GlideLoader(this@ProductDetailsActivity).loadProductPicture(
            product.image,
            iv_product_detail_image
        )
        tv_product_details_title.text = product.title
        tv_product_details_publisher.text = product.publisher
        tv_product_details_price.text = "₹${product.price}"
        tv_product_details_description.text = product.description
        tv_product_details_available_quantity.text = product.stock_quantity

        when (product.condition) {
            Constants.GOOD -> {
                tv_product_details_condition.text = "Good Condition"
            }
            Constants.BAD -> {
                tv_product_details_condition.text = "Bad Condition"
            }
            Constants.AVERAGE -> {
                tv_product_details_condition.text = "Average Condition"
            }
        }


        if (product.stock_quantity.toInt() == 0) {
            hideProgressDialog()
            btn_add_to_cart.visibility = View.GONE
            tv_product_details_quantity.text = resources.getString(R.string.lbl_out_of_stock)
            tv_product_details_quantity.setTextColor(
                ContextCompat.getColor(
                    this@ProductDetailsActivity,
                    R.color.colorSnackBarError
                )
            )
        } else if (FirebaseFunctionsClass().getCurrentUserID() == product.user_id) {
            hideProgressDialog()
        } else {
            FirebaseFunctionsClass().checkIfItemExistInCart(this, mProductID)
        }
    }

    private fun getProductDetails() {
        showProgressDialog(resources.getString(R.string.please_wait))
        FirebaseFunctionsClass().getProductDetails(this, mProductID)
    }

    private fun addToCart() {
        val cartItem = CartItem(
            FirebaseFunctionsClass().getCurrentUserID(),
            mProductOwnerID,
            mProductID,
            mProductDetails.title,
            mProductDetails.publisher,
            mProductDetails.price,
            mProductDetails.image,
            Constants.DEFAULT_CART_QUANTITY
        )

        showProgressDialog(resources.getString(R.string.please_wait))
        FirebaseFunctionsClass().addCartItems(this, cartItem)


    }

    fun addToCartSuccess() {
        hideProgressDialog()
        showSnackBar(resources.getString(R.string.success_message_item_added_to_cart), false)
        btn_add_to_cart.visibility = View.GONE
        btn_go_to_cart.visibility = View.VISIBLE
    }

    fun productExistsInCart() {
        hideProgressDialog()
        btn_add_to_cart.visibility = View.GONE
        btn_go_to_cart.visibility = View.VISIBLE
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.btn_add_to_cart -> {
                    addToCart()
                }

                R.id.btn_go_to_cart -> {
                    intent = Intent(this@ProductDetailsActivity, CartListActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}