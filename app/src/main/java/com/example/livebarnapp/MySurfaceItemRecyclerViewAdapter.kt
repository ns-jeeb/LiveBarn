package com.example.livebarnapp

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.livebarnapp.databinding.FragmentSurfaceItemBinding

import com.example.livebarnapp.models.SurfaceItem

class MySurfaceItemRecyclerViewAdapter(val lifecycleOwner: LifecycleOwner,val viewModel: SurfaceViewModel, private var values: ArrayList<SurfaceItem>?
) : RecyclerView.Adapter<MySurfaceItemRecyclerViewAdapter.ViewHolder>() {

    var venueName :String? = null
    lateinit var onItemClick: OnItemClickListener
    fun setOnItemClickListener(listener: OnItemClickListener){
        onItemClick = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        values?.let {
            val item = it[position]
            holder.bind(item)
        }
    }
    override fun getItemCount(): Int = values?.size ?: 0

    inner class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_surface_item, parent, false)) {

        var binding = FragmentSurfaceItemBinding.bind(itemView)

        @RequiresApi(Build.VERSION_CODES.KITKAT)
        fun bind(surface: SurfaceItem){

            val listLiveData : LiveData<Bitmap>? =  when(adapterPosition % 2){
                0 ->  viewModel.getThumbnail1Bitmap()
                1 ->  viewModel.getThumbnail2Bitmap()
                else -> null
            }
            if (venueName == null || venueName != surface.venueName){
                binding.venueName.visibility = View.VISIBLE
                binding.venueName.text = surface.venueName
                venueName = surface.venueName
            }else{
                binding.venueName.visibility = View.INVISIBLE
            }

            listLiveData?.observe(lifecycleOwner, Observer {
                binding.videoItem.setImageBitmap(it)

            })

            binding.serverIp.text = surface.server.ip4
            binding.serverName.text =surface.surfaceName
            itemView.setOnClickListener {
               onItemClick.onItemClick(surface)
            }
        }
        override fun toString(): String {
            return super.toString() + " '" + binding.serverName.text + "'"
        }
    }
    interface OnItemClickListener{
        fun onItemClick(surface: SurfaceItem)
    }
}