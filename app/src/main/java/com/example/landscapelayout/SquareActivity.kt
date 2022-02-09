package com.example.landscapelayout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast

const val EXTRA_TAPPED_INSIDE_SQUARE = "com.example.landscapelayout.TAPPED_INSIDE_SQUARE"

class SquareActivity : AppCompatActivity() {

    private lateinit var squareImage: ImageView
    private lateinit var container: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_square)

        squareImage = findViewById(R.id.square)
        container = findViewById(R.id.container)


        // can get size of square from data sent via intent in main activity
        var squareSize = intent.getIntExtra(EXTRA_SQUARE_SIZE, 100)


        if (squareSize == 0) {
            squareSize = 1
        }

        squareImage.layoutParams.width = squareSize
        squareImage.layoutParams.height = squareSize

        squareImage.setOnClickListener {
            squareTapped(true)
        }

        container.setOnClickListener {
            squareTapped(false)
        }

    }

    private fun squareTapped(didTapSquare: Boolean) {
        // todo send message back to main activity
        // end squareActivity
//        Toast.makeText(this, "square tapped $didTapSquare", Toast.LENGTH_SHORT).show()
        val resultIntent = Intent()
        resultIntent.putExtra(EXTRA_TAPPED_INSIDE_SQUARE, didTapSquare)
        // user tried to do what the app asked them to do
        setResult(RESULT_OK, resultIntent)
        finish() // end activity
    }
}