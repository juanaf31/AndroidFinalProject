package com.example.androidfinalproject.screens.user

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.androidfinalproject.MyApplication
import com.example.androidfinalproject.R
import com.example.androidfinalproject.user.account.User
import com.example.androidfinalproject.user.account.UserViewModel
import com.example.androidfinalproject.user.profile.UserProfile
import com.example.androidfinalproject.user.profile.UserProfileViewModel
import com.example.androidfinalproject.user.profile.UserUpdate
import kotlinx.android.synthetic.main.fragment_profile_user_fagment.*
import java.util.*
import javax.inject.Inject

class ProfileUserFagment : Fragment(), View.OnClickListener {
    @Inject
    lateinit var userProfileViewModel: UserProfileViewModel
    var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApplication).applicationComponent.inject(this)
        sharedPreferences = activity?.getSharedPreferences(
            getString(R.string.shared_preference_name),
            Context.MODE_PRIVATE
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_user_fagment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mPickTimeBtn = view?.findViewById<TextView>(R.id.calenderPickerUser)
        val textView = view?.findViewById<EditText>(R.id.bornDateEditTextUser)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        mPickTimeBtn?.setOnClickListener {
            val monthView = month + 1
            val dpd = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { view, year, month, day ->
                    // Display Selected date in TextView
                    textView?.setText("$year-$monthView-$day")
                }, year, month, day
            )
            dpd.show()
        }
        userProfileViewModel.getById(sharedPreferences?.getString("ID_USER", "").toString())
        println("ID USER" + sharedPreferences?.getString("ID_USER", "").toString())
        userProfileViewModel.userData.observe(viewLifecycleOwner, Observer {
            fNameUserEditTextUser.text =
                Editable.Factory.getInstance().newEditable(it.fullname.toString())
            phoneNumberEditTextUser.text =
                Editable.Factory.getInstance().newEditable(it.phone_number.toString())
            addressEditTextUser.text =
                Editable.Factory.getInstance()
                    .newEditable(it.address.toString())
            bornDateEditTextUser.text =
                Editable.Factory.getInstance().newEditable(it.borndate.toString())
        })

        deleteUserPhoto.setOnClickListener(this)
        ChangePhotoUser.setOnClickListener(this)
        simpanEditUserButton.setOnClickListener(this)
        logoutUserButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val alertDialog = AlertDialog.Builder(requireContext()).create()

        when (v) {
            logoutUserButton -> {
                with(sharedPreferences?.edit()) {
                    this?.putBoolean(
                        "ISLOGGEDIN_USER",
                        false
                    )
                    this?.commit()
                }
                view?.findNavController()
                    ?.navigate(R.id.action_global_loginUserFragment)
            }
            simpanEditUserButton -> {
                val id = sharedPreferences?.getString(
                    getString(R.string.id_user_key),
                    getString(R.string.default_value)
                )
                userProfileViewModel.updateUserProfile(
                    id.toString(),
                    UserUpdate(
                        borndate = bornDateEditTextUser.text.toString(),
                        address = addressEditTextUser.text.toString()
                    )
                )
                alertDialog.setTitle("Edit Profile")
                alertDialog.setMessage("Edit Success")

                alertDialog.setButton(
                    AlertDialog.BUTTON_POSITIVE, "OK"
                ) { dialog, which -> dialog.dismiss() }
                alertDialog.show()

                val btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)

                val layoutParams = btnPositive.layoutParams as LinearLayout.LayoutParams
                layoutParams.weight = 10f
                btnPositive.layoutParams = layoutParams
            }
            deleteUserPhoto -> {
                userProfileViewModel.deleteUserPhoto(arguments?.getString("id").toString())
                alertDialog.setTitle("Delete Photo")
                alertDialog.setMessage("Delete Success")

                alertDialog.setButton(
                    AlertDialog.BUTTON_POSITIVE, "OK"
                ) { dialog, which -> dialog.dismiss() }
                alertDialog.show()

                val btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)

                val layoutParams = btnPositive.layoutParams as LinearLayout.LayoutParams
                layoutParams.weight = 10f
                btnPositive.layoutParams = layoutParams
            }
        }
    }
}