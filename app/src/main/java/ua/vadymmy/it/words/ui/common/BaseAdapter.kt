package ua.vadymmy.it.words.ui.common

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<BINDING_TYPE : ViewBinding, ELEMENT_TYPE> :
    RecyclerView.Adapter<BaseAdapter<BINDING_TYPE, ELEMENT_TYPE>.BaseViewHolder>() {

    var elements = mutableListOf<ELEMENT_TYPE>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun removeAt(position: Int) {
        if (position > elements.lastIndex) return
        notifyItemRemoved(position)
        elements.removeAt(position)
    }

    override fun getItemCount(): Int = elements.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(elements[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BaseViewHolder(
        inflateBinding(LayoutInflater.from(parent.context), parent)
    )

    abstract fun BINDING_TYPE.onBindElement(
        element: ELEMENT_TYPE,
        context: Context,
        adapterPosition: Int
    )

    abstract fun inflateBinding(layoutInflater: LayoutInflater, parent: ViewGroup): BINDING_TYPE

    inner class BaseViewHolder(
        private val binding: BINDING_TYPE
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(element: ELEMENT_TYPE) {
            binding.onBindElement(element, binding.root.context, adapterPosition)
        }
    }
}