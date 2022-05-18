package com.example.codegen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class MainActivity : AppCompatActivity() {

    val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://besafe-ecb67-default-rtdb.europe-west1.firebasedatabase.app")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var generateButton = findViewById<Button>(R.id.GenerateButton)


        generateButton.setOnClickListener {
            generateCodes()
        }
    }

    private fun generateCodes() {
        var numofcodes = findViewById<EditText>(R.id.NumberOfCodes)
        var numbertxt = numofcodes.getText().toString()
        var number = Integer.parseInt(numbertxt)


        for(i in 1 .. number){
            val newKEY = UUID.randomUUID().toString()
            val key = newKEY.drop(20)

            databaseReference.child("ActivationKeys").child(key).child("Key").setValue(key)
        }

        Toast.makeText(this, "Kody zostały pomyślnie wygenerowane", Toast.LENGTH_SHORT).show()
        finish()
    }


}