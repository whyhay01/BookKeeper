package com.example.bookkeeper

import android.app.SearchableInfo
import android.content.Context.SEARCH_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookkeeper.RoomDB.Book
import com.example.bookkeeper.adpater.BookListAdapter
import com.example.bookkeeper.viewModel.BookViewModel
import com.example.bookkeeper.databinding.FragmentHomeBinding
import android.app.SearchManager as SearchManager

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private val viewModel: BookViewModel by lazy { ViewModelProvider(requireActivity()).get(BookViewModel::class.java) }
    private var searchView: SearchView? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Handling FAB clicks
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.to_newBookFragment)
        }

        //getting all the books
        viewModel.getAllBooks().observe(requireActivity(),Observer{books ->
            books?.let {
               setBooks(books)
            }
        })

    }

    //Displaying books
    private fun setBooks(books: List<Book>) {

       val adapter = BookListAdapter(books,
           {position  ->
               val homeBook = books[position]
               findNavController().navigate(HomeFragmentDirections.toEditBookFragment(homeBook))
       },
           {deletedItemPosition ->
               viewModel.delete(books[deletedItemPosition])
               Toast.makeText(requireContext(), "Delete Successful", Toast.LENGTH_SHORT).show()
       })
               binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter.notifyDataSetChanged()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.main_menu, menu)

        val searchItem = menu.findItem(R.id.search)

        //Get search view and set the search configuration
        val searchManager = activity?.getSystemService(SEARCH_SERVICE) as SearchManager
        searchView = searchItem?.actionView as SearchView?

        //Setting the searchResultFragment for result

//        val  componentName = ComponentName(requireActivity(),SearchResultFragment::class.java)
        val searchableInfo : SearchableInfo = searchManager.getSearchableInfo(activity!!.componentName)
        searchView?.setSearchableInfo(searchableInfo)


        searchView?.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Log.i(TAG,"Query TextSubmit: $query")
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSearchResultFragment("$query"))
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                Log.i(TAG,"queryTextChange: $newText")
                return true
            }
        })

        return super.onCreateOptionsMenu(menu, inflater)
    }

    companion object{
        const val TAG :String = "HomeFragment"
    }

}