package tw.edu.ncku.an.setcard_game

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView

class HistoryRecyclerViewAdapter(
    private val gameControl: GameControl,
) : RecyclerView.Adapter<HistoryRecyclerViewAdapter.MyViewHolder>(){

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView1: CardView = itemView.findViewById(R.id.CardPos1)
        val cardView2: CardView = itemView.findViewById(R.id.CardPos2)
        val cardView3: CardView = itemView.findViewById(R.id.CardPos3)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_add, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.i("history size", gameControl.history.size.toString())
        Log.i("backgroundColor size", gameControl.backgroundColor.size.toString())

        return gameControl.history.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val cardView1 = holder.cardView1
        val cardView2 = holder.cardView2
        val cardView3 = holder.cardView3

        cardView1.color = gameControl.history[position][0].cardColor
        cardView1.shapeCount = gameControl.history[position][0].shapeCount
        cardView1.shape = gameControl.history[position][0].shape
        cardView1.shading = gameControl.history[position][0].shading

        cardView2.color = gameControl.history[position][1].cardColor
        cardView2.shapeCount = gameControl.history[position][1].shapeCount
        cardView2.shape = gameControl.history[position][1].shape
        cardView2.shading = gameControl.history[position][1].shading

        cardView3.color = gameControl.history[position][2].cardColor
        cardView3.shapeCount = gameControl.history[position][2].shapeCount
        cardView3.shape = gameControl.history[position][2].shape
        cardView3.shading = gameControl.history[position][2].shading
    }

    private inner class MyObserver : Observer<List<MutableList<Card>>> {
        override fun onChanged(value: List<MutableList<Card>>) {
            Log.i("history size", gameControl.history.size.toString())
            Log.i("backgroundColor size", gameControl.backgroundColor.size.toString())

            notifyDataSetChanged()
        }
    }


}