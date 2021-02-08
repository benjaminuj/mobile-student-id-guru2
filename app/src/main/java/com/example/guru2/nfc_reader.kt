package com.example.guru2

import android.app.Activity
import android.app.PendingIntent
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.NfcEvent
import android.nfc.tech.NfcA
import android.nfc.tech.NfcF
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class nfc_reader : Activity(), NfcAdapter.CreateNdefMessageCallback {

    private var nfcAdapter: NfcAdapter? = null
    lateinit var btnLoad : Button
    var readId : String =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nfc_reader)

        btnLoad = findViewById(R.id.btnLoad)



        // Check for available NFC Adapter
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (nfcAdapter == null) {
            Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG).show()
            finish()
            return
        }
        // Register callback
        nfcAdapter?.setNdefPushMessageCallback(this, this)

        btnLoad.setOnClickListener {
            if(readId == ""){
                val builder = AlertDialog.Builder(this)
                builder.setTitle("알림!")
                builder.setMessage("학생 정보를 불러오지 못했습니다. 다시 태그해주세요.")
                builder.setIcon(R.drawable.symbol)
                builder.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                })
                builder.show()
            }
            else if(readId =="리더기"){
                val builder = AlertDialog.Builder(this)
                builder.setTitle("알림!")
                builder.setMessage("올바른 아이디 카드가 아닙니다. 다시 태그해주세요.")
                builder.setIcon(R.drawable.symbol)
                builder.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                })
                builder.show()
            }else{
                var intent = Intent(this, admin_read_card::class.java)
                intent.putExtra("getId",readId)
                startActivity(intent)

            }
        }

    }







    override fun createNdefMessage(event: NfcEvent): NdefMessage {

        val text = "리더기"
        return NdefMessage(
                arrayOf(
                        NdefRecord.createMime("application/vnd.com.example.android.beam", text.toByteArray())
                )
                /**
                 * The Android Application Record (AAR) is commented out. When a device
                 * receives a push with an AAR in it, the application specified in the AAR
                 * is guaranteed to run. The AAR overrides the tag dispatch system.
                 * You can add it back in to guarantee that this
                 * activity starts when receiving a beamed message. For now, this code
                 * uses the tag dispatch system.
                 */
                /**
                 * The Android Application Record (AAR) is commented out. When a device
                 * receives a push with an AAR in it, the application specified in the AAR
                 * is guaranteed to run. The AAR overrides the tag dispatch system.
                 * You can add it back in to guarantee that this
                 * activity starts when receiving a beamed message. For now, this code
                 * uses the tag dispatch system.
                 *///NdefRecord.createApplicationRecord("com.example.android.beam")
        )
    }

    override fun onResume() {
        super.onResume()
        // Check to see that the Activity started due to an Android Beam
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
            processIntent(intent)
        }
    }

    override fun onNewIntent(intent: Intent) {
        // onResume gets called after this to handle the intent
        setIntent(intent)
    }

    /**
     * Parses the NDEF Message from the intent and prints to the TextView
     */
    private fun processIntent(intent: Intent) {
        // only one message sent during the beam
        intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)?.also { rawMsgs ->
            (rawMsgs[0] as NdefMessage).apply {
                // record 0 contains the MIME type, record 1 is the AAR, if present
                readId=String(records[0].payload)


            }
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this,admin_login_first::class.java)
        startActivity(intent)
    }


}

