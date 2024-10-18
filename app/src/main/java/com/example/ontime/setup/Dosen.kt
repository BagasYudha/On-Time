package com.example.ontime.setup

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_dosen")
class Dosen(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nama: String,
    val email: String
)
