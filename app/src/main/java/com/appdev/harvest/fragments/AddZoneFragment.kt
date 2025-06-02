package com.appdev.harvest.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.appdev.harvest.R
import com.appdev.harvest.models.Zone
import com.appdev.harvest.fragments.BaseFragment
import java.util.*

/**
 * Фрагмент для добавления новой зоны участка
 */
class AddZoneFragment : BaseFragment() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var storage: FirebaseStorage

    private var selectedImageUri: Uri? = null

    companion object {
        private const val REQUEST_CODE_SELECT_IMAGE = 1001
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_zone, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!isAuthorized()) {
            onNotAuthorized()
            return
        }

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()

        val imageView = view.findViewById<ImageView>(R.id.zoneImageView)
        val btnSelectImage = view.findViewById<Button>(R.id.btnSelectImage)
        val btnSaveZone = view.findViewById<Button>(R.id.btnSaveZone)

        // Кнопка выбора изображения из галереи
        btnSelectImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE)
        }

        // Сохранение зоны
        btnSaveZone.setOnClickListener {
            val editTextName = view.findViewById<EditText>(R.id.editTextZoneName)
            val editTextDescription = view.findViewById<EditText>(R.id.editTextZoneDescription)

            val name = editTextName.text.toString().trim()
            val description = editTextDescription.text.toString().trim()

            if (name.isEmpty()) {
                Toast.makeText(context, "Введите название зоны", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val currentUser = auth.currentUser
            val userId = currentUser?.uid

            if (userId == null) {
                Toast.makeText(context, "Ошибка: пользователь не найден", Toast.LENGTH_LONG).show()
                parentFragmentManager.popBackStack()
                return@setOnClickListener
            }

            val zone = Zone(
                id = UUID.randomUUID().toString(),
                name = name,
                description = description,
                userId = userId,
                imageUrl = ""
            )

            if (selectedImageUri != null) {
                uploadZoneImage(selectedImageUri!!, zone)
            } else {
                saveZoneToFirestore(zone)
            }
        }
    }

    /**
     * Загружает изображение в Firebase Storage
     */
    private fun uploadZoneImage(imageUri: Uri, zone: Zone) {
        val fileRef = storage.reference.child("images/zones/${zone.id}.jpg")

        fileRef.putFile(imageUri)
            .addOnSuccessListener { taskSnapshot ->
                fileRef.downloadUrl.addOnSuccessListener { downloadUri ->
                    val updatedZone = zone.copy(imageUrl = downloadUri.toString())
                    saveZoneToFirestore(updatedZone)
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, "Ошибка загрузки изображения", Toast.LENGTH_LONG).show()
            }
    }

    /**
     * Сохраняет зону в Firestore
     */
    private fun saveZoneToFirestore(zone: Zone) {
        firestore.collection("zones")
            .document(zone.id)
            .set(zone)
            .addOnSuccessListener {
                Toast.makeText(context, "Зона сохранена", Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack()
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Ошибка сохранения зоны: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }

    /**
     * Получаем URI выбранного изображения
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == -1) { // RESULT_OK == -1
            data?.data?.let { uri ->
                selectedImageUri = uri
                view?.findViewById<ImageView>(R.id.zoneImageView)?.setImageURI(uri)
            }
        }
    }
}