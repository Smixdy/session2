package com.valance.oechappfinal.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.valance.oechappfinal.R
import com.valance.oechappfinal.databinding.LogInFragmentBinding

class LoginFragment: Fragment() {

    private lateinit var binding: LogInFragmentBinding
    private var isAgreed = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LogInFragmentBinding.inflate(inflater,container,false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        applyPasswordMask(binding.password)
        applyEmailMask(binding.email)

        binding.email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                checkAllFields()
            }
        })

        binding.SignIn.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.my_nav_host_fragment, RegistrationFragment())
                .commit()
        }

        binding.forgot.setOnClickListener {
            parentFragmentManager.beginTransaction()
                //.replace(R.id.my_nav_host_fragment, ForgotFragment())
                .commit()
        }

        binding.appCompatImageView2.setOnClickListener {
            isAgreed = if (isAgreed) {
                binding.appCompatImageView2.setImageResource(R.drawable.remember_password)
                false
            } else {
                binding.appCompatImageView2.setImageResource(R.drawable.password_remember_agree)
                true
            }
        }
    }
    private fun applyEmailMask(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val email = s.toString()
                val atIndex = email.indexOf('@')
                if (atIndex != -1) {
                    val maskedEmail = maskEmail(email, atIndex)
                    editText.removeTextChangedListener(this)
                    editText.setText(maskedEmail)
                    editText.setSelection(maskedEmail.length)
                    editText.addTextChangedListener(this)
                }
            }
        })
    }
    private fun applyPasswordMask(editText: EditText) {
        val hidePasswordDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.eye_slash)!!
        hidePasswordDrawable.setBounds(0, 0, hidePasswordDrawable.intrinsicWidth, hidePasswordDrawable.intrinsicHeight)
        editText.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, hidePasswordDrawable, null)
        editText.transformationMethod = PasswordTransformationMethod.getInstance()

        editText.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEndWidth = editText.compoundDrawablesRelative[2]?.bounds?.width() ?: 0
                if (event.rawX >= (editText.right - drawableEndWidth)) {
                    editText.performClick()

                    editText.transformationMethod =
                        if (editText.transformationMethod == null) {
                            // Показываем пароль
                            editText.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, hidePasswordDrawable, null)
                            PasswordTransformationMethod.getInstance()
                        } else {
                            // Скрываем пароль
                            val showPasswordDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.eye_slash)!!
                            showPasswordDrawable.setBounds(0, 0, showPasswordDrawable.intrinsicWidth, showPasswordDrawable.intrinsicHeight)
                            editText.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, showPasswordDrawable, null)
                            null
                        }
                    return@setOnTouchListener true
                }
            }
            return@setOnTouchListener false
        }

        editText.setOnClickListener {
            editText.transformationMethod =
                if (editText.transformationMethod == null) {
                    PasswordTransformationMethod.getInstance()
                } else {
                    null
                }
        }
    }

    private fun maskEmail(email: String, atIndex: Int): String {
        val username = email.substring(0, atIndex)
        val domain = email.substring(atIndex)
        val maskedUsername = username.replace(Regex("[^@\\s]"), "*")
        return maskedUsername + domain
    }
    private fun checkAllFields() {

        val email = binding.email.text.toString().trim()
        val password = binding.password.text.toString().trim()

        val isValid =  email.isNotEmpty() && password.isNotEmpty()

        if (isValid && isAgreed) {
            binding.appCompatTextView9.setBackgroundResource(R.drawable.button_signin1)
        } else {
            binding.appCompatTextView9.setBackgroundResource(R.drawable.button_signin)
        }
    }
}