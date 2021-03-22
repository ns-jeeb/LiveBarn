package com.example.livebarnapp

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.livebarnapp.databinding.FragmentSurfaceItemBinding

import com.example.livebarnapp.models.SurfaceItem

class MySurfaceItemRecyclerViewAdapter(val viewModel: SurfaceViewModel, private var values: ArrayList<SurfaceItem>?
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
            if (venueName == null || venueName != surface.venueName){
                binding.venueName.visibility = View.VISIBLE
                binding.venueName.text = surface.venueName
                venueName = surface.venueName
            }else{
                binding.venueName.visibility = View.INVISIBLE
            }
            var thumbnail: Bitmap? = null
            if (adapterPosition%2 == 0){
                thumbnail = viewModel.getThumbnail1Bitmap()
                binding.videoItem.setBackgroundColor(Color.BLACK)
            } else{
                thumbnail = viewModel.getThumbnail2Bitmap()
                binding.videoItem.setBackgroundColor(Color.LTGRAY)
            }
            thumbnail?.let {
                binding.videoItem.setImageBitmap(thumbnail)
            }
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