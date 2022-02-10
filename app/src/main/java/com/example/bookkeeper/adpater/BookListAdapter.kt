package com.example.bookkeeper.adpater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookkeeper.R
import com.example.bookkeeper.RoomDB.Book
import java.text.SimpleDateFormat
import java.util.*

class BookListAdapter(private val books: List<Book>, private val block: (Int)->Unit, private val delete: (Int)-> Unit):
    RecyclerView.Adapter<BookListAdapter.BookListViewHolder>() {


//    interface OnDeleteClickListener {
//        fun onDeleteClickListen(myBook: Book)
//
//    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_list_display,parent,false)

        return BookListViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookListViewHolder, position: Int) {
        holder.author.text = books[position].authorName
        holder.title.text = books[position].bookName
        holder.lastUpdated.text = holder.getFormattedDate(books[position].lastUpdated)
    }

    override fun getItemCount(): Int {
        return books.size
    }


    inner class BookListViewHolder(view: View): RecyclerView.ViewHolder(view){

        val title: TextView = view.findViewById(R.id.bookTitle)
        val author:TextView = view.findViewById(R.id.authorName)
        val lastUpdated: TextView = view.findViewById(R.id.lastUpdated)

        init {
            view.setOnClickListener {
                block.invoke(adapterPosition)

            }

            view.findViewById<ImageView>(R.id.ivRowDelete).setOnClickListener {
                delete.invoke(adapterPosition)

            }
        }

        fun getFormattedDate(lastUpdated: Date?): String {
            var time = "Last Updated: "
            time += lastUpdated?.let {
                val sdf = SimpleDateFormat("HH:mm d MMM, yyyy", Locale.getDefault())
                sdf.format(lastUpdated)
            } ?: "Not Found"
            return time
        }

    }
}