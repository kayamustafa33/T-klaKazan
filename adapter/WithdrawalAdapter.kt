package com.mustafa.tklakazan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mustafa.tklakazan.databinding.RecyclerWithdrawalRowBinding
import com.mustafa.tklakazan.model.UserWithdrawal

class WithdrawalAdapter(val arrayList: ArrayList<UserWithdrawal>) : RecyclerView.Adapter<WithdrawalAdapter.ViewHolder>() {

    class ViewHolder(val binding : RecyclerWithdrawalRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerWithdrawalRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.withdrawalIbanName.text = arrayList[position].ibanName
        holder.binding.withdrawalIbanAddress.text = arrayList[position].iban
        holder.binding.withdrawalMoney.text = arrayList[position].money
        holder.binding.statusBtn.text = arrayList[position].status
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}