package com.example.appcrud.navigation

import android.content.Context
import android.os.Environment
import com.example.appcrud.screens.Actividad
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import java.io.File

class DocumentUtil {

    // creacion funcion para crear un PDF a partir de una actividad
    // Context : se hace referencia a un objeto que se necesita para acceder a todos los recursos
    // tipo file
    fun createPdfFile(context : Context, actividad : Actividad): File? {

        try {


            val pdfDirectory = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "Actividades")
            if (!pdfDirectory.exists()) { // si el directorio no existe, lo crea
                pdfDirectory.mkdirs()
            }
            // crear archivo dentro del directorio Actividades
            val pdfFile = File(pdfDirectory, "${actividad.nombreActividad}.pdf")

            val writer = PdfWriter(pdfFile) // Crea un escritor (writer) de PDF que escribirá en el archivo pdfFile
            val pdfDocument = com.itextpdf.kernel.pdf.PdfDocument(writer)//  escribir el contenido en el PDFescribir el contenido en el PDF
            val document = Document(pdfDocument)

            document.add(Paragraph("Detalles de Actividad"))
            document.add(Paragraph("Nombre de Actividad: ${actividad.nombreActividad}"))
            document.add(Paragraph("Precio de Actividad: ${actividad.precioPorActividad} €"))
            document.add(Paragraph("cantidad de Personas: ${actividad.personasPorActividad}"))
            document.add(Paragraph("Precio Total: ${actividad.precioTotalActividad} €"))

            document.close()

            return pdfFile // devolver el archivo PDF

        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }



    }

}