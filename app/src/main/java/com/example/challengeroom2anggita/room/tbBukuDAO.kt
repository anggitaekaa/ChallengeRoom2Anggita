package com.example.challengeroom2anggita.room

import androidx.room.*


@Dao
interface tbBukuDAO {
    @Insert
    fun addtbbuku(tbbook:tbbuku)

    @Update
    fun updatetbbuku(tbbook: tbbuku)

    @Delete
    fun deletetbbuku(tbbook: tbbuku)

    @Query("SELECT * FROM tbbuku")
    fun tampilsemua(): List<tbbuku>

    @Query("SELECT *FROM tbbuku WHERE id_buku=:idbuku")
    fun getTampilid(idbuku: Int): List<tbbuku>
}