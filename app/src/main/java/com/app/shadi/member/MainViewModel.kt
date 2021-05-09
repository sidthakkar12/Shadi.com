package com.app.shadi.member

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.app.shadi.MainApplication
import com.app.shadi.data.User
import com.app.shadi.database.entity.UserEntity
import com.app.shadi.network.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    val updateUserLiveData: MutableLiveData<UserEntity> = MutableLiveData()

    private val appDatabase = MainApplication.appDatabase
    private val userDAO = appDatabase.userDao()

    fun getUsers() = liveData(Dispatchers.IO) {
        emit(Response.loading(data = null))

        try {
            val data = mainRepository.getUsers()
            setUserData(data)
            emit(Response.success(fetchUsers()))

        } catch (exception: Exception) {
            emit(Response.success(fetchUsers()))
        }
    }

    private fun setUserData(users: User) {
        val userEntityList = ArrayList<UserEntity>()
        users.results.forEachIndexed { index, result ->
            val userEntity = UserEntity()

            userEntity.phone = result.phone

            userEntity.title = result.name.title
            userEntity.firstName = result.name.first
            userEntity.lastName = result.name.last

            userEntity.email = result.email

            userEntity.age = result.dob.age.toString()

            userEntity.city = result.location.city
            userEntity.state = result.location.state
            userEntity.country = result.location.country

            userEntity.profilePicture = result.picture.large

            userEntity.isAccepted = false
            userEntity.isDeclined = false

            userEntityList.add(userEntity)
        }

        insertUsers(userEntityList)
    }

    private fun insertUsers(users: ArrayList<UserEntity>) {
        userDAO.insertUsers(users)
    }

    private fun fetchUsers(): List<UserEntity> {
        return userDAO.getAllUsers()
    }

    fun updateUserStatus(phone: String, status: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = userDAO.getUser(phone = phone)
            user.isAccepted = status == UserEntity.STATUS_IS_ACCEPTED
            user.isDeclined = status == UserEntity.STATUS_IS_DECLINED
            userDAO.updateUser(user)
            updateUserLiveData.postValue(userDAO.getUser(phone = phone))
        }
    }
}