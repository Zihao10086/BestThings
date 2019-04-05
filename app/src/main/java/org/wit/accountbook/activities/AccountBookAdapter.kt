package org.wit.accountbook.activities
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.card_accountbook.view.*
import org.wit.accountbook.R
import org.wit.accountbook.helpers.readImageFromPath
import org.wit.accountbook.models.AccountBookModel

//represent click events on the AccountBook card, and allow us to response the event
interface AccountBookListener{
    fun onAccountBookClick(record: AccountBookModel)
}

//RecyclerView + Adapter
class AccountBookAdapter constructor(private var records:List<AccountBookModel>,
                                     private val listener:AccountBookListener): RecyclerView.Adapter<AccountBookAdapter.MainHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(LayoutInflater.from(parent?.context).inflate(R.layout.card_accountbook,parent,false))
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val record = records[holder.adapterPosition]
        holder.bind(record,listener)
    }
    override fun getItemCount(): Int = records.size

    //put values of records into the AccountBookList
    class MainHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(record: AccountBookModel,listener: AccountBookListener){
            itemView.accountTypeC.text = record.type
            itemView.descriptionC.text = record.description
            itemView.totalC.text = record.total
            itemView.rating_barC.rating = record.rating
            itemView.dateC.text = record.date
            //put the image that you choosed to card view (imageView in card)
            itemView.imageView.setImageBitmap(readImageFromPath(itemView.context,record.image))
            itemView.setOnClickListener{listener.onAccountBookClick(record)}
        }
    }
}