package com.example.tracking

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import java.text.DecimalFormat

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FoodPyramidFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FoodPyramidFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        val view = inflater.inflate(R.layout.fragment_food_pyramid, container, false)

        val button = view.findViewById<ImageButton>(R.id.imageView2)
        button.setOnClickListener {
            val intent = Intent(requireContext(), FoodSelectionTab::class.java)
            startActivity(intent)
        }

        val db = FirebaseFirestore.getInstance()
        val collectionRef = db.collection("food_calories")

// Create a query to retrieve documents
        collectionRef.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    // Access the fields within each document
                    val totalCalories = document.getDouble("totalCalories")
                    val totalFat = document.getDouble("totalFat")
                    val totalCarbohydrates = document.getDouble("totalCarbs")
                    val totalProtein = document.getDouble("totalProtein")
                    println(totalProtein)

                    if (totalCalories != null && totalFat != null && totalCarbohydrates != null && totalProtein != null) {
                        // retrieved values in calories, fat, carbohydrates, and protein
                        val caloriesTextView = view?.findViewById<TextView>(R.id.caloriesNo)
                        val fatTextView = view?.findViewById<TextView>(R.id.totalFatNo)
                        val carbohydratesTextView = view?.findViewById<TextView>(R.id.totalCarbsNo)
                        val proteinTextView = view?.findViewById<TextView>(R.id.proteinNo)

                        val decimalFormat = DecimalFormat("#.##")
                        val formattedFat = decimalFormat.format(totalFat)
                        val formattedCalories = decimalFormat.format(totalCalories)
                        val formattedCarbohydrates = decimalFormat.format(totalCarbohydrates)
                        val formattedProtein = decimalFormat.format(totalProtein)

                        caloriesTextView?.text = "Total Calories: $formattedCalories"
                        fatTextView?.text = "Total Fat: $formattedFat g"
                        carbohydratesTextView?.text = "Total Carbs: $formattedCarbohydrates g"
                        proteinTextView?.text = "Total Protein: $formattedProtein"
                    }
                }
            }
            .addOnFailureListener { exception ->
                // Handle any errors here
            }
//        val intent = intent
//        val calories = intent.getDoubleExtra()

        return view
    }




    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FoodPyramidFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FoodPyramidFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}