package com.example.challengeroom2anggita.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class tbbuku (
    @PrimaryKey(autoGenerate = true)
    val id_buku: Int,
    val kategori: String,
    val judul: String,
    val pengarang: String,
    val penerbit: String,
    val jumlah_buku: Int
        )