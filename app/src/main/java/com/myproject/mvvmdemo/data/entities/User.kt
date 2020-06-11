package com.myproject.mvvmdemo.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

const val CURRENT_USER_ID = 0
/* We are using local database to store only one user who is currently logged in. that's why we fixed UserId 0 */

@Entity
data class User (
    var id: Int? = null,
    var name: String? = null,
    var email: String? = null,
    var password: String? = null,
    var email_verified_at: String? = null,
    var created_at: String? = null,
    var updated_at: String? = null
){

    /* we are storing only one record that's why we don't need auto incrementing primary key */
    @PrimaryKey(autoGenerate = false)
    var uid: Int = CURRENT_USER_ID
}