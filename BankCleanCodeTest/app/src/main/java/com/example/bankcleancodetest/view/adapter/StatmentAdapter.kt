package com.example.bankcleancodetest.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bankcleancodetest.R
import com.example.bankcleancodetest.convertToBRL
import com.example.bankcleancodetest.entity.InvoiceExtract
import com.example.bankcleancodetest.formatDate
import kotlinx.android.synthetic.main.item_lancamentos.view.*

class StatmentAdapter(
    private var dataList: List<InvoiceExtract.Statement>?
): RecyclerView.Adapter<StatmentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatmentAdapter.ViewHolder {
        val viewRoot = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_lancamentos, parent, false)

        return ViewHolder(viewRoot)
    }

    override fun getItemCount() = dataList?.size ?: 0

    fun updateData(list: List<InvoiceExtract.Statement>){
        this.dataList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvTitle: TextView? = itemView.tv_lancamento_title
        val tvDate: TextView? = itemView.tv_lancamento_date
        val tvAccount: TextView? = itemView.tv_lancamento_conta
        val tvValue: TextView? = itemView.tv_lancamento_valor
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        dataList?.let {
            holder.tvTitle?.text = it[position].title
            holder.tvDate?.text = it[position].date.formatDate()
            holder.tvAccount?.text = it[position].desc
            holder.tvValue?.text = it[position].value.convertToBRL()
        }
    }

}