package underTheHood

import entities.Author
import entities.Book
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTextArea
import kotlin.concurrent.thread

object Display {

    private var infoArea = JTextArea().apply {
        isEditable = false
    }

    private val loadButton = JButton("Load Book").apply {
        addActionListener {
            loadData()
        }
    }
    private val timerLabel = JLabel("Time: 00:00")
    private val topPanel = JPanel(BorderLayout()).apply {
        add(timerLabel, BorderLayout.WEST)
        add(loadButton, BorderLayout.EAST)
    }

    private val mainFrame = JFrame("Book and Author info").apply {
        layout = BorderLayout()
        add(topPanel, BorderLayout.NORTH)
        add(JScrollPane(infoArea), BorderLayout.CENTER)
        size = Dimension(400, 300)
    }

    private fun loadData(step: Int = 0, data: Any? = null) {
        when (step) {
            0 -> {
                loadButton.isEnabled = false
                infoArea.text = "Loading book from information...\n"
                loadBook { loadData(1, it) }
            }
            1 -> {
                val book = data as Book
                infoArea.append("Book: ${book.title}\nYear: ${book.year}\nGenre: ${book.genre}\n")
                infoArea.append("Loading author information...\n")
                loadAuthor(book) { loadData(2, it) }
            }
            2 -> {
                val author = data as Author
                infoArea.append("Author: ${author.name}\nBiography: ${author.bio}\n")
                loadButton.isEnabled = true
            }
        }
    }

    fun show() {
        mainFrame.isVisible = true
        startTimer()
    }

    private fun loadBook(callback: (Book) -> Unit) {
        thread {
            Thread.sleep(3000)
            callback(Book("1984", 1949, "Dystopia"))
        }
    }

    private fun loadAuthor(book: Book, callback: (Author) -> Unit) {
        thread {
            Thread.sleep(3000)
            callback(Author("George Orwell", "British writer and journalist"))
        }
    }

    private fun startTimer() {
        thread {
            var totalSeconds = 0
            while (true) {
                val minutes = totalSeconds / 60
                val seconds = totalSeconds % 60
                timerLabel.text = String.format("Time: %02d:%02d", minutes, seconds)
                Thread.sleep(1000)
                totalSeconds++
            }
        }
    }
}