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

    val isMiss: Boolean
        get() = number == 0 && multiplier == 0

    companion object {

        val MISS = Dart(
            number = 0,
            multiplier = 0
        )

        val BULL = Dart(
            number = 25,
            multiplier = 2
        )
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
    startingScore: Int,
    private val inMode: InMode,
    private val outMode: OutMode
) {

    var score: Int = startingScore
        private set

    private var enteredGame = false

    private val history =
        mutableListOf<VisitResult>()

    fun submitVisit(
        darts: List<Dart>
    ): VisitResult {

        val previousScore = score

        if (darts.isEmpty()) {
            return VisitResult(
                previousScore = previousScore,
                newScore = previousScore,
                darts = emptyList(),
                total = 0,
                bust = false,
                checkout = false
            )
        }

        var temporaryScore = score
        var temporaryEntered = enteredGame

        for (dart in darts) {

            if (dart.isMiss) {
                continue
            }

            if (!temporaryEntered) {

                val validEntry = when (inMode) {

                    InMode.STRAIGHT ->
                        true

                    InMode.DOUBLE ->
                        dart.isDouble

                    InMode.MASTER ->
                        dart.isDouble || dart.isTreble
                }

                if (!validEntry) {
                    continue
                }

                temporaryEntered = true
            }

            temporaryScore -= dart.score
        }

        /*
         * هنوز وارد بازی نشده‌ایم
         */
        if (!enteredGame && !temporaryEntered) {

            val result = VisitResult(
                previousScore = previousScore,
                newScore = previousScore,
                darts = darts,
                total = 0,
                bust = false,
                checkout = false
            )

            history.add(result)

            return result
        }

        /*
         * امتیاز 1 در Double/Master Out
         * همیشه Bust است.
         */
        if (
            temporaryScore == 1 &&
            outMode != OutMode.STRAIGHT
        ) {

            val result = VisitResult(
                previousScore = previousScore,
                newScore = previousScore,
                darts = darts,
                total = darts.sumOf { it.score },
                bust = true,
                checkout = false
            )

            history.add(result)

            return result
        }

        /*
         * امتیاز منفی
         */
        if (temporaryScore < 0) {

            val result = VisitResult(
                previousScore = previousScore,
                newScore = previousScore,
                darts = darts,
                total = darts.sumOf { it.score },
                bust = true,
                checkout = false
            )

            history.add(result)

            return result
        }

        /*
         * Checkout
         */
        if (temporaryScore == 0) {

            val lastDart =
                darts.lastOrNull {
                    !it.isMiss
                }

            val validCheckout = when (outMode) {

                OutMode.STRAIGHT ->
                    true

                OutMode.DOUBLE ->
                    lastDart?.isDouble == true

                OutMode.MASTER ->
                    lastDart?.isDouble == true ||
                            lastDart?.isTreble == true
            }

            if (!validCheckout) {

                val result = VisitResult(
                    previousScore = previousScore,
                    newScore = previousScore,
                    darts = darts,
                    total = darts.sumOf { it.score },
                    bust = true,
                    checkout = false
                )

                history.add(result)

                return result
            }

            score = 0
            enteredGame = true

            val result = VisitResult(
                previousScore = previousScore,
                newScore = 0,
                darts = darts,
                total = darts.sumOf { it.score },
                bust = false,
                checkout = true
            )

            history.add(result)

            return result
        }

        /*
         * Visit معمولی
         */
        score = temporaryScore
        enteredGame = temporaryEntered

        val result = VisitResult(
            previousScore = previousScore,
            newScore = score,
            darts = darts,
            total = darts.sumOf { it.score },
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

        history.removeAt(
            history.lastIndex
        )

        if (history.isEmpty()) {

            /*
             * برای Undo کامل،
             * امتیاز اولیه را از اولین وضعیت نگه می‌داریم.
             */
            return false
        }

        score =
            history.last().newScore

        enteredGame =
            history.any {
                !it.bust && it.total > 0
            }

        return true
    }

    fun getHistory(): List<VisitResult> {
        return history.toList()
    }

    fun reset(
        startingScore: Int
    ) {

        score = startingScore
        enteredGame = false
        history.clear()
    }
}