package com.cmaye.tic_toc_toe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmaye.tic_toc_toe.databinding.ActivityMainBinding
import com.cmaye.tic_toc_toe.model.TTT
import com.cmaye.tic_toc_toe.model.TTTViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mAdapter: GridViewAdapter
    private lateinit var viewModel: TTTViewModel
    private lateinit var timer: CountDownTimer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(TTTViewModel::class.java)
        setContentView(binding.root)


        setUpRecycler()
        viewModelProvider()
        countDownTimer()
        binding.btnReset.setOnClickListener {
            reset()
        }


    }

    private fun reset()
    {
        binding.winPlayer.text = "Win Player is : "
        binding.lineView.translationX = 0f
        binding.lineView.translationY = 0f
        binding.lineView.visibility = View.GONE
        setUpRecycler()
        timer.cancel()
        countDownTimer()
    }
    private fun viewModelProvider() {
        viewModel.finalResult.observe(this, Observer { result ->
            if (result.isWin) {
                binding.winPlayer.text =
                    "Win Player is : ${if (result.status == 1) "Player1" else "Player2"}"
                mAdapter.isClickable = false
                timer.cancel()
            }
        })

    }

    private fun setUpRecycler() {
        mAdapter = GridViewAdapter(createGrid())
        mAdapter.setOnClickListener(object : GridViewAdapter.OnClickListener {
            override fun isCheck(status: Int, list: MutableList<TTT>) {
                winItemList(status, list)
            }
            override fun showToast(msg: String) {
                Toast.makeText(this@MainActivity, msg.toString(), Toast.LENGTH_SHORT).show()
            }

        })
        binding.recyclerView.layoutManager = GridLayoutManager(this,3)
        binding.recyclerView.adapter = mAdapter
    }


    private fun gameOver()
    {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Game Over!")
        builder.setMessage("Your play game is Game Over!!")
        builder.setPositiveButton("OK") { dialog, which ->
            dialog.dismiss()
            reset()
        }

        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.show()
    }

    private fun winItemList(status: Int, possibleWinList: MutableList<TTT>) {
        if (possibleWinList.filter {
                it.id in setOf(
                    1,
                    2,
                    3
                ) && it.check && it.status == status
            }.size == 3) {
            viewModel.setGameWin(true, status, true, possibleWinList)
            straightLine("top_H")
        }

        if (possibleWinList.filter {
                it.id in setOf(
                    4,
                    5,
                    6
                ) && it.check && it.status == status
            }.size == 3) {
            viewModel.setGameWin(true, status, true, possibleWinList)
            straightLine("middle_H")
        }
        if (possibleWinList.filter {
                it.id in setOf(
                    7,
                    8,
                    9
                ) && it.check && it.status == status
            }.size == 3) {
            viewModel.setGameWin(true, status, true, possibleWinList)
            straightLine("bottom_H")
        }

        if (possibleWinList.filter {
                it.id in setOf(
                    1,
                    4,
                    7
                ) && it.check && it.status == status
            }.size == 3) {
            viewModel.setGameWin(true, status, true, possibleWinList)
            straightLine("left_V")
        }

        if (possibleWinList.filter {
                it.id in setOf(
                    2,
                    5,
                    8
                ) && it.check && it.status == status
            }.size == 3) {
            viewModel.setGameWin(true, status, true, possibleWinList)
            straightLine("middle_V")
        }
        if (possibleWinList.filter {
                it.id in setOf(
                    3,
                    6,
                    9
                ) && it.check && it.status == status
            }.size == 3) {
            viewModel.setGameWin(true, status, true, possibleWinList)
            straightLine("right_V")
        }
        if (possibleWinList.filter {
                it.id in setOf(
                    1,
                    5,
                    9
                ) && it.check && it.status == status
            }.size == 3) {
            viewModel.setGameWin(true, status, true, possibleWinList)
            straightLine("top_right_to_bottom_left")
        }
        if (possibleWinList.filter {
                it.id in setOf(
                    3,
                    5,
                    7
                ) && it.check && it.status == status
            }.size == 3) {
            viewModel.setGameWin(true, status, true, possibleWinList)
            straightLine("top_left_to_bottom_right")
        }

        if (possibleWinList.filter { it.check}.size >= 9)
        {
            gameOver()
        }

    }

    private fun createGrid(): MutableList<TTT> {
        var list = mutableListOf<TTT>()
        for (i in 1..9) {
            list.add(
                TTT(
                    i, 0, false
                )
            )
        }
        return list
    }


    private fun countDownTimer() {
        val totalTime = 60000L // 60 seconds in milliseconds
        val interval = 1000L // 1 second in milliseconds
        binding.countDownTimer.text = ""
        binding.countDownTimer.setTextColor(getColor(R.color.green))
        timer = object : CountDownTimer(totalTime, interval) {
            override fun onTick(millisUntilFinished: Long) {
                // Update the UI with the remaining time
                val remainingSeconds = millisUntilFinished / 1000
                // For example, update a TextView with the remaining time

                if (remainingSeconds < 10) {
                    binding.countDownTimer.setTextColor(getColor(R.color.yellow))
                }
                binding.countDownTimer.text = "00 : $remainingSeconds"
            }

            override fun onFinish() {
                // Do something when the timer finishes, such as displaying a message
                binding.countDownTimer.setTextColor(getColor(R.color.red))
                binding.countDownTimer.text = "Time's up!"
                mAdapter.isClickable = false
                gameOver()
            }
        }

// Start the timer
        timer.start()
    }

    private fun straightLine(status: String)  //status  is left,right , etc.
    {
        binding.lineView.visibility = View.VISIBLE
        when (status) {
            "top_H" -> {  //H - horizontal
                binding.lineView.translationY = -210f
                binding.lineView.animate().apply {
                    rotation(270f)
                    duration = 1000 // 1 second
                }.start()
            }

            "middle_H" -> {
                binding.lineView.translationY = 0f
                binding.lineView.animate().apply {
                    rotation(270f)
                    duration = 1000 // 1 second
                }.start()
            }
            "bottom_H" -> {
                binding.lineView.translationY = 210f
                binding.lineView.animate().apply {
                    rotation(270f)
                    duration = 1000 // 1 second
                }.start()
            }


            "left_V" -> { // V - vertical
                binding.lineView.translationX = -210f
                binding.lineView.animate().apply {
                    rotation(180f)
                    duration = 1000 // 1 second
                }.start()
            }
            "middle_V" -> { // V - vertical
                binding.lineView.translationX = 0f
                binding.lineView.animate().apply {
                    rotation(180f)
                    duration = 1000 // 1 second
                }.start()
            }
            "right_V" -> { // V - vertical
                binding.lineView.translationX = 210f
                binding.lineView.animate().apply {
                    rotation(180f)
                    duration = 1000 // 1 second
                }.start()
            }

            "top_right_to_bottom_left" -> {
                binding.lineView.animate().apply {
                    rotation(135f)
                    duration = 1000 // 1 second
                }.start()
            }

            "top_left_to_bottom_right" -> {
                binding.lineView.animate().apply {
                    rotation(225f)
                    duration = 1000 // 1 second
                }.start()
            }
        }
    }
}