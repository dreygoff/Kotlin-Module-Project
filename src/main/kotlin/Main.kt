import java.util.Scanner

fun main(args: Array<String>) {
   val archiveList = mutableListOf<Archive>()
   val menu = Menu()
   var menuState = MenuState.ARCHIVE
   var selectedArchiveIndex = -1
   var selectedNoteIndex = -1
   val scanner = Scanner(System.`in`)


   while(true) {
      when (menuState) {
         MenuState.ARCHIVE -> menu.render(archiveList, "архив")
         MenuState.NOTES -> menu.render(archiveList[selectedArchiveIndex].notes, "заметку")
         MenuState.VIEW -> {
            if (selectedArchiveIndex >= 0 && selectedNoteIndex >= 0) {
               println (archiveList[selectedArchiveIndex].notes[selectedNoteIndex])
            }
            println("Введите любой ввод для выхода")
            scanner.nextLine()
            menuState = MenuState.NOTES
            continue
         }
         MenuState.CREATE -> {
            when {
               selectedArchiveIndex < 0 -> {
                  var name: String = ""
                  while (true) {
                     println ("Введите название архива")
                     name = scanner.nextLine()
                     if (name == "") {
                        println("Название архива не должно быть пустым")
                     } else {
                        break
                     }
                  }
                  archiveList.add(Archive(name, mutableListOf()))
                  menuState = MenuState.ARCHIVE
                  continue
               }
               selectedArchiveIndex >= 0 -> {
                  var name: String = ""
                  while (true) {
                     println ("Введите название заметки")
                     name = scanner.nextLine()
                     if (name == "") {
                        println("Название заметки не должно быть пустым")
                     } else {
                        break
                     }
                  }
                  var text: String = ""
                  while (true) {
                     println ("Введите текст заметки")
                     text = scanner.nextLine()
                     if (text == "") {
                        println("Текст заметки не должен быть пустым")
                     } else {
                        break
                     }
                  }

                  archiveList[selectedArchiveIndex].notes.add(Note(name, text))
                  menuState = MenuState.NOTES
                  continue
               }
            }
         }
      }

      println("Введите команду(число)")
      val selectedIndex: Int? = scanner.nextLine().toIntOrNull()
      when {
         selectedIndex == null -> {
            println("Нужно ввести число")
         }
         selectedIndex == 0 -> menuState = MenuState.CREATE
         selectedIndex < menu.menuItems.lastIndex -> {
            when (menuState) {
               MenuState.ARCHIVE -> {
                  selectedArchiveIndex = selectedIndex - 1
                  menuState = MenuState.NOTES
               }
               MenuState.NOTES -> {
                  selectedNoteIndex = selectedIndex - 1
                  menuState = MenuState.VIEW
               }
               else -> {} //do nothing
            }

         }
         selectedIndex == menu.menuItems.lastIndex -> {
            when (menuState) {
               MenuState.NOTES -> menuState = MenuState.ARCHIVE
               else -> return
            }
         }
         selectedIndex > menu.menuItems.lastIndex -> {
            println("Команды с таким номером нет")
         }
      }
   }
}