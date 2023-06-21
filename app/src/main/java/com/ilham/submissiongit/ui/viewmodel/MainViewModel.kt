package com.ilham.submissiongit.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.ilham.submissiongit.GitResponse
import com.ilham.submissiongit.SettingPreferences
import com.ilham.submissiongit.UsersItem
import com.ilham.submissiongit.networking.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: SettingPreferences) : ViewModel() {
    private val _listGitUser = MutableLiveData<List<UsersItem>>()
    val listGitUser: LiveData<List<UsersItem>> = _listGitUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _totalCount = MutableLiveData<Int>()
    val totalCount: LiveData<Int> = _totalCount

    fun searchUser(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(query)
        client.enqueue(object : Callback<GitResponse> {
            override fun onResponse(call: Call<GitResponse>, response: Response<GitResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        _listGitUser.value = response.body()?.items
                        _totalCount.value = response.body()?.totalCount
                    }
                }
            }

            override fun onFailure(call: Call<GitResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getTheme() = pref.getThemeSetting().asLiveData()

    fun saveTheme(isDark: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDark)
        }
    }

    class FACTORY(private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = MainViewModel(pref) as T
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}