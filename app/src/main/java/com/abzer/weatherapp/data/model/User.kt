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
    val id: Long?,

    @ColumnInfo(name = "name")
    val name: String?,

    @ColumnInfo(name = "email")
    val email: String?,

    @ColumnInfo(name = "password")
    val password: String?,

    @ColumnInfo(name = "phone")
    val phone: String? = null,

): Parcelable {

    fun checkNameIsEmpty(): Boolean {
        return name?.isBlank() == true
    }

    fun checkEmailIsEmpty(): Boolean {
        return email?.isBlank() == true
    }

    fun checkEmailIsNotValid(): Boolean {
        return !Patterns.EMAIL_ADDRESS.matcher(email ?: "").matches()
    }

    fun checkPasswordIsEmpty(): Boolean {
        return password?.isBlank() == true
    }

    fun checkPhoneIsEmpty(): Boolean {
        return phone == null
    }

    fun checkPhoneIsNotValid(): Boolean {
        return phone?.length != 10
    }
}
