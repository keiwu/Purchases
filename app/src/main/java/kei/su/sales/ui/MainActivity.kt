package kei.su.sales.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kei.su.sales.R
import kei.su.sales.adapter.CustomDropDownAdapter
import kei.su.sales.database.CountrySale
import kei.su.sales.databinding.ActivityMainBinding
import kei.su.sales.domain.Building
import kei.su.sales.domain.Sale
import kei.su.sales.utils.Util
import kei.su.sales.viewmodels.SaleBuildingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var manuSpinner: Spinner
    lateinit var catSpinner: Spinner

    /**
     * One way to delay creation of the viewModel until an appropriate lifecycle method is to use
     * lazy.
     */
    private val viewModel: SaleBuildingViewModel by lazy {
        val activity = requireNotNull(this) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, SaleBuildingViewModel.Factory(activity.application))
            .get(SaleBuildingViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.buildingViewModel = viewModel
        binding.setLifecycleOwner(this)
        manuSpinner = binding.manuSpinner
        catSpinner = binding.categorySpinner

        viewModel.salelist.observe(
            this,
            Observer<List<Sale>> {
                    sales ->
                    var itemList = Util.createManufacturerList(sales)
                    var catList = Util.createCatList(sales)

                    var spinnerAdapter = CustomDropDownAdapter(this, itemList)
                    manuSpinner?.adapter = spinnerAdapter

                    manuSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                        override fun onNothingSelected(parent: AdapterView<*>?) {

                        }

                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            var item = parent!!.getItemAtPosition(position)
                            var manufacturer = item?.toString() ?: ""
                            Toast.makeText(applicationContext, manufacturer, Toast.LENGTH_SHORT).show()

                            var cost = 0.0
                            GlobalScope.launch {
                                withContext(Dispatchers.IO) {
                                    cost = viewModel.getSaleByManufacturer(manufacturer)
                                }
                            }

                            Thread.sleep(100)       //temporary solution
                            binding.manuPurchaseTv.setText("$" + cost.toLong())
                        }

                    }

                var catSpinnerAdapter = CustomDropDownAdapter(this, catList)
                catSpinner?.adapter = catSpinnerAdapter

                catSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }

                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        var item = parent!!.getItemAtPosition(position)
                        var category = item?.toString() ?: ""
                        Toast.makeText(applicationContext, category, Toast.LENGTH_SHORT).show()

                        var cost = 0.0
                        GlobalScope.launch {
                            withContext(Dispatchers.IO) {
                                cost = viewModel.getSaleByCategory(category)
                            }
                        }

                        Thread.sleep(100)       //temporary solution
                        binding.catPurchaseTv.setText("$" + cost.toLong())
                    }

                }

            })

//        viewModel.manufactuerSale.observe(
//            this,
//            Observer<Double> {
//                    cost -> binding.manuPurchaseTv.setText("$" + cost)
//
//            })

    }



}
