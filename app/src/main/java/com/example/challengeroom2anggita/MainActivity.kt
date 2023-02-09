package com.example.challengeroom2anggita

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challengeroom2anggita.room.Constant
import com.example.challengeroom2anggita.room.dbPerpustakaan
import com.example.challengeroom2anggita.room.tbBukuDAO
import com.example.challengeroom2anggita.room.tbbuku
import kotlinx.android.synthetic.main.activity_data.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    val db by lazy { dbPerpustakaan(this) }
    lateinit var bukuAdapter: bukuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        haldata()
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        loadtbbuku()

    }
    fun loadtbbuku(){
        CoroutineScope(Dispatchers.IO).launch {
            val databook= db.tbbookDao().tampilsemua()
            Log.d("MainActivity","dbResponse: $databook")
            withContext(Dispatchers.Main){
                bukuAdapter.setData(databook)
            }
        }
    }
    private fun haldata(){
        Btinput.setOnClickListener {
            intentData(0, Constant.TYPE_CREATE)
        }
    }

    fun intentData(idbuku: Int, intentType: Int){
        startActivity(
            Intent(applicationContext,DataActivity::class.java)
                .putExtra("intent_id", idbuku)
                .putExtra("intent_type", intentType)
        )
    }

    private fun setupRecyclerView(){
        bukuAdapter = bukuAdapter(arrayListOf(), object : bukuAdapter.OnadapterListener{
            override fun onCLick(tbBuku: tbbuku) {

                //menampilkan data beberapa detik
                Toast.makeText(applicationContext, tbBuku.judul, Toast.LENGTH_SHORT).show()
                // read detail
                intentData(tbBuku.id_buku, Constant.TYPE_READ)
            }

            override fun onUpdate(tbBuku: tbbuku) {
                intentData(tbBuku.id_buku, Constant.TYPE_UPDATE)
            }

            override fun onDelete(tbBuku: tbbuku) {
                deleteDialog(tbBuku)
            }

        })
        listdatasiswa.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = bukuAdapter
        }
    }
    private fun deleteDialog(tbBuku: tbbuku){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Yakin hapus ${tbBuku.judul}?")
            setNegativeButton("Batal") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") { dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.tbbookDao().deletetbbuku(tbBuku)
                    loadtbbuku()
                }
            }
        }
        alertDialog.show()
    }
}