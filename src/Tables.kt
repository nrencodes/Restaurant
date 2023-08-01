/***
 * Nishant Rengasamy
 * vmnishant21@gmail.com
 */

import java.time.Duration
import java.util.*

class Tables(val capacity: Int, estMinutes: Int, estSeconds: Int) {
    var timeLeft: Duration
    private var timer: Timer?
    private val startTime: Duration

    init {
        timeLeft = Duration.ofMinutes(estMinutes.toLong()).plusSeconds(estSeconds.toLong())
        timer = null
        startTime = Duration.ofMinutes(estMinutes.toLong()).plusSeconds(estSeconds.toLong())
    }

    fun getTimer(): Timer? {
        return timer
    }

    fun setTimer(timer: Timer){
        this.timer = timer
    }

    fun resetTimer() {
        timeLeft = startTime
    }

    fun startTimer(task: TimerTask?) {
        timer?.cancel()
        timer = Timer()
        timer!!.scheduleAtFixedRate(task, 0, 1000)
    }

    fun cancelTimer() {
        timer?.cancel() // Cancel the timer
        timer = null // Set the timer reference to null
    }

    fun increaseTimer(duration: Duration?) {
        timeLeft = timeLeft.plus(duration)
    }

    fun decreaseTimer(duration: Duration?) {
        timeLeft = timeLeft.minus(duration)
    }
}