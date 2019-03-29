package com.satish.android.entertainmentminiapp.ui.views

import android.content.Context
import android.databinding.DataBindingUtil
import android.text.Editable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.satish.android.entertainmentminiapp.R
import com.satish.android.entertainmentminiapp.databinding.SearchLayBinding
import com.satish.android.entertainmentminiapp.ui.adapter.TextWatcherAdapter
import com.satish.android.entertainmentminiapp.ui.listeners.EntActionListener
import com.satish.android.entertainmentminiapp.utility.showKeyboard

class SearchView : RelativeLayout, View.OnClickListener {

    private var mContext: Context? = null
    private lateinit var binding: SearchLayBinding
    private var entActionListener: EntActionListener? = null

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        mContext = context
        if (mContext is EntActionListener)
            entActionListener = mContext as EntActionListener
        val mInflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(mInflater, R.layout.search_lay, this, true)
        binding.clear.setOnClickListener(this)
        binding.searchTv.setOnClickListener(this)
    }

    private fun handleSearch() {
        binding.searchTv.visibility = View.GONE
        binding.searchEt.visibility = View.VISIBLE
        binding.searchEt.requestFocus()
        binding.clear.visibility = View.VISIBLE
        binding.searchEt.addTextChangedListener(object : TextWatcherAdapter() {

            override fun afterTextChanged(s: Editable?) {
                super.afterTextChanged(s)
                if(!s.isNullOrBlank())
                    entActionListener?.onSearch(s.toString())
            }
        })
        binding.searchEt.showKeyboard()
    }

    private fun searchClearHandle() {
        if (binding.searchEt.text != null && binding.searchEt.text.isNotBlank()) {
            binding.searchEt.setText("")
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.search_tv -> handleSearch()
            R.id.clear -> searchClearHandle()
        }
    }
}