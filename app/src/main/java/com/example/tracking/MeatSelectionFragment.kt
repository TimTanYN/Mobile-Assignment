package com.example.tracking

import CalorieViewModel
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MeatSelectionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@Suppress("UNREACHABLE_CODE", "CAST_NEVER_SUCCEEDS")
class MeatSelectionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val selectedFoods = mutableListOf<Food>()
    private var totalCalories = 0.0
    private var totalFat = 0.0
    private var totalCarbohydrates = 0.0
    private var totalProtein = 0.0
    private var totalFibre = 0.0
    private lateinit var calorieViewModel: CalorieViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }



    data class Food(val name: String,
                    val calories: Double,
                    val totalFat: Double,
                    val totalCarbohydrates: Double,
                    val protein: Double,
                    val fibre: Double)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calorieViewModel = ViewModelProvider(requireActivity()).get(CalorieViewModel::class.java)
        // Set up click listeners for food image buttons
        val foodButton1 = view.findViewById<ImageButton>(R.id.imageButton1)
        foodButton1.setOnClickListener {
            val foodItem = Food("Beef", 331.09, 23.00, 0.0, 17.81,0.0)
            selectedFoods.add(foodItem)
            val calorieViewModel = ViewModelProvider(requireActivity()).get(CalorieViewModel::class.java)
            calorieViewModel.updateTotalCalories(331.09)
            calorieViewModel.updateTotalFat(23.00)
            calorieViewModel.updateTotalFiber(0.0)
            calorieViewModel.updateTotalCarbs(17.81)
            calorieViewModel.updateTotalProtein(0.0)
        }

        val foodButton2 = view.findViewById<ImageButton>(R.id.imageButton2)
        foodButton2.setOnClickListener {
            val foodItem = Food("pork", 298.0, 24.03, 0.0, 24.03,0.0)
            selectedFoods.add(foodItem)
            val calorieViewModel = ViewModelProvider(requireActivity()).get(CalorieViewModel::class.java)
            calorieViewModel.updateTotalCalories(298.0)
            calorieViewModel.updateTotalFat(24.03)
            calorieViewModel.updateTotalFiber(0.0)
            calorieViewModel.updateTotalCarbs(0.0)
            calorieViewModel.updateTotalProtein(24.03)
        }

        val foodButton3 = view.findViewById<ImageButton>(R.id.imageButton3)
        foodButton3.setOnClickListener {
            val foodItem = Food("chicken", 120.0, 1.5, 0.0, 26.0,0.0)
            selectedFoods.add(foodItem)
            calorieViewModel.updateTotalCalories(120.0)
            calorieViewModel.updateTotalFat(1.5)
            calorieViewModel.updateTotalFiber(0.0)
            calorieViewModel.updateTotalCarbs(0.0)
            calorieViewModel.updateTotalProtein(26.0)
        }

        val foodButton4 = view.findViewById<ImageButton>(R.id.imageButton4)
        foodButton4.setOnClickListener {
            val foodItem = Food("goat", 124.0, 2.62, 0.0, 23.36,0.0)
            selectedFoods.add(foodItem)
            calorieViewModel.updateTotalCalories(124.0)
            calorieViewModel.updateTotalFat(2.62)
            calorieViewModel.updateTotalFiber(0.0)
            calorieViewModel.updateTotalCarbs(0.0)
            calorieViewModel.updateTotalProtein(23.36)
        }

        val foodButton5 = view.findViewById<ImageButton>(R.id.imageButton5)
        foodButton5.setOnClickListener {
            val foodItem = Food("roasted beef", 303.0, 19.64, 0.0, 29.38,0.0)
            selectedFoods.add(foodItem)
            calorieViewModel.updateTotalCalories(303.0)
            calorieViewModel.updateTotalFat(19.64)
            calorieViewModel.updateTotalFiber(0.0)
            calorieViewModel.updateTotalCarbs(0.0)
            calorieViewModel.updateTotalProtein(29.38)
        }

        val foodButton6 = view.findViewById<ImageButton>(R.id.imageButton6)
        foodButton6.setOnClickListener {
            val foodItem = Food("roasted pork", 307.0, 19.32, 0.0, 31.0,0.0)
            selectedFoods.add(foodItem)
            calorieViewModel.updateTotalCalories(307.0)
            calorieViewModel.updateTotalFat(19.32)
            calorieViewModel.updateTotalFiber(0.0)
            calorieViewModel.updateTotalCarbs(0.0)
            calorieViewModel.updateTotalProtein(31.00)
        }

        val foodButton7 = view.findViewById<ImageButton>(R.id.imageButton7)
        foodButton7.setOnClickListener {
            val foodItem = Food("fried chicken", 337.0, 21.34, 18.51, 17.68,0.0)
            selectedFoods.add(foodItem)
            calorieViewModel.updateTotalCalories(337.0)
            calorieViewModel.updateTotalFat(21.34)
            calorieViewModel.updateTotalFiber(0.0)
            calorieViewModel.updateTotalCarbs(18.51)
            calorieViewModel.updateTotalProtein(17.68)
        }
        val foodButton8 = view.findViewById<ImageButton>(R.id.imageButton8)
        foodButton8.setOnClickListener {
            val foodItem = Food("grilled goat", 347.0, 25.34, 0.0, 21.68,0.0)
            selectedFoods.add(foodItem)
            calorieViewModel.updateTotalCalories(347.0)
            calorieViewModel.updateTotalFat(25.34)
            calorieViewModel.updateTotalFiber(0.0)
            calorieViewModel.updateTotalCarbs(0.0)
            calorieViewModel.updateTotalProtein(21.68)
        }

        val calculateButton = view?.findViewById<Button>(R.id.calculateButton)
        calculateButton?.setOnClickListener {
            // Calculate the totals and navigate to the result fragment
            calculateTotals()

        }

        val storeButton = view?.findViewById<Button>(R.id.storeButton)
        storeButton?.setOnClickListener {
//            val currentDate = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())

            val db = FirebaseFirestore.getInstance()
            val documentReference = db.collection("food_calories")
            val documentId = "x80uSQJUIrbb7dDJGAmL" // Replace with the document ID
            val documentRef = documentReference.document(documentId)

//            documentReference.get()
//                .addOnSuccessListener { documents ->
//                    for (document in documents) {
//                        // Access the fields within each document
//                        val totalCalories = document.getDouble("totalCalories")
//                        val totalFat = document.getDouble("totalFat")
//                        val totalCarbohydrates = document.getDouble("totalCarbs")
//                        val totalProtein = document.getDouble("totalProtein")
//                        println(totalProtein)
//
//                        if (totalCalories != null && totalFat != null && totalCarbohydrates != null && totalProtein != null) {
//                            // retrieved values in calories, fat, carbohydrates, and protein
//
//
//                            val decimalFormat = DecimalFormat("#.##")
//                            val formattedFat1 = decimalFormat.format(totalFat)
//                            val formattedCalories1 = decimalFormat.format(totalCalories)
//                            val formattedCarbohydrates1 = decimalFormat.format(totalCarbohydrates)
//                            val formattedProtein1 = decimalFormat.format(totalProtein)
//
//                        }
//                    }
//                }
//                .addOnFailureListener { exception ->
//                    // Handle any errors here
//                }


            val decimalFormat = DecimalFormat("#.##")
            val formattedFat = calorieViewModel.totalFat.value?.let { decimalFormat.format(it) } ?: "N/A"
            val formattedCalories = calorieViewModel.totalCalories.value?.let { decimalFormat.format(it) } ?: "N/A"
            val formattedProtein = calorieViewModel.totalProtein.value?.let { decimalFormat.format(it) } ?: "N/A"
            val formattedFiber = calorieViewModel.totalFiber.value?.let { decimalFormat.format(it) } ?: "N/A"
            val formattedCarbohydrates = calorieViewModel.totalCarbs.value?.let { decimalFormat.format(it) } ?: "N/A"

            val updates = hashMapOf<String, Any>(
                "totalCalories" to formattedCalories.toDouble() as Any,
                "totalFat" to formattedFat.toDouble() as Any,
                "totalCarbs" to formattedCarbohydrates.toDouble() as Any,
                "totalFibre" to formattedFiber.toDouble() as Any,
                "totalProtein" to formattedProtein.toDouble() as Any
            )


//             Create a map of fields to update
//            val add: MutableMap<String, Any> = hashMapOf(
//                "totalCalories" to formattedCalories.toDouble() as Any,
//                "totalFat" to formattedFat.toDouble() as Any,
//                "totalCarbs" to formattedCarbohydrates.toDouble() as Any,
//                "totalProtein" to formattedProtein.toDouble() as Any
//                // ... add more fields as needed
//            )


            // Update the fields
            documentRef.update(updates)
                .addOnSuccessListener {
                    Log.d(ContentValues.TAG, "Document successfully updated!")
                }
                .addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "Error updating document", e)
                }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meat_selection, container, false)
        // Set up click listeners for food image buttons


        // Implement similar click listeners for other food items


        return view
    }







    private fun calculateTotals()  {

        for (food in selectedFoods) {
            println(food.calories)
            totalCalories += food.calories
            totalFat += food.totalFat
            totalCarbohydrates += food.totalCarbohydrates
            totalProtein += food.protein
            totalFibre += food.fibre
        }

        val caloriesTextView = view?.findViewById<TextView>(R.id.caloriesTextView)
        val fatTextView = view?.findViewById<TextView>(R.id.fatTextView)
        val carbohydratesTextView = view?.findViewById<TextView>(R.id.carbohydratesTextView)
        val proteinTextView = view?.findViewById<TextView>(R.id.proteinTextView)
        val fiberTextView = view?.findViewById<TextView>(R.id.fibreTextView)


        val decimalFormat = DecimalFormat("#.##")
        val formattedFat = calorieViewModel.totalFat.value?.let { decimalFormat.format(it) } ?: "N/A"
        val formattedCalories = calorieViewModel.totalCalories.value?.let { decimalFormat.format(it) } ?: "N/A"
        val formattedProtein = calorieViewModel.totalProtein.value?.let { decimalFormat.format(it) } ?: "N/A"
        val formattedFiber = calorieViewModel.totalFiber.value?.let { decimalFormat.format(it) } ?: "N/A"
        val formattedCarbohydrates = calorieViewModel.totalCarbs.value?.let { decimalFormat.format(it) } ?: "N/A"


        caloriesTextView?.text = "Total Calories: $formattedCalories"
        fatTextView?.text = "Total Fat: $formattedFat"
        carbohydratesTextView?.text = "Total Carbohydrates: $formattedCarbohydrates"
        proteinTextView?.text = "Total Protein: $formattedProtein"
        fiberTextView?.text = "Total Fiber: $formattedFiber"



//        val intent = Intent(activity, FoodPyramidTab::class.java)
//        intent.putExtra("totals", totalCalories)
//        intent.putExtra("totals", totalFat)
//        intent.putExtra("totals", totalCarbohydrates)
//        intent.putExtra("totals", totalProtein)
//        startActivity(intent)

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MeatSelectionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MeatSelectionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}