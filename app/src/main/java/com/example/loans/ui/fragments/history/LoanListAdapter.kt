package com.example.loans.ui.fragments.history

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loans.R
import com.example.loans.databinding.LoanListItemBinding
import com.example.loans.domain.entities.Loan
import com.example.loans.domain.entities.LoanState
import com.example.loans.extensions.DateFormat

class LoanListAdapter(
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<LoanListAdapter.LoanViewHolder>() {

    private var listLoan: List<Loan> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun updateList(list: List<Loan>) {
        listLoan = list
    }

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoanViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.loan_list_item, parent, false)
        return LoanViewHolder(view = view, onClick = onClick)
    }

    override fun onBindViewHolder(holder: LoanViewHolder, position: Int) {
        holder.bind(listLoan[position])
    }

    override fun getItemCount(): Int =
        listLoan.size

    inner class LoanViewHolder(private val onClick: (Int) -> Unit, view: View) :
        RecyclerView.ViewHolder(view) {
        private val loanBinding = LoanListItemBinding.bind(view)
        fun bind(loan: Loan) {
            loanBinding.apply {
                textUserName.text =
                    context?.getString(R.string.full_name, loan.lastName, loan.firstName)
                textAmount.text = context?.getString(R.string.amount_ruble, loan.amount.toString())

                val state = when (loan.state) {
                    LoanState.APPROVED -> context?.getString(R.string.approved)
                    LoanState.REGISTERED -> context?.getString(R.string.registered)
                    LoanState.REJECTED -> context?.getString(R.string.rejected)
                }
                textStatus.text = state
                textDate.text = DateFormat.getFormattedDate(loan.date)
            }

            itemView.setOnClickListener { onClick(loan.id) }
        }
    }


}

