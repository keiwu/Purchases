package kei.su.sales.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import kei.su.sales.domain.Sale
import androidx.recyclerview.widget.RecyclerView
import kei.su.sales.R
import kei.su.sales.databinding.ManufacturerItemBinding


/**
 * RecyclerView Adapter for setting up data binding on the items in the list.
 */
class ManufacturerAdapter(val callback: SaleClick) : RecyclerView.Adapter<ManufacturerViewHolder>() {

    /**
     * The videos that our Adapter will show
     */
    var sales: List<Sale> = emptyList()
        set(value) {
            field = value
            // For an extra challenge, update this to use the paging library.

            // Notify any registered observers that the data set has changed. This will cause every
            // element in our RecyclerView to be invalidated.
            notifyDataSetChanged()
        }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManufacturerViewHolder {
        val withDataBinding: ManufacturerItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            ManufacturerViewHolder.LAYOUT,
            parent,
            false)
        return ManufacturerViewHolder(withDataBinding)
    }

    override fun getItemCount() = sales.size

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     */
    override fun onBindViewHolder(holder: ManufacturerViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.sale = sales[position]
            it.manufacturerCallback = callback
        }
    }

}

/**
 * ViewHolder for manufactuer items. All work is done by data binding.
 */
class ManufacturerViewHolder(val viewDataBinding: ManufacturerItemBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.manufacturer_item
    }
}

/**
 * Click listener for Videos. By giving the block a name it helps a reader understand what it does.
 *
 */
class SaleClick(val block: (Sale) -> Unit) {
    /**
     * Called when a video is clicked
     *
     * @param video the video that was clicked
     */
    fun onClick(sale: Sale) = block(sale)
}


