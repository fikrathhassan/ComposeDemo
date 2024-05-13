package com.abzer.weatherapp.data.model

import android.os.Parcelable
import android.util.Patterns
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "users")
data class User(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = null,

    @ColumnInfo(name = "name")
    val name: String? = null,

    @ColumnInfo(name = "email")
    val email: String? = null,

    @ColumnInfo(name = "password")
    val password: String? = null,

    @ColumnInfo(name = "phone")
    val phone: String? = null,

): Parcelable {

    fun checkNameIsNotEmpty(): Boolean {
        return name?.isNotBlank() == true
    }

    fun checkEmailIsNotEmpty(): Boolean {
        return email?.isNotBlank() == true
    }

    fun checkEmailIsValid(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email ?: "").matches()
    }

    fun checkPasswordIsNotEmpty(): Boolean {
        return password?.isNotBlank()  == true
    }

    fun checkPhoneIsNotEmpty(): Boolean {
        return phone != null
    }

    fun checkPhoneIsValid(): Boolean {
        return phone?.length == 10
    }
}
