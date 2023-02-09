package com.example.challengeroom2anggita

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.challengeroom2anggita.room.Constant
import com.example.challengeroom2anggita.room.dbPerpustakaan
import com.example.challengeroom2anggita.room.tbbuku
import kotlinx.android.synthetic.main.activity_data.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataActivity : AppCompatActivity() {
    val db by lazy { dbPerpustakaan(this) }
    private var idbuku: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)
        setupView()
        simpandata()
        getTampilid()

        idbuku = intent.getIntExtra("intent_id", 0)
        Toast.makeText(this,idbuku.toString(), Toast.LENGTH_SHORT).show()
    }

    fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType= intent.getIntExtra("intent_type", 0)
        when (intentType) {
            Constant.TYPE_CREATE -> {
                btupdate.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                btsimpan.visibility = View.GONE
                btupdate.visibility = View.GONE
                getTampilid()
            }
            Constant.TYPE_UPDATE -> {
                btsimpan.visibility = View.GONE
                getTampilid()
            }
        }
    }

    fun simpandata(){
        btsimpan.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.tbbookDao().addtbbuku(
                    tbbuku(etid.text.toString().toInt(),etkategori.text.toString(),etjudul.text.toString(),
                        etpengarang.text.toString(),etpenerbit.text.toString(),etjml.text.toString().toInt())
                )
                finish()
            }
        }
        btupdate.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.tbbookDao().updatetbbuku(
                    tbbuku(etid.text.toString().toInt(),etkategori.text.toString(),etjudul.text.toString(),
                        etpengarang.text.toString(),etpenerbit.text.toString(),etjml.text.toString().toInt())
                )
                finish()
            }
        }
    }
    fun getTampilid(){
        idbuku = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
         val tampil =   db.tbbookDao().getTampilid(idbuku)[0]
            val dataId : String = tampil.id_buku.toString()
            val dataJml : String = tampil.jumlah_buku.toString()
            etid.setText(dataId)
            etkategori.setText(tampil.kategori)
            etjudul.setText(tampil.judul)
            etpengarang.setText(tampil.pengarang)
            etpenerbit.setText(tampil.penerbit)
            etjml.setText(dataJml)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}