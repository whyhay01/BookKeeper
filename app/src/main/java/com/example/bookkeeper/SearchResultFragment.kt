package com.example.bookkeeper

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookkeeper.RoomDB.Book
import com.example.bookkeeper.adpater.BookListAdapter
import com.example.bookkeeper.databinding.FragmentHomeBinding
import com.example.bookkeeper.viewModel.BookViewModel

class SearchResultFragment: Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel by lazy {ViewModelProvider(requireActivity()).get(BookViewModel::class.java)}


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.floatingActionButton.visibility = View.INVISIBLE


        val queryString = SearchResultFragmentArgs.fromBundle(requireArguments()).stringQuery

        handlingQuery(queryString)
    }

    //Handling Search Query
    private fun handlingQuery(searchString: String) {

            Log.i("SearchResultFragment"," Search query is: $searchString")

        viewModel.getBooksByAuthorOrBooks("%$searchString%").observe(requireActivity(), Observer { books->
                books?.let { bookList->

                    if (books.isEmpty()){
                        Toast.makeText(requireContext(),"No search Result", Toast.LENGTH_SHORT).show()
                        Log.i("SearchResultFragment", "The value of books is null: $bookList")
                    }else
                    {
                        Log.i("SearchResultFragment", "The value of books is not null: ${bookList.size}")

                        setBooks(books)
                    }
                }

            })
        }

//    Display Book
    private fun setBooks(books: List<Book>) {

        val adapter = BookListAdapter(books,
            {position  ->
                val book = books[position]
                findNavController().navigate(SearchResultFragmentDirections.toEditBookFragment(book))
            },
            {deletedItemPosition ->
                viewModel.delete(books[deletedItemPosition])
                Toast.makeText(requireContext(), "Delete Successful", Toast.LENGTH_SHORT).show()
            })
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter.notifyDataSetChanged()

    }
}