package com.example.androidfinalproject.screens.user

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.example.androidfinalproject.MyApplication
import com.example.androidfinalproject.R
import com.example.androidfinalproject.user.account.User
import com.example.androidfinalproject.user.profile.UserProfileViewModel
import kotlinx.android.synthetic.main.fragment_profile_user_fagment.*
import javax.inject.Inject

class ProfileUserFagment : Fragment(), View.OnClickListener {
    @Inject
    lateinit var userProfileViewModel: UserProfileViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApplication).applicationComponent.inject(this)
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

            val dpd = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    // Display Selected date in TextView
                    textView?.setText("$year-$month-$dayOfMonth")
                },
                year,
                month,
                day
            )
            dpd.show()
        }
        fNameUserEditTextUser.text =
            Editable.Factory.getInstance().newEditable(arguments?.getString("fname").toString())
        phoneNumberEditTextUser.text =
            Editable.Factory.getInstance().newEditable(arguments?.getString("pnumber").toString())
        addressEditTextUser.text =
            Editable.Factory.getInstance()
                .newEditable(arguments?.getString("address_user").toString())
        bornDateEditTextUser.text =
            Editable.Factory.getInstance().newEditable(arguments?.getString("born").toString())

        deleteUserPhoto.setOnClickListener(this)
        ChangePhotoUser.setOnClickListener(this)
        simpanEditUserButton.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onClick(v: View?) {
        val alertDialog = AlertDialog.Builder(requireContext()).create()

        when (v) {
            simpanEditUserButton -> {
                userProfileViewModel.updateSaldoUser(
                    arguments?.getString("id").toString(),
                    User(
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