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

    //utworzenie zmiennej databaseReference, łączącej projekt z realtime database w Firebase
    val databaseReference: DatabaseReference = FirebaseDatabase.getInstance()
        .getReferenceFromUrl("https://besafe-ecb67-default-rtdb.europe-west1.firebasedatabase.app")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var generateButton = findViewById<Button>(R.id.GenerateButton)

        // wywołanie funkcji generującej kody aktywacyjne po kliknięciu przycisku "generuj"
        generateButton.setOnClickListener {
            generateCodes()
        }
    }

    //funkcja generująca zadaną przez użytkownika liczbę kodów aktywacyjnych oraz umieszczająca je w bazie danych
    private fun generateCodes() {
        //pobranie wartości wprowadzonych przez użytkownika
        var numofcodes = findViewById<EditText>(R.id.NumberOfCodes)
        var numbertxt = numofcodes.getText().toString()
        var number = Integer.parseInt(numbertxt)

        //generowanie określonej liczby kodów w pętli for, przesłanie ich do bazy danych
        for (i in 1..number) {
            // generowanie 128-bitowego ciągu znaków UUID
            val newKEY = UUID.randomUUID().toString()
            // skrócenie kodu o 20 pierwszych znaków
            val key = newKEY.drop(20)

            //przesłanie kodu do bazy danych
            databaseReference.child("ActivationKeys").child(key).child("Key").setValue(key)
        }

        // wyświetlenie informacji o pomyślnym wygenerowaniu kodu, zamknięcie aplikacji.
        Toast.makeText(this, "Kody zostały pomyślnie wygenerowane", Toast.LENGTH_SHORT).show()
        finish()
    }
}