package com.app.shadi.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class UserEntity {

    companion object {
        const val STATUS_IS_ACCEPTED = "isAccepted"
        const val STATUS_IS_DECLINED = "isDeclined"
    }

    @PrimaryKey
    var phone: String = ""

    @ColumnInfo(name = "title")
    var title: String = ""

    @ColumnInfo(name = "firstName")
    var firstName: String = ""

    @ColumnInfo(name = "lastName")
    var lastName: String = ""

    @ColumnInfo(name = "email")
    var email: String = ""

    @ColumnInfo(name = "age")
    var age: String = ""

    @ColumnInfo(name = "city")
    var city: String = ""

    @ColumnInfo(name = "state")
    var state: String = ""

    @ColumnInfo(name = "country")
    var country: String = ""

    @ColumnInfo(name = "profilePicture")
    var profilePicture: String = ""

    @ColumnInfo(name = "isAccepted")
    var isAccepted: Boolean = false

    @ColumnInfo(name = "isDeclined")
    var isDeclined: Boolean = false

}