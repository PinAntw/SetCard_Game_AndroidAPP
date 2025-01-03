package tw.edu.ncku.an.setcard_game

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tw.edu.ncku.an.setcard_game.databinding.FragmentHistoryBinding

class History_frag: Fragment() {
    private lateinit var gameControl: GameControl
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var adapter: HistoryRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.recyclerViewHistory
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 1)

        adapter = HistoryRecyclerViewAdapter(gameControl)
        //adapter.setSetCardViewClickListener(this)
        recyclerView.adapter = adapter

        recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))

        gameControl = ViewModelProvider(requireActivity()).get(GameControl::class.java)

        gameControl.historyLiveData.observe(viewLifecycleOwner, Observer {
            adapter.notifyDataSetChanged()
        })

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gameControl = ViewModelProvider(requireActivity()).get(GameControl::class.java)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

}