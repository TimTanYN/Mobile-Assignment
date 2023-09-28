import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalorieViewModel : ViewModel() {
    // Define LiveData properties for overall totals
    private val _totalCalories = MutableLiveData<Double>(0.0)
    private val _totalFat = MutableLiveData<Double>(0.0)
    private val _totalFiber = MutableLiveData<Double>(0.0)
    private val _totalCarbs = MutableLiveData<Double>(0.0)
    private val _totalProtein = MutableLiveData<Double>(0.0)

    // Public accessors for the LiveData
    val totalCalories: LiveData<Double> = _totalCalories
    val totalFat: LiveData<Double> = _totalFat
    val totalFiber: LiveData<Double> = _totalFiber
    val totalCarbs: LiveData<Double> = _totalCarbs
    val totalProtein: LiveData<Double> = _totalProtein

    // Functions to update the values
    fun updateTotalCalories(calories: Double) {
        _totalCalories.value = _totalCalories.value?.plus(calories)
    }

    fun updateTotalFat(fat: Double) {
        _totalFat.value = _totalFat.value?.plus(fat)
    }

    fun updateTotalFiber(fiber: Double) {
        _totalFiber.value = _totalFiber.value?.plus(fiber)
    }

    fun updateTotalCarbs(carbs: Double) {
        _totalCarbs.value = _totalCarbs.value?.plus(carbs)
    }

    fun updateTotalProtein(protein: Double) {
        _totalProtein.value = _totalProtein.value?.plus(protein)
    }
}