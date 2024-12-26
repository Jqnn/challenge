package de.jqnn.challenge.timer

class TimeConverter {

    companion object {
        fun convert(input: Int): String {
            var string = ""

            var duration = input
            var days = 0
            var hours = 0
            var minutes = 0
            var seconds = 0

            if (duration / 60 / 60 / 24 >= 1) {
                days = duration / 60 / 60 / 24
                duration -= ((duration / 60 / 60 / 24) * 60 * 60 * 24)
            }

            if (duration / 60 / 60 >= 1) {
                hours = duration / 60 / 60
                duration -= ((duration / 60 / 60) * 60 * 60)
            }

            if (duration / 60 >= 1) {
                minutes = duration / 60
                duration -= ((duration / 60) * 60)
            }

            if (duration >= 1) {
                seconds = duration
            }

            if (days >= 1) {
                string = if (days == 1) {
                    "$days Tag, "
                } else {
                    "$days Tage, "
                }
            }

            string = if (hours <= 9) {
                string + "0" + hours + ":"
            } else {
                "$string$hours:"
            }

            string = if (minutes <= 9) {
                string + "0" + minutes + ":"
            } else {
                "$string$minutes:"
            }

            string = if (seconds <= 9) {
                string + "0" + seconds
            } else {
                string + seconds
            }

            return string
        }
    }
}