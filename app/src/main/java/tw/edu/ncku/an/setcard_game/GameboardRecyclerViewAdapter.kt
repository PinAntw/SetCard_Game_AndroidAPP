package tw.edu.ncku.an.setcard_game

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class GameboardRecyclerViewAdapter(
    private val GameControl: GameControl
): RecyclerView.Adapter<GameboardRecyclerViewAdapter.MyViewHolder>() {

    interface CardViewClickListener {
        fun onCardViewClick(position: Int, cardView: CardView)
    }
    private var cardViewClickListener: CardViewClickListener? = null

    fun setCardViewClickListener(listener: CardViewClickListener) {
        cardViewClickListener = listener
    }
    //繼承RecyclerView.ViewHolder
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView1: CardView = itemView.findViewById(R.id.CardPos1)
        val cardView2: CardView = itemView.findViewById(R.id.CardPos2)
        val cardView3: CardView = itemView.findViewById(R.id.CardPos3)
    }
    //創建一排並返回其資料
    //通過 LayoutInflater 從 row_add.xml 中創建出每個 RecyclerView 項目的 View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_add, parent, false)
        return MyViewHolder(view)
    }

    //看檯面上有幾張
    override fun getItemCount(): Int {
        return GameControl.onTable.size
    }
    //將數據連結到到ViewHolder
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val cardView1 = holder.cardView1
        val cardView2 = holder.cardView2
        val cardView3 = holder.cardView3

        // 設定監聽器
        cardView1.setOnClickListener {
            cardViewClickListener?.onCardViewClick(position, cardView1)
        }
        cardView2.setOnClickListener {
            cardViewClickListener?.onCardViewClick(position, cardView2)
        }
        cardView3.setOnClickListener {
            cardViewClickListener?.onCardViewClick(position, cardView3)
        }

        cardView1.color = GameControl.onTable[position][0].cardColor
        cardView1.shapeCount = GameControl.onTable[position][0].shapeCount
        cardView1.shape = GameControl.onTable[position][0].shape
        cardView1.shading = GameControl.onTable[position][0].shading
        cardView1.cardBackgroundColor  = GameControl.backgroundColor[position][0]

        cardView2.color = GameControl.onTable[position][1].cardColor
        cardView2.shapeCount = GameControl.onTable[position][1].shapeCount
        cardView2.shape = GameControl.onTable[position][1].shape
        cardView2.shading = GameControl.onTable[position][1].shading
        cardView2.cardBackgroundColor  = GameControl.backgroundColor[position][1]

        cardView3.color = GameControl.onTable[position][2].cardColor
        cardView3.shapeCount = GameControl.onTable[position][2].shapeCount
        cardView3.shape = GameControl.onTable[position][2].shape
        cardView3.shading = GameControl.onTable[position][2].shading
        cardView3.cardBackgroundColor  = GameControl.backgroundColor[position][2]
    }
}