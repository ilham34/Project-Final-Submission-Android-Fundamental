package com.ilham.submissiongit.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorite")
data class FavoriteUser(
    val login: String,
    @PrimaryKey
    val id: Int,
    val avatarUrl: String,
    val htmlUrl: String
) : Serializable
