package com.example.androidfinalproject.activity.user

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.androidfinalproject.MyApplication
import com.example.androidfinalproject.R
import com.example.androidfinalproject.user.account.UserViewModel
import kotlinx.android.synthetic.main.activity_menu_user.*
import javax.inject.Inject


class MenuUserActivity : AppCompatActivity() {
    @Inject
    lateinit var userViewModel: UserViewModel
    lateinit var navController: NavController
    var sharedPreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_user)
        sharedPreferences = this?.getSharedPreferences(
            getString(R.string.shared_preference_name),
            Context.MODE_PRIVATE
        )
        (applicationContext as MyApplication).applicationComponent.inject(this)
        navController = (nav_host_fragment_menu_user_container as NavHostFragment).navController
        NavigationUI.setupWithNavController(bottom_navigation_user, navController)
        val id = intent.getStringExtra("id_user")
        val id_wallet = intent.getStringExtra("id_wallet")
        val fullname = intent.getStringExtra("fullname")
        val phone = intent.getStringExtra("phone")
        val photo = intent.getStringExtra("photo")
        val address = intent.getStringExtra("address")
        val borndate = intent.getStringExtra("borndate")
        println("phone from intent" + phone)
//        val id = sharedPreferences?.getString(
//            getString(R.string.id_key),
//            getString(R.string.default_value)
//        )
//        userViewModel.getUserSaldo(id.toString())
        println("ID wallet USER MENU" + id_wallet)
        this.findNavController(R.id.nav_host_fragment_menu_user_container).navigate(
            R.id.action_global_homeUserFragment,
            bundleOf("user_id" to id, "wallet_id" to id_wallet)
        )
        bottom_navigation_user.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeUser -> {
                    navController.navigate(R.id.action_global_homeUserFragment,
                        bundleOf("user_id" to id, "wallet_id" to id_wallet))
                    true
                }
                R.id.profileUser -> {
                    navController.navigate(
                        R.id.action_global_profileUserFagment,
                        bundleOf(
                            "id" to id, "fname" to fullname, "pnumber" to phone,
                            "photo_user" to photo, "address_user" to address, "born" to borndate
                        )
                    )
                    true
                }
                else -> {
                    true
                }
            }
        }
    }

}