package com.example.fitensslessonstest.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitensslessonstest.R
import com.example.fitensslessonstest.adapters.DateAdapter
import com.example.fitensslessonstest.adapters.LessonAdapter
import com.example.fitensslessonstest.databinding.FragmentLessonsBinding
import com.example.fitensslessonstest.ui.FitnessLessonsActivity
import com.example.fitensslessonstest.ui.LessonsViewModel
import com.example.fitensslessonstest.util.Resource
import java.time.LocalDate


class LessonsFragment : Fragment() {

    private var _binding: FragmentLessonsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LessonsViewModel
    private lateinit var dateAdapter: DateAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLessonsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = (activity as FitnessLessonsActivity).viewModel
        binding.rvDates.apply {
            dateAdapter = DateAdapter()
            adapter = dateAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        viewModel.fitnessInformation.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { fitnessResponse ->
                        hideProgressBar()
                        dateAdapter.differ.submitList(
                            fitnessResponse.lessons.toList().groupBy { it.date }.keys.map { date ->
                                LocalDate.parse(date)
                            })
                        dateAdapter.lessonsForDate = fitnessResponse.lessons.groupBy { it.date }

                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(activity, "Произошла ошибка: $message", Toast.LENGTH_LONG)
                            .show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }


    }
    var isLoading = false

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
        isLoading = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}