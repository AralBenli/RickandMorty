package com.example.rickandmorty.ui.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.rickandmorty.R

abstract class BaseFragment<VB : ViewBinding> :
    Fragment() {


    private lateinit var progressDialog: Dialog
    lateinit var binding: VB
    var savedInstanceView: View? = null
    abstract fun getViewBinding(): VB
    abstract fun initViews()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (savedInstanceView == null) {
            onCreateViewBase()
            binding = getViewBinding()
            savedInstanceView = binding.root
            savedInstanceView
        } else {
            savedInstanceView
        }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observer()
    }

    open fun observer(){}

    open fun onCreateViewBase(){}

    fun showLoadingProgress() {
        if (!::progressDialog.isInitialized) {
            progressDialog = Dialog(requireContext())
            progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            progressDialog.setContentView(R.layout.loading_progress)
            progressDialog.setCancelable(false)
            progressDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        progressDialog.show()
    }
    fun dismissLoadingProgress() {
        if (::progressDialog.isInitialized && progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }
    fun showCustomToast(status : Status, message: String, fragment: Fragment) {

        val layout = fragment.layoutInflater.inflate(R.layout.custom_toast_layout,null)

        val textView = layout.findViewById<TextView>(R.id.toast_text)
        textView.text = message

        val imageView = layout.findViewById<ImageView>(R.id.titleIcon)
        when(status){
            Status.Added -> imageView.setImageResource(R.drawable.jerry_smith)
            Status.Removed -> imageView.setImageResource(R.drawable.morty_smith)
        }

        val myToast = Toast(this.context)
        myToast.duration = Toast.LENGTH_SHORT
        myToast.setGravity(Gravity.BOTTOM, 0, 200)
        myToast.view = layout //setting the view of custom toast layout
        myToast.show()

    } enum class Status {
        Added,
        Removed ,
    }
}




