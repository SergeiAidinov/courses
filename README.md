![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Debian](https://img.shields.io/badge/Debian-D70A53?style=for-the-badge&logo=debian&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)

Стартовая страница приложения открывается по адресу: http://localhost:8080/swagger-ui/index.html
1. В приложении имеются Контроллер и два Сервиса: DataService.java и ValidationService.java
2. DataService.java обрабатывает пользовательские запросы и хранит информацию об установленных курсах. 
ValidationService.java осуществляет валидацию пользовательского ввода. На данном этапе осуществляется валидация только по виду валюты: в случае выбора пользователем неподдерживаемого вида валюты выбрасывается исключение с соответствующим сообщением. 
Проверка правильности выбора пользователем вида валюты осуществляется с помощью регулярного выражения. 
Исключения, выброшенные в Контроллере, перехватываются тут же в @ExceptionHandler’е
3. В тестах проверяются методы как сервиса, так и контроллера. Для методов контроллера проверяется только выбрасывание исключения при указании неподдерживаемого типа валюты. 
4. Приложение запускалось на компьютере под управлением операционной системы Debian, процессор Intel Core i5 2400, ОЗУ 16ГБ. Работает неограниченно долгое время без сбоев и потери производительности.

