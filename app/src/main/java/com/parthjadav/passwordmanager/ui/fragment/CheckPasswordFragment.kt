package com.parthjadav.passwordmanager.ui.fragment


import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.parthjadav.passwordmanager.R
import com.parthjadav.passwordmanager.ui.activity.SetPinActivity
import com.parthjadav.passwordmanager.utils.PreferenceManager
import kotlinx.android.synthetic.main.activity_add_password.*
import kotlinx.android.synthetic.main.fragment_check_password.*

/**
 * A simple [Fragment] subclass.
 */
class CheckPasswordFragment : BottomSheetDialogFragment() {

    lateinit var preferenceManager: PreferenceManager

    var pwd :Int = 0

    lateinit var onPasswordCheck: OnPasswordCheck
    private var isViewPass: Boolean = false

    fun CheckPasswordFragment(isViewPass: Boolean, onPasswordCheck: OnPasswordCheck) {
        this.onPasswordCheck = onPasswordCheck
        this.isViewPass = isViewPass
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check_password, container, false)
    }

    interface OnPasswordCheck {
        fun onClick(isPasswordTrue: Boolean)
        fun onCancel(isCancel: Boolean)
    }


    interface OnCancel {
        fun onCancel(isCancel: Boolean)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferenceManager = PreferenceManager(context)

        btnCancel.setOnClickListener {
            onPasswordCheck.onCancel(true)
        }

        btnSubmit.setOnClickListener {
            if (edtCheckPassword.text.toString().isEmpty()) {
                edtCheckPassword.error = "Please enter password"
                edtCheckPassword.requestFocus()
            } else {
                if (isViewPass) {
                    if (!preferenceManager.getKeyValueString("old_password").equals(edtCheckPassword.text.toString())) {
                        onPasswordCheck.onClick(false)
                    }else {
                        onPasswordCheck.onClick(true)
                    }
                } else {
                    if (!preferenceManager.getKeyValueString("old_password").equals(edtCheckPassword.text.toString())) {
                        edtCheckPassword.error = "Invalid password"
                        edtCheckPassword.requestFocus()
                    } else {
                        dismiss()
                        val mainIntent = Intent(context, SetPinActivity::class.java)
                        startActivity(mainIntent)
                    }
                }
            }

        }

        tvCheckPasswordVisibility.setOnClickListener {
            if (pwd == 0) {
                pwd = 1;
                tvCheckPasswordVisibility.text = "Hide"
                edtCheckPassword.transformationMethod = HideReturnsTransformationMethod.getInstance();
                edtCheckPassword.setSelection(edtCheckPassword.getText().length);
            } else if (pwd == 1) {
                pwd = 0;
                tvCheckPasswordVisibility.text = "Show"
                edtCheckPassword.transformationMethod = PasswordTransformationMethod.getInstance();
                edtCheckPassword.setSelection(edtCheckPassword.getText().length);
            }
        }
    }


}
