package kei.su.sales.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kei.su.sales.R
import kei.su.sales.adapter.CustomDropDownAdapter
import kei.su.sales.adapter.ManufacturerAdapter
import kei.su.sales.adapter.SaleClick
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
    private var viewModelAdapter: ManufacturerAdapter? = null


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

        viewModelAdapter = ManufacturerAdapter(SaleClick {
            // When a video is clicked this block or lambda will be called by DevByteAdapter

            // context is not around, we can safely discard this click since the Fragment is no
            // longer on the screen
            Toast.makeText(this, it.manufacturer + " " + it.cost, Toast.LENGTH_SHORT).show()
            val packageManager = this?.packageManager ?: return@SaleClick

//            // Try to generate a direct intent to the YouTube app
//            var intent = Intent(Intent.ACTION_VIEW, it.launchUri)
//            if(intent.resolveActivity(packageManager) == null) {
//                // YouTube app isn't found, use the web url
//                intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.url))
//            }

            startActivity(intent)
        })


        binding.root.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = viewModelAdapter
        }


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
                    var distinctManufacturerSale = Util.createManufacturerSaleList(sales)
                    viewModelAdapter?.sales = distinctManufacturerSale


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
