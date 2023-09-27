package com.example.tracking

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.UUID

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PharmacyUpdateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PharmacyUpdateFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var storageRef: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pharmacy_update, container, false)
    }

    private var selectedImageUri: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pickImageButton: Button = view.findViewById(R.id.pickImageButton)
        pickImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, PharmacyUpdateFragment.REQUEST_CODE_IMAGE_PICK)
        }

        val uploadImageButton: Button = view.findViewById(R.id.uploadImageButton)
        uploadImageButton.setOnClickListener {
            // Call your method to upload the image to Firebase Storage
            selectedImageUri?.let { uri ->
                uploadImageToFirebaseStorage(uri)
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PharmacyUpdateFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PharmacyUpdateFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
        const val REQUEST_CODE_IMAGE_PICK = 1001
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val view = view ?: return  // Ensure the fragment's view is not null
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PharmacyCreateFragment.REQUEST_CODE_IMAGE_PICK && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data
            val selectedImageView: ImageView = view.findViewById(R.id.selectedImageView)
            selectedImageView.setImageURI(selectedImageUri)
        }
    }

    private fun uploadImageToFirebaseStorage(selectedImageUri: Uri) {
        val view = view ?: return
        val name = view.findViewById<EditText>(R.id.name).text.toString()
        val desc = view.findViewById<EditText>(R.id.desc).text.toString()
        val id = view.findViewById<EditText>(R.id.pharmcyID).text.toString()
        println(id)
        storageRef = FirebaseStorage.getInstance().reference.child("upload${UUID.randomUUID()}")

        // Upload the image to Firebase Storage
        storageRef.putFile(selectedImageUri)
            .addOnSuccessListener {
                // Get the download URL of the uploaded image
                storageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                    // Use the downloadUri (which is a URI pointing to the uploaded image in Firebase Storage)
                    Log.d("Upload", "Uploaded image URL: $downloadUri")

                    // Get a reference to the Firestore database
                    val db = FirebaseFirestore.getInstance()

                    // Create a reference to the specific collection and document where you want to save the downloadUri
                    val documentReference = db.collection("/medicine").document(id)


                    // Use the set() or update() method to save the downloadUri to the specified document
                    val data : MutableMap<String, Any> = hashMapOf(
                        "URL" to downloadUri.toString(),
                        "pharmacyDesc" to desc,
                        "pharmacyName" to name
                    )
                    documentReference.update(data)
                        .addOnSuccessListener {
                            Log.d("Firestore", "Document successfully written!")
                        }
                        .addOnFailureListener { e ->
                            Log.w("Firestore", "Error writing document", e)
                        }
                }
            }
            .addOnFailureListener { exception ->
                // Handle any errors during the upload
                Log.e("Upload", "Error uploading image", exception)
            }
    }
}