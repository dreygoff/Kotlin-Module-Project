import java.util.*
import kotlin.system.exitProcess

class Menu {
    private val menuItems = mutableListOf<MenuItem>()
    private val archiveList = mutableListOf<Archive>()
    private var menuState = MenuState.ARCHIVE
    private var selectedArchiveIndex = -1
    private var selectedNoteIndex = -1
    private val scanner = Scanner(System.`in`)
    private var screenFlag: Boolean = false

    private fun renderItems(list: List<File>, filetype: String) {
        menuItems.clear()
        menuItems.add(MenuItem("Создать ${filetype}"))
        menuItems.addAll(list.map { MenuItem(it.name) })
        menuItems.add(MenuItem("Выход"))

        menuItems.forEachIndexed { index, archiveMenuItem ->
            println("$index. ${archiveMenuItem.name}")
        }
    }

    private fun renderScreen() {
        when (menuState) {
            MenuState.ARCHIVE -> {
                selectedArchiveIndex = -1
                this.renderItems(archiveList, "архив")
            }
            MenuState.NOTES -> this.renderItems(archiveList[selectedArchiveIndex].notes, "заметку")
            MenuState.VIEW -> {
                if (selectedArchiveIndex >= 0 && selectedNoteIndex >= 0) {
                    println(archiveList[selectedArchiveIndex].notes[selectedNoteIndex])
                }
                println("Введите любой ввод для выхода")
                scanner.nextLine()
                menuState = MenuState.NOTES
                screenFlag = true
            }
            MenuState.CREATE -> {
                when {
                    selectedArchiveIndex < 0 -> {
                        var name: String = ""
                        while (true) {
                            println("Введите название архива")
                            name = scanner.nextLine().trim()
                            if (name.isEmpty()) {
                                println("Название архива не должно быть пустым")
                            } else {
                                break
                            }
                        }
                        archiveList.add(Archive(name))
                        menuState = MenuState.ARCHIVE
                        screenFlag = true
                    }
                    selectedArchiveIndex >= 0 -> {
                        var name: String = ""
                        while (true) {
                            println("Введите название заметки")
                            name = scanner.nextLine().trim()
                            if (name.isEmpty()) {
                                println("Название заметки не должно быть пустым")
                            } else {
                                break
                            }
                        }
                        var text: String = ""
                        while (true) {
                            println("Введите текст заметки")
                            text = scanner.nextLine().trim()
                            if (text.isEmpty()) {
                                println("Текст заметки не должен быть пустым")
                            } else {
                                break
                            }
                        }
                        archiveList[selectedArchiveIndex].notes.add(Note(name, text))
                        menuState = MenuState.NOTES
                        screenFlag = true
                    }
                }
            }
        }
    }

    private fun handleInput() {
        println("Введите команду(число)")
        val selectedIndex: Int? = scanner.nextLine().toIntOrNull()
        when {
            selectedIndex == null -> {
                println("Нужно ввести число")
            }
            selectedIndex > this.menuItems.lastIndex || selectedIndex < 0 -> {
                println("Команды с таким номером нет")
            }
            selectedIndex == 0 -> menuState = MenuState.CREATE
            selectedIndex < this.menuItems.lastIndex -> {
                when (menuState) {
                    MenuState.ARCHIVE -> {
                        selectedArchiveIndex = selectedIndex - 1
                        menuState = MenuState.NOTES
                    }
                    MenuState.NOTES -> {
                        selectedNoteIndex = selectedIndex - 1
                        menuState = MenuState.VIEW
                    }
                    else -> Unit
                }
            }
            selectedIndex == this.menuItems.lastIndex -> {
                when (menuState) {
                    MenuState.NOTES -> menuState = MenuState.ARCHIVE
                    else -> exitProcess(-1)
                }
            }
        }
    }

    fun start() {
        while (true) {
            screenFlag = false
            renderScreen()
            if (screenFlag) continue
            handleInput()
        }
    }
}