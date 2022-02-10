package com.example.bookkeeper.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.bookkeeper.RoomDB.Book
import com.example.bookkeeper.RoomDB.BookDao
import com.example.bookkeeper.RoomDB.BookRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookViewModel(application: Application): AndroidViewModel(application) {

    private val bookDao:BookDao

    private lateinit var allBooks:LiveData<List<Book>>

    init {
        val bookDb = BookRoomDatabase.getDatabase(application)
        bookDao = bookDb!!.bookDao()

    }

    fun insert(book: Book) = viewModelScope.launch(Dispatchers.IO) {
        bookDao.insert(book)
    }

    fun update(book: Book) = viewModelScope.launch(Dispatchers.IO) {
        bookDao.update(book)
    }

    fun delete(book: Book) = viewModelScope.launch(Dispatchers.IO) {
        bookDao.delete(book)
    }

    fun getAllBooks(): LiveData<List<Book>> {
       this.allBooks = bookDao.allBooks
        return this.allBooks
    }

    fun getBooksByAuthorOrBooks(searchString: String):LiveData<List<Book>>{
        return bookDao.getBooksByAuthorOrBook(searchString)
    }

}