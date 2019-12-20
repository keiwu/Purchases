package kei.su.sales.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kei.su.sales.R
import kei.su.sales.adapter.CustomDropDownAdapter
import kei.su.sales.databinding.ActivityMainBinding
import kei.su.sales.domain.Building
import kei.su.sales.domain.Sale
import kei.su.sales.utils.Util
import kei.su.sales.viewmodels.SaleBuildingViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var manuSpinner: Spinner
    lateinit var catSpinner: Spinner
    lateinit var countrySpinner: Spinner
    lateinit var stateSpinner: Spinner
    lateinit var itemSpinner: Spinner

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
        countrySpinner = binding.countrySpinner
        stateSpinner = binding.stateSpinner
        itemSpinner = binding.itemIdSpinner

        val c = Calendar.getInstance().getTime()
        val df = SimpleDateFormat("dd-MMM-yyyy")
        val formattedDate = df.format(c)
        binding.dateTv.setText(formattedDate)

        viewModel.buildinglist.observe(
            this,
            Observer<List<Building>> {
                    buildings ->
                var countryList = Util.createCountryList(buildings)
                var countrySpinnerAdapter = CustomDropDownAdapter(this, countryList)
                countrySpinner?.adapter = countrySpinnerAdapter

                countrySpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        var item = parent!!.getItemAtPosition(position)
                        var country = item?.toString() ?: ""

                        GlobalScope.launch {
                            viewModel.getSaleByCountry(country)
                        }
                    }

                }

                var stateList = Util.createStateList(buildings)
                var stateSpinnerAdapter = CustomDropDownAdapter(this, stateList)
                stateSpinner?.adapter = stateSpinnerAdapter

                stateSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        var item = parent!!.getItemAtPosition(position)
                        var state = item?.toString() ?: ""

                        GlobalScope.launch {
                            viewModel.getSaleByState(state)
                        }
                    }
                }
            })

        viewModel.salelist.observe(
            this,
            Observer<List<Sale>> {
                    sales ->
                    var itemList = Util.createManufacturerList(sales)
                    var catList = Util.createCatList(sales)


                    var spinnerAdapter = CustomDropDownAdapter(this, itemList)
                    manuSpinner?.adapter = spinnerAdapter

                    manuSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                        override fun onNothingSelected(p0: AdapterView<*>?) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            var item = parent!!.getItemAtPosition(position)
                            var manufacturer = item?.toString() ?: ""
                            GlobalScope.launch {
                                viewModel.getSaleByManufacturer(manufacturer)
                            }
                        }
                    }

                var catSpinnerAdapter = CustomDropDownAdapter(this, catList.map{ it.toString() })
                catSpinner?.adapter = catSpinnerAdapter

                catSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        var item = parent!!.getItemAtPosition(position)
                        var category = item?.toString() ?: ""

                        GlobalScope.launch {
                            viewModel.getSaleByCategory(category)
                        }
                    }
                }


                var itemIdList = Util.createItemIdList(sales)
                var itemSpinnerAdapter = CustomDropDownAdapter(this, itemIdList.map{it.toString()})
                itemSpinner?.adapter = itemSpinnerAdapter

                itemSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        var item = parent!!.getItemAtPosition(position)
                        var itemId = item?.toString() ?: ""
                        GlobalScope.launch {
                            viewModel.getItemCountById(itemId)
                        }
                    }
                }

            })

        viewModel.manufacturerSale.observe(this, Observer {
                cost->binding.manuPurchaseTv.setText("$" + cost.toLong())
        })

        viewModel.categorySale.observe(this, Observer {
                cost->binding.catPurchaseTv.setText("$" + cost.toLong())
        })

        viewModel.countrySale.observe(this, Observer {
                cost->binding.countryPurchaseTv.setText("$" + cost.toLong())
        })

        viewModel.stateSale.observe(this, Observer {
                cost->binding.statePurchaseTv.setText("$" + cost.toLong())
        })

        viewModel.itemCount.observe(this, Observer {
                count->binding.itemCountTv.setText(count.toString())
        })

        viewModel.buildinngWithMostSale.observe(this, Observer {
                buildingSale->binding.buildingMostSaleTv.setText(buildingSale.get(0).buildingName)
        })
    }
}
