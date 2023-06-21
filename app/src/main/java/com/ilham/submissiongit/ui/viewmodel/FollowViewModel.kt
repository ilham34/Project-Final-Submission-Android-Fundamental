package com.ilham.submissiongit.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ilham.submissiongit.UsersItem
import com.ilham.submissiongit.networking.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {
    private val _listFollowing = MutableLiveData<ArrayList<UsersItem>>()
    val listFollowing: LiveData<ArrayList<UsersItem>> = _listFollowing

    private val _listFollower = MutableLiveData<ArrayList<UsersItem>>()
    val listFollower: LiveData<ArrayList<UsersItem>> = _listFollower

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getFollowingUser(login: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollow(login, "following")
        client.enqueue(object : Callback<ArrayList<UsersItem>> {
            override fun onResponse(
                call: Call<ArrayList<UsersItem>>,
                response: Response<ArrayList<UsersItem>>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    if (response.body() != null){
                        _listFollowing.value = response.body()
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<UsersItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    fun getFollowerUser(login: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollow(login, "followers")
        client.enqueue(object : Callback<ArrayList<UsersItem>> {
            override fun onResponse(
                call: Call<ArrayList<UsersItem>>,
                response: Response<ArrayList<UsersItem>>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    if (response.body() != null) {
                        _listFollower.value = response.body()
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<UsersItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG2, "onFailure: ${t.message}")
            }

        })
    }

    companion object {
        private const val TAG = "following"
        private const val TAG2 = "follower"
    }
}