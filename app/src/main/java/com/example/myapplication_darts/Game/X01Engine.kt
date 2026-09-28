package com.example.myapplication_darts.game

enum class InMode {
    STRAIGHT,
    DOUBLE,
    MASTER
}

enum class OutMode {
    STRAIGHT,
    DOUBLE,
    MASTER
}

data class Dart(
    val number: Int,
    val multiplier: Int
) {

    val score: Int
        get() = when {
            number == 25 && multiplier == 2 -> 50
            number == 0 -> 0
            else -> number * multiplier
        }

    val isDouble: Boolean
        get() = multiplier == 2

    val isTreble: Boolean
        get() = multiplier == 3

    val isSingle: Boolean
        get() = multiplier == 1

    val isBull: Boolean
        get() = number == 25 && multiplier == 2

    val isMiss: Boolean
        get() = number == 0 && multiplier == 0

    override fun toString(): String {
        return when {
            isMiss -> "MISS"
            isBull -> "BULL"
            multiplier == 3 -> "T$number"
            multiplier == 2 -> "D$number"
            else -> number.toString()
        }
    }

    companion object {
        val MISS = Dart(0, 0)
        val BULL = Dart(25, 2)
    }
}

data class VisitResult(
    val previousScore: Int,
    val newScore: Int,
    val darts: List<Dart>,
    val total: Int,
    val bust: Boolean,
    val checkout: Boolean
)

class X01Engine(
    private val startingScore: Int,
    private val inMode: InMode,
    private val outMode: OutMode
) {

    var score: Int = startingScore
        private set

    private var enteredGame = false

    private val history = mutableListOf<VisitResult>()

    fun submitVisit(
        darts: List<Dart>
    ): VisitResult {

        if (darts.isEmpty()) {
            return VisitResult(
                previousScore = score,
                newScore = score,
                darts = emptyList(),
                total = 0,
                bust = false,
                checkout = false
            )
        }

        val previousScore = score
        val previousEnteredGame = enteredGame

        var workingScore = score
        var total = 0
        var checkout = false
        var bust = false

        val acceptedDarts = mutableListOf<Dart>()

        for (dart in darts.take(3)) {

            if (!enteredGame) {

                if (dart.isMiss) {
                    acceptedDarts.add(dart)
                    continue
                }

                val validEntry = when (inMode) {

                    InMode.STRAIGHT -> true

                    InMode.DOUBLE ->
                        dart.isDouble

                    InMode.MASTER ->
                        dart.isDouble || dart.isTreble
                }

                if (!validEntry) {
                    acceptedDarts.add(dart)
                    continue
                }

                enteredGame = true
            }

            val dartScore = dart.score
            val candidateScore = workingScore - dartScore

            if (candidateScore == 0) {

                val validCheckout = when (outMode) {

                    OutMode.STRAIGHT ->
                        true

                    OutMode.DOUBLE ->
                        dart.isDouble

                    OutMode.MASTER ->
                        dart.isDouble || dart.isTreble
                }

                acceptedDarts.add(dart)

                if (validCheckout) {

                    workingScore = 0
                    total += dartScore
                    checkout = true

                } else {

                    bust = true
                }

                break
            }

            if (
                candidateScore < 0 ||
                candidateScore == 1
            ) {

                acceptedDarts.add(dart)
                bust = true
                break
            }

            workingScore = candidateScore
            total += dartScore
            acceptedDarts.add(dart)
        }

        if (bust) {

            score = previousScore
            enteredGame = previousEnteredGame

            val result = VisitResult(
                previousScore = previousScore,
                newScore = previousScore,
                darts = acceptedDarts,
                total = total,
                bust = true,
                checkout = false
            )

            history.add(result)

            return result
        }

        score = workingScore

        val result = VisitResult(
            previousScore = previousScore,
            newScore = score,
            darts = acceptedDarts,
            total = total,
            bust = false,
            checkout = checkout
        )

        history.add(result)

        return result
    }

    fun submitManualScore(
        total: Int
    ): VisitResult {

        if (total < 0) {
            return VisitResult(
                previousScore = score,
                newScore = score,
                darts = emptyList(),
                total = 0,
                bust = true,
                checkout = false
            )
        }

        val previousScore = score
        val previousEnteredGame = enteredGame

        if (!enteredGame) {

            if (
                inMode == InMode.DOUBLE ||
                inMode == InMode.MASTER
            ) {

                /*
                 * A manually entered score cannot prove
                 * the required entry dart.
                 *
                 * Therefore manual score is allowed only
                 * after the player has entered the game.
                 */
                return VisitResult(
                    previousScore = score,
                    newScore = score,
                    darts = emptyList(),
                    total = 0,
                    bust = true,
                    checkout = false
                )
            }

            enteredGame = true
        }

        val candidate = score - total

        if (
            candidate < 0 ||
            candidate == 1
        ) {

            enteredGame = previousEnteredGame

            val result = VisitResult(
                previousScore = previousScore,
                newScore = previousScore,
                darts = emptyList(),
                total = total,
                bust = true,
                checkout = false
            )

            history.add(result)

            return result
        }

        if (candidate == 0) {

            /*
             * Manual checkout is not accepted for
             * Double/Master Out because the exact
             * finishing dart is unknown.
             *
             * The user should use Checkout Assistant.
             */
            if (
                outMode == OutMode.DOUBLE ||
                outMode == OutMode.MASTER
            ) {

                return VisitResult(
                    previousScore = score,
                    newScore = score,
                    darts = emptyList(),
                    total = 0,
                    bust = true,
                    checkout = false
                )
            }
        }

        score = candidate

        val result = VisitResult(
            previousScore = previousScore,
            newScore = score,
            darts = emptyList(),
            total = total,
            bust = false,
            checkout = false
        )

        history.add(result)

        return result
    }

    fun undo(): Boolean {

        if (history.isEmpty()) {
            return false
        }

        history.removeAt(history.lastIndex)

        if (history.isEmpty()) {

            score = startingScore
            enteredGame = false

            return true
        }

        val last = history.last()

        score = last.newScore

        enteredGame =
            score != startingScore

        return true
    }

    fun getHistory(): List<VisitResult> {
        return history.toList()
    }

    fun reset() {
        score = startingScore
        enteredGame = false
        history.clear()
    }

    fun hasEnteredGame(): Boolean {
        return enteredGame
    }

    fun getStartingScore(): Int {
        return startingScore
    }

    fun getInMode(): InMode {
        return inMode
    }

    fun getOutMode(): OutMode {
        return outMode
    }
}