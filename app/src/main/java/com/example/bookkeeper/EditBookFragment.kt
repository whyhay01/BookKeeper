package com.example.bookkeeper

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.bookkeeper.RoomDB.Book
import com.example.bookkeeper.databinding.EditBookFragementBinding
import com.example.bookkeeper.viewModel.BookViewModel
import java.text.SimpleDateFormat
import java.util.*

class EditBookFragment : Fragment() {

    lateinit var binding: EditBookFragementBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = EditBookFragementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var id: String? = null

        val viewModel = ViewModelProvider(requireActivity()).get(BookViewModel::class.java)

        //Getting the book to be edit and display on screen
        val bookDisplay: Book = EditBookFragmentArgs.fromBundle(requireArguments()).book
        binding.author.setText(bookDisplay.authorName)
        binding.title.setText(bookDisplay.bookName)
        binding.description.setText(bookDisplay.description)
        binding.updatedDate.text = getFormattedDate(bookDisplay.lastUpdated)
        id = bookDisplay.id

        //Saving the updated entries
        binding.btnSave.setOnClickListener {
            val updatedTitle = binding.title.text.toString()
            val updatedAuthor = binding.author.text.toString()
            val updateDescription = binding.description.text.toString()
            val currentTime: Date = Calendar.getInstance().time

            val book = Book(id.toString(),updatedAuthor,updatedTitle, updateDescription, currentTime)

            viewModel.update(book)
            Toast.makeText(requireContext(),"Updated", Toast.LENGTH_SHORT).show()

            findNavController().popBackStack()
        }

        binding.btnCancel.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    companion object{
        const val TAG: String = "EditBookFragment"

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