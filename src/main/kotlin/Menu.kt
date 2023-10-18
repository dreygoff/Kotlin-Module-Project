class Menu {

    private val menuFormatter = "%d. %s"
    val menuItems = mutableListOf<MenuItem>()

    fun render(list: List<File>, filetype: String){
        menuItems.clear()
        menuItems.add(MenuItem("Создать ${filetype}"))
        menuItems.addAll(list.map { MenuItem(it.name) })
        menuItems.add(MenuItem("Выход"))

        menuItems.forEachIndexed { index, archiveMenuItem ->
            println(String.format(menuFormatter, index, archiveMenuItem.name))
        }
    }

}