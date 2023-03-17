package com.mustafa.tklakazan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mustafa.tklakazan.R
import com.mustafa.tklakazan.databinding.RecyclerIbanRowBinding
import com.mustafa.tklakazan.model.User
import com.mustafa.tklakazan.model.UserIBAN

class IBANAdapter(val arrayList: ArrayList<UserIBAN>) : RecyclerView.Adapter<IBANAdapter.IbanHolder>() {

    class IbanHolder(val binding : RecyclerIbanRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IbanHolder {
        val binding = RecyclerIbanRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return IbanHolder(binding)
    }

    override fun onBindViewHolder(holder: IbanHolder, position: Int) {
        holder.binding.ibanNameTextView.text = arrayList[position].ibanName
        holder.binding.ibanAddressTextView.text = arrayList[position].iban

        holder.itemView.setOnLongClickListener {
            val popupMenu = PopupMenu(holder.itemView.context,holder.itemView)
            popupMenu.menuInflater.inflate(R.menu.delete_item_menu,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.delete_item -> {
                        val deleteItem = UserIBAN()
                        deleteItem.deleteIBAN(holder.itemView.context,arrayList[position].email,arrayList,position)
                        notifyItemChanged(position)
                    }
                }
                true
            }
            popupMenu.show()
            return@setOnLongClickListener(true)
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

}