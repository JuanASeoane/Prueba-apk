package com.example.nombrepdf

import android.content.ContentValues
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editNombre = findViewById<TextInputEditText>(R.id.editNombre)
        val btnGuardarPdf = findViewById<Button>(R.id.btnGuardarPdf)
        val txtEstado = findViewById<TextView>(R.id.txtEstado)

        btnGuardarPdf.setOnClickListener {
            val nombre = editNombre.text?.toString()?.trim().orEmpty()

            if (nombre.isBlank()) {
                editNombre.error = "Escribe un nombre"
                return@setOnClickListener
            }

            try {
                val archivo = crearPdf(nombre)
                txtEstado.text = "PDF guardado en Descargas: $archivo"
                Toast.makeText(this, "PDF guardado correctamente", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                txtEstado.text = "Error al guardar el PDF"
                Toast.makeText(this, e.message ?: "Error desconocido", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun crearPdf(nombre: String): String {
        val pdf = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val page = pdf.startPage(pageInfo)
        val canvas = page.canvas

        val titulo = Paint().apply {
            textSize = 24f
            isFakeBoldText = true
        }

        val texto = Paint().apply {
            textSize = 18f
        }

        canvas.drawText("Registro de nombre", 60f, 100f, titulo)
        canvas.drawText("Nombre: $nombre", 60f, 160f, texto)

        pdf.finishPage(page)

        val nombreArchivo = "Nombre_${System.currentTimeMillis()}.pdf"
        val values = ContentValues().apply {
            put(MediaStore.Downloads.DISPLAY_NAME, nombreArchivo)
            put(MediaStore.Downloads.MIME_TYPE, "application/pdf")
            put(MediaStore.Downloads.RELATIVE_PATH, "Download/NombrePDF")
        }

        val uri = contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values)
            ?: throw IllegalStateException("No se pudo crear el archivo PDF")

        contentResolver.openOutputStream(uri)?.use { output ->
            pdf.writeTo(output)
        } ?: throw IllegalStateException("No se pudo abrir el archivo")

        pdf.close()
        return nombreArchivo
    }
}
