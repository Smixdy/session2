package com.valance.oechappfinal.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.InputFilter
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.valance.oechappfinal.R
import com.valance.oechappfinal.databinding.RegistrationFragmentBinding

class RegistrationFragment : Fragment() {

    private lateinit var binding: RegistrationFragmentBinding
    private var isAgreed = false
    private var password1: String = ""
    private var password2: String = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RegistrationFragmentBinding.inflate(inflater, container,false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val originalText = getString(R.string.by_ticking_this_box_you_agree_to_our_terms_and_n_conditions_and_private_policy)
        val startIndex = originalText.indexOf("Terms and")
        val endIndex = originalText.lastIndexOf("policy") + "policy".length

        val spannableString = SpannableString(originalText)
        spannableString.setSpan(ForegroundColorSpan(resources.getColor(R.color.yellow)), startIndex, endIndex, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.appCompatTextView8.text = spannableString

        binding.agree.setOnClickListener {
            isAgreed = if (isAgreed) {
                binding.agree.setImageResource(R.drawable.type_unselected__state_enabled)
                false
            } else {
                binding.agree.setImageResource(R.drawable.type_selected__state_enabled)
                true
            }
        }
        binding.SignIn.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.my_nav_host_fragment, LoginFragment())
                .commit()
        }
        applyPasswordMask1(binding.ConfirmPassword)
        applyPasswordMask(binding.password)
        applyEmailMask(binding.email)

        binding.email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                checkAllFields()
            }
        })
        binding.phone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                formatPhoneNumber(s)
            }
        })
        binding.password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                password1 = s.toString()
                checkAllFields()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        binding.name.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                checkAllFields()
            }
        })

        binding.ConfirmPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                password2 = s.toString()
                checkAllFields()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.phone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                formatPhoneNumber(s)
            }
        })

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

    private fun maskEmail(email: String, atIndex: Int): String {
        val username = email.substring(0, atIndex)
        val domain = email.substring(atIndex)
        val maskedUsername = username.replace(Regex("[^@\\s]"), "*")
        return maskedUsername + domain
    }
    private fun formatPhoneNumber(text: Editable?) {
        text?.let {
            if (it.length == 1 && it[0] != '+') {
                // Если пользователь начал вводить номер без "+", добавляем его автоматически
                it.insert(0, "+")
            }
            if (it.length == 2 && it[1] != '7') {
                // Если пользователь ввел "+" без "7", добавляем "7" автоматически
                it.insert(1, "7")
            }
            if (it.length >= 3 && it[2] != '(') {
                it.insert(2, "(")
            }
            if (it.length >= 7 && it[6] != ')') {
                it.insert(6, ")")
            }
            if (it.length >= 8 && it[7] != '9' && it[7] != '8') {
                it.delete(7, it.length)
            }
            if (it.length >= 11 && it[10] != '-') {
                it.insert(10, "-")
            }
            if (it.length >= 14 && it[13] != '-') {
                it.insert(13, "-")
            }
        }
    }

    private fun applyPasswordMask1(editText: EditText) {
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
    private fun goToHomeFragment(){
        binding.appCompatTextView9.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .replace(R.id.my_nav_host_fragment, HomeFragment())
                .commit()
        }
    }
    private fun checkAllFields() {
        val name = binding.name.text.toString().trim()
        val phone = binding.phone.text.toString().trim()
        val email = binding.email.text.toString().trim()
        val password = binding.password.text.toString().trim()
        val confirmPassword = binding.ConfirmPassword.text.toString().trim()

        val isValid = name.isNotEmpty() && phone.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()

        if (isValid && passwordsMatch()) {
            goToHomeFragment()
            binding.appCompatTextView9.setBackgroundResource(R.drawable.button_signin1)
        } else {
            binding.appCompatTextView9.setBackgroundResource(R.drawable.button_signin)
        }
    }
    private fun passwordsMatch(): Boolean {
        val password1 = binding.password.text.toString().trim()
        val password2 = binding.ConfirmPassword.text.toString().trim()
        return password1 == password2
    }
}