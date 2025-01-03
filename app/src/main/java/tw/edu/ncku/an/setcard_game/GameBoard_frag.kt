package tw.edu.ncku.an.setcard_game

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tw.edu.ncku.an.setcard_game.databinding.FragmentGameboardBinding

class GameBoard_frag: Fragment(),GameboardRecyclerViewAdapter.CardViewClickListener {
    private lateinit var gameControl: GameControl
    private lateinit var binding: FragmentGameboardBinding
    private lateinit var adapter: GameboardRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 1)

        adapter = GameboardRecyclerViewAdapter(gameControl)
        adapter.setCardViewClickListener(this)
        recyclerView.adapter = adapter

        recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))

        val btnRestart = binding.btnRestart
        btnRestart.setOnClickListener {
            gameControl.restartGame()
            adapter.notifyDataSetChanged()
        }
        val btnAddCard = binding.btnAddCard
        btnAddCard.setOnClickListener {
            gameControl.addOnTable()
            adapter.notifyDataSetChanged()
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameControl = ViewModelProvider(requireActivity()).get(GameControl::class.java)
    }

    override fun onCardViewClick(position: Int, cardView: CardView) {
        var cardIndex = when (cardView.id) {
            R.id.CardPos1 -> 0
            R.id.CardPos2 -> 1
            R.id.CardPos3 -> 2
            else -> -1
        }

        val positionIdx = position * gameControl.columns + cardIndex
        gameControl.findValidSelection()
        gameControl.updateSelectedCardIdx(positionIdx)
        adapter.notifyDataSetChanged()
    }

}