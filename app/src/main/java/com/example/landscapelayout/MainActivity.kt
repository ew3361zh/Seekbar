package com.example.landscapelayout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts

const val EXTRA_SQUARE_SIZE = "com.example.landscapelayout.tap_the_square.SQUARE_SIZE"

class MainActivity : AppCompatActivity() {

    private lateinit var seekBar: SeekBar
    private lateinit var seekBarLabel: TextView
    private lateinit var showSquareButton: Button

    // this can launch the other activity
    private val squareResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result -> handleSquareResult(result)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seekBar = findViewById(R.id.seek_bar)
        seekBarLabel = findViewById(R.id.seek_bar_label)
        showSquareButton = findViewById(R.id.show_square_button)

        val initialProgress = seekBar.progress
        updateLabel(initialProgress)

        // add listener to update label as seekbar changes

        seekBar.setOnSeekBarChangeListener( object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekbarComponent: SeekBar?, progress: Int, fromUser: Boolean) {
                updateLabel(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        showSquareButton.setOnClickListener {
            showSquare()
        }

    }

    private fun updateLabel(progress: Int) {
        seekBarLabel.text = getString(R.string.seekbar_value_message, progress)
    }

    private fun showSquare() {
        // launch Square activity via intent
        val showSquareIntent = Intent(this, SquareActivity::class.java)
        // const set globally, read seekbar progress as value
        showSquareIntent.putExtra(EXTRA_SQUARE_SIZE, seekBar.progress)
//        startActivity(showSquareIntent)
        // tell square activity how large square should be
        // based on progress of seekbar
        squareResultLauncher.launch(showSquareIntent)
    }

    private fun handleSquareResult(result: ActivityResult) {
        // display result to user
        if (result.resultCode == RESULT_OK) {
            val intent = result.data
            val tapped = intent?.getBooleanExtra(EXTRA_TAPPED_INSIDE_SQUARE, false) ?: false
            val message = if (tapped) {
                    getString(R.string.tapped_square_msg)
                } else {
                    getString(R.string.missed_square_msg)
            }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        } else if (result.resultCode == RESULT_CANCELED) {
            Toast.makeText(this, getString(R.string.canceled_tapping_msg), Toast.LENGTH_SHORT).show()
        }
    }
}