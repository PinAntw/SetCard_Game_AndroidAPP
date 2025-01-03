package tw.edu.ncku.an.setcard_game

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View

class CardView: View {
    enum class Shape {
        OVAL, WORM, DIAMOND
    }
    enum class Shading {
        EMPTY, SOLID, STRIP
    }

    public var shapeCount: Int = 1
        set(value) {
            if (value >= 1 && value <= 3) {
                field = value //確保值是正常的
                invalidate() //觸發onDraw()
            }
        }

    public var shape = Shape.OVAL
        set(value) {
            field = value
            invalidate()
        }

    public var color = Color.BLUE
        set(value) {
            field = value
            invalidate()
        }

    public var shading = Shading.EMPTY
        set(value) {
            field = value
            invalidate()
        }

    public var cardBackgroundColor = Color.WHITE
        set(value){
            field = value
            invalidate()
        }

    companion object SetCardConstants {
        const val CARD_STANDARD_HEIGHT = 240.0f
        const val CORNER_RADIUS = 12.0f
        const val SYMBOL_WIDTH_SCALE_FACTOR = 0.6f
        const val SYMBOL_HEIGHT_SCALE_FACTOR = 0.125f
    }

    private val cornerScaleFactor: Float
        get() {
            return height / CARD_STANDARD_HEIGHT
        }

    private val cornerRadius: Float
        get() {
            return CORNER_RADIUS * cornerScaleFactor
        }

    private val mPaint = Paint()
    private val mTextPaint = TextPaint()

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context, attrs, defStyle
    ) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet? = null) {
        mPaint.setAntiAlias(true);
        mTextPaint.setAntiAlias(true);


        val a = context.obtainStyledAttributes(attrs, R.styleable.CardView)

        //build the cards' attributes
        val shapeValue  = a.getString(R.styleable.CardView_shape)
        shape = when (shapeValue) {
            "OVAL" -> CardView.Shape.OVAL
            "DIAMOND" -> CardView.Shape.DIAMOND
            "WORM" -> CardView.Shape.WORM
            else -> CardView.Shape.OVAL
        }
        val shadingValue = a.getString(R.styleable.CardView_shading)
        shading = when (shadingValue) {
            "EMPTY" -> CardView.Shading.EMPTY
            "SOLID" -> CardView.Shading.SOLID
            "STRIP" -> CardView.Shading.STRIP
            else -> CardView.Shading.EMPTY
        }
        color = a.getColor(R.styleable.CardView_color, Color.BLUE)
        shapeCount = a.getInt(R.styleable.CardView_shapeCount, 1)

        cardBackgroundColor  = a.getColor(R.styleable.CardView_cardBackgroundColor, Color.WHITE)

        a.recycle()

    }

    //決定畫的樣式 包括呼叫了填充樣式選擇的function
    private fun drawShapeWithVerticalOffset(canvas: Canvas, voffset: Float) {
        val path = Path()
        val width = width * SYMBOL_WIDTH_SCALE_FACTOR
        val height = height * SYMBOL_HEIGHT_SCALE_FACTOR
        var bitmap = BitmapFactory.decodeResource(resources, R.drawable.ovaltest) // 假设 oval1.jpg 放在 res/drawable 目录下

        if (shape == Shape.OVAL) { // OVAL
            if(color == Color.GREEN){
                if(shading == Shading.SOLID){
                    bitmap = BitmapFactory.decodeResource(resources, R.drawable.ogs)
                }else if (shading == Shading.STRIP){
                    bitmap = BitmapFactory.decodeResource(resources, R.drawable.ogp)
                }else if (shading == Shading.EMPTY){
                    bitmap = BitmapFactory.decodeResource(resources, R.drawable.ogb)
                }
            }else if (color == Color.RED){
                if(shading == Shading.SOLID){
                    bitmap = BitmapFactory.decodeResource(resources, R.drawable.ors)
                }else if (shading == Shading.STRIP){
                    bitmap = BitmapFactory.decodeResource(resources, R.drawable.orp)
                }else if (shading == Shading.EMPTY){
                    bitmap = BitmapFactory.decodeResource(resources, R.drawable.orb)
                }

            }else if (color == Color.parseColor("#800080")){
                if(shading == Shading.SOLID){
                    bitmap = BitmapFactory.decodeResource(resources, R.drawable.obs)

                }else if (shading == Shading.STRIP){
                    bitmap = BitmapFactory.decodeResource(resources, R.drawable.obp)

                }else if (shading == Shading.EMPTY){
                    bitmap = BitmapFactory.decodeResource(resources, R.drawable.obb)

                }
            }
        } else if (shape == Shape.DIAMOND) { // DIAMOND
            if(color == Color.GREEN){
                if(shading == Shading.SOLID){
                    bitmap = BitmapFactory.decodeResource(resources, R.drawable.dgs)

                }else if (shading == Shading.STRIP){
                    bitmap = BitmapFactory.decodeResource(resources, R.drawable.dgp)

                }else if (shading == Shading.EMPTY){
                    bitmap = BitmapFactory.decodeResource(resources, R.drawable.dgb)

                }
            }else if (color == Color.RED){
                if(shading == Shading.SOLID){
                    bitmap = BitmapFactory.decodeResource(resources, R.drawable.drs)

                }else if (shading == Shading.STRIP){
                    bitmap = BitmapFactory.decodeResource(resources, R.drawable.drp)

                }else if (shading == Shading.EMPTY){
                    bitmap = BitmapFactory.decodeResource(resources, R.drawable.drb)

                }

            }else if (color == Color.parseColor("#800080")){
                if(shading == Shading.SOLID){
                    bitmap = BitmapFactory.decodeResource(resources, R.drawable.dbs)

                }else if (shading == Shading.STRIP){
                    bitmap = BitmapFactory.decodeResource(resources, R.drawable.dbp)

                }else if (shading == Shading.EMPTY){
                    bitmap = BitmapFactory.decodeResource(resources, R.drawable.dbb)

                }
            }
        } else { // WORM
            if(color == Color.GREEN){
                if(shading == Shading.SOLID){
                    bitmap = BitmapFactory.decodeResource(resources, R.drawable.wgs)

                }else if (shading == Shading.STRIP){
                    bitmap = BitmapFactory.decodeResource(resources, R.drawable.wgp)

                }else if (shading == Shading.EMPTY){
                    bitmap = BitmapFactory.decodeResource(resources, R.drawable.wgb)

                }
            }else if (color == Color.RED){
                if(shading == Shading.SOLID){
                    bitmap = BitmapFactory.decodeResource(resources, R.drawable.wrs)

                }else if (shading == Shading.STRIP){
                    bitmap = BitmapFactory.decodeResource(resources, R.drawable.wrp)

                }else if (shading == Shading.EMPTY){
                    bitmap = BitmapFactory.decodeResource(resources, R.drawable.wrb)

                }

            }else if (color == Color.parseColor("#800080")){
                if(shading == Shading.SOLID){
                    bitmap = BitmapFactory.decodeResource(resources, R.drawable.wbs)

                }else if (shading == Shading.STRIP){
                    bitmap = BitmapFactory.decodeResource(resources, R.drawable.wbp)

                }else if (shading == Shading.EMPTY){
                    bitmap = BitmapFactory.decodeResource(resources, R.drawable.wbb)

                }
            }
        }
        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, width.toInt(), height.toInt(), true)
        val left = (getWidth() / 2 - width / 2)
        val top = (getHeight() / 2 - height / 2 + voffset)
        canvas.drawBitmap(scaledBitmap, left, top, mPaint)
    }
    private fun drawShapes(canvas: Canvas) {
        when (shapeCount) {
            1 -> drawShapeWithVerticalOffset(canvas, 0f)
            2 -> {
                val yOffset = height * 0.13f
                drawShapeWithVerticalOffset(canvas, -yOffset)
                drawShapeWithVerticalOffset(canvas, yOffset)
            }

            3 -> {
                val yOffset = height * 0.26f //
                drawShapeWithVerticalOffset(canvas, -yOffset)
                drawShapeWithVerticalOffset(canvas, 0f)
                drawShapeWithVerticalOffset(canvas, yOffset)
            }
        }
    }

    private fun drawBackgroundColor(canvas: Canvas, path: Path){
        if(cardBackgroundColor  == Color.WHITE){
            // fill
            mPaint.style = Paint.Style.FILL
            mPaint.color = Color.WHITE
            canvas.drawPath(path, mPaint)
        }else if(cardBackgroundColor  == Color.BLACK){
            mPaint.style = Paint.Style.FILL
            mPaint.color = Color.BLACK
            canvas.drawPath(path, mPaint)
        }
        else{
            mPaint.style = Paint.Style.FILL
            mPaint.color = Color.YELLOW
            canvas.drawPath(path, mPaint)
        }
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val path = Path()
        val rect = RectF(0f, 0f, width.toFloat(), height.toFloat())
        path.addRoundRect(rect, cornerRadius, cornerRadius, Path.Direction.CW)
        canvas.clipPath(path)
        drawBackgroundColor(canvas, path)
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 3.0f
        mPaint.color = Color.BLACK
        canvas.drawPath(path, mPaint)

        //點卡片後 把圖片畫上去
        if(cardBackgroundColor == Color.WHITE || cardBackgroundColor == Color.YELLOW){
            drawShapes(canvas)
        }
    }
}