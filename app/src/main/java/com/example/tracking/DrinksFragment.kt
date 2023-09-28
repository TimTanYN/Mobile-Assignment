package com.example.tracking

import CalorieViewModel
import android.content.ContentValues
import android.os.Bundle
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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DrinksFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DrinksFragment : Fragment() {
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
            val foodItem = Food("green tea", 2.5,0.0, 0.0, 0.0, 0.0)
            selectedFoods.add(foodItem)
            calorieViewModel.updateTotalCalories(2.5)
            calorieViewModel.updateTotalFat(0.0)
            calorieViewModel.updateTotalCarbs(0.0)
            calorieViewModel.updateTotalProtein(0.0)
            calorieViewModel.updateTotalFiber(0.0)
//            println(selectedFoods.get(0))
        }

        val foodButton2 = view.findViewById<ImageButton>(R.id.imageButton2)
        foodButton2.setOnClickListener {
            val foodItem = Food("orange juice", 112.0, 0.5, 25.79, 1.74,0.5)
            selectedFoods.add(foodItem)
            calorieViewModel.updateTotalCalories(112.0)
            calorieViewModel.updateTotalFat(0.5)
            calorieViewModel.updateTotalCarbs(25.79)
            calorieViewModel.updateTotalProtein(1.74)
            calorieViewModel.updateTotalFiber(0.5)
        }

        val foodButton3 = view.findViewById<ImageButton>(R.id.imageButton3)
        foodButton3.setOnClickListener {
            val foodItem = Food("coconut water", 46.0, 0.5, 8.9, 1.7,2.6)
            selectedFoods.add(foodItem)
            calorieViewModel.updateTotalCalories(46.0)
            calorieViewModel.updateTotalFat(0.5)
            calorieViewModel.updateTotalCarbs(8.9)
            calorieViewModel.updateTotalProtein(1.7)
            calorieViewModel.updateTotalFiber(2.6)
        }

        val foodButton4 = view.findViewById<ImageButton>(R.id.imageButton4)
        foodButton4.setOnClickListener {
            val foodItem = Food("lemon water", 0.4, 0.0, 0.1, 0.0,0.0)
            selectedFoods.add(foodItem)
            calorieViewModel.updateTotalCalories(0.4)
            calorieViewModel.updateTotalFat(0.0)
            calorieViewModel.updateTotalCarbs(0.1)
            calorieViewModel.updateTotalProtein(0.0)
            calorieViewModel.updateTotalFiber(0.0)
        }

        val foodButton5 = view.findViewById<ImageButton>(R.id.imageButton5)
        foodButton5.setOnClickListener {
            val foodItem = Food("hot chocolate", 192.0, 5.82, 26.58, 8.8,2.5)
            selectedFoods.add(foodItem)
            calorieViewModel.updateTotalCalories(192.0)
            calorieViewModel.updateTotalFat(5.82)
            calorieViewModel.updateTotalCarbs(26.58)
            calorieViewModel.updateTotalProtein(8.8)
            calorieViewModel.updateTotalFiber(2.5)
        }

        val foodButton6 = view.findViewById<ImageButton>(R.id.imageButton6)
        foodButton6.setOnClickListener {
            val foodItem = Food("beet juice", 98.0, 0.3, 23.0, 2.8,4.2)
            selectedFoods.add(foodItem)
            calorieViewModel.updateTotalCalories(98.0)
            calorieViewModel.updateTotalFat(0.3)
            calorieViewModel.updateTotalCarbs(23.0)
            calorieViewModel.updateTotalProtein(2.8)
            calorieViewModel.updateTotalFiber(4.2)
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




            val decimalFormat = DecimalFormat("#.##")
            val formattedFat = decimalFormat.format(totalFat)
            val formattedCarbohydrates = decimalFormat.format(totalCarbohydrates)
            val formattedProtein = decimalFormat.format(totalProtein)
            val formattedCalories = decimalFormat.format(totalCalories)
            val formattedFibre = decimalFormat.format(totalFibre)

            val updates = hashMapOf<String, Any>(
                "totalCalories" to formattedCalories.toDouble() as Any,
                "totalFat" to formattedFat.toDouble() as Any,
                "totalCarbs" to formattedCarbohydrates.toDouble() as Any,
                "totalFibre" to formattedFibre.toDouble() as Any,
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
    private fun calculateTotals()  {

        for (food in selectedFoods) {
            println(food.calories)
            totalCalories += food.calories
            totalFat += food.totalFat
            totalCarbohydrates += food.totalCarbohydrates
            totalProtein += food.protein
        }

        val caloriesTextView = view?.findViewById<TextView>(R.id.caloriesTextView)
        val fatTextView = view?.findViewById<TextView>(R.id.fatTextView)
        val carbohydratesTextView = view?.findViewById<TextView>(R.id.carbohydratesTextView)
        val proteinTextView = view?.findViewById<TextView>(R.id.proteinTextView)

        val decimalFormat = DecimalFormat("#.##")
        val formattedFat = decimalFormat.format(totalFat)
        val formattedCarbohydrates = decimalFormat.format(totalCarbohydrates)
        val formattedProtein = decimalFormat.format(totalProtein)
        val formattedCalories = decimalFormat.format(totalCalories)

        caloriesTextView?.text = "Total Calories: $formattedCalories"
        fatTextView?.text = "Total Fat: $formattedFat"
        carbohydratesTextView?.text = "Total Carbohydrates: $formattedCarbohydrates"
        proteinTextView?.text = "Total Protein: $formattedProtein"



//        val intent = Intent(activity, FoodPyramidTab::class.java)
//        intent.putExtra("totals", totalCalories)
//        intent.putExtra("totals", totalFat)
//        intent.putExtra("totals", totalCarbohydrates)
//        intent.putExtra("totals", totalProtein)
//        startActivity(intent)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drinks, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DrinksFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DrinksFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}