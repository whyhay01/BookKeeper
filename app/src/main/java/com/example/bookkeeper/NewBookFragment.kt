package com.example.bookkeeper

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.bookkeeper.RoomDB.Book
import com.example.bookkeeper.viewModel.BookViewModel
import com.example.bookkeeper.databinding.FragmentNewBookBinding
import java.util.*

class NewBookFragment : Fragment() {

    lateinit var binding: FragmentNewBookBinding
    lateinit var viewModel: BookViewModel

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentNewBookBinding.inflate(inflater,container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(BookViewModel::class.java)

        binding.btnSave.setOnClickListener {
            if (TextUtils.isEmpty(binding.author.text)||
                TextUtils.isEmpty(binding.title.text)||
                TextUtils.isEmpty(binding.description.text)){
                Toast.makeText(requireContext(),"Complete field entry", Toast.LENGTH_SHORT).show()
            }else {
                val id = UUID.randomUUID().toString()
                val author = binding.author.text.toString()
                val title = binding.title.text.toString()
                val description = binding.description.text.toString()
                val currentTime = Calendar.getInstance().time

                val book = Book(id, author, title, description, currentTime)

                viewModel.insert(book)

                Toast.makeText(requireContext(),"Saved",Toast.LENGTH_SHORT).show()

                findNavController().popBackStack()
            }
        }

        binding.btnCancel.setOnClickListener {
            findNavController().popBackStack()
        }

    }


}