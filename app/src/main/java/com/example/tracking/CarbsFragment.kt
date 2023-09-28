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
 * Use the [CarbsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CarbsFragment : Fragment() {
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
            val foodItem = Food("rice", 74.0, 0.16, 15.97, 1.53,0.2)
            selectedFoods.add(foodItem)
            calorieViewModel.updateTotalCalories(74.0)
            calorieViewModel.updateTotalFat(0.16)
            calorieViewModel.updateTotalCarbs(15.97)
            calorieViewModel.updateTotalProtein(1.53)
            calorieViewModel.updateTotalFiber(0.2)
//            println(selectedFoods.get(0))
        }

        val foodButton2 = view.findViewById<ImageButton>(R.id.imageButton2)
        foodButton2.setOnClickListener {
            val foodItem = Food("quinoa", 212.0, 3.29, 39.07, 7.43,3.3)
            selectedFoods.add(foodItem)
            calorieViewModel.updateTotalCalories(212.0)
            calorieViewModel.updateTotalFat(3.29)
            calorieViewModel.updateTotalCarbs(39.07)
            calorieViewModel.updateTotalProtein(7.43)
            calorieViewModel.updateTotalFiber(3.3)
        }

        val foodButton3 = view.findViewById<ImageButton>(R.id.imageButton3)
        foodButton3.setOnClickListener {
            val foodItem = Food("sweet potato", 49.0, 0.03, 11.41, 0.89,1.7)
            selectedFoods.add(foodItem)
            calorieViewModel.updateTotalCalories(49.0)
            calorieViewModel.updateTotalFat(0.03)
            calorieViewModel.updateTotalCarbs(11.41)
            calorieViewModel.updateTotalProtein(0.89)
            calorieViewModel.updateTotalFiber(1.7)
        }

        val foodButton4 = view.findViewById<ImageButton>(R.id.imageButton4)
        foodButton4.setOnClickListener {
            val foodItem = Food("kidney beans", 48.0, 0.19, 8.84, 2.98,3.6)
            selectedFoods.add(foodItem)
            calorieViewModel.updateTotalCalories(48.0)
            calorieViewModel.updateTotalFat(0.19)
            calorieViewModel.updateTotalCarbs(8.84)
            calorieViewModel.updateTotalProtein(2.98)
            calorieViewModel.updateTotalFiber(3.6)
        }

        val foodButton5 = view.findViewById<ImageButton>(R.id.imageButton5)
        foodButton5.setOnClickListener {
            val foodItem = Food("beets", 24.0, 0.1, 5.42, 0.91,1.6)
            selectedFoods.add(foodItem)
            calorieViewModel.updateTotalCalories(24.0)
            calorieViewModel.updateTotalFat(0.1)
            calorieViewModel.updateTotalCarbs(5.42)
            calorieViewModel.updateTotalProtein(0.91)
            calorieViewModel.updateTotalFiber(1.6)
        }

        val foodButton6 = view.findViewById<ImageButton>(R.id.imageButton6)
        foodButton6.setOnClickListener {
            val foodItem = Food("oats", 219.0, 3.7, 38.0, 7.5,5.7)
            selectedFoods.add(foodItem)
            calorieViewModel.updateTotalCalories(219.0)
            calorieViewModel.updateTotalFat(3.7)
            calorieViewModel.updateTotalCarbs(38.0)
            calorieViewModel.updateTotalProtein(7.5)
            calorieViewModel.updateTotalFiber(5.7)
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
        return inflater.inflate(R.layout.fragment_carbs, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CarbsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CarbsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}