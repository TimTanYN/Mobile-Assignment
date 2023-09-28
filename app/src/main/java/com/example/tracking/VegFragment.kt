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
 * Use the [VegFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VegFragment : Fragment() {
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
            val foodItem = Food("spinach", 26.0, 0.44, 4.12, 3.24,2.5)
            selectedFoods.add(foodItem)
            val calorieViewModel = ViewModelProvider(requireActivity()).get(CalorieViewModel::class.java)
            calorieViewModel.updateTotalCalories(26.0)
            calorieViewModel.updateTotalFat(0.44)
            calorieViewModel.updateTotalCarbs(4.12)
            calorieViewModel.updateTotalProtein(3.24)
            calorieViewModel.updateTotalFiber(2.5)
        }

        val foodButton2 = view.findViewById<ImageButton>(R.id.imageButton2)
        foodButton2.setOnClickListener {
            val foodItem = Food("cabbage", 48.0, 3.16, 4.94, 1.12,2.6)
            selectedFoods.add(foodItem)
            calorieViewModel.updateTotalCalories(48.0)
            calorieViewModel.updateTotalFat(3.16)
            calorieViewModel.updateTotalCarbs(4.94)
            calorieViewModel.updateTotalProtein(1.12)
            calorieViewModel.updateTotalFiber(2.6)

        }

        val foodButton3 = view.findViewById<ImageButton>(R.id.imageButton3)
        foodButton3.setOnClickListener {
            val foodItem = Food("zucchini", 18.0, 0.2, 3.8, 1.37,1.2)
            selectedFoods.add(foodItem)
            calorieViewModel.updateTotalCalories(18.0)
            calorieViewModel.updateTotalFat(0.2)
            calorieViewModel.updateTotalCarbs(3.8)
            calorieViewModel.updateTotalProtein(1.37)
            calorieViewModel.updateTotalFiber(1.2)
        }

        val foodButton4 = view.findViewById<ImageButton>(R.id.imageButton4)
        foodButton4.setOnClickListener {
            val foodItem = Food("lettuce", 19.0, 0.34, 3.72, 1.39,2.4)
            selectedFoods.add(foodItem)
            calorieViewModel.updateTotalCalories(19.0)
            calorieViewModel.updateTotalFat(0.34)
            calorieViewModel.updateTotalCarbs(3.72)
            calorieViewModel.updateTotalProtein(1.39)
            calorieViewModel.updateTotalFiber(2.4)
        }

        val foodButton5 = view.findViewById<ImageButton>(R.id.imageButton5)
        foodButton5.setOnClickListener {
            val foodItem = Food("green peas", 92.0, 0.45, 16.4, 6.15,5.8)
            selectedFoods.add(foodItem)
            calorieViewModel.updateTotalCalories(92.0)
            calorieViewModel.updateTotalFat(0.45)
            calorieViewModel.updateTotalCarbs(16.4)
            calorieViewModel.updateTotalProtein(6.15)
            calorieViewModel.updateTotalFiber(5.8)
        }

        val foodButton6 = view.findViewById<ImageButton>(R.id.imageButton6)
        foodButton6.setOnClickListener {
            val foodItem = Food("kale", 57.0, 0.79, 11.35, 3.74,2.3)
            selectedFoods.add(foodItem)
            calorieViewModel.updateTotalCalories(57.0)
            calorieViewModel.updateTotalFat(0.79)
            calorieViewModel.updateTotalCarbs(11.35)
            calorieViewModel.updateTotalProtein(3.74)
            calorieViewModel.updateTotalFiber(2.3)
        }

        val foodButton7 = view.findViewById<ImageButton>(R.id.imageButton7)
        foodButton7.setOnClickListener {
            val foodItem = Food("cauliflower", 28.0, 0.11, 6.01, 2.25,2.8)
            selectedFoods.add(foodItem)
            calorieViewModel.updateTotalCalories(28.0)
            calorieViewModel.updateTotalFat(0.11)
            calorieViewModel.updateTotalCarbs(6.01)
            calorieViewModel.updateTotalProtein(2.25)
            calorieViewModel.updateTotalFiber(2.8)
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
            val formattedFat = decimalFormat.format(calorieViewModel.totalFat)
            val formattedCarbohydrates = decimalFormat.format(calorieViewModel.totalCarbs)
            val formattedProtein = decimalFormat.format(calorieViewModel.totalProtein)
            val formattedCalories = decimalFormat.format(calorieViewModel.totalCalories)
            val formattedFibre = decimalFormat.format(calorieViewModel.totalFiber)

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_veg, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment VegFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            VegFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}