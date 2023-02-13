package com.example.adamassignment1

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.adamassignment1.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

private const val TAG = "MainActivity"
private const val REQUEST_CODE_READ_CONTACTS = 1

class MainActivity : AppCompatActivity() {

    //problem
    //make screen compatible for any size
    //overlying
    //gradient for drawable


    private lateinit var binding: ActivityMainBinding
    private lateinit var playerScreenBtn: Button
    private lateinit var contactListBtn: Button
    private lateinit var listContact: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)//R.layout.activity_main)

        batteryChanged()
        initViews()
    }

    private fun initViews() {
        //views
        playerScreenBtn = binding.playerBtn
        contactListBtn = binding.contactBtn
        listContact = binding.listContact
        //listeners
        playerScreenBtn.setOnClickListener { playMusic() }
        contactListBtn.setOnClickListener { view -> importContact(view) }
    }

    private fun batteryChanged() {
        // Broadcast Receiver
        val batteryChanged = BatteryReceiver()
        applicationContext.registerReceiver(
            batteryChanged,
            IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        )
    }

    private fun checkHasReadContactPermission(): Int {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_CONTACTS
        )
    }

    fun readContact(): ArrayList<String> {
        val projection = arrayOf(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)

        val cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            projection,
            null,
            null,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
        )

        val contacts = ArrayList<String>()
        cursor?.use {
            while (it.moveToNext()) {
                contacts.add(it.getString(it.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)))
            }
        }
        return contacts
    }

    private fun importContact(view: View) {

        val hasReadContactsPermission = checkHasReadContactPermission()
        Log.d(TAG, "onCreate: checkSelfPermission returned $hasReadContactsPermission")
        Log.d(TAG, "Fab onClick: starts")

        // if (readGranted) {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val contacts = readContact()
            val adapter = ArrayAdapter<String>(this, R.layout.contact_detail, R.id.name, contacts)
            listContact.adapter = adapter
        } else {
            requestPermissionReadContact(view)
        }

        Log.d(TAG, "Fab onClick: ends")
    }

   private fun requestPermissionReadContact(view: View) {
        Snackbar.make(view, "Please grant access to your Contacts", Snackbar.LENGTH_LONG)
            .setAction("Grant Access") {
                Log.d(TAG, "Snackbar onClick: starts")

                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.READ_CONTACTS
                    )
                ) {
                    Log.d(TAG, "Snackbar onClick: calling request permission")
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.READ_CONTACTS),
                        REQUEST_CODE_READ_CONTACTS
                    )
                } else {
                    Log.d(TAG, "Snackbar onClick: calling request permission")
                    val intent = Intent()
                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    val uri = Uri.fromParts("package", this.packageName, null)
                    Log.d(TAG, "Snackbar onClick: Uri is $uri")
                    intent.data = uri
                    this.startActivity(intent)
                }
                Log.d(TAG, "Snackbar onClick: ends")

                Toast.makeText(it.context, "Snackbar action clicked", Toast.LENGTH_SHORT).show()
            }.show()
    }

    private fun playMusic() {
        val serviceIntent = Intent(this, MusicService::class.java)
        Log.d(TAG, "onCreate: $serviceIntent")
        startService(serviceIntent)

    }
}
