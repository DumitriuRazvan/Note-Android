package com.ndev.noter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class AddObjectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_object)

        val editTextTitle = findViewById<EditText>(R.id.editTextTitleField)
        val editTextContent = findViewById<EditText>(R.id.editTextFullNoteField)


        var createNoteButton = findViewById<Button>(R.id.createAddNoteButton)
        var title = editTextTitle.text
        var content = editTextContent.text
        createNoteButton.setOnClickListener {
            Toast.makeText(this, title, Toast.LENGTH_LONG).show()

            /// DE COMPLETAT CU ADAUGAREA NOTITEI IN BAZA DE DATE



        }

    }
}