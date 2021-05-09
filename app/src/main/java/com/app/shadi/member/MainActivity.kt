package com.app.shadi.member

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.shadi.R
import com.app.shadi.core.ViewModelFactory
import com.app.shadi.database.entity.UserEntity
import com.app.shadi.listener.OnClickListener
import com.app.shadi.network.ApiHelper
import com.app.shadi.network.ResponseStatus
import com.app.shadi.network.RetrofitBuilder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViewModel()
        initializeComponents()
        setListeners()
        setupApiCallAndObservers()
    }

    private fun initializeViewModel() {
        viewModel = ViewModelProviders.of(this, ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))).get(MainViewModel::class.java)
    }

    private fun initializeComponents() {
        rvUsers.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(arrayListOf())
        rvUsers.adapter = adapter
    }

    private fun setListeners() {
        tvVertical.setOnClickListener {
            rvUsers.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            adapter.setOrientation(MainAdapter.ORIENTATION_VERTICAL)
        }

        tvHorizontal.setOnClickListener {
            rvUsers.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
            adapter.setOrientation(MainAdapter.ORIENTATION_HORIZONTAL)
        }

        tvMessage.setOnClickListener {
            setupApiCallAndObservers()
        }

        adapter.setOnClickListener(object : OnClickListener {
            override fun onClick(phone: String, status: String) {
                viewModel.updateUserStatus(phone = phone, status = status)
            }
        })
    }

    private fun setupApiCallAndObservers() {
        viewModel.getUsers().observe(this, Observer {
            progressBar.isVisible = it.status == ResponseStatus.LOADING
            tvMessage.isVisible = it.status == ResponseStatus.ERROR

            when (it?.status) {
                ResponseStatus.SUCCESS -> {
                    retrieveList(it.data!!)
                }
                ResponseStatus.ERROR -> {
                    tvMessage.text = getString(R.string.s_tap_to_retry, it.message)
                }
                else -> {
                }
            }
        })

        viewModel.updateUserLiveData.observe(this, Observer {
            adapter.updateUser(userEntity = it)
        })
    }

    private fun retrieveList(users: List<UserEntity>) {
        adapter.addUsers(users)
        adapter.notifyDataSetChanged()

        invalidateViews()
    }

    private fun invalidateViews() {
        rvUsers.isVisible = adapter.itemCount > 0
        tvMessage.isVisible = adapter.itemCount == 0
        tvMessage.text = getString(R.string.no_member_found)
    }
}