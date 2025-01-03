package tw.edu.ncku.an.setcard_game

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class GameControl: ViewModel() {
    val shapes =
        arrayOf(CardView.Shape.OVAL, CardView.Shape.DIAMOND, CardView.Shape.WORM)
    val shapeCounts = intArrayOf(1, 2, 3)
    val shadings =
        arrayOf(CardView.Shading.EMPTY, CardView.Shading.SOLID, CardView.Shading.STRIP)
    val colors = arrayOf(
        Color.RED,
        Color.GREEN,
        Color.parseColor("#800080")
    )

    var rows = 4
    val columns = 3
    val deck = mutableListOf<Card>()
    var onTable = mutableListOf<MutableList<Card>>()
    var backgroundColor = mutableListOf<MutableList<Int>>()
    val selectedCardIdxs = mutableListOf<Int>()
    var history = mutableListOf<MutableList<Card>>()
    val historyLiveData: MutableLiveData<List<List<Card>>> = MutableLiveData()

    init {
        initDeck()
        initOnTable()
        selectedCardIdxs.addAll(listOf(-1, -1, -1))//初始化為三個 -1，表示沒有卡片被選擇
        for(i in 0 until rows){
            val arr = mutableListOf<Int>()
            arr.add(Color.WHITE)
            arr.add(Color.WHITE)
            arr.add(Color.WHITE)
            backgroundColor.add(arr)
        }
    }

    private fun initDeck() {
        for (color in colors) {
            for (shapeCount in shapeCounts) {
                for (shape in shapes) {
                    for (shading in shadings) {
                        deck.add(Card(color, shapeCount, shape, shading))
                    }
                }
            }
        }
    }
    private fun initOnTable() {
        val random = Random
        for(i in 0 until rows){
            val drawnCards = mutableListOf<Card>()
            for(j in 0 until columns){
                val drawnIndex = random.nextInt(deck.size)
                val drawnCard = deck[drawnIndex]
                drawnCards.add(drawnCard)
                deck.removeAt(drawnIndex)
            }
            onTable.add(drawnCards)
        }
    }

    private fun updateBackgroundColor(cardIdxs: MutableList<Int>) {
        turnWhiteBackgroundColor()
        for (i in 0 until 3) {
            if (cardIdxs[i] != -1) {
                if (backgroundColor[cardIdxs[i] / columns][cardIdxs[i] % columns] != Color.BLACK) {
                    backgroundColor[cardIdxs[i] / columns][cardIdxs[i] % columns] = Color.YELLOW
                }

            }
        }
    }
    private fun updateOnTable(row:Int, column:Int){
        val random = Random
        val drawnIndex = random.nextInt(deck.size)
        val drawnCard = deck[drawnIndex]
        onTable[row][column] = drawnCard
        deck.removeAt(drawnIndex)
    }

    fun updateSelectedCardIdx(selectedCardIdx: Int){
        //avoid selecting the card unselected
        if(backgroundColor[selectedCardIdx / columns][selectedCardIdx % columns] != Color.BLACK){
            val existSameIdx = selectedCardIdxs.indexOf(selectedCardIdx)
            if(existSameIdx == -1){
                val index = selectedCardIdxs.indexOfFirst { it < 0 }
                if (index != -1) {
                    selectedCardIdxs[index] = selectedCardIdx
                    val isThreePositiveIntegers = selectedCardIdxs.all { it >= 0 }
                    if(isThreePositiveIntegers){
                        if(deck.isNotEmpty()){
                            if(checkSelectedCardValidation(selectedCardIdxs)){ //valid
                                addHistory(selectedCardIdxs)
                                threeCard(selectedCardIdxs)
                                selectedCardIdxs.fill(-1)
                            }else{ //不合格
                                selectedCardIdxs.fill(-1)
                            }
                        }else{
                            //then not add 3 cards and turn the position black
                            if(checkSelectedCardValidation(selectedCardIdxs)){ //valid
                                addHistory(selectedCardIdxs)
                                turnBlackThreeBackgroundColor(selectedCardIdxs)
                                selectedCardIdxs.fill(-1)
                            }else{ //not valid
                                selectedCardIdxs.fill(-1)
                            }

                        }

                    }
                }
            }else{
                selectedCardIdxs[existSameIdx] = -1
            }
            updateBackgroundColor(selectedCardIdxs);
        }
    }

    fun turnBlackThreeBackgroundColor(cardIdx: MutableList<Int>){
        for(i in 0 until 3){
            backgroundColor[cardIdx[i] / columns][cardIdx[i] % columns] = Color.BLACK
        }
    }

    fun turnWhiteBackgroundColor(){
        for(i in 0 until rows){
            for(j in 0 until columns){
                if(backgroundColor[i][j] != Color.BLACK){
                    backgroundColor[i][j] = Color.WHITE
                }
            }
        }
    }

    fun threeCard(cardIdxs: MutableList<Int>){
        for(i in 0 until 3){
            updateOnTable(cardIdxs[i] / columns, cardIdxs[i] % columns)
        }
    }

    fun addOnTable(){
        if(!deck.isEmpty()){
            rows += 1;
            val random = Random
            val drawnCards = mutableListOf<Card>()
            for(i in 0 until columns){
                val drawnIndex = random.nextInt(deck.size)
                val drawnCard = deck[drawnIndex]
                drawnCards.add(drawnCard)
                deck.removeAt(drawnIndex)
            }

            onTable.add(drawnCards)

            val arr = mutableListOf<Int>()
            arr.add(Color.WHITE)
            arr.add(Color.WHITE)
            arr.add(Color.WHITE)
            backgroundColor.add(arr)
        }
    }

    fun addHistory(cardIdx: MutableList<Int>){
        val arr = mutableListOf<Card>()
        for(i in 0 until 3){
            arr.add(onTable[cardIdx[i] / columns][cardIdx[i] % columns])
        }
        history.add(arr)
        historyLiveData.value = history.toList()
    }
    fun checkSelectedCardValidation(selectedCardIdxs: MutableList<Int>): Boolean {
        var selectionValidation = false
        if (checkShapeCountValidation(selectedCardIdxs) && checkShapeValidation(selectedCardIdxs) && checkCardColorValidation(selectedCardIdxs) && checkShadingValidation(selectedCardIdxs)) {
            selectionValidation = true
        }
        return selectionValidation
    }
    fun checkShapeCountValidation(selectedCardIdxs: MutableList<Int>): Boolean {
        val card1 = onTable[selectedCardIdxs[0] / columns][selectedCardIdxs[0] % columns]
        val card2 = onTable[selectedCardIdxs[1] / columns][selectedCardIdxs[1] % columns]
        val card3 = onTable[selectedCardIdxs[2] / columns][selectedCardIdxs[2] % columns]

        var selectionValidation = false

        if (card1.shapeCount == card2.shapeCount && card2.shapeCount == card3.shapeCount) {
            selectionValidation = true
        }

        if (card1.shapeCount != card2.shapeCount && card2.shapeCount != card3.shapeCount && card1.shapeCount != card3.shapeCount) {
            selectionValidation = true
        }
        return selectionValidation
    }
    fun checkShapeValidation(selectedCardIdxs: MutableList<Int>): Boolean {
        val card1 = onTable[selectedCardIdxs[0] / columns][selectedCardIdxs[0] % columns]
        val card2 = onTable[selectedCardIdxs[1] / columns][selectedCardIdxs[1] % columns]
        val card3 = onTable[selectedCardIdxs[2] / columns][selectedCardIdxs[2] % columns]

        var selectionValidation = false

        if (card1.shape == card2.shape && card2.shape == card3.shape) {
            selectionValidation = true
        }

        if (card1.shape != card2.shape && card2.shape != card3.shape && card1.shape != card3.shape) {
            selectionValidation = true
        }

        return selectionValidation
    }
    fun checkCardColorValidation(selectedCardIdxs: MutableList<Int>): Boolean {
        val card1 = onTable[selectedCardIdxs[0] / columns][selectedCardIdxs[0] % columns]
        val card2 = onTable[selectedCardIdxs[1] / columns][selectedCardIdxs[1] % columns]
        val card3 = onTable[selectedCardIdxs[2] / columns][selectedCardIdxs[2] % columns]

        var selectionValidation = false

        if (card1.cardColor == card2.cardColor && card2.cardColor == card3.cardColor) {
            selectionValidation = true
        }

        if (card1.cardColor != card2.cardColor && card2.cardColor != card3.cardColor && card1.cardColor != card3.cardColor) {
            selectionValidation = true
        }

        return selectionValidation
    }
    fun checkShadingValidation(selectedCardIdxs: MutableList<Int>): Boolean {
        val card1 = onTable[selectedCardIdxs[0] / columns][selectedCardIdxs[0] % columns]
        val card2 = onTable[selectedCardIdxs[1] / columns][selectedCardIdxs[1] % columns]
        val card3 = onTable[selectedCardIdxs[2] / columns][selectedCardIdxs[2] % columns]

        var selectionValidation = false

        if (card1.shading == card2.shading && card2.shading == card3.shading) {
            selectionValidation = true
        }

        if (card1.shading != card2.shading && card2.shading != card3.shading && card1.shading != card3.shading) {
            selectionValidation = true
        }

        return selectionValidation
    }

    fun findValidSelection(){
        var existValidation:Boolean = false
        for (i in 0 until rows * columns) {
            for (j in 0 until rows * columns) {
                for (k in 0 until rows * columns) {
                    if (i != j && i != k && j != k) {
                        val arr = mutableListOf<Int>(0, 0, 0)
                        arr[0] = i
                        arr[1] = j
                        arr[2] = k
                        if (checkSelectedCardValidation(arr)) {
                            existValidation = true
                            println("valid set: ${i + 1} ${j + 1} ${k + 1}")
                            Log.i("i",(i + 1).toString())
                            Log.i("j",(j + 1).toString())
                            Log.i("k",(k + 1).toString())
                        }
                    }
                }
            }
        }
        if(!existValidation){
            println("No valid set\n")
        }
    }
    fun restartGame(){
        deck.clear()
        for (color in colors) {
            for (shapeCount in shapeCounts) {
                for (shape in shapes) {
                    for (shading in shadings) {
                        deck.add(Card(color, shapeCount, shape, shading))
                    }
                }
            }
        }

        rows = 4;

        if (onTable.size > 4) {
            val subListsToRemove = onTable.subList(4, onTable.size)
            subListsToRemove.clear()
        }
        if (backgroundColor.size > 4) {
            val subListsToRemove = backgroundColor.subList(4, backgroundColor.size)
            subListsToRemove.clear()
        }

        val random = Random
        for(i in 0 until rows){
            val drawnCards = mutableListOf<Card>()
            for(j in 0 until columns){
                val drawnIndex = random.nextInt(deck.size)
                val drawnCard = deck[drawnIndex]
                drawnCards.add(drawnCard)
                deck.removeAt(drawnIndex)
            }
            onTable[i] = drawnCards
        }
        for(i in 0 until rows){
            val arr = mutableListOf<Int>()
            arr.add(Color.WHITE)
            arr.add(Color.WHITE)
            arr.add(Color.WHITE)
            backgroundColor[i] = arr
        }
    }


}